package com.axiomalaska.sos.data;

/**
 * Represents the depth where a phenomena readings are taken from.
 * 
 * When a station has multiple sensors reading the same phenomenon at different
 * depths this object must be used to differentiate the sensors in the SOS.
 * 
 * @author Lance Finfrock
 */
public class PhenomenonDepth {

	// -------------------------------------------------------------------------
	// Private Data
	// -------------------------------------------------------------------------

	private double value;
	private String units;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------

	public PhenomenonDepth(double value, String units) {
		this.value = value;
		this.units = units;
	}
	
	// -------------------------------------------------------------------------
	// Public Members
	// -------------------------------------------------------------------------

	/**
	 * The depth 
	 */
	public double getValue() {
		return value;
	}
	
	/**
	 * The units of the depth
	 */
	public String getUnits() {
		return units;
	}
	
	public void setValue(double value) {
		this.value = value;
	}
	
	public void setUnits(String units) {
		this.units = units;
	}
	
	public String toString(){
		return value + " " + units;
	}
}
