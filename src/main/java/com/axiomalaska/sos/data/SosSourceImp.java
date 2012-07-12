package com.axiomalaska.sos.data;

public class SosSourceImp implements SosSource {

	private String id;
	private String name;
	private String country;
	private String email;
	private String webAddress;
	private String operatorSector;
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getCountry() {
		return country;
	}

	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public String getWebAddress() {
		return webAddress;
	}

	@Override
	public String getOperatorSector() {
		return operatorSector;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setWebAddress(String webAddress) {
		this.webAddress = webAddress;
	}

	public void setOperatorSector(String operatorSector) {
		this.operatorSector = operatorSector;
	}

}
