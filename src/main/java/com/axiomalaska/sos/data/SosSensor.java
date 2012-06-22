package com.axiomalaska.sos.data;

import java.util.List;

public interface SosSensor {

	public String getId();
	
	public String getDescription();

	public List<SosPhenomenon> getPhenomena();
	
	/**
	 * A list of networks this station is associated to
	 * @return
	 */
	public List<SosNetwork> getNetworks();
}
