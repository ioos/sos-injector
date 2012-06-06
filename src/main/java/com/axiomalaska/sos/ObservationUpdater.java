package com.axiomalaska.sos;

import org.apache.log4j.Logger;

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
	
	public void update() throws Exception {
		ObservationSubmitter observationUpdater = new ObservationSubmitter(
				sosUrl, logger);

		observationUpdater.update(stationRetriever.getStations(),
				observationRetriever);
	}
}
