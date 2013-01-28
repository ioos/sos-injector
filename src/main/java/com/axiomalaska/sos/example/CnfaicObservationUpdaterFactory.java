package com.axiomalaska.sos.example;

import org.apache.log4j.Logger;

import com.axiomalaska.sos.ObservationUpdater;
import com.axiomalaska.sos.data.PublisherInfo;
import com.axiomalaska.sos.data.SosNetwork;

public class CnfaicObservationUpdaterFactory {

	// -------------------------------------------------------------------------
	// Public Members
	// -------------------------------------------------------------------------
	
	public ObservationUpdater buildCnfaicObservationUpdater(
			String sosUrl, PublisherInfo publisherInfo, SosNetwork rootNetwork){
		Logger logger = Logger.getRootLogger();
		
		return buildCnfaicObservationUpdater(sosUrl, publisherInfo, rootNetwork, logger);
	}
	
	public ObservationUpdater buildCnfaicObservationUpdater(
			String sosUrl, PublisherInfo publisherInfo, SosNetwork rootNetwork, 
			Logger logger){

		CnfaicStationRetriever stationRetriever = 
				new CnfaicStationRetriever(rootNetwork);
		CnfaicObservationRetriever observationRetriever = 
				new CnfaicObservationRetriever();
		
		ObservationUpdater observationUpdater = new ObservationUpdater(sosUrl, 
				logger, stationRetriever, publisherInfo, 
				observationRetriever, "CNFAIC Observation Updater");
		
		return observationUpdater;
	}
}
