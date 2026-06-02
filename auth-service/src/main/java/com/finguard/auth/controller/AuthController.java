package com.finguard.auth.controller;

import com.finguard.auth.dto.LoginRequest;
import com.finguard.auth.dto.LoginResponse;
import com.finguard.auth.dto.RegisterRequest;
import com.finguard.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

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

	@PostMapping("/register")
	public String register(@Valid @RequestBody RegisterRequest request) {

		return authService.register(request);
	}

	@PostMapping("/login")
	public LoginResponse login(@RequestBody LoginRequest request) {
		String token = authService.login(request);
		return new LoginResponse(token);
	}
}