package com.axiomalaska.sos.data;

/**
 * Contains information for a phenomenon. 
 * 
 * Examples of phenomena are:
 * 		Air temperature
 * 		Wind speed
 * 		Wind direction
 * 
 * @author Lance Finfrock
 */
public interface SosPhenomenon {
	
	/**
	 * The name of the phenomenon. For example 'Wind Speed'
	 * @return
	 */
	public String getName();

	/**
	 * The defining tag for the phenomenon. For example urn:x-ogc:def:phenomenon:IOOS:0.0.1:wind_speed
	 * 
	 * Maximum characters 100
	 * 
	 * If characters are over 100 they will be truncated to 100
	 */
	public String getId();

	/**
	 * The units that observations are measured in. For example 'm/s'
	 * 
	 * Maximum characters 30
	 * 
	 * If characters are over 30 they will be truncated to 30
	 */
	public String getUnits();
}
