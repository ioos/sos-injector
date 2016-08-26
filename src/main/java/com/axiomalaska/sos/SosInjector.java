package com.axiomalaska.sos;

import java.util.List;

import org.apache.commons.validator.routines.RegexValidator;
import org.apache.commons.validator.routines.UrlValidator;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.axiomalaska.ioos.sos.exception.UnsupportedGeometryTypeException;
import com.axiomalaska.phenomena.Phenomenon;
import com.axiomalaska.sos.data.AbstractSosAsset;
import com.axiomalaska.sos.data.PublisherInfo;
import com.axiomalaska.sos.data.SosSensor;
import com.axiomalaska.sos.data.SosStation;
import com.axiomalaska.sos.exception.InvalidObservationCollectionException;
import com.axiomalaska.sos.exception.ObservationRetrievalException;
import com.axiomalaska.sos.exception.SosCommunicationException;
import com.axiomalaska.sos.exception.SosInjectorConfigurationException;
import com.axiomalaska.sos.exception.SosUpdateException;
import com.axiomalaska.sos.exception.StationCreationException;

/**
 * This class is used to update the SOS server
 * 
 * @author lance Finfrock
 */
public class SosInjector {

	// -------------------------------------------------------------------------
	// Private Data
	// -------------------------------------------------------------------------
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SosInjector.class);
    private String name;
	private StationRetriever stationRetriever;
	private ObservationRetriever observationRetriever;
	private ISOFileWriter isoWriter;
    private IProcedureSubmitter procedureSubmitter;	
	private IObservationSubmitter observationSubmitter;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------

    public SosInjector(
            String name,
            String sosUrl,
            PublisherInfo publisherInfo,
            StationRetriever stationRetriever,            
            ObservationRetriever observationRetriever,
            ISOFileWriter fileWriter) throws SosInjectorConfigurationException {
        this(name, sosUrl, null, publisherInfo, stationRetriever, observationRetriever, fileWriter);
    }

    public SosInjector(
            String name,
            String sosUrl,
            String authorizationToken,
            PublisherInfo publisherInfo,
            StationRetriever stationRetriever,
            ObservationRetriever observationRetriever,
            ISOFileWriter fileWriter) throws SosInjectorConfigurationException {
        if (name == null || name.isEmpty()){
            throw new SosInjectorConfigurationException("name cannot be null or empty");
        }
        if (sosUrl == null || sosUrl.isEmpty()){
            throw new SosInjectorConfigurationException("sosUrl cannot be null or empty");
        }
        if (!(new UrlValidator(new RegexValidator(".*"),0).isValid(sosUrl))){
            throw new SosInjectorConfigurationException("sosUrl must be valid");
        }
        if (publisherInfo == null){
            throw new SosInjectorConfigurationException("publisherInfo cannot be null");
        }
        if (stationRetriever == null){
            throw new SosInjectorConfigurationException("stationRetriever cannot be null");
        }
        if (observationRetriever == null){
            throw new SosInjectorConfigurationException("observationRetriever cannot be null");
        }

        this.name = name;
        this.stationRetriever = stationRetriever;
        this.observationRetriever = observationRetriever;
        this.isoWriter = fileWriter;
        this.procedureSubmitter = new ProcedureSubmitter(sosUrl, authorizationToken, publisherInfo);
        this.observationSubmitter = new ObservationSubmitter(sosUrl, authorizationToken);
        
    }

    /**
     * Constructor with submitters (useful for mocking)
     * 
     * @param name
     * @param stationRetriever
     * @param observationRetriever
     * @param procedureSubmitter
     * @param observationSubmitter
     */
    public SosInjector(
            String name,
            StationRetriever stationRetriever,
            ObservationRetriever observationRetriever,
            IProcedureSubmitter procedureSubmitter,
            IObservationSubmitter observationSubmitter) {
        this.name = name;
        this.stationRetriever = stationRetriever;
        this.observationRetriever = observationRetriever;
        this.procedureSubmitter = procedureSubmitter;
        this.observationSubmitter = observationSubmitter;
    }

    /**
     * Create a mock SOS injector that can be used for testing. On update,
     * station information will be retrieved from the StationRetriever. If the 
     * retrieveObservations flag is true, observations since 1970-01-01 UTC
     * will be retrieved from the ObservationRetriever. The queried information
     * will not be submitted to any target SOS.
     * 
     * @param name Name of the mock SOS injector
     * @param stationRetriever Implementation of StationRetriever
     * @param observationRetriever Implementation of ObservationRetriever
     * @param retrieveObservations Whether to retrieve observations or not
     *      (can take a long time for large data sources)       
     * @return SosInjector
     */
    public static SosInjector mock(String name, StationRetriever stationRetriever,
            ObservationRetriever observationRetriever, final boolean retrieveObservations) {
        return mock(name, stationRetriever, observationRetriever, retrieveObservations,
                SosInjectorConstants.DEFAULT_START_DATE);
    }
    
    /**
     * Create a mock SOS injector that can be used for testing. On update,
     * station information will be retrieved from the StationRetriever. If the 
     * retrieveObservations flag is true, observations since the provided start date
     * will be retrieved from the ObservationRetriever. The queried information
     * will not be submitted to any target SOS.
     * 
     * @param name Name of the mock SOS injector
     * @param stationRetriever Implementation of StationRetriever
     * @param observationRetriever Implementation of ObservationRetriever
     * @param retrieveObservations Whether to retrieve observations or not
     *      (can take a long time for large data sources)
     * @param startDate Start date to retrieve observations       
     * @return SosInjector
     */
    public static SosInjector mock(String name, StationRetriever stationRetriever,
            ObservationRetriever observationRetriever, final boolean retrieveObservations,
            final DateTime startDate) {
        return new SosInjector(name, stationRetriever, observationRetriever,
            new IProcedureSubmitter() {
                @Override
                public boolean checkProcedureWithSos(AbstractSosAsset asset) {
                    // NOOP (mock)
                    return true;
                }
            },
            new IObservationSubmitter() {
                @Override
                public void update(SosSensor sensor, Phenomenon phenomenon,
                        ObservationRetriever observationRetriever)
                        throws InvalidObservationCollectionException,
                        ObservationRetrievalException,
                        SosCommunicationException,
                        UnsupportedGeometryTypeException {
                    if (retrieveObservations) {
                        observationRetriever.getObservationCollection(sensor, phenomenon, startDate);
                    }
                }
            });
        
    }
    
	// -------------------------------------------------------------------------
	// Public Members
	// -------------------------------------------------------------------------
	
	public String getName(){
		return name;
	}

    public String getSource() {
        try {
            return stationRetriever.getStations().get(0).getSource().getId();
        } catch (Exception ex) {
            LOGGER.error("Error getting station source", ex);
            return "source";
        }
    }

	/**
	 * Update the SOS with new observations
	 * @throws SosUpdateException  
	 */
	public void update() throws SosUpdateException {
	    LOGGER.info("Updating " + name);
	    List<SosStation> stations;
        try {
            stations = stationRetriever.getStations();
        } catch (StationCreationException e) {
            throw new SosUpdateException(e);
        }
	    int stationsSize = stations.size();
	    int stationCounter = 0;
        for (SosStation station : stations) {
            LOGGER.info("Harvesting station {} of {}.", ++stationCounter, stationsSize);
            if (station.getSensors().isEmpty()) {
                LOGGER.info("Station " + station.getId() + " does not have any sensors and will not be added.");
                continue;
            }
            for (SosSensor sensor : station.getSensors()) {
                if(sensor.getPhenomena().isEmpty()){
                    LOGGER.info("Sensor " + sensor.getId() + " does not have any phenomena and will not be added.");
                    continue;
                }
                try {
                    if (procedureSubmitter.checkProcedureWithSos(sensor)) {
                        for(Phenomenon phenomenon : sensor.getPhenomena()){
                            observationSubmitter.update(sensor, phenomenon, observationRetriever);
                        }
                    }
                } catch (SosCommunicationException | InvalidObservationCollectionException
                        | ObservationRetrievalException | UnsupportedGeometryTypeException e) {
                    throw new SosUpdateException(e);
                }
            }
        }

        if (isoWriter != null) {
            try {
                for (SosStation station : stationRetriever.getStations()) {
                    isoWriter.writeISOFileForStation(station);
                }
            } catch (StationCreationException e) {
                throw new SosUpdateException(e);
            }
        }
	}
}
