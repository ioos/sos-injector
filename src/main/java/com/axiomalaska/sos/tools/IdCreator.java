package com.axiomalaska.sos.tools;

import com.axiomalaska.sos.data.Location;
import com.axiomalaska.sos.data.SosSensor;
import com.axiomalaska.sos.data.SosStation;

public class IdCreator {

	public String createProcederId(SosStation station){
		return "urn:ioos:station:" + station.getSourceName() + ":" + station.getId();
	}
	
	public String createFeatureOfInterestId(SosStation station, 
			SosSensor sensor, Location location){
		
		String featureOfInterestId = station.getSourceName() + ":" + station.getId();
		
		featureOfInterestId += ":" + location.getLatitude() + "_" + location.getLongitude();
		
		return featureOfInterestId;
	}
	
	public String createFeatureOfInterestName(SosStation station,
			SosSensor sensor) {
		String featureOfInterestName = station.getFeatureOfInterestName();
		
		return featureOfInterestName;
	}
	
	public String createFeatureOfInterestId(SosStation station, 
			SosSensor sensor){
		
		String featureOfInterestId = station.getSourceName() + ":" + station.getId();
		
		return featureOfInterestId;
	}
}
