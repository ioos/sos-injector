package com.axiomalaska.sos.data;

public class SosNetworkImp implements SosNetwork {

	private String id = "";
	private String sourceId = "";
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getSourceId() {
		return sourceId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
}
