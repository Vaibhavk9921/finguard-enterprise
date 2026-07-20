package com.finguard.auth.service;

import org.springframework.stereotype.Service;

import com.finguard.auth.client.LoanServiceClient;
import com.finguard.auth.client.TransactionServiceClient;
import com.finguard.auth.dto.dashboard.DashboardResponse;
import com.finguard.auth.dto.dashboard.LoanStatsResponse;
import com.finguard.auth.dto.dashboard.TransactionStatsResponse;
import com.finguard.auth.repository.UserRepository;

@Service
public class DashboardService {
	private final TransactionServiceClient transactionClient;
	private final UserRepository userRepository;
	private final LoanServiceClient loanClient;

	public DashboardService(UserRepository userRepository, TransactionServiceClient transactionClient,
			LoanServiceClient loanClient) {
		this.userRepository = userRepository;
		this.transactionClient = transactionClient;
		this.loanClient = loanClient;
	}

	public DashboardResponse getDashboard() {
		TransactionStatsResponse transactionStats = transactionClient.getTransactionStats();

		LoanStatsResponse loanStats = loanClient.getLoanStats();

		DashboardResponse response = new DashboardResponse();

		response.setTotalUsers(userRepository.count());

		response.setTotalAccounts(transactionStats.getTotalAccounts());
		response.setTotalBalance(transactionStats.getTotalBalance());
		response.setTotalTransactions(transactionStats.getTotalTransactions());

		response.setTotalLoans(loanStats.getTotalLoans());
		response.setApprovedLoans(loanStats.getApprovedLoans());
		response.setRejectedLoans(loanStats.getRejectedLoans());

		return response;
	}
}