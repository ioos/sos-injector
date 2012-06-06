package com.axiomalaska.sos;

import java.util.Calendar;

import com.axiomalaska.sos.data.ObservationCollection;
import com.axiomalaska.sos.data.Sensor;
import com.axiomalaska.sos.data.Station;

/**
 * Represents an interface to request observations from some data store.
 * 
 * @author Lance Finfrock
 */
public interface ObservationRetriever {

	/**
	 * Provides observations (from the startDate to current) for a specific 
	 * phenomenon and station.
	 * 
	 * @param sensor - the associated sensor of the requested observations 
	 * @param station - the associated station of the requested observations
	 * @param startDate - all observations returned should be older than this date
	 */
	public ObservationCollection getObservationCollection(Station station, 
			Sensor sensor, 	Calendar startDate);
}
