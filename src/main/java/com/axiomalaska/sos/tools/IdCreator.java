package com.axiomalaska.sos.tools;

import com.axiomalaska.sos.data.Location;
import com.axiomalaska.sos.data.SosNetwork;
import com.axiomalaska.sos.data.SosSensor;
import com.axiomalaska.sos.data.SosStation;

/**
 * This class is used to create the ID of the objects of the SOS. This was created
 * because of the multiple changes being made to the IDs. 
 * 
 * @author Lance Finfrock
 *
 */
public class IdCreator {
	
	public String createFeatureOfInterestId(SosStation station, 
			SosSensor sensor, Location location){
		String locationTag = ":" + location.getLatitude() + ":" + location.getLongitude();
		
		return "urn:ioos:station:" + station.getId() + ":" + sensor.getId() + locationTag;
	}
	
	public String createFeatureOfInterestName(SosStation station, SosSensor sensor) {
		return station.getFeatureOfInterestName();
	}
	
	public String createFeatureOfInterestId(SosStation station, 
			SosSensor sensor){
		return "urn:ioos:station:" + station.getId();
	}
	
	public String createNetworkId(SosNetwork network){
		return "urn:ioos:network:" + network.getSourceId() + ":" + network.getId();
	}
	
	public String createStationId(SosStation station){
		return "urn:ioos:station:" + station.getId();
	}
	
	public String createSensorId(SosStation station, SosSensor sensor){
		return "urn:ioos:sensor:" + station.getId() + ":" + sensor.getId();
	}
}
