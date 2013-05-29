package com.axiomalaska.sos.data;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import com.axiomalaska.phenomena.Phenomenon;

/**
 * Contains a collection of observations for one associated phenomenon and station
 * 
 * The dates, locations and values of the observations are in the same order. Meaning that
 * the first value is associated to the first date and location. 
 * 
 * The location list is only fill if the station is moving. Else the list is empty
 * 
 * @author Lance Finfrock
 */
public class ObservationCollection {

	// -------------------------------------------------------------------------
	// Private Data
	// -------------------------------------------------------------------------
	private static final Logger LOGGER = Logger.getLogger(ObservationCollection.class);
	private SosSensor sensor;
	private Phenomenon phenomenon;
	private Map<DateTime,Double> observationValues = new HashMap<DateTime,Double>();
	private Double height = null;
	
	// -------------------------------------------------------------------------
	// Public Members
	// -------------------------------------------------------------------------
	
	/**
	 * Could be null if no depth is involved
	 * 
	 * @return
	 */
	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}
	
	public Phenomenon getPhenomenon() {
		return phenomenon;
	}

	public void setPhenomenon(Phenomenon phenomenon) {
		this.phenomenon = phenomenon;
	}
	
	/**
	 * The sensor of the observations
	 */
	public SosSensor getSensor() {
		return sensor;
	}

    public void setSensor(SosSensor sensor) {
        this.sensor = sensor;
    }
	
    public Map<DateTime, Double> getObservationValues() {
        return observationValues;
    }

    public void setObservationValues(Map<DateTime, Double> observationValues) {
        this.observationValues = observationValues;
    }

    public boolean hasObservationValue(DateTime dateTime) {
        return this.observationValues.containsKey(dateTime);
    }    
    
    public void addObservationValue(DateTime dateTime, Double value) {
        this.observationValues.put(dateTime, value);
    }
    
    /**
     * Remove observations that are between the start and end dates 
     * @param startDate - start date of observations to remove
     * @param endDate - end date of observations to remove
     */
    public void filterObservations(DateTime startDate, DateTime endDate) {
        for (Iterator<Entry<DateTime,Double>> it = observationValues.entrySet().iterator(); it.hasNext();) {
            Entry<DateTime,Double> entry = it.next();
            DateTime obsDate = entry.getKey();
            if (obsDate.isAfter(startDate) && obsDate.isBefore(endDate)) {
                it.remove();
            }
        }
    }
    
    /**
     * A method to validate the ObservationCollection object. 
     * 
     * @return [True] if the ObservationCollection object is valid. [False] if the
     * ObservationCollection is not valid
     */
    public boolean isValid() {
        if(sensor == null){
            LOGGER.info("Sensor was null in ObservationCollection");
            return false;
        }
        
        if(phenomenon == null){
            LOGGER.info("Phenomenon was null in ObservationCollection");            
            return false;
        }
        
        if(sensor.getStation() == null){
            LOGGER.info("Station was null in ObservationCollection");           
            return false;
        }

        if(observationValues.isEmpty()){
            LOGGER.info("No values for sensor " + sensor.getId() 
                    + " phenomenon: " + phenomenon.getId());
            return false;
        }

        return true;
    }    
    
    public String toString(){
        return "ObservationCollection["
                + "sensor: " + (sensor == null ? "null" : sensor.getId())
                + ",phenomenon: " + (phenomenon == null ? "null" : phenomenon.getId())
                + ",height: " + (height == null ? "null" : Double.toString(height))
                + ",size: " + observationValues.size()
                + "]";
    }
}
