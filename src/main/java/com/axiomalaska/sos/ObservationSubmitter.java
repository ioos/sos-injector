package com.axiomalaska.sos;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.opengis.ows.x11.ExceptionReportDocument;
import net.opengis.sos.x20.GetObservationResponseDocument;

import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.axiomalaska.ioos.sos.exception.UnsupportedGeometryTypeException;
import com.axiomalaska.phenomena.Phenomenon;
import com.axiomalaska.sos.data.ObservationCollection;
import com.axiomalaska.sos.data.SosSensor;
import com.axiomalaska.sos.exception.InvalidObservationCollectionException;
import com.axiomalaska.sos.exception.ObservationRetrievalException;
import com.axiomalaska.sos.exception.SosCommunicationException;
import com.axiomalaska.sos.tools.HttpSender;
import com.axiomalaska.sos.tools.IdCreator;
import com.axiomalaska.sos.tools.ResponseInterpretter;
import com.axiomalaska.sos.tools.XmlHelper;
import com.axiomalaska.sos.xmlbuilder.GetNewestObservationBuilder;
import com.axiomalaska.sos.xmlbuilder.GetObservationBuilder;
import com.axiomalaska.sos.xmlbuilder.GetOldestObservationBuilder;
import com.axiomalaska.sos.xmlbuilder.InsertObservationBuilder;
import com.vividsolutions.jts.geom.Geometry;

/**
 * This class is used to push observations from a station and it's 
 * phenomena/sensor into the SOS
 * 
 * There are two ways to add observations to the SOS. 
 * 1.) Push an ObservationCollection
 * 2.) Pull observations from a ObservationRetriever
 * 
 * 1.) Call method insertObservations(ObservationCollection observationCollection) with a
 * prebuilt ObservationCollection. This will only add the observations that have not 
 * already been added to the SOS. 
 * 
 * 2.) Call update(SosSensor sensor, Phenomenon phenomenon, ObservationRetriever observationRetriever)
 * 
 * @author Lance Finfrock
 */
public class ObservationSubmitter implements IObservationSubmitter {
	// -------------------------------------------------------------------------
	// Private Data
	// -------------------------------------------------------------------------    
	private static final Logger LOGGER = LoggerFactory.getLogger(ObservationSubmitter.class);
	private final DecimalFormat threePlaceDecimalFormat = new DecimalFormat("#.###");
	private String sosPoxUrl;
	private String sosFeatureExistsUrl;
    private String authorizationToken;
    private final int MAX_OBS_COLLECTION_SIZE = 1000;
	
	// -------------------------------------------------------------------------
	// Public Members
	// -------------------------------------------------------------------------

	public ObservationSubmitter(String sosUrl, String authorizationToken) {
	    this.sosPoxUrl = sosUrl + SosInjectorConstants.POX_ENDPOINT;
	    this.sosFeatureExistsUrl = sosUrl + SosInjectorConstants.FEATURE_EXISTS_ENDPOINT;
	    this.authorizationToken = authorizationToken;	    
	}

	/* (non-Javadoc)
     * @see com.axiomalaska.sos.IObservationSubmitter#update(com.axiomalaska.sos.data.SosSensor, com.axiomalaska.phenomena.Phenomenon, com.axiomalaska.sos.ObservationRetriever)
     */
	@Override
    public void update(SosSensor sensor, Phenomenon phenomenon, ObservationRetriever observationRetriever)
	           throws InvalidObservationCollectionException, ObservationRetrievalException, SosCommunicationException,
	           UnsupportedGeometryTypeException{
		DateTime startDateForAllGeometries = getNewestObservationDateForAllGeometries(sensor, phenomenon);
		
		List<ObservationCollection> observationCollections = observationRetriever
				.getObservationCollection(sensor, phenomenon, startDateForAllGeometries);

		for(ObservationCollection observationCollection : observationCollections){
			DateTime startDate = getNewestObservationDate(sensor, phenomenon,
			        observationCollection.getGeometry());
			insertObservations(observationCollection, startDate);
		}
	}
	
	/**
	 * Insert observations from the observationCollection object to the SOS. 
	 * 
	 * @param observationCollection - contains the observations to be placed in the SOS
	 * @throws InvalidObservationCollectionException 
	 * @throws ObservationRetrievalException 
	 * @throws SosCommunicationException 
	 * @throws UnsupportedGeometryTypeException 
	 */
	public void insertObservations(ObservationCollection observationCollection) throws
	        InvalidObservationCollectionException, ObservationRetrievalException, SosCommunicationException,
	        UnsupportedGeometryTypeException{
		DateTime newestObservationInSosDate = getNewestObservationDate(
				observationCollection.getSensor(),
				observationCollection.getPhenomenon(), observationCollection.getGeometry());

		insertObservations(observationCollection, newestObservationInSosDate);
	}
	
	/**
	 * Insert observations from the observationCollection object in the SOS. 
	 * 
	 * @param observationCollection - contains the observations to be placed in the SOS 
	 * @throws InvalidObservationCollectionException 
	 * @throws ObservationRetrievalException 
	 * @throws SosCommunicationException 
	 * @throws UnsupportedGeometryTypeException 
	 */
	private void insertObservations(ObservationCollection observationCollection, DateTime startDate)
	        throws InvalidObservationCollectionException, ObservationRetrievalException, SosCommunicationException,
	        UnsupportedGeometryTypeException  {		
		DateTime oldestDate = getOldestObservationDate(observationCollection.getSensor(), 
				observationCollection.getPhenomenon(), observationCollection.getGeometry());
		
		insertObservations(observationCollection, startDate, oldestDate);
	}
	
	/**
	 * Insert observations from the observationCollection object in the SOS. 
	 * 
	 * @param observationCollection - contains the observations to be placed in the
	 * SOS. This object should have been validated before this method is called
	 * @param newestObservationInSosDate - the newest observation's date
	 * @throws InvalidObservationCollectionException 
	 * @throws IOException 
	 * @throws XmlException 
	 * @throws SosCommunicationException 
	 * @throws UnsupportedGeometryTypeException 
	 */
	private void insertObservations(ObservationCollection observationCollection, 
			DateTime newestObservationInSosDate, DateTime oldestObservationInSosDate)
			        throws InvalidObservationCollectionException, SosCommunicationException,
			        UnsupportedGeometryTypeException {
	    if (!observationCollection.isValid()) {
	        throw new InvalidObservationCollectionException(observationCollection);
	    }
		observationCollection.filterObservations(oldestObservationInSosDate, newestObservationInSosDate);		
		if (observationCollection.getObservationValues().size() > 0) {
            XmlObject xbResponse;		    
		    for (ObservationCollection splitObsCollection : splitObservationCollection(observationCollection)){
                LOGGER.info("Inserting " + splitObsCollection.toString());
                long startTime = System.currentTimeMillis();
	            try {
	                xbResponse = ResponseInterpretter.getXmlObject(
	                    HttpSender.sendPostMessage(sosPoxUrl, authorizationToken,
	                            new InsertObservationBuilder(splitObsCollection).build()));
	            } catch (XmlException e) {
	                throw new SosCommunicationException(e);
	            } catch (IOException e) {
	                throw new SosCommunicationException(e);
	            }
	            if (xbResponse == null || ResponseInterpretter.isError(xbResponse)) {
	                LOGGER.error("Error while inserting " + splitObsCollection.toString()
	                        + ":\n" + XmlHelper.xmlText(xbResponse));                   
	            } else {
	                long elapsedTime = System.currentTimeMillis() - startTime;
	                double elapsedSeconds = elapsedTime / 1000.0;
	                double rate = splitObsCollection.getObservationValues().size() / elapsedSeconds;
	                LOGGER.info("Inserted " + splitObsCollection.toString() + " in " +
	                        threePlaceDecimalFormat.format(elapsedSeconds) + " seconds (" +
	                        threePlaceDecimalFormat.format(rate) + " obs/s)");
	            }               		        
		    }
		}
	}
	
	private List<ObservationCollection> splitObservationCollection(ObservationCollection obsCollection) {
	    List<ObservationCollection> obsCollections = new ArrayList<ObservationCollection>();
	    
	    //if obs collection is under size limit, return it in a list
	    if( obsCollection.getObservationValues().size() <= MAX_OBS_COLLECTION_SIZE ){
	        obsCollections.add(obsCollection);
	        return obsCollections;
	    }
	    
        List<DateTime> times = new ArrayList<DateTime>(
                obsCollection.getObservationValues().keySet());
        Collections.sort(times);

        for (int i = 0; i < times.size(); i += MAX_OBS_COLLECTION_SIZE) {
            int timesInThisChunk = Math.min(MAX_OBS_COLLECTION_SIZE, times.size() - i);
            int endIndexPlusOne = i + timesInThisChunk;
            //don't subtract 1 from endIndexPlusOne because List.subList's toIndex arg is exclusive (it's ok to overshoot the real index here)
            List<DateTime> thisTimeRange = times.subList(i, endIndexPlusOne);
            DateTime firstTime = thisTimeRange.get(0);
            DateTime lastTime = thisTimeRange.get(thisTimeRange.size() - 1);            
            
            ObservationCollection thisChunk = new ObservationCollection();
            thisChunk.setGeometry(obsCollection.getGeometry());
            thisChunk.setPhenomenon(obsCollection.getPhenomenon());
            thisChunk.setSensor(obsCollection.getSensor());

            //set thisChunk to the correct subset of observation values
            thisChunk.setObservationValues(obsCollection.getObservationValues()
                    .subMap(firstTime, true, lastTime, true));
            obsCollections.add(thisChunk);
        }
	    return obsCollections;
	}
	
	private DateTime getNewestObservationDateForAllGeometries(SosSensor sensor,
	        Phenomenon phenomenon) throws ObservationRetrievalException, SosCommunicationException,
	        UnsupportedGeometryTypeException  {
       return getNewestObservationDate(sensor, phenomenon, null);
	}
	
	/**
	 * Get the newest observation date from the SOS server for the station and phenomenon. 
	 * 
	 * @param station - the station to look up the date from
	 * @param phenomenon - the phenomenon to look up the date from
	 * 
	 * @return returns a Calendar object of the newest observation in the SOS 
	 * from the station phenomenon passed in. If there are no observations in 
	 * the SOS it returns a date from the first century.
	 * @throws ObservationRetrievalException 
	 * @throws SosCommunicationException 
	 * @throws UnsupportedGeometryTypeException 
	 */
	private DateTime getNewestObservationDate(SosSensor sensor, Phenomenon phenomenon, Geometry geometry)
	        throws ObservationRetrievalException, SosCommunicationException, UnsupportedGeometryTypeException {
        DateTime dateTime = getObservationDateExtrema(sensor, phenomenon, geometry, DateExtremaType.NEWEST);
        return dateTime != null ? dateTime : SosInjectorConstants.DEFAULT_START_DATE;        
	}

	/**
	 * Get the oldest observation date from the SOS server for the station and phenomenon. 
	 * 
	 * @param station - the station to look up the date from
	 * @param phenomenon - the phenomenon to look up the date from
	 * 
	 * @return returns a Calendar object of the oldest observation in the SOS 
	 * from the station phenomenon passed in. If there are no observations in 
	 * the SOS it returns a date from the first century.
	 * @throws ObservationRetrievalException 
	 * @throws SosCommunicationException 
	 * @throws UnsupportedGeometryTypeException 
	 */
	private DateTime getOldestObservationDate(SosSensor sensor, Phenomenon phenomenon, Geometry geometry)
	        throws ObservationRetrievalException, SosCommunicationException, UnsupportedGeometryTypeException{
	    DateTime dateTime = getObservationDateExtrema(sensor, phenomenon, geometry, DateExtremaType.OLDEST);
	    return dateTime != null ? dateTime : SosInjectorConstants.DEFAULT_START_DATE; 
	}
	
    /**
     * Get the observation date extrema from the SOS server for the station and phenomenon. 
     * 
     * @param station - the station to look up the date from
     * @param phenomenon - the phenomenon to look up the date from
     * 
     * @return returns a Calendar object of the oldest observation in the SOS 
     * from the station phenomenon passed in. If there are no observations in 
     * the SOS it returns a date from the first century.
     * @throws ObservationRetrievalException 
     * @throws SosCommunicationException 
     * @throws UnsupportedGeometryTypeException 
     */
    private DateTime getObservationDateExtrema(SosSensor sensor, Phenomenon phenomenon, Geometry geometry,
            DateExtremaType type) throws ObservationRetrievalException, SosCommunicationException,
            UnsupportedGeometryTypeException {
        //try to check feature existence first with shortcut
        try {
            if (geometry != null && !ResponseInterpretter.getExists(HttpSender.sendGetMessage(sosFeatureExistsUrl + 
                    IdCreator.createObservationFeatureOfInterestId(sensor, geometry)))){
                //observation feature doesn't exist yet, return null for extrema time
                return null;
            }
        } catch (Exception e) {
            //NOOP, keep going and ignore foi error if it's not created yet
        }
        
        GetObservationBuilder builder = null;
        switch (type){
            case NEWEST:
                builder = new GetNewestObservationBuilder(sensor, phenomenon, geometry);
                break;
            case OLDEST:
                builder = new GetOldestObservationBuilder(sensor, phenomenon, geometry);
                break;
        }
        
        XmlObject xbResponse = null;
        try {
            xbResponse = ResponseInterpretter.getXmlObject(HttpSender.sendPostMessage(sosPoxUrl, authorizationToken,
                    builder.build()));
        } catch (XmlException e) {
            throw new SosCommunicationException(e);
        } catch (IOException e) {
            throw new SosCommunicationException(e);
        }   
        
        DateTime dateTime = null;        
        if (ResponseInterpretter.isError(xbResponse) || !(xbResponse instanceof GetObservationResponseDocument)) {
            //ignore foi errors, since the foi won't be valid until the first result is inserted
            if( !ResponseInterpretter.onlyExceptionContains((ExceptionReportDocument) xbResponse,
                    "of the parameter 'featureOfInterest' is invalid")) {
                throw new ObservationRetrievalException(sensor, phenomenon, geometry, type, xbResponse.toString());
            }
        } else {
            dateTime = ResponseInterpretter.parseMaxDateFromGetObservationResponse(
                    (GetObservationResponseDocument) xbResponse);            
        }

        if (dateTime == null) {
            LOGGER.debug("No observations found in SOS for Sensor: "
                    + sensor.getId() + " phenomonon: "
                    + phenomenon.getId());                  
        }
        return dateTime;        
    }	
}
