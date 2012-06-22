package com.axiomalaska.sos.cnfaic;

import org.apache.log4j.Logger;

import com.axiomalaska.sos.ObservationUpdater;

public class CnfaicObservationUpdaterFactory {

	// -------------------------------------------------------------------------
	// Public Members
	// -------------------------------------------------------------------------
	
	public ObservationUpdater buildCnfaicObservationUpdater(
			String sosUrl){
		Logger logger = Logger.getRootLogger();
		
		return buildCnfaicObservationUpdater(sosUrl, logger);
	}
	
	public ObservationUpdater buildCnfaicObservationUpdater(
			String sosUrl, Logger logger){

		CnfaicStationRetriever stationRetriever = 
				new CnfaicStationRetriever();
		CnfaicObservationRetriever observationRetriever = 
				new CnfaicObservationRetriever();
		
		ObservationUpdater observationUpdater = new ObservationUpdater(sosUrl, 
				logger, stationRetriever, observationRetriever);
		
		return observationUpdater;
	}
}
