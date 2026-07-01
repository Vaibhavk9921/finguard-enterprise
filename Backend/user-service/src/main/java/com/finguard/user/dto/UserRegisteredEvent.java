package com.finguard.user.dto;

public class UserRegisteredEvent {
	private Long userId;
	private String email;
	private String role;

	public UserRegisteredEvent() {
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public UserRegisteredEvent(Long userId, String email, String role) {
		super();
		this.userId = userId;
		this.email = email;
		this.role = role;
	}

}