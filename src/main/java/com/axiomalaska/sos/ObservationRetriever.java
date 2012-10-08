package com.axiomalaska.sos;

import java.util.Calendar;

import com.axiomalaska.phenomena.Phenomenon;
import com.axiomalaska.sos.data.ObservationCollection;
import com.axiomalaska.sos.data.SosSensor;
import com.axiomalaska.sos.data.SosStation;

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
	 * 
	 * @return a ObservationCollection with observations or null if no observation were found
	 */
	public ObservationCollection getObservationCollection(SosStation station, 
			SosSensor sensor, Phenomenon phenomenon, Calendar startDate);
}
