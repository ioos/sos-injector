package com.axiomalaska.sos.tools;

import com.axiomalaska.phenomena.Phenomenon;
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
    //TODO review feature of interest id/name functions. do we really want lat/lng in the id?
    //can't we just have one function with optional height?
	public static String createFeatureOfInterestId(SosSensor sensor, Location location){
		return sensor.getId() + ":" + location.getLatitude() + ":" + location.getLongitude();
	}
	
	public static String createFeatureOfInterestName(SosStation station, SosSensor sensor) {
		return station.getFeatureOfInterestName();
	}
	
	public static String createFeatureOfInterestId(SosStation station, SosSensor sensor){
	    return station.getId();
	}

    public static String createObservationFeatureOfInterestId(SosSensor sensor, Double height) {
        if (height != null && !Double.isNaN(height)) {
            return sensor.getId() + height + "m";
        } else {
            return sensor.getId();
        }
    }

    public static String createObservationFeatureOfInterestName(SosSensor sensor, Double height) {
        return createObservationFeatureOfInterestId(sensor, height);
    }

    public static String createResultTemplateId(SosSensor sensor, Phenomenon phenomenon, Double height) {
        return createObservationFeatureOfInterestId(sensor, height) + ":" + phenomenon.getId();       
    }
}
