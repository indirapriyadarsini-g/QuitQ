package com.quitq.ECom.dto;

public class VendorAddressDto {
	private String city;
	private String state;
	private String landmark;
	private int pincode;
	private String status;
	public VendorAddressDto() {
		super();
		// TODO Auto-generated constructor stub
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
	public String getLandmark() {
		return landmark;
	}
	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}
	public int getPincode() {
		return pincode;
	}
	public void setPincode(int pincode) {
		this.pincode = pincode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStreetdetails() {
		return streetdetails;
	}
	public void setStreetdetails(String streetdetails) {
		this.streetdetails = streetdetails;
	}
	private String streetdetails;
	

}