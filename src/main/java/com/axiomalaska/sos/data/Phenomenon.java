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
public class Phenomenon {
	
	// -------------------------------------------------------------------------
	// Private Data
	// -------------------------------------------------------------------------

	private String name;
	private String id;
	private String units;
	private PhenomenonDepth depth;

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
	public String getId() {
		return id;
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
	
	/**
	 * If there is a station with multiple of the same phenomena but different 
	 * depths one must get the depth to differentiate the phenomena. 
	 * @return
	 */
	public PhenomenonDepth getDepth(){
		return depth;
	}
	
	public void setDepth(PhenomenonDepth depth){
		this.depth = depth;
	}
	
	public void setDepth(double value, String units){
		this.depth = new PhenomenonDepth(value, units);
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setUnits(String units) {
		this.units = units;
	}
	
	public String toString(){
		return id;
	}
}
