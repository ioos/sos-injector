package com.axiomalaska.sos.data;

public class SosNetworkImp implements SosNetwork {

	private String id = "";
	private String sourceId = "";
	private String description = "";
	private String offeringName = "";
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getSourceId() {
		return sourceId;
	}
	
	@Override
	public String getDescription(){
		return description;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	
	public void setDescription(String description){
		this.description = description;
	}

	public String getOfferingName() {
		return offeringName;
	}

	public void setOfferingName(String offeringName) {
		this.offeringName = offeringName;
	}
}
