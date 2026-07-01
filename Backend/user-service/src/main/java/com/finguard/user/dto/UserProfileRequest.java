package com.finguard.user.dto;

public class UserProfileRequest {
	private Long userId;
	private String fullName;
	private String phoneNumber;
	private String panNumber;
	private String aadharNumber;
	private String address;

	public UserProfileRequest() {
	}

	public UserProfileRequest(Long userId, String fullName, String phoneNumber, String panNumber, String aadharNumber,
			String address) {
		super();
		this.userId = userId;
		this.fullName = fullName;
		this.phoneNumber = phoneNumber;
		this.panNumber = panNumber;
		this.aadharNumber = aadharNumber;
		this.address = address;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public String getAadharNumber() {
		return aadharNumber;
	}

	public void setAadharNumber(String aadharNumber) {
		this.aadharNumber = aadharNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}