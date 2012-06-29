package com.axiomalaska.sos;

import java.util.List;

import com.axiomalaska.sos.data.SosStation;

/**
 * The interface to pull SosStation
 * 
 * @author Lance Finfrock
 */
public interface StationRetriever {

	public List<SosStation> getStations()  throws Exception ;
}
