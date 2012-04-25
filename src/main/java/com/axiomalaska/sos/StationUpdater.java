package com.axiomalaska.sos;

import java.util.List;

import org.apache.log4j.Logger;

import com.axiomalaska.sos.data.Station;
import com.axiomalaska.sos.tools.HttpSender;
import com.axiomalaska.sos.xmlbuilder.DescribeSensorBuilder;
import com.axiomalaska.sos.xmlbuilder.RegisterSensorBuilder;

/**
 * This class used used to update a station and it's phenomenons/sensor in the
 * SOS
 * 
 * Create the Object and pass in the URL to your SOS server Then call update
 * method with a station or a list of station to be used to update the SOS with.
 * 
 * This code first checks if the station is already created with a
 * DescribeSensor call to the SOS. Then if the station is not in the SOS it is
 * created with a RegisterSensor call to the SOS. Then the
 * StationObservationsUpdater object is called with the station to add the
 * observation of the station to the SOS
 * 
 * @author Lance Finfrock
 */
public class StationUpdater {

	// -------------------------------------------------------------------------
	// Private Data
	// -------------------------------------------------------------------------

	private String sosUrl;
	private HttpSender httpSender = new HttpSender();
	private StationObservationsUpdater observationsUpdater;
	private Logger logger;

	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------

	public StationUpdater(String sosUrl) {
		this(sosUrl, Logger.getRootLogger());
	}

	public StationUpdater(String sosUrl, Logger logger) {
		this.sosUrl = sosUrl;
		this.logger = logger;
		this.observationsUpdater = new StationObservationsUpdater(sosUrl);
	}

	// -------------------------------------------------------------------------
	// Public Members
	// -------------------------------------------------------------------------

	/**
	 * Update a list of stations
	 * 
	 * @param stations
	 *            - the stations to be updated
	 */
	public void update(List<Station> stations) throws Exception {
		for (Station station : stations) {
			update(station);
		}
	}

	/**
	 * Update only one station
	 * 
	 * @param station
	 *            - the station to be updated
	 */
	public void update(Station station) throws Exception {
		if (needToCreateSosStation(station)) {
			createNewSosStation(station);
		}

		observationsUpdater.updateObservations(station);
	}

	// -------------------------------------------------------------------------
	// Private Members
	// -------------------------------------------------------------------------

	/**
	 * Create a new station in the SOS server
	 * 
	 * @param station
	 *            - the station to be created
	 */
	private void createNewSosStation(Station station) throws Exception {
		logger.info("Creating station: " + station.getProcedureId());

		RegisterSensorBuilder registerSensorBuilder = new RegisterSensorBuilder(
				station);

		String xml = registerSensorBuilder.build();

		String response = httpSender.sendPostMessage(sosUrl, xml);

		if (response.contains("Exception")) {
			logger.error("station: " + station.getProcedureId() + " = " + response);
		} else {
			logger.info("Finished creating station: " + station.getProcedureId());
		}
	}

	/**
	 * Check if the station is already created in the SOS server
	 * 
	 * @param station
	 *            - the station to be checked
	 * @return [true] if the station needs to be created. [false] if the station
	 *         does not need to be created
	 */
	private Boolean needToCreateSosStation(Station station) throws Exception {
		DescribeSensorBuilder describeSensorBuilder = new DescribeSensorBuilder(
				station);

		String text = describeSensorBuilder.build();

		String output = httpSender.sendPostMessage(sosUrl, text);

		return !output.contains("sml:SensorML");
	}
}
