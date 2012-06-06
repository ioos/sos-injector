package com.axiomalaska.sos.tools;

import com.axiomalaska.sos.data.Location;
import com.axiomalaska.sos.data.Sensor;
import com.axiomalaska.sos.data.SensorDepth;
import com.axiomalaska.sos.data.Station;

public class IdCreator {

	public String createProcederId(Station station){
		return "urn:ioos:station:" + station.getSourceName() + ":" + station.getId();
	}
	
	public String createFeatureOfInterestId(Station station, 
			Sensor sensor, Location location){
		
		String featureOfInterestId = station.getSourceName() + ":" + station.getId();
		SensorDepth depth = sensor.getSensorDepth();
		
		featureOfInterestId += ":" + location.getLatitude() + "_" + location.getLongitude();
		
		if(depth != null){
			featureOfInterestId += "_Depth_" + depth.getValue() + "_" + 
					depth.getUnits();
		}
		
		return featureOfInterestId;
	}
	
	public String createFeatureOfInterestName(Station station,
			Sensor sensor) {
		String featureOfInterestName = station.getFeatureOfInterestName();
		
		SensorDepth depth = sensor.getSensorDepth();
		if(depth != null){
			featureOfInterestName += " Depth " + depth.getValue() + " " + 
					depth.getUnits();
		}
		
		return featureOfInterestName;
	}
	
	public String createFeatureOfInterestId(Station station, 
			Sensor sensor){
		
		String featureOfInterestId = station.getSourceName() + ":" + station.getId();
		SensorDepth depth = sensor.getSensorDepth();
		
		if(depth != null){
			featureOfInterestId += "_Depth_" + depth.getValue() + "_" + 
					depth.getUnits();
		}
		
		return featureOfInterestId;
	}
}
