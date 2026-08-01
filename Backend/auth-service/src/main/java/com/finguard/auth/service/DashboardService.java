package com.finguard.auth.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.finguard.auth.dto.dashboard.DashboardResponse;
import com.finguard.auth.dto.dashboard.LoanStatsResponse;
import com.finguard.auth.dto.dashboard.TransactionStatsResponse;
import com.finguard.auth.repository.UserRepository;

@Service
public class DashboardService {

	private final UserRepository userRepository;
	private final DashboardClientService dashboardClientService;

	public DashboardService(UserRepository userRepository, DashboardClientService dashboardClientService) {
		this.userRepository = userRepository;
		this.dashboardClientService = dashboardClientService;
	}

	@Cacheable("dashboard")
	public DashboardResponse getDashboard() {

		TransactionStatsResponse transactionStats = dashboardClientService.getTransactionStats();

		LoanStatsResponse loanStats = dashboardClientService.getLoanStats();

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