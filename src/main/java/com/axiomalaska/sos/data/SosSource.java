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
		Government–Federal
		Government–State
		Government–Municipal
		Tribal
		Academic
		Industry
		Non-Profit
		Other
		Unknown
	 */
	public String getOperatorSector();
}
