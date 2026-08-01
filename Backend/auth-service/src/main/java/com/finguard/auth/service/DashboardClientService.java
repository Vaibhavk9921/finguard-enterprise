package com.finguard.auth.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.finguard.auth.client.LoanServiceClient;
import com.finguard.auth.client.TransactionServiceClient;
import com.finguard.auth.dto.dashboard.LoanStatsResponse;
import com.finguard.auth.dto.dashboard.TransactionStatsResponse;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class DashboardClientService {

	private final TransactionServiceClient transactionClient;
	private final LoanServiceClient loanClient;
	private static final Logger log = LoggerFactory.getLogger(DashboardClientService.class);

	public DashboardClientService(TransactionServiceClient transactionClient, LoanServiceClient loanClient) {

		this.transactionClient = transactionClient;
		this.loanClient = loanClient;
	}

	@Retry(name = "transactionService", fallbackMethod = "transactionFallback")
	@CircuitBreaker(name = "transactionService", fallbackMethod = "transactionFallback")
	public TransactionStatsResponse getTransactionStats() {

		log.info("Calling Transaction Service...");

		return transactionClient.getTransactionStats();
	}

	public TransactionStatsResponse transactionFallback(Exception ex) {

		log.warn("Transaction Service is unavailable. Returning fallback response.", ex);

		TransactionStatsResponse response = new TransactionStatsResponse();

		response.setTotalAccounts(0L);
		response.setTotalBalance(BigDecimal.ZERO);
		response.setTotalTransactions(0L);

		return response;
	}

	@Retry(name = "loanService", fallbackMethod = "loanFallback")
	@CircuitBreaker(name = "loanService", fallbackMethod = "loanFallback")
	public LoanStatsResponse getLoanStats() {

		log.info("Calling Loan Service...");

		return loanClient.getLoanStats();
	}

	public LoanStatsResponse loanFallback(Exception ex) {

		log.warn("Loan Service is unavailable. Returning fallback response.", ex);

		LoanStatsResponse response = new LoanStatsResponse();

		response.setTotalLoans(0L);
		response.setApprovedLoans(0L);
		response.setRejectedLoans(0L);

		return response;
	}
}