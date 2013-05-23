package com.axiomalaska.sos.data;

public class SosSource {

	private String id;
	private String name;
	private String country;
	private String email;
	private String webAddress;
	private String operatorSector;
	private String address;
	private String city;
	private String state;
	private String zipcode;
	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getCountry() {
		return country;
	}

	public String getEmail() {
		return email;
	}

	public String getWebAddress() {
		return webAddress;
	}

	/**
    gov_federal
    gov_state
    gov_municipal
    tribal
    academic
    industry
    nonprofit
    other
    unknown
    */
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
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

}
