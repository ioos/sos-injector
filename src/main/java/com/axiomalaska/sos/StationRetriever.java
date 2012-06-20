package com.axiomalaska.sos;

import java.util.List;

import com.axiomalaska.sos.data.SosStation;

public interface StationRetriever {

	public List<SosStation> getStations()  throws Exception ;
}
