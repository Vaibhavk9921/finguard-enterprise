package com.finguard.auth.controller;

import com.finguard.auth.dto.ApiResponse;
import com.finguard.auth.dto.LoginRequest;
import com.finguard.auth.dto.LoginResponse;
import com.finguard.auth.dto.RegisterRequest;
import com.finguard.auth.dto.UserValidationResponse;
import com.finguard.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication", description = "Authentication & Authorization APIs")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@GetMapping("/hello")
	public String hello() {
		return "Auth Service Running Successfully";
	}

	@Operation(summary = "Register User", description = "Register a new user")
	@PostMapping("/register")
	public ApiResponse<Void> register(@Valid @RequestBody RegisterRequest request) {

		return authService.register(request);
	}

	@Operation(summary = "Login", description = "Authenticate user and generate JWT token")
	@PostMapping("/login")
	public LoginResponse login(@Valid @RequestBody LoginRequest request) {
		String token = authService.login(request);
		return new LoginResponse(token);
	}

	@GetMapping("/validate/{userId}")
	public UserValidationResponse validateUser(@PathVariable("userId") Long userId) {

		return authService.validateUser(userId);
	}

	@PostMapping("/register-admin")
	public ApiResponse<Void> registerAdmin(@Valid @RequestBody RegisterRequest request) {
		return authService.registerAdmin(request);
	}
}