package com.axiomalaska.sos.data;

public class SosNetworkImp implements SosNetwork {

	private String id = "";
	private String sourceId = "";
	private String description = "";
	
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
}
