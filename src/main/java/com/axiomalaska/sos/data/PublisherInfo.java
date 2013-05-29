package com.axiomalaska.sos.data;

public class PublisherInfo{

	private String name;
	private String country;
	private String email;
	private String webAddress;
	private String code;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWebAddress() {
		return webAddress;
	}
	public void setWebAddress(String webAddress) {
		this.webAddress = webAddress;
	}
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code.toLowerCase();
    }	
}
