package com.axiomalaska.sos;

import org.apache.log4j.Logger;

import com.axiomalaska.sos.data.PublisherInfo;
import com.axiomalaska.sos.data.SosStation;
import com.axiomalaska.sos.data.SosNetwork;
import java.util.logging.Level;

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
        
        private ISOFileWriter isoWriter = null;
	
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
        
        public ObservationUpdater(String sosUrl, Logger logger,
                StationRetriever stationRetriever,
                PublisherInfo publisherInfo,
                ObservationRetriever observationRetriever,
                ISOFileWriter fileWriter) {
            this.sosUrl = sosUrl;
            this.logger = logger;
            this.stationRetriever = stationRetriever;
            this.observationRetriever = observationRetriever;
            this.name = "no name";
            this.publisherInfo = publisherInfo;
            this.isoWriter = fileWriter;
        }
	
	// -------------------------------------------------------------------------
	// Public Members
	// -------------------------------------------------------------------------
	
	public String getName(){
		return name;
	}
        
        public String getSource() {
            try {
                return stationRetriever.getStations().get(0).getSource().getId();
            } catch (Exception ex) {
                java.util.logging.Logger.getLogger(ObservationUpdater.class.getName()).log(Level.SEVERE, null, ex);
                return "source";
            }
        }
	/**
	 * Update the SOS with new observations
	 * @throws Exception
	 */
	public void update(SosNetwork network) throws Exception {
            logger.info("Updating with root network: " + network.getShortName());
		ObservationSubmitter observationSubmitter = new ObservationSubmitter(
				sosUrl, logger);

		observationSubmitter.update(network, stationRetriever.getStations(),
				observationRetriever, publisherInfo);
                
                if (isoWriter != null) {
                    for (SosStation station : stationRetriever.getStations()) {
                        isoWriter.writeISOFileForStation(station);
                    }
                }
	}
}
