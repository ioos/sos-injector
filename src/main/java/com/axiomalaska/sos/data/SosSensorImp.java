package com.axiomalaska.sos.data;

import java.util.ArrayList;
import java.util.List;

import com.axiomalaska.phenomena.Phenomenon;

public class SosSensorImp implements SosSensor{
	// -------------------------------------------------------------------------
	// Private Data
	// -------------------------------------------------------------------------

	private List<Phenomenon> phenomena = new ArrayList<Phenomenon>();
	private String description = "";
	private String id = "";
	private List<SosNetwork> networks = new ArrayList<SosNetwork>();

	// -------------------------------------------------------------------------
	// Public Members
	// -------------------------------------------------------------------------

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Phenomenon> getPhenomena() {
		return phenomena;
	}

	public void setPhenomena(List<Phenomenon> phenomena) {
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
