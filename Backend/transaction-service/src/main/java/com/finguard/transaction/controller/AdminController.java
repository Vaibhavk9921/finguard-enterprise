package com.finguard.transaction.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finguard.transaction.dto.TransactionStatsResponse;
import com.finguard.transaction.entity.Account;
import com.finguard.transaction.entity.Transaction;
import com.finguard.transaction.service.AccountService;
import com.finguard.transaction.service.AdminStatsService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
	private final AccountService accountService;
	private final AdminStatsService adminStatsService;

	public AdminController(AccountService accountService, AdminStatsService adminStatsService) {
		this.accountService = accountService;
		this.adminStatsService = adminStatsService;
	}

	@GetMapping("/accounts")
	public List<Account> getAllaAccounts() {
		return accountService.getAllAccounts();
	}

	@GetMapping("/transactions")
	public List<Transaction> getAllTransactions() {
		return accountService.getallTransactions();
	}

	@GetMapping("/stats/accounts")
	public TransactionStatsResponse getTransactionStats() {
		return adminStatsService.getTransactionStats();
	}
}
