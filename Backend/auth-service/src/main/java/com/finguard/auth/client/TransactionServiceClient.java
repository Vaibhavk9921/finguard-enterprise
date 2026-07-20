package com.finguard.auth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.finguard.auth.dto.dashboard.TransactionStatsResponse;

@FeignClient(name = "TRANSACTION-SERVICE")
public interface TransactionServiceClient {
	@GetMapping("/api/admin/stats/accounts")
	TransactionStatsResponse getTransactionStats();
}