package com.finguard.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class RegisterRequest {
	@NotBlank(message = "Name is Required")
	private String name;
	@Email(message = "Invalid Email Format")
	private String email;
	@NotBlank(message = "Password is Required")
	private String password;

	public RegisterRequest() {
		// TODO Auto-generated constructor stub
	}

	public RegisterRequest(@NotBlank(message = "Name is Required") String name,
			@Email(message = "Invalid Email Format") String email,
			@NotBlank(message = "Password is Required") String password) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
