package com.axiomalaska.sos.data;

public class SosSourceImp implements SosSource {

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
	
	@Override
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

}
