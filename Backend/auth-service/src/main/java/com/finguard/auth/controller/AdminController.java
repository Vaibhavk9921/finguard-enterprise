package com.finguard.auth.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finguard.auth.common.ApiResponse;
import com.finguard.auth.dto.UserResponse;
import com.finguard.auth.dto.UserStatsResponse;
import com.finguard.auth.dto.dashboard.DashboardResponse;
import com.finguard.auth.service.AdminStatsService;
import com.finguard.auth.service.AuthService;
import com.finguard.auth.service.DashboardService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Admin Management", description = "Administrative APIs")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

	private final AuthService authService;
	private final AdminStatsService adminStatsService;
	private final DashboardService dashboardService;

	public AdminController(AuthService authService, AdminStatsService adminStatsService,
			DashboardService dashboardService) {

		this.authService = authService;
		this.adminStatsService = adminStatsService;
		this.dashboardService = dashboardService;
	}

	@Operation(summary = "Get All Users", description = "Returns a list of all registered users.")
	@ApiResponses({
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Dashboard fetched successfully"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Access denied"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@GetMapping("/users")
	@PreAuthorize("hasRole('ADMIN')")
	public List<UserResponse> getAllUsers() {
		return authService.getAllUsers();
	}

	@Operation(summary = "Get User Statistics", description = "Returns user statistics including total users and admins.")
	@ApiResponses({
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Dashboard fetched successfully"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Access denied"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@GetMapping("/stats/users")
	@PreAuthorize("hasRole('ADMIN')")
	public UserStatsResponse getUserStats() {
		return adminStatsService.getUserStats();
	}

	@Operation(summary = "Get Dashboard Statistics", description = "Returns aggregated dashboard statistics from User, Transaction and Loan services.")
	@ApiResponses({
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Dashboard fetched successfully"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Access denied"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@GetMapping("/dashboard")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<DashboardResponse>> getDashboard() {

		DashboardResponse response = dashboardService.getDashboard();

		return ResponseEntity.ok(ApiResponse.success("Dashboard Fetched Successfully", response));
	}
}