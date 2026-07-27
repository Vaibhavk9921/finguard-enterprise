package com.finguard.transaction.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.finguard.transaction.dto.TransactionStatsResponse;
import com.finguard.transaction.repository.AccountRepository;
import com.finguard.transaction.repository.TransactionRepository;

@ExtendWith(MockitoExtension.class)
class AdminStatsServiceTest {

	@Mock
	private AccountRepository accountRepository;

	@Mock
	private TransactionRepository transactionRepository;

	@InjectMocks
	private AdminStatsService adminStatsService;

	@Test
	void shouldReturnTransactionStatistics() {

		when(accountRepository.count()).thenReturn(50L);
		when(accountRepository.getTotalBalance()).thenReturn(new BigDecimal("1500000"));
		when(transactionRepository.count()).thenReturn(350L);

		TransactionStatsResponse response = adminStatsService.getTransactionStats();

		assertEquals(50L, response.getTotalAccounts());
		assertEquals(new BigDecimal("1500000"), response.getTotalBalance());
		assertEquals(350L, response.getTotalTransactions());

		verify(accountRepository).count();
		verify(accountRepository).getTotalBalance();
		verify(transactionRepository).count();
	}
}