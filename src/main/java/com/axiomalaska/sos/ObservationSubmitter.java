package com.axiomalaska.sos;

import java.io.ByteArrayInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.n52.sos.ioos.asset.NetworkAsset;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.axiomalaska.phenomena.Phenomenon;
import com.axiomalaska.sos.data.ObservationCollection;
import com.axiomalaska.sos.data.PublisherInfo;
import com.axiomalaska.sos.data.SosNetwork;
import com.axiomalaska.sos.data.SosSensor;
import com.axiomalaska.sos.data.SosStation;
import com.axiomalaska.sos.tools.HttpSender;
import com.axiomalaska.sos.xmlbuilder.DescribeSensorBuilder;
import com.axiomalaska.sos.xmlbuilder.GetNewestObservationBuilder;
import com.axiomalaska.sos.xmlbuilder.GetOldestObservationBuilder;
import com.axiomalaska.sos.xmlbuilder.InsertObservationBuilder;
import com.axiomalaska.sos.xmlbuilder.SensorRegisterSensorBuilder;
import com.axiomalaska.sos.xmlbuilder.SosXmlBuilder;
import com.axiomalaska.sos.xmlbuilder.StationRegisterSensorBuilder;

/**
 * This class is used to push observations from a station and it's 
 * phenomena/sensor into the SOS
 * 
 * There are two ways to add observations to the SOS. 
 * 1.) Push a ObservationCollection
 * 2.) Pull observations from a ObservationRetriever
 * 
 * 1.) Call method update(ObservationCollection observationCollection) with a
 * prebuilt ObservationCollection. This will only add the observations that have not 
 * already been added to the SOS. 
 * 
 * 2.) Methods
 * update(List<Station> stations, ObservationRetriever observationRetriever)
 * update(Station station, ObservationRetriever observationRetriever)
 * updateObservations(Station station, Phenomenon phenomenon, ObservationRetriever observationRetriever)
 * Methods take a one or more stations with option of a phenomenon and a ObservationRetriever. 
 * All the combinations or the stations and phenomena are used to pull 
 * observations from the ObservationRetriever object. ObservationRetriever 
 * represents an interface to pull observations from a database or some data store. 
 *		
 * Create the Object and pass in the URL to your SOS server Then call update
 * method with a station or a list of station to be used to update the SOS with.
 * 
 * @author Lance Finfrock
 */
public class ObservationSubmitter {
	// -------------------------------------------------------------------------
	// Private Data
	// -------------------------------------------------------------------------
	private final SimpleDateFormat parseDate = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss.sss'Z'");
	private String sosUrl;
	private HttpSender httpSender = new HttpSender();
	private Logger logger;
	private NetworkSubmitter networkSubmitter = null;

	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------

	/**
	 * Default constructor
	 * 
	 * Creates a default logger
	 * @param sosUrl - the URL to the SOS server
	 */
	public ObservationSubmitter(String sosUrl) {
		this(sosUrl, Logger.getRootLogger());
	}

	/**
	 * ObservationSubmitter built with a custom logger
	 * 
	 * @param sosUrl - the URL to the SOS server
	 * @param logger - custom logger. Used to log information from the update process
	 */
	public ObservationSubmitter(String sosUrl, Logger logger) {
		this.sosUrl = sosUrl;
		this.logger = logger;
		this.networkSubmitter = new NetworkSubmitter(sosUrl, logger);
	}

	// -------------------------------------------------------------------------
	// Public Members
	// -------------------------------------------------------------------------

	/**
	 * Push: Update the SOS with the given observationCollection
	 * 
	 * @param observationCollection - the data used to update the SOS
	 */
	public void update(SosNetwork rootNetwork, 
			ObservationCollection observationCollection,
			PublisherInfo publisherInfo) throws Exception {
		if (isObservationCollectionValid(observationCollection)) {
			SosStation station = observationCollection.getStation();
			boolean isStationCreated = isStationCreated(station);
			if (!isStationCreated) {
				isStationCreated = createNewSosStation(station, publisherInfo);
			}

			if (isStationCreated) {
				SosSensor sensor = observationCollection.getSensor();
				boolean isSensorCreated = isSensorCreated(station, sensor);
				if (!isSensorCreated) {
					isSensorCreated = createNewSosSensor(sensor, publisherInfo);
				}
				if (isSensorCreated) {
					insertObservations(rootNetwork, observationCollection);
				}
			}
		}
	}
	
	/**
	 * Pull: Update the SOS with observations pulled from the observationRetriever with
	 * the each station and its phenomena
	 * 
	 * This code first checks if a station is already created in the 
	 * SOS with a DescribeSensor call. Then if the station is not in the 
	 * SOS it is created with a RegisterSensor call to the SOS. Then a request
	 * for the newest observations is called on the ObservationRetriever object.
	 * 
	 * @param stations - a list of stations used to pull observations
	 * @param observationRetriever - the data store of observations used to 
	 * pull observations from
	 */
	public void update(SosNetwork network, List<SosStation> stations, 
			ObservationRetriever observationRetriever, 
			PublisherInfo publisherInfo) throws Exception {
            // check for existence of network-all, add it if not
	        SosNetwork networkAll = createNetworkAll();
            if (!networkSubmitter.checkNetworkWithSos(networkAll, publisherInfo, true)) {
                logger.info("Error creating network " + networkAll.getId());
            }
            // update the stations
            for (int i=0; i<stations.size(); i++) {
                SosStation station = stations.get(i);
                // update the station
                update(network, station, observationRetriever, publisherInfo);
            }
	}

	/**
	 * Pull: Update the SOS with observations pulled from the observationRetriever 
	 * with the passed in station with all its phenomena
	 * 
	 * This code first checks if a station is already created in the 
	 * SOS with a DescribeSensor call. Then if the station is not in the 
	 * SOS it is created with a RegisterSensor call to the SOS. Then a request
	 * for the newest observations is called on the ObservationRetriever object.
	 * 
	 * @param station - the station to be used to pull observations
	 * @param observationRetriever -  the data store of observations used to 
	 * pull observations from
	 */
	public void update(SosNetwork network, SosStation station,  
			ObservationRetriever observationRetriever, PublisherInfo publisherInfo) throws Exception {
		if (station.getSensors().size() > 0) {
			boolean isStationCreated = isStationCreated(station);
			if (!isStationCreated) {
				isStationCreated = createNewSosStation(station, publisherInfo);
			}

			if (isStationCreated) {
				for (SosSensor sensor : station.getSensors()) {
					update(network, station, sensor, observationRetriever, publisherInfo);
				}
			}
		}
		else{
			logger.info("Station " + station.getId() +  
					" does not have any sensors. The station will not be added.");
		}
	}
	
	// -------------------------------------------------------------------------
	// Private Members
	// -------------------------------------------------------------------------
	
	/**
	 * Pull: Update the observations of a station with a specific phenomenon in the SOS server.
	 * 
	 * @param station - the station to pull observations 
	 *  Requirement: The station is already created
	 * @param sensor - the sensor to pull observations 
	 * @param observationRetriever - the data store of observations used to 
	 * pull observations from
	 */
	private void update(SosNetwork network, SosStation station, SosSensor sensor,  
			ObservationRetriever observationRetriever, PublisherInfo publisherInfo)
			throws Exception {
		if(sensor.getPhenomena().size() > 0){
                    
			boolean isSensorCreated = isSensorCreated(station, sensor);
			
			if (!isSensorCreated) {
                logger.info("Creating sensor: " + sensor.getId());
                for (Phenomenon phem : sensor.getPhenomena()) {
                    Calendar startDateForAllDepths = getNewestObservationDateForAllDepths(network, station, sensor, phem);
                    for (ObservationCollection oc : observationRetriever.getObservationCollection(station, sensor, phem, startDateForAllDepths)) {
                        isSensorCreated = createNewSosSensor(sensor, publisherInfo, oc.getHeight());
                    }
                }
			}

			if (isSensorCreated) {
				for(Phenomenon phenomenon : sensor.getPhenomena()){
					update(network, station, sensor, phenomenon, observationRetriever);
				}
			}
		}
		else{
			logger.info("Station " + station.getId() +  
					" does not have any phenomena. The station will not be added.");
		}
	}
	
	/**
	 * Pull: Update the observations of a station with a specific phenomenon in the SOS server.
	 * 
	 * @param station - the station to pull observations. 
	 *  Requirement: The station is already created
	 * @param sensor - the sensor to pull observations from. 
	 *  Requirement: the sensor is already created
	 * @param phenomenon - the phenomenon to pull observations 
	 * @param observationRetriever - the data store of observations used to 
	 * pull observations from
	 */
	private void update(SosNetwork network, SosStation station, SosSensor sensor, 
			Phenomenon phenomenon, ObservationRetriever observationRetriever) throws Exception {
		Calendar startDateForAllHeights = getNewestObservationDateForAllDepths(network, station, sensor, phenomenon);
		
		List<ObservationCollection> observationCollections = observationRetriever
				.getObservationCollection(station, sensor, phenomenon,
						startDateForAllHeights);

		for(ObservationCollection observationCollection : observationCollections){
			Calendar startDate = getNewestObservationDate(network, station, sensor, 
					phenomenon, observationCollection.getHeight());
			insertObservations(network, observationCollection, startDate);
		}
	}
	
	/**
	 * Insert observations from the observationCollection object in the SOS. 
	 * 
	 * @param observationCollection - contains the observations to be placed in the
	 * SOS. This object should have been validated before this method is called
	 */
	private void insertObservations(SosNetwork network, ObservationCollection observationCollection)
			throws Exception {
		Calendar newestObservationInSosDate = getNewestObservationDate(network,
				observationCollection.getStation(),
				observationCollection.getSensor(),
				observationCollection.getPhenomenon(), observationCollection.getHeight());

		insertObservations(network, observationCollection, newestObservationInSosDate);
	}
	
	/**
	 * Insert observations from the observationCollection object in the SOS. 
	 * 
	 * @param observationCollection - contains the observations to be placed in the
	 * SOS. This object should have been validated before this method is called
	 */
	private void insertObservations(SosNetwork network, ObservationCollection observationCollection, 
			Calendar startDate) 
			throws Exception {
		
		if (isObservationCollectionValid(observationCollection)) {
			Calendar oldestDate = getOldestObservationDate(network,
					observationCollection.getStation(), observationCollection.getSensor(), 
					observationCollection.getPhenomenon(), observationCollection.getHeight());
			
			insertObservations(observationCollection, 
					startDate, oldestDate);
		}
	}

	/**
	 * Insert observations from the observationCollection object in the SOS. 
	 * 
	 * @param observationCollection - contains the observations to be placed in the
	 * SOS. This object should have been validated before this method is called
	 * @param newestObservationInSosDate - the newest observation's date
	 */
	private void insertObservations(ObservationCollection observationCollection, 
			Calendar newestObservationInSosDate, 
			Calendar oldestObservationInSosDate) throws Exception {
		SosStation station = observationCollection.getStation();
		SosSensor sensor = observationCollection.getSensor();
		Phenomenon phenomenon = observationCollection.getPhenomenon();
		Double height = observationCollection.getHeight();
                
		ObservationCollection filteredObservationCollection = removeEnteredObservations(
				newestObservationInSosDate, oldestObservationInSosDate, 
				observationCollection);
		
		if (filteredObservationCollection.getObservationDates().size() > 0) {
			try {
				InsertObservationBuilder insertObservationBuilder = 
						new InsertObservationBuilder(
						station, sensor, phenomenon,
						filteredObservationCollection, height);

				String insertXml = insertObservationBuilder.build();

				String response = httpSender.sendPostMessage(sosUrl, insertXml);

				if (response == null || response.toLowerCase().contains("exception")) {
					logger.error("Trying to input "
							+ filteredObservationCollection.getObservationDates().size()
							+ " observations from sensor: "+ sensor.getId()
							+ " phenomenon: " + phenomenon.getId() + " from: "
							+ " response: \n" + response);
				} else {
					logger.info("Inputed "
							+ filteredObservationCollection.getObservationDates()
									.size() + " observations from sensor: "
							+ sensor.getId() + " phenomenon: " + phenomenon.getId());
				}
			} catch (Exception e) {
				logger.error("Trying to input "
						+ filteredObservationCollection.getObservationDates().size()
						+ " observations from sensor: " + sensor.getId()
						+ " phenomenon: " + phenomenon.getId() + " message: \n"
						+ e.getMessage());
			}
		}
	}

	/**
	 * Remove observations that are between the newest and oldest observations. 
	 * @param newestObservationInSosDate - the newest observation's date
	 * @param oldestObservationInSosDate - the oldest observation's date
	 * @param observationCollection - the observationCollection to remove the
	 * older observations from.
	 * @return
	 */
	private ObservationCollection removeEnteredObservations(
			Calendar newestObservationInSosDate, 
			Calendar oldestObservationInSosDate,
			ObservationCollection observationCollection) {
		
		ObservationCollection filteredObservationCollection = new ObservationCollection();
		
		filteredObservationCollection.setStation(observationCollection.getStation());
		filteredObservationCollection.setSensor(observationCollection.getSensor());
		filteredObservationCollection.setPhenomenon(observationCollection.getPhenomenon());
		
		List<Calendar> observationDates = observationCollection.getObservationDates();
		List<Double> observationValues = observationCollection.getObservationValues();
		
		List<Calendar> filteredObservationDates = new ArrayList<Calendar>();
		List<Double> filteredObservationValues = new ArrayList<Double>();
		for(int index = 0; index < observationDates.size(); index++){
			if(observationDates.get(index).after(newestObservationInSosDate) || 
					observationDates.get(index).before(oldestObservationInSosDate)){
				filteredObservationDates.add(observationDates.get(index));
				filteredObservationValues.add(observationValues.get(index));
			}
		}
		
		filteredObservationCollection.setObservationDates(filteredObservationDates);
		filteredObservationCollection.setObservationValues(filteredObservationValues);
		
		return filteredObservationCollection;
	}
	
	/**
	 * A method to validate the ObservationCollection object. 
	 * 
	 * @return [True] if the ObservationCollection object is valid. [False] if the
	 * ObservationCollection is not valid
	 */
	private boolean isObservationCollectionValid(
			ObservationCollection observationCollection){
		if (observationCollection == null){
			return false;
		}
		
		if(observationCollection.getSensor() == null){
			logger.info("Sensor was null in ObservationCollection");
			
			return false;
		}
		
		if(observationCollection.getPhenomenon() == null){
			logger.info("Phenomenon was null in ObservationCollection");
			
			return false;
		}
		
		if(observationCollection.getStation() == null){
			logger.info("Station was null in ObservationCollection");
			
			return false;
		}
		
		SosStation station = observationCollection.getStation();
		Phenomenon phenomenon = observationCollection.getPhenomenon();
		SosSensor sensor = observationCollection.getSensor();
		
	    if (observationCollection.getObservationDates().size() != 
	    		observationCollection.getObservationValues().size()){
			logger.info("The observationCollection's size of the dates list is " 
	    		    + "not equal to the values list "
					+ " for station: " + station.getId()
					+ " sensor: " + sensor.getId() 
					+ " phenomenon: " + phenomenon.getId());
	    	return false;
	    }
	    if(observationCollection.getObservationDates().size() == 0
				&& observationCollection.getObservationValues().size() == 0){
			logger.info("No values from source "
					+ " for station: " + station.getId()
					+ " sensor: " + sensor.getId() 
					+ " phenomenon: " + phenomenon.getId());
			return false;
		}
		
		return true;
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
	 */
	private Calendar getNewestObservationDate(SosNetwork network, SosStation station,
			SosSensor sensor, Phenomenon phenomenon, Double depth) throws Exception {
		GetNewestObservationBuilder getObservationNewestBuilder = 
				new GetNewestObservationBuilder(station, sensor, 
						phenomenon, network, depth);

		Calendar date = extractDateFromResponse(getObservationNewestBuilder);
		
		if(date != null) {
			date.add(Calendar.MINUTE, 1);
			return date;
		}
		else {
			logger.debug("No observations found in SOS for Sensor: "
					+ sensor.getId() + " phenomonon: "
					+ phenomenon.getId());
			Calendar defaultDate = Calendar.getInstance();

			defaultDate.set(1970, Calendar.JANUARY, 1);

			defaultDate.getTime();

			return defaultDate;
		}
	}
	
	/**
	 * Get the newest observation date from the SOS server for the station 
	 * and phenomenon for all depths. 
	 * 
	 * @param station - the station to look up the date from
	 * @param phenomenon - the phenomenon to look up the date from
	 * 
	 * @return returns a Calendar object of the newest observation in the SOS 
	 * from the station phenomenon passed in. If there are no observations in 
	 * the SOS it returns a date from the first century.
	 */
	private Calendar getNewestObservationDateForAllDepths(SosNetwork network, SosStation station,
			SosSensor sensor, Phenomenon phenomenon) throws Exception {
		GetNewestObservationBuilder getObservationNewestBuilder = 
				new GetNewestObservationBuilder(station, sensor, 
						phenomenon, network);

		Calendar date = extractDateFromResponse(getObservationNewestBuilder);
		
		if(date != null) {
			date.add(Calendar.MINUTE, 1);
			return date;
		}
		else {
			logger.debug("No observations found in SOS for Sensor: "
					+ sensor.getId() + " phenomonon: "
					+ phenomenon.getId());
			Calendar defaultDate = Calendar.getInstance();

			defaultDate.set(1970, Calendar.JANUARY, 1);

			defaultDate.getTime();

			return defaultDate;
		}
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
	 */
	private Calendar getOldestObservationDate(SosNetwork network, 
			SosStation station,	SosSensor sensor, 
			Phenomenon phenomenon, Double depth) throws Exception {
		GetOldestObservationBuilder getOldestObservationBuilder = 
				new GetOldestObservationBuilder(station, sensor, 
						phenomenon, network, depth);

		Calendar date = extractDateFromResponse(getOldestObservationBuilder);
		
		if(date != null) {
			date.add(Calendar.MINUTE, -1);
			return date;
		}
		else {
			logger.debug("No observations found in SOS for Sensor: "
					+ sensor.getId() + " phenomonon: "
					+ phenomenon.getId());
			Calendar defaultDate = Calendar.getInstance();

			defaultDate.add(Calendar.YEAR, 1);

			defaultDate.getTime();

			return defaultDate;
		}
	}

	private Calendar extractDateFromResponse(SosXmlBuilder sosXmlBuilder)
			throws Exception {
		String getObservationXml = sosXmlBuilder.build();

		String response = httpSender.sendPostMessage(sosUrl, getObservationXml);
                
		if (response != null & !response.contains("HTTP Status 404")) {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(new ByteArrayInputStream(response
					.getBytes()));

			doc.normalize();

			/*
			 * <om:samplingTime> <gml:TimeInstant
			 * xsi:type="gml:TimeInstantType">
			 * <gml:timePosition>2012-05-01T07:00:00.000Z</gml:timePosition>
			 * </gml:TimeInstant> </om:samplingTime>
			 */
			NodeList nodeList = doc.getElementsByTagName("gml:beginPosition");

			if (nodeList.getLength() == 1) {

				Element timePosition = (Element) nodeList.item(0);

				Calendar date = createDate(timePosition.getTextContent());

				return date;
			}
		}

		return null;
	}
	
	/**
	 * Create a Calendar object from a string
	 * @param dayRawText - the string to convert into a Calendar object
	 */
	@SuppressWarnings("deprecation")
	private Calendar createDate(String dateString) throws ParseException {
		Date date = parseDate.parse(dateString);
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		calendar.set(Calendar.YEAR, date.getYear() + 1900);
		calendar.set(Calendar.MONTH, date.getMonth());
		calendar.set(Calendar.DAY_OF_MONTH, date.getDate());
		calendar.set(Calendar.HOUR_OF_DAY, date.getHours());
		calendar.set(Calendar.MINUTE, date.getMinutes());
		calendar.set(Calendar.SECOND, date.getSeconds());

		// The time is not able to be changed from the
		// setTimezone if this is not set. Java Error
		calendar.getTime();

		return calendar;
	}
	
	/**
	 * Create a new station in the SOS
	 * 
	 * @param station - the station to be created
	 * @return [true] if the station was create successfully [false] if else. 
	 */
	private boolean createNewSosStation(SosStation station, 
			PublisherInfo publisherInfo) throws Exception {
		logger.info("Creating station: " + station.getId());

		for(SosNetwork network : station.getNetworks()){
			if(!networkSubmitter.checkNetworkWithSos(network, publisherInfo)){
				logger.info("Error creating network " + network.getId());
			}
		}
		
		StationRegisterSensorBuilder registerSensorBuilder = 
				new StationRegisterSensorBuilder(station, publisherInfo);

		String xml = registerSensorBuilder.build();
                
		String response = httpSender.sendPostMessage(sosUrl, xml);

		if (response == null || response.toLowerCase().contains("exception")) {
			logger.error("station: " + station.getId() + " = " + response);
			return false;
		} else {
			logger.info("Finished creating station: " + station.getId());
			return true;
		}
	}
        
        private boolean createNewSosSensor(SosSensor sensor, PublisherInfo publisherInfo) throws Exception {
            return createNewSosSensor(sensor, publisherInfo, Double.NaN);
        }
	
	private boolean createNewSosSensor(SosSensor sensor, PublisherInfo publisherInfo, Double depth) throws Exception {
		logger.info("Creating sensor: " + sensor.getId());

		for(SosNetwork network : sensor.getNetworks()){
			if(!networkSubmitter.checkNetworkWithSos(network, publisherInfo)){
				logger.info("Error creating network " + network.getId());
			}
		}

		SensorRegisterSensorBuilder registerSensorBuilder = new SensorRegisterSensorBuilder(sensor, depth);

		String xml = registerSensorBuilder.build();
                
		String response = httpSender.sendPostMessage(sosUrl, xml);

		if (response == null || response.toLowerCase().contains("exception")) {
		    logger.error("error in response to: " + sosUrl + "\n" + xml);
		    logger.error("sensor: " + sensor.getId() + " = " + response); 
			return false;
		} else {
			logger.info("Finished creating sensor: " + sensor.getId());
			return true;
		}
	}
	
	/**
	 * Create a new station in the SOS
	 * 
	 * @param station
	 *            - the station to be created
	 * @return - [true] if the station was create successfully [false] if else. 
	 */
//	private boolean createNewSosNetwork(SosNetwork network) throws Exception {
//		logger.info("Creating network: " + idCreator.createNetworkId(network));
//                return false;
//
//		NetworkRegisterSensorBuilder registerSensorBuilder = new NetworkRegisterSensorBuilder(
//				network, idCreator);
//
//		String xml = registerSensorBuilder.build();
//
//		String response = httpSender.sendPostMessage(sosUrl, xml);
//
//		if (response == null || response.toLowerCase().contains("exception")) {
//			logger.error("network: " + idCreator.createNetworkId(network) + " = " + response);
//			return false;
//		} else {
//			logger.info("Finished creating network: " + idCreator.createNetworkId(network));
//			return true;
//		}
//	}
	
	private Boolean isNetworkCreated(SosNetwork network) throws Exception {
        logger.info("Checking network: " + network.getId());
        return isProcedureCreated(network.getId());
	}
	
	private Boolean isSensorCreated(SosStation station, SosSensor sensor) throws Exception {
	    logger.info("Checking sensor: " + sensor.getId());
	    return isProcedureCreated(sensor.getId());
	}

	/**
	 * Check if the station is already created in the SOS
	 * 
	 * @param station
	 *            - the station to be checked
	 * @return [true] if the station needs to be created. [false] if the station
	 *         does not need to be created
	 */
	private Boolean isStationCreated(SosStation station) throws Exception {
        logger.info("Checking station: " + station.getId());
        return isProcedureCreated(station.getId());
	}

    private Boolean isProcedureCreated(String procedureId) throws Exception {
        DescribeSensorBuilder describeSensorBuilder = new DescribeSensorBuilder(procedureId);
        String text = describeSensorBuilder.build();
        String output = httpSender.sendPostMessage(sosUrl, text);
        return output != null && !output.toLowerCase().contains("exception");
    }
	
	
    /**
     * Creates the network-all network which will contain all procedures
     * @return SosNetwork containing info for network all
     */
    private SosNetwork createNetworkAll() {
        SosNetwork netall = new SosNetwork();
        netall.setAsset(new NetworkAsset("ioos","all"));
        netall.setLongName("Contains all procedures in the SOS Server");
        netall.setShortName("network-all");
        return netall;
    }
}
