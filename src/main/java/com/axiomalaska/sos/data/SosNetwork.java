package com.axiomalaska.sos.data;

public interface SosNetwork {

	/**
	 * This ID should be unique for each network
	 * 
	 * example "oceanacidif"
	 * @return
	 */
	public String getId();
	
	/**
	 * The ID of the Network's source
	 * example "nanoos"
	 * @return
	 */
	public String getSourceId();
}
