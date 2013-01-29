package com.axiomalaska.sos.tools;

import com.axiomalaska.sos.data.Location;
import com.axiomalaska.sos.data.SosNetwork;
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
	public String createFeatureOfInterestId(SosStation station, 
			SosSensor sensor, Location location){
		String locationTag = ":" + location.getLatitude() + ":" + location.getLongitude();
		String authority = getAuthority(station);
                
                if (!authority.equals(""))
                    return "urn:ioos:station:" + authority + ":" + station.getId() + ":" + sensor.getId() + locationTag;
                else
                    return "urn:ioos:station:" + station.getId() + ":" + sensor.getId() + locationTag;
	}
	
	public String createFeatureOfInterestName(SosStation station, SosSensor sensor) {
		return station.getFeatureOfInterestName();
	}
	
	public String createFeatureOfInterestId(SosStation station, 
			SosSensor sensor){
                String authority = getAuthority(station);
                if (!authority.equals(""))
                    return "urn:ioos:station:" + authority + ":" + station.getId();
                else
                    return "urn:ioos:station:" + station.getId();
	}
	
	public String createNetworkId(SosNetwork network){
//            if (!network.getId().equals("all"))
//                return "network-" + network.getSourceId();
//            else
//                return "network-all";
            if (!network.getSourceId().equals(network.getId())) {
                return "urn:ioos:network:" + network.getSourceId() + ":" + network.getId();
            } else if (!network.getSourceId().equals("") && !network.getId().equals("all")) {
                return "urn:ioos:network:" + network.getSourceId() + ":all";
            } else {
                return "urn:ioos:network:all";
            }
	}
        
        public String createNetworkProcedure(SosNetwork network) {
            if (!network.getSourceId().equals(network.getId())) {
                return "urn:ioos:network:" + network.getSourceId() + ":" + network.getId();
            } else if (!network.getSourceId().equals("") && !network.getId().equals("all")) {
                return "urn:ioos:network:" + network.getSourceId() + ":all";
            } else {
                return "urn:ioos:network:all";
            }
        }
	
	public String createStationId(SosStation station){
            String authority = getAuthority(station);
            if (!authority.equals(""))
                return "urn:ioos:station:" + authority + ":" + station.getId();
            else
		return "urn:ioos:station:" + station.getId();
	}

	public String createStationShortName(SosStation station) {
		String[] terms = station.getId().split(":");
		if (terms.length == 2) {
			return terms[1];
		} else {
			return "";
		}
	}
	public String createSensorId(SosStation station, SosSensor sensor){
            String authority = getAuthority(station);
            if (!authority.equals(""))
                return "urn:ioos:sensor:" + authority + ":" + station.getId() + ":" + sensor.getId();
            else
		return "urn:ioos:sensor:" + station.getId() + ":" + sensor.getId();
	}
        
        private String getAuthority(SosStation station) {
            String authority = "";
            for (SosNetwork network : station.getNetworks()) {
                if (!"network-all".equals(network.getSourceId())) {
                    authority = network.getSourceId();
                    break;
                }
            }
            return authority;
        }

    public String createObservationFeatureOfInterestId(SosStation station, SosSensor sensor, Double depth) {
        	return station.getFeatureOfInterestName() + " " + sensor.getDescription() + "(" + depth + " meters)";
    }

    public String createObservationFeatureOfInterestName(SosStation station, SosSensor sensor, Double depth) {
        	if (depth != null) {
                    return createSensorId(station, sensor) + depth + "m";
                } else {
                    return createSensorId(station, sensor);
                }
    }
}
