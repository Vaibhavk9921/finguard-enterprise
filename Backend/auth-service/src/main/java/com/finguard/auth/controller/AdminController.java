package com.finguard.auth.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finguard.auth.dto.UserResponse;
import com.finguard.auth.dto.UserStatsResponse;
import com.finguard.auth.service.AdminStatsService;
import com.finguard.auth.service.AuthService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	private final AuthService authService;
	private final AdminStatsService adminStatsService;

	public AdminController(AuthService authService, AdminStatsService adminStatsService) {
		this.authService = authService;
		this.adminStatsService = adminStatsService;
	}

	@GetMapping("/users")
	@PreAuthorize("hasRole('ADMIN')")
	public List<UserResponse> getAllUsers() {
		return authService.getAllUsers();
	}

	@GetMapping("/stats/users")
	public UserStatsResponse getUserStats() {
		return adminStatsService.getUserStats();
	}
}