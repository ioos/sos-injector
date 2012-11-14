package com.axiomalaska.sos.data;

public interface SosSource {
	/**
	 * Name of the source for the station. 
	 * 
	 * @return
	 */
	public String getId();

	public String getName();

	public String getCountry();

	public String getEmail();

	public String getWebAddress();

	/**
		gov_federal
		gov_state
		gov_municipal
		tribal
		academic
		industry
		nonprofit
		other
		unknown
	 */
	public String getOperatorSector();
	
	public String getAddress();
	
	public String getCity();
	
	public String getState();
	
	public String getZipcode();
}
