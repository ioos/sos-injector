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

	public List<SosPhenomenon> getPhenomena() {
		return phenomena;
	}

	public void setPhenomena(List<SosPhenomenon> phenomena) {
		this.phenomena = phenomena;
	}
}
