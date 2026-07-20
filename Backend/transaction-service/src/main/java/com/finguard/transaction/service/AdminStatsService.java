package com.finguard.transaction.service;

import org.springframework.stereotype.Service;

import com.finguard.transaction.dto.TransactionStatsResponse;
import com.finguard.transaction.repository.AccountRepository;
import com.finguard.transaction.repository.TransactionRepository;

@Service
public class AdminStatsService {
	private final AccountRepository accountRepository;
	private final TransactionRepository transactionRepository;

	public AdminStatsService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
		this.accountRepository = accountRepository;
		this.transactionRepository = transactionRepository;
	}

	public TransactionStatsResponse getTransactionStats() {
		return new TransactionStatsResponse(accountRepository.count(), accountRepository.getTotalBalance(),
				transactionRepository.count());
	}
}