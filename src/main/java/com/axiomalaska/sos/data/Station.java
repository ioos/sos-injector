package com.axiomalaska.sos.data;

import java.util.ArrayList;
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
	
	private Location location;
	private String featureOfInterestName;
	private String sourceName;
	private String id;
	private boolean isMoving = false;
	private List<Phenomenon> phenomena = new ArrayList<Phenomenon>();
	
	// -------------------------------------------------------------------------
	// Public Members
	// -------------------------------------------------------------------------
	
	/**
	 * A list of phenomena that this station has readings for
	 */
	public List<Phenomenon> getPhenomena() {
		return phenomena;
	}
	
	/**
	 * Is the station moving
	 */
	public boolean isMoving() {
		return isMoving;
	}

	/**
	 * This ID should be unique for each station. For example '11111'
	 */
	public String getId() {
		return id;
	}

	/**
	 * The default name of the location with which the station takes its 
	 * reading from.
	 * 
	 * Maximum characters 80
	 * 
	 * If characters are over 100 they will be truncated to 80
	 */
	public String getFeatureOfInterestName() {
		return featureOfInterestName;
	}

	/**
	 * The location of the station
	 */
	public Location getLocation() {
		return location;
	}
	
	/**
	 * Name of the source for the station. 
	 * 
	 * @return
	 */
	public String getSourceName() {
		return sourceName;
	}
	
	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}
	
	public void setSourceName(String sourceName){
		this.sourceName = sourceName;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public void setFeatureOfInterestName(String featureOfInterestName) {
		this.featureOfInterestName = featureOfInterestName;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public void setPhenomena(List<Phenomenon> phenomenons) {
		this.phenomena = phenomenons;
	}
	
	public String toString(){
		return "ID " + id + " " + featureOfInterestName;
	}
}
