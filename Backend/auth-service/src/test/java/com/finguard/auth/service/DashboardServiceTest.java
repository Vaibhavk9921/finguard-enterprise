package com.finguard.auth.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.finguard.auth.dto.dashboard.DashboardResponse;
import com.finguard.auth.dto.dashboard.LoanStatsResponse;
import com.finguard.auth.dto.dashboard.TransactionStatsResponse;
import com.finguard.auth.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class DashboardServiceTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private DashboardClientService dashboardClientService;

	@InjectMocks
	private DashboardService dashboardService;

	private TransactionStatsResponse transactionStats;
	private LoanStatsResponse loanStats;

	@BeforeEach
	void setUp() {

		transactionStats = new TransactionStatsResponse();
		transactionStats.setTotalAccounts(10L);
		transactionStats.setTotalBalance(new BigDecimal("250000"));
		transactionStats.setTotalTransactions(75L);

		loanStats = new LoanStatsResponse();
		loanStats.setTotalLoans(30L);
		loanStats.setApprovedLoans(22L);
		loanStats.setRejectedLoans(8L);
	}

	@Test
	void shouldReturnDashboardStatistics() {

		when(userRepository.count()).thenReturn(100L);

		when(dashboardClientService.getTransactionStats()).thenReturn(transactionStats);

		when(dashboardClientService.getLoanStats()).thenReturn(loanStats);

		DashboardResponse response = dashboardService.getDashboard();

		assertEquals(100L, response.getTotalUsers());
		assertEquals(10L, response.getTotalAccounts());
		assertEquals(new BigDecimal("250000"), response.getTotalBalance());
		assertEquals(75L, response.getTotalTransactions());

		assertEquals(30L, response.getTotalLoans());
		assertEquals(22L, response.getApprovedLoans());
		assertEquals(8L, response.getRejectedLoans());

		verify(userRepository).count();
		verify(dashboardClientService).getTransactionStats();
		verify(dashboardClientService).getLoanStats();
	}
}