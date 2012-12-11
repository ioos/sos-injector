package com.axiomalaska.sos.data;

/**
 * This interface represents a Network in an SOS. A Network is a tag used to 
 * collect sensors or stations across all the stations and sources. For example 
 * one can create a Network for all the Air temperature sensors.
 * 
 * @author Lance Finfrock
 */
public interface SosNetwork {

	/**
	 * This ID should be unique for each network
	 * 
	 * example "oceanacidif"
	 * @return
	 */
	public String getId();
	
	public String getDescription();
	
	/**
	 * The ID of the Network's source
	 * example "nanoos"
	 * @return
	 */
	public String getSourceId();

	public String getLongName();

	public String getShortName();
}
