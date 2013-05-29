package com.axiomalaska.sos;

import java.util.List;

import com.axiomalaska.sos.data.SosStation;
import com.axiomalaska.sos.exception.StationCreationException;

/**
 * The interface to pull SosStation
 * 
 * @author Lance Finfrock
 */
public interface StationRetriever {

	public List<SosStation> getStations() throws StationCreationException;
}
