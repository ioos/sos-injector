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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


import com.axiomalaska.sos.data.ObservationCollection;
import com.axiomalaska.sos.data.Phenomenon;
import com.axiomalaska.sos.data.Sensor;
import com.axiomalaska.sos.data.Station;
import com.axiomalaska.sos.tools.HttpSender;
import com.axiomalaska.sos.tools.IdCreator;
import com.axiomalaska.sos.xmlbuilder.DescribeSensorBuilder;
import com.axiomalaska.sos.xmlbuilder.GetNewestObservationBuilder;
import com.axiomalaska.sos.xmlbuilder.InsertObservationBuilder;
import com.axiomalaska.sos.xmlbuilder.RegisterSensorBuilder;

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
	private IdCreator idCreator;

	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------

	/**
	 * Default constructor
	 * 
	 * Creates a default logger and IdCreator
	 * @param sosUrl - the URL to the SOS server
	 */
	public ObservationSubmitter(String sosUrl) {
		this(sosUrl, Logger.getRootLogger(), new IdCreator());
	}

	/**
	 * Built with a custom logger
	 * 
	 * creates a default IdCreator
	 * 
	 * @param sosUrl - the URL to the SOS server
	 * @param logger - custom logger. Used to log information from the update process
	 */
	public ObservationSubmitter(String sosUrl, Logger logger) {
		this(sosUrl, logger, new IdCreator());
	}

	
	/**
	 * ObservationUpdater built with a custom idCreator
	 * 
	 * @param sosUrl - the URL to the SOS server
	 * @param idCreator - custom idCreator. Used to create SOS IDs from stations and phenomena
	 */
	public ObservationSubmitter(String sosUrl, IdCreator idCreator) {
		this(sosUrl, Logger.getRootLogger(), idCreator);
	}

	/**
	 * ObservationUpdater built with a custom idCreator and logger
	 * 
	 * @param sosUrl - the URL to the SOS server
	 * @param idCreator - custom idCreator. Used to create SOS IDs from stations and phenomena
	 * @param logger - custom logger. Used to log information from the update process
	 */
	public ObservationSubmitter(String sosUrl, 
			Logger logger, IdCreator idCreator) {
		this.sosUrl = sosUrl;
		this.logger = logger;
		this.idCreator = idCreator;
	}

	// -------------------------------------------------------------------------
	// Public Members
	// -------------------------------------------------------------------------

	/**
	 * Push: Update the SOS with the given observationCollection
	 * 
	 * @param observationCollection - the data used to update the SOS
	 */
	public void update(ObservationCollection observationCollection) throws Exception {
		if (isObservationCollectionValid(observationCollection)) {
			Station station = observationCollection.getStation();
			boolean isStationCreated = isStationCreated(station);
			if (!isStationCreated) {
				isStationCreated = createNewSosStation(station);
			}

			if (isStationCreated) {
				insertObservations(observationCollection);
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
	public void update(List<Station> stations, 
			ObservationRetriever observationRetriever) throws Exception {
		for (Station station : stations) {
			update(station, observationRetriever);
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
	public void update(Station station,  
			ObservationRetriever observationRetriever) throws Exception {
		if (station.getSensors().size() > 0) {
			boolean isStationCreated = isStationCreated(station);
			if (!isStationCreated) {
				isStationCreated = createNewSosStation(station);
			}

			if (isStationCreated) {
				for (Sensor sensor : station.getSensors()) {
					update(station, sensor,
							observationRetriever);
				}
			}
		}
		else{
			logger.info("Station " + idCreator.createProcederId(station) + " does not have any phenomena. The station will not be added.");
		}
	}
	
	/**
	 * Pull: Update the observations of a station with a specific phenomenon in the SOS server.
	 * 
	 * @param station - the station to pull observations 
	 * @param phenomenon - the phenomenon to pull observations 
	 * @param observationRetriever - the data store of observations used to 
	 * pull observations from
	 */
	public void update(Station station, Sensor sensor,  
			ObservationRetriever observationRetriever)
			throws Exception {
		Calendar startDate = getNewestObservationDate(station, sensor);
		
		ObservationCollection observationCollection = observationRetriever.getObservationCollection(
				station, sensor, startDate);
		
		if (isObservationCollectionValid(observationCollection)) {
			insertObservations(observationCollection, startDate);
		}
	}
	
	// -------------------------------------------------------------------------
	// Private Members
	// -------------------------------------------------------------------------

	/**
	 * Insert observations from the observationCollection object in the SOS. 
	 * 
	 * @param observationCollection - contains the observations to be placed in the
	 * SOS. This object should have been validated before this method is called
	 */
	private void insertObservations(ObservationCollection observationCollection) 
			throws Exception {
		Calendar newestObservationInSosDate = getNewestObservationDate(
				observationCollection.getStation(), observationCollection.getSensor());
		
		insertObservations(observationCollection, newestObservationInSosDate);
	}

	/**
	 * Insert observations from the observationCollection object in the SOS. 
	 * 
	 * @param observationCollection - contains the observations to be placed in the
	 * SOS. This object should have been validated before this method is called
	 * @param newestObservationInSosDate - the newest observation's date
	 */
	private void insertObservations(ObservationCollection observationCollection, 
			Calendar newestObservationInSosDate) throws Exception {
		Sensor sensor = observationCollection.getSensor();
		Station station = observationCollection.getStation();

		ObservationCollection filteredObservationCollection = removeOlderObservations(
				newestObservationInSosDate, observationCollection);

		try {
			InsertObservationBuilder insertObservationBuilder = new InsertObservationBuilder(
					station, filteredObservationCollection, idCreator);

			String insertXml = insertObservationBuilder.build();

			String response = httpSender.sendPostMessage(sosUrl, insertXml);

			if (response.contains("Exception")) {
				logger.error("Inputed "
						+ observationCollection.getObservationDates().size()
						+ " observations from station: " + idCreator.createProcederId(station)
						+ " and phenomenon: " + sensor.getPhenomenon().getName()
						+ " from: " + " response: \n" + response);
			} else {
				logger.info("Inputed "
						+ observationCollection.getObservationDates().size()
						+ " observations from station: " + idCreator.createProcederId(station)
						+ " and phenomenon: " + sensor.getPhenomenon().getName());
			}
		} catch (Exception e) {
			logger.error("Inputed "
					+ observationCollection.getObservationDates().size()
					+ " observations from station: " + idCreator.createProcederId(station)
					+ " and phenomenon: " + sensor.getPhenomenon().getName()
					+ " message: \n" + e.getMessage());
		}
	}

	/**
	 * Remove older observations than the newestObservationInSosDate from the 
	 * observationCollection 
	 * @param newestObservationInSosDate - the newest observation's date
	 * @param observationCollection - the observationCollection to remove the
	 * older observations from.
	 * @return
	 */
	private ObservationCollection removeOlderObservations(
			Calendar newestObservationInSosDate,
			ObservationCollection observationCollection) {
		
		ObservationCollection filteredObservationCollection = new ObservationCollection();
		
		filteredObservationCollection.setStation(observationCollection.getStation());
		filteredObservationCollection.setSensor(observationCollection.getSensor());
		
		List<Calendar> observationDates = observationCollection.getObservationDates();
		List<Double> observationValues = observationCollection.getObservationValues();
		
		List<Calendar> filteredObservationDates = new ArrayList<Calendar>();
		List<Double> filteredObservationValues = new ArrayList<Double>();
		for(int index = 0; index < observationDates.size(); index++){
			if(observationDates.get(index).after(newestObservationInSosDate)){
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
	private boolean isObservationCollectionValid(ObservationCollection observationCollection){
		if (observationCollection == null){
			logger.info("observationCollection is null");
			return false;
		}
		
		if(observationCollection.getSensor() == null){
			logger.info("Phenomenon was null in ObservationCollection");
			
			return false;
		}
		
		if(observationCollection.getStation() == null){
			logger.info("Station was null in ObservationCollection");
			
			return false;
		}
		
		Sensor sensor = observationCollection.getSensor();
		Station station = observationCollection.getStation();
		
	    if (observationCollection.getObservationDates().size() != observationCollection.getObservationValues().size()){
			logger.info("The observationCollection's size of the dates list is not equal to the values list "
					+ " for station: "
					+ idCreator.createProcederId(station) + " and phenomenon: "
					+ sensor.getPhenomenon().getName());
	    	return false;
	    }
	    if(observationCollection.getObservationDates().size() == 0
				&& observationCollection.getObservationValues().size() == 0){
			logger.info("No values from source "
					+ " for station: "
					+ idCreator.createProcederId(station) + " and phenomenon: "
					+ sensor.getPhenomenon().getName());
			return false;
		}
	    
	    if(station.isMoving() && 
	    		observationCollection.getObservationLocations().size() != 
	    		observationCollection.getObservationValues().size()){
			logger.info("Moving station does not have the same amount of " +
					"locations has values. Station name " + 
					idCreator.createProcederId(station) + " and phenomenon: "
					+ sensor.getPhenomenon().getName());
			
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
	private Calendar getNewestObservationDate(Station station,
			Sensor sensor) throws Exception {
		GetNewestObservationBuilder getObservationLatestBuilder = 
				new GetNewestObservationBuilder(station, sensor, idCreator);

		String getObservationXml = getObservationLatestBuilder.build();

		String response = httpSender.sendPostMessage(sosUrl, getObservationXml);

		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(new ByteArrayInputStream(response
				.getBytes()));

		doc.normalize();

		/*
      <om:samplingTime>
        <gml:TimeInstant xsi:type="gml:TimeInstantType">
          <gml:timePosition>2012-05-01T07:00:00.000Z</gml:timePosition>
        </gml:TimeInstant>
      </om:samplingTime>
		 */
		NodeList nodeList = doc.getElementsByTagName("gml:timePosition");

		if (nodeList.getLength() == 1) {

			Element timePosition = (Element) nodeList.item(0);

			Calendar date = createDate(timePosition.getTextContent());
			
			date.add(Calendar.MINUTE, 1);

			return date;
		} else {
			logger.info("station: " + idCreator.createProcederId(station) + " phenomenon: "
					+ sensor.getPhenomenon().getName() + " has no observations in SOS");
			Calendar defaultDate = Calendar.getInstance();

			defaultDate.set(1970, Calendar.JANUARY, 1);

			defaultDate.getTime();

			return defaultDate;
		}
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
	 * @param station
	 *            - the station to be created
	 * @return - [true] if the station was create successfully [false] if else. 
	 */
	private boolean createNewSosStation(Station station) throws Exception {
		logger.info("Creating station: " + idCreator.createProcederId(station));

		RegisterSensorBuilder registerSensorBuilder = new RegisterSensorBuilder(
				station, idCreator);

		String xml = registerSensorBuilder.build();

		String response = httpSender.sendPostMessage(sosUrl, xml);

		if (response.contains("Exception")) {
			logger.error("station: " + idCreator.createProcederId(station) + " = " + response);
			return false;
		} else {
			logger.info("Finished creating station: " + idCreator.createProcederId(station));
			return true;
		}
	}

	/**
	 * Check if the station is already created in the SOS
	 * 
	 * @param station
	 *            - the station to be checked
	 * @return [true] if the station needs to be created. [false] if the station
	 *         does not need to be created
	 */
	private Boolean isStationCreated(Station station) throws Exception {
		DescribeSensorBuilder describeSensorBuilder = new DescribeSensorBuilder(
				station, idCreator);

		String text = describeSensorBuilder.build();

		String output = httpSender.sendPostMessage(sosUrl, text);

		return output.contains("sml:SensorML");
	}
}
