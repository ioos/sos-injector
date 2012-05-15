package com.axiomalaska.sos.tools;

import com.axiomalaska.sos.data.Location;
import com.axiomalaska.sos.data.Phenomenon;
import com.axiomalaska.sos.data.PhenomenonDepth;
import com.axiomalaska.sos.data.Station;

public class IdCreator {

	public String createProcederId(Station station){
		return "urn:ioos:station:" + station.getSourceName() + ":" + station.getId();
	}
	
	public String createFeatureOfInterestId(Station station, 
			Phenomenon phenomenon, Location location){
		
		String featureOfInterestId = station.getSourceName() + ":" + station.getId();
		PhenomenonDepth depth = phenomenon.getDepth();
		
		featureOfInterestId += ":" + location.getLatitude() + "_" + location.getLongitude();
		
		if(depth != null){
			featureOfInterestId += "_Depth_" + depth.getValue() + "_" + 
					depth.getUnits();
		}
		
		return featureOfInterestId;
	}
	
	public String createFeatureOfInterestName(Station station,
			Phenomenon phenomenon) {
		String featureOfInterestName = station.getFeatureOfInterestName();
		
		PhenomenonDepth depth = phenomenon.getDepth();
		if(depth != null){
			featureOfInterestName += " Depth " + depth.getValue() + " " + 
					depth.getUnits();
		}
		
		return featureOfInterestName;
	}
	
	public String createFeatureOfInterestId(Station station, 
			Phenomenon phenomenon){
		
		String featureOfInterestId = station.getSourceName() + ":" + station.getId();
		PhenomenonDepth depth = phenomenon.getDepth();
		
		if(depth != null){
			featureOfInterestId += "_Depth_" + depth.getValue() + "_" + 
					depth.getUnits();
		}
		
		return featureOfInterestId;
	}

}
