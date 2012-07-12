package com.axiomalaska.sos.cnfaic;

import org.apache.log4j.Logger;

import com.axiomalaska.sos.ObservationUpdater;
import com.axiomalaska.sos.data.PublisherInfo;

public class CnfaicObservationUpdaterFactory {

	// -------------------------------------------------------------------------
	// Public Members
	// -------------------------------------------------------------------------
	
	public ObservationUpdater buildCnfaicObservationUpdater(
			String sosUrl, PublisherInfo publisherInfo){
		Logger logger = Logger.getRootLogger();
		
		return buildCnfaicObservationUpdater(sosUrl, publisherInfo, logger);
	}
	
	public ObservationUpdater buildCnfaicObservationUpdater(
			String sosUrl, PublisherInfo publisherInfo, Logger logger){

		CnfaicStationRetriever stationRetriever = 
				new CnfaicStationRetriever();
		CnfaicObservationRetriever observationRetriever = 
				new CnfaicObservationRetriever();
		
		ObservationUpdater observationUpdater = new ObservationUpdater(sosUrl, 
				logger, stationRetriever, publisherInfo, 
				observationRetriever, "CNFAIC Observation Updater");
		
		return observationUpdater;
	}
}
