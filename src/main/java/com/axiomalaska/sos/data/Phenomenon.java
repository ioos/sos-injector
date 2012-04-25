package com.axiomalaska.sos.data;

/**
 * Contains information for a phenomenon. 
 * 
 * Examples of phenomenons are:
 * 		Air temperature
 * 		Wind speed
 * 		Wind direction
 * 
 * @author Lance Finfrock
 */
public class Phenomenon {
	
	// -------------------------------------------------------------------------
	// Private Data
	// -------------------------------------------------------------------------

	private String name;
	private String tag;
	private String units;

	// -------------------------------------------------------------------------
	// Public Members
	// -------------------------------------------------------------------------

	/**
	 * The name of the phenomenon. For example 'Wind Speed'
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * The defining tag for the phenomenon. For example urn:x-ogc:def:phenomenon:IOOS:0.0.1:wind_speed
	 * 
	 * Maximum characters 100
	 * 
	 * If characters are over 100 they will be truncated to 100
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * The units that observations are measured in. For example 'm/s'
	 * 
	 * Maximum characters 30
	 * 
	 * If characters are over 30 they will be truncated to 30
	 */
	public String getUnits() {
		return units;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public void setUnits(String units) {
		this.units = units;
	}
}
