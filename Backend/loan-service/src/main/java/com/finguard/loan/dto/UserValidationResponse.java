package com.finguard.loan.dto;

public class UserValidationResponse {

	private Long id;
	private String email;
	private boolean exists;

	public UserValidationResponse() {
	}

	public UserValidationResponse(Long id, String email, boolean exists) {
		super();
		this.id = id;
		this.email = email;
		this.exists = exists;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isExists() {
		return exists;
	}

	public void setExists(boolean exists) {
		this.exists = exists;
	}

}