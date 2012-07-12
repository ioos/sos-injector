package com.axiomalaska.sos;

import org.apache.log4j.Logger;

import com.axiomalaska.sos.data.PublisherInfo;

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
	private String name;
	private PublisherInfo publisherInfo;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public ObservationUpdater(String sosUrl, 
			StationRetriever stationRetriever, 
			PublisherInfo publisherInfo,
			ObservationRetriever observationRetriever){
		this(sosUrl, Logger.getRootLogger(), 
				stationRetriever, publisherInfo, observationRetriever, "no name");
	}
	
	public ObservationUpdater(String sosUrl, Logger logger, 
			StationRetriever stationRetriever, 
			PublisherInfo publisherInfo,
			ObservationRetriever observationRetriever){
		this(sosUrl, logger, 
				stationRetriever, publisherInfo, observationRetriever, "no name");
	}
	
	public ObservationUpdater(String sosUrl, Logger logger, 
			StationRetriever stationRetriever, 
			PublisherInfo publisherInfo,
			ObservationRetriever observationRetriever, String name){
		this.sosUrl = sosUrl;
		this.logger = logger;
		this.stationRetriever = stationRetriever;
		this.observationRetriever = observationRetriever;
		this.name = name;
		this.publisherInfo = publisherInfo;
	}
	
	// -------------------------------------------------------------------------
	// Public Members
	// -------------------------------------------------------------------------
	
	public String getName(){
		return name;
	}
	/**
	 * Update the SOS with new observations
	 * @throws Exception
	 */
	public void update() throws Exception {
		ObservationSubmitter observationSubmitter = new ObservationSubmitter(
				sosUrl, logger);

		observationSubmitter.update(stationRetriever.getStations(),
				observationRetriever, publisherInfo);
	}
}
