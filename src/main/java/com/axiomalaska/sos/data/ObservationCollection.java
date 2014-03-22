package com.axiomalaska.sos.data;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.axiomalaska.ioos.sos.GeomHelper;
import com.axiomalaska.phenomena.Phenomenon;
import com.vividsolutions.jts.geom.Geometry;

/**
 * Contains a collection of observations for one associated phenomenon and station
 * 
 * @author Lance Finfrock
 */
public class ObservationCollection {

	// -------------------------------------------------------------------------
	// Private Data
	// -------------------------------------------------------------------------
	private static final Logger LOGGER = LoggerFactory.getLogger(ObservationCollection.class);
	private SosSensor sensor;
	private Phenomenon phenomenon;
	private NavigableMap<DateTime,Double> observationValues = new TreeMap<DateTime,Double>();
	private Geometry geometry = null;
	
	// -------------------------------------------------------------------------
	// Public Members
	// -------------------------------------------------------------------------
	
	/**
	 * Could be null if no depth is involved
	 * 
	 * @return
	 */
	public Geometry getGeometry() {
	    if (geometry == null && sensor != null && sensor.getLocation() != null ){
	        return sensor.getLocation();
	    }
		return geometry;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
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
	
    public NavigableMap<DateTime, Double> getObservationValues() {
        return observationValues;
    }

    public void setObservationValues(NavigableMap<DateTime, Double> observationValues) {
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
            if ((obsDate.isAfter(startDate) || obsDate.isEqual(startDate))
                    && (obsDate.isBefore(endDate) || obsDate.isEqual(endDate))) {
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
            LOGGER.info("Station was null in ObservationCollection's sensor");
            return false;
        }

        if(getGeometry() == null){
            LOGGER.info("Geometry was null in ObservationCollection");
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
                + ",geometry: " + (geometry == null ? "null" : GeomHelper.toString3d(geometry))
                + ",size: " + (observationValues == null ? "null" : observationValues.size())
                + ",firstTime: " + (observationValues == null ? "null" :
                    new DateTime(observationValues.firstKey(), DateTimeZone.UTC))
                + ",lastTime: " + (observationValues == null ? "null" :
                    new DateTime(observationValues.lastKey(), DateTimeZone.UTC))
                + "]";
    }
}
