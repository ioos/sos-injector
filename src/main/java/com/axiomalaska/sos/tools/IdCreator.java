package com.axiomalaska.sos.tools;

import com.axiomalaska.sos.data.Location;
import com.axiomalaska.sos.data.SosNetwork;
import com.axiomalaska.sos.data.SosSensor;
import com.axiomalaska.sos.data.SosSource;
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
		SosSource source = station.getSource();
		String locationTag = ":" + location.getLatitude() + ":" + location.getLongitude();
		
		return "urn:ioos:foi:" + source.getId() + ":" + 
			station.getId() + ":" + sensor.getId() + locationTag;
	}
	
	public String createFeatureOfInterestName(SosStation station, SosSensor sensor) {
		return station.getFeatureOfInterestName();
	}
	
	public String createFeatureOfInterestId(SosStation station, 
			SosSensor sensor){
		SosSource source = station.getSource();
		return "urn:ioos:foi:" + source.getId() + ":" + 
			station.getId();
	}
	
	public String createNetworkId(SosNetwork network){
		return "urn:ioos:network:" + network.getSourceId() + ":" + network.getId();
	}
	
	public String createStationId(SosStation station){
		SosSource source = station.getSource();
		return "urn:ioos:station:" + source.getId() + ":" + station.getId();
	}
	
	public String createSensorId(SosStation station, SosSensor sensor){
		SosSource source = station.getSource();
		return "urn:ioos:sensor:" + source.getId() + ":" + 
				station.getId() + ":" + sensor.getId();
	}
}
