package com.axiomalaska.sos.data;

import java.util.ArrayList;
import java.util.List;

public class SosSensorImp implements SosSensor{
	// -------------------------------------------------------------------------
	// Private Data
	// -------------------------------------------------------------------------

	private List<SosPhenomenon> phenomena = new ArrayList<SosPhenomenon>();
	private String description = "";
	private String id = "";
	private List<SosNetwork> networks = new ArrayList<SosNetwork>();
	private String stationId = "";
	private String sourceId = "";

	// -------------------------------------------------------------------------
	// Public Members
	// -------------------------------------------------------------------------

	public String getId() {
		return id;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getStationId(){
		return stationId;
	}
	
	public void setStationId(String stationId){
		this.stationId = stationId;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<SosPhenomenon> getPhenomena() {
		return phenomena;
	}

	public void setPhenomena(List<SosPhenomenon> phenomena) {
		this.phenomena = phenomena;
	}
	
	/**
	 * A list of networks this station is associated to
	 * @return
	 */
	public List<SosNetwork> getNetworks(){
		return networks;
	}
	
	public void setNetworks(List<SosNetwork> networks){
		this.networks = networks;
	}
	
	public void addNetwork(SosNetwork network){
		networks.add(network);
	}
}
