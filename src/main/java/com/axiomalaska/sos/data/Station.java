package com.axiomalaska.sos.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A station represents a location where one or more phenomena are measured. 
 * 
 * @author Lance Finfrock
 */
public class Station {
	
	// -------------------------------------------------------------------------
	// Private Members
	// -------------------------------------------------------------------------
	
	private Double longitude;
	private Double latitude;
	private String foiDescrition;
	private String featureOfInterestId;
	private String procedureId;
	private String description;
	private List<Phenomenon> phenomena = new ArrayList<Phenomenon>();
	
	// -------------------------------------------------------------------------
	// Public Members
	// -------------------------------------------------------------------------

	/**
	 * This method should be overridden to provide observations for a specific 
	 * phenomenon for this station. 
	 * 
	 * @param phenomenon - the phenomenon of the observations to return. 
	 * @param startDate - the start date of the observation to find
	 * @param endDate - the end date of the observation to find.
	 */
	public ObservationCollection getObservationCollection(Phenomenon phenomenon, 
			Calendar startDate, Calendar endDate) {
		return null;
	}
	
	/**
	 * A list of phenomena that this station has readings for
	 */
	public List<Phenomenon> getPhenomena() {
		return phenomena;
	}
	
	/**
	 * The Description of the station. For example 'Anchorage Hillside'
	 * 
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * A Procedure is equivalent to a station in the SOS. This ID is the 
	 * procedure ID that is used in the SOS server. This ID should be unique for
	 * each station. For example urn:ogc:object:feature:Sensor:11111
	 * 
	 * Maximum characters 100
	 * 
	 * If characters are over 100 they will be truncated to 100
	 */
	public String getProcedureId() {
		return procedureId;
	}

	/**
	 * A FeatureOfInterest is a named location where the station is located. Each
	 * station needs a unique ID for the location where the station is. 
	 * An example is foi_11111
	 * 
	 * Maximum characters 100
	 * 
	 * If characters are over 100 they will be truncated to 100
	 */
	public String getFeatureOfInterestId() {
		return featureOfInterestId;
	}

	/**
	 * This contains a description of the location of the station
	 * 
	 * Maximum characters 100
	 * 
	 * If characters are over 100 they will be truncated to 100
	 */
	public String getFoiDescription() {
		return foiDescrition;
	}

	/**
	 * The longitude of the station
	 */
	public Double getLongitude() {
		return longitude;
	}

	/**
	 * The latitude of the station
	 */
	public Double getLatitude() {
		return latitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public void setFoiDescrition(String foiDescrition) {
		this.foiDescrition = foiDescrition;
	}

	public void setFeatureOfInterestId(String featureOfInterestId) {
		this.featureOfInterestId = featureOfInterestId;
	}

	public void setProcedureId(String procedureId) {
		this.procedureId = procedureId;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public void setPhenomena(List<Phenomenon> phenomenons) {
		this.phenomena = phenomenons;
	}
}
