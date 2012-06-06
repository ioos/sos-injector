package com.axiomalaska.sos;

import org.apache.log4j.Logger;

import com.axiomalaska.sos.cnfaic.CnfaicObservationRetriever;
import com.axiomalaska.sos.cnfaic.CnfaicStationRetriever;

public class ObservationUpdaterFactory {

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
