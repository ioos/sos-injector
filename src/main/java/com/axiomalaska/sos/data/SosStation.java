package com.axiomalaska.sos.data;

import java.util.List;

/**
 * A station represents a actual physical station. A station contains a location 
 * where one or more phenomena are measured. 
 * 
 * @author Lance Finfrock
 */
public interface SosStation {
	/**
	 * A list of phenomena that this station has readings for
	 */
	public List<SosSensor> getSensors();
	
	/**
	 * Is the station moving
	 */
	public boolean isMoving();

	/**
	 * This ID should be unique for each station. For example '11111'
	 */
	public String getId();

	/**
	 * The default name of the location with which the station takes its 
	 * reading from.
	 * 
	 * Maximum characters 80
	 * 
	 * If characters are over 100 they will be truncated to 80
	 */
	public String getFeatureOfInterestName();

	/**
	 * The location of the station
	 */
	public Location getLocation() ;
	
	/**
	 * Name of the source for the station. 
	 * 
	 * @return
	 */
	public String getSourceId();
	
	/**
	 * A list of networks this station is associated to
	 * @return
	 */
	public List<SosNetwork> getNetworks();
}
