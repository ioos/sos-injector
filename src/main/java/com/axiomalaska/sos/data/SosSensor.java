package com.axiomalaska.sos.data;

import java.util.List;

import com.axiomalaska.phenomena.Phenomenon;

public interface SosSensor {

	public String getId();
	
	public String getDescription();

	public List<Phenomenon> getPhenomena();
	
	/**
	 * A list of networks this station is associated to
	 * @return
	 */
	public List<SosNetwork> getNetworks();
}
