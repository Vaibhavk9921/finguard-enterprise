package com.finguard.auth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.finguard.auth.dto.dashboard.LoanStatsResponse;

@FeignClient(name = "LOAN-SERVICE")
public interface LoanServiceClient {
	@GetMapping("/api/admin/stats/loans")
	LoanStatsResponse getLoanStats();
}