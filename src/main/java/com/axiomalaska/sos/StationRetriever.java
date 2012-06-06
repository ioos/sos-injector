package com.axiomalaska.sos;

import java.util.List;

import com.axiomalaska.sos.data.Station;

public interface StationRetriever {

	public List<Station> getStations()  throws Exception ;
}
