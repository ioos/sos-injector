package com.axiomalaska.sos.tools;

import com.axiomalaska.sos.data.Location;
import com.axiomalaska.sos.data.SosSensor;
import com.axiomalaska.sos.data.SosStation;

/**
 * This class is used to create the ID of the objects of the SOS. This was
 * created because of the multiple changes being made to the IDs.
 * 
 * @author Lance Finfrock
 * 
 */
public class IdCreator {
	public static String createFeatureOfInterestId(SosSensor sensor, Location location){
		return sensor.getId() + ":" + location.getLatitude() + ":" + location.getLongitude();
	}
	
	public static String createFeatureOfInterestName(SosStation station, SosSensor sensor) {
		return station.getFeatureOfInterestName();
	}
	
	public static String createFeatureOfInterestId(SosStation station, SosSensor sensor){
	    return station.getId();
	}

    public static String createObservationFeatureOfInterestId(SosSensor sensor, Double depth) {
        if (depth != null && !Double.isNaN(depth)) {
            return sensor.getId() + depth + "m";
        } else {
            return sensor.getId();
        }
    }

    public static String createObservationFeatureOfInterestName(SosSensor sensor, Double depth) {
        return createObservationFeatureOfInterestId(sensor, depth);
    }
}
