package com.axiomalaska.sos.data;

public class SosNetworkImp implements SosNetwork {

	private String id = "";
	private String sourceId = "";
	private String description = "";
	private String longName = "";
	private String shortName = "";

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
	
	@Override
	public String getLongName() {
		return longName;
	}

	@Override
	public String getShortName() {
		return shortName;
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

	public void setLongName(String longName) {
		this.longName = longName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
}
