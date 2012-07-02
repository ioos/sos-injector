package com.axiomalaska.sos.data;

/**
 * The Location object that contains a Latitude and Longitude
 * 
 * @author Lance Finfrock
 */
public class Location {

	// -------------------------------------------------------------------------
	// Private Data
	// -------------------------------------------------------------------------
	
	private double latitude;
	private double longitude;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public Location(double latitude, double longitude){
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	// -------------------------------------------------------------------------
	// Public Members
	// -------------------------------------------------------------------------
	
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public String toString(){
		return "Lat: " + latitude + " Long:" + longitude;
	}
}
