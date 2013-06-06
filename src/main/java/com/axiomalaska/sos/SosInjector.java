package com.axiomalaska.sos;

import java.io.IOException;

import org.apache.commons.validator.routines.RegexValidator;
import org.apache.commons.validator.routines.UrlValidator;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;

import com.axiomalaska.phenomena.Phenomenon;
import com.axiomalaska.sos.data.PublisherInfo;
import com.axiomalaska.sos.data.SosSensor;
import com.axiomalaska.sos.data.SosStation;
import com.axiomalaska.sos.exception.InvalidObservationCollectionException;
import com.axiomalaska.sos.exception.ObservationRetrievalException;
import com.axiomalaska.sos.exception.SosCommunicationException;
import com.axiomalaska.sos.exception.SosInjectorConfigurationException;
import com.axiomalaska.sos.exception.StationCreationException;
import com.axiomalaska.sos.exception.UnsupportedGeometryTypeException;
import com.axiomalaska.sos.exception.UnsupportedSosAssetTypeException;

/**
 * This class is used to update the SOS server
 * 
 * @author lance Finfrock
 */
public class SosInjector {

	// -------------------------------------------------------------------------
	// Private Data
	// -------------------------------------------------------------------------
	
	private static final Logger LOGGER = Logger.getLogger(SosInjector.class);
    private String name;    
	private StationRetriever stationRetriever;
	private ObservationRetriever observationRetriever;
	private ISOFileWriter isoWriter;
    private ProcedureSubmitter procedureSubmitter;	
	private ObservationSubmitter observationSubmitter;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------

    public SosInjector(
            String name,
            String sosUrl,
            PublisherInfo publisherInfo,
            StationRetriever stationRetriever,            
            ObservationRetriever observationRetriever,
            ISOFileWriter fileWriter) throws SosInjectorConfigurationException {
        if (name == null || name.isEmpty()){
            throw new SosInjectorConfigurationException("name cannot be null or empty");
        }
        if (sosUrl == null || sosUrl.isEmpty()){
            throw new SosInjectorConfigurationException("sosUrl cannot be null or empty");
        }
        if (!(new UrlValidator(new RegexValidator(".*"),0).isValid(sosUrl))){
            throw new SosInjectorConfigurationException("sosUrl must be valid");
        }
        if (publisherInfo == null){
            throw new SosInjectorConfigurationException("publisherInfo cannot be null");
        }
        if (stationRetriever == null){
            throw new SosInjectorConfigurationException("stationRetriever cannot be null");
        }
        if (observationRetriever == null){
            throw new SosInjectorConfigurationException("observationRetriever cannot be null");
        }

        this.name = name;
        this.stationRetriever = stationRetriever;
        this.observationRetriever = observationRetriever;
        this.isoWriter = fileWriter;
        this.procedureSubmitter = new ProcedureSubmitter(sosUrl, publisherInfo);
        this.observationSubmitter = new ObservationSubmitter(sosUrl);
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
            LOGGER.error("Error getting station source", ex);
            return "source";
        }
    }

	/**
	 * Update the SOS with new observations
	 * @throws UnsupportedSosAssetTypeException 
	 * @throws IOException 
	 * @throws XmlException 
	 * @throws ObservationRetrievalException 
	 * @throws InvalidObservationCollectionException 
	 * @throws SosCommunicationException 
	 * @throws StationCreationException 
	 * @throws UnsupportedGeometryTypeException 
	 */
	public void update() throws InvalidObservationCollectionException,
	        ObservationRetrievalException, UnsupportedSosAssetTypeException, StationCreationException,
	        SosCommunicationException, UnsupportedGeometryTypeException{
	    LOGGER.info("Updating " + name);
        for (SosStation station : stationRetriever.getStations()) {
            if (station.getSensors().isEmpty()) {
                LOGGER.info("Station " + station.getId() + " does not have any sensors and will not be added.");
                continue;
            }
            for (SosSensor sensor : station.getSensors()) {
                if(sensor.getPhenomena().isEmpty()){
                    LOGGER.info("Sensor " + sensor.getId() + " does not have any phenomena and will not be added.");
                    continue;
                }
                if (procedureSubmitter.checkProcedureWithSos(sensor)) {
                    for(Phenomenon phenomenon : sensor.getPhenomena()){
                        observationSubmitter.update(sensor, phenomenon, observationRetriever);
                    }           
                }
            }
        }
	    
        if (isoWriter != null) {
            for (SosStation station : stationRetriever.getStations()) {
                isoWriter.writeISOFileForStation(station);
            }
        }
	}
}
