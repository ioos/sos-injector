package com.axiomalaska.sos;

import java.util.List;

import org.joda.time.DateTime;

import com.axiomalaska.phenomena.Phenomenon;
import com.axiomalaska.sos.data.ObservationCollection;
import com.axiomalaska.sos.data.SosSensor;

/**
 * Represents an interface to request observations from some data store.
 * 
 * @author Lance Finfrock
 */
public interface ObservationRetriever {

	/**
	 * Provides observations (from the startDate to current) for a specific 
	 * phenomenon and sensor.
	 * 
	 * @param sensor - the associated sensor of the requested observations 
     * @param phenomenon - the measured phenomenon (observed property) of the requested observations 
	 * @param startDate - all observations returned should be older than this date
	 * 
	 * @return a ObservationCollection with observations or null if no observation were found
	 */
	public List<ObservationCollection> getObservationCollection(SosSensor sensor,
	        Phenomenon phenomenon, DateTime startDate);
}
