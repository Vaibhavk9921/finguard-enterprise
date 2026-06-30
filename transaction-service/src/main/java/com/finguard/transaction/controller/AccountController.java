package com.finguard.transaction.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finguard.transaction.dto.TransactionRequest;
import com.finguard.transaction.entity.Account;
import com.finguard.transaction.entity.Transaction;
import com.finguard.transaction.service.AccountService;
import com.finguard.transaction.util.AccountStatus;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
	private final AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	@PostMapping("/deposit")
	public Account deposit(@RequestBody TransactionRequest request) {
		return accountService.deposit(request.getUserId(), request.getAmount());
	}

	@PostMapping("/withdraw")
	public Account withdraw(@RequestBody TransactionRequest request) {
		return accountService.withdraw(request.getUserId(), request.getAmount());
	}

	@GetMapping("/{userId}")
	public Account getAccount(@PathVariable("userId") Long userId) {
		return accountService.getAccount(userId);
	}

	@GetMapping("/history/{userId}")
	public List<Transaction> getHistory(@PathVariable("userId") Long userId) {
		return accountService.getHistory(userId);
	}

	@PutMapping("/{accountId}/freeze")
	public Account freezeAccount(@PathVariable("accountId") Long accountId) {
		return accountService.freezeAccount(accountId);
	}

	@PutMapping("/{accountId}/unfreeze")
	public Account unFreezeAccount(@PathVariable("accountId") Long accountId) {
		return accountService.unfreezeAccount(accountId);
	}
}