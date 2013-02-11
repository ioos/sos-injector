package com.axiomalaska.sos;

import com.axiomalaska.sos.data.SosStation;
import java.util.List;

/**
 * The interface to pull SosStation
 * 
 * @author Lance Finfrock
 */
public interface StationRetriever {

	public List<SosStation> getStations()  throws Exception ;
}
