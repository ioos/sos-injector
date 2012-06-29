package com.axiomalaska.sos;

import org.apache.log4j.Logger;

/**
 * This class is used to update the a SOS server
 * 
 * @author lance Finfrock
 */
public class ObservationUpdater {

	// -------------------------------------------------------------------------
	// Private Data
	// -------------------------------------------------------------------------
	
	private Logger logger;
	private String sosUrl;
	private StationRetriever stationRetriever;
	private ObservationRetriever observationRetriever;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public ObservationUpdater(String sosUrl, 
			StationRetriever stationRetriever, 
			ObservationRetriever observationRetriever){
		this(sosUrl, Logger.getRootLogger(), 
				stationRetriever, observationRetriever);
	}
	
	public ObservationUpdater(String sosUrl, Logger logger, 
			StationRetriever stationRetriever, 
			ObservationRetriever observationRetriever){
		this.sosUrl = sosUrl;
		this.logger = logger;
		this.stationRetriever = stationRetriever;
		this.observationRetriever = observationRetriever;
	}
	
	// -------------------------------------------------------------------------
	// Public Members
	// -------------------------------------------------------------------------
	
	/**
	 * Update the SOS with new observations
	 * @throws Exception
	 */
	public void update() throws Exception {
		ObservationSubmitter observationSubmitter = new ObservationSubmitter(
				sosUrl, logger);

		observationSubmitter.update(stationRetriever.getStations(),
				observationRetriever);
	}
}
