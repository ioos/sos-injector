package com.axiomalaska.sos.data;

public class DocumentMemberImp implements DocumentMember {

	private String name = "";
	private String arcrole = "";
	private String description = "";
	private String format = "";
	private String onlineResource = "";
	
	public void setName(String name) {
		this.name = name;
	}

	public void setArcrole(String arcrole) {
		this.arcrole = arcrole;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public void setOnlineResource(String onlineResource) {
		this.onlineResource = onlineResource;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getArcrole() {
		return arcrole;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public String getFormat() {
		return format;
	}

	@Override
	public String getOnlineResource() {
		return onlineResource;
	}

}
