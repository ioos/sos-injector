package com.axiomalaska.sos.tools;

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

	public String createStationFeatureOfInterestId(SosStation station) {
		return createStationId(station);
	}

	public String createSensorFeatureOfInterestName(SosStation station,
			SosSensor sensor) {
		return station.getFeatureOfInterestName();
	}

	public String createObservationFeatureOfInterestName(SosStation station,
			SosSensor sensor, Double depth) {
		return station.getFeatureOfInterestName() + " "
				+ sensor.getDescription() + "(" + depth + " meters)";
	}

	public String createStationFeatureOfInterestName(SosStation station) {
		return station.getFeatureOfInterestName();
	}

	public String createSensorFeatureOfInterestId(SosStation station,
			SosSensor sensor) {
		return createSensorId(station, sensor);
	}

	public String createObservationFeatureOfInterestId(SosStation station,
			SosSensor sensor, Double depth) {
		if (depth != null) {
			return createSensorId(station, sensor) + depth + "m";
		} else {
			return createSensorId(station, sensor);
		}
	}

	public String createNetworkId(SosNetwork network) {
		return "urn:ioos:network:" + network.getSourceId() + ":"
				+ network.getId();
	}

	public String createStationId(SosStation station) {
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

	public String createSensorId(SosStation station, SosSensor sensor) {
		return "urn:ioos:sensor:" + station.getId() + ":" + sensor.getId();
	}
}
