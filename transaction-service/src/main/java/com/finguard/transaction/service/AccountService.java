package com.finguard.transaction.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.finguard.transaction.entity.Account;
import com.finguard.transaction.entity.Transaction;
import com.finguard.transaction.repository.AccountRepository;
import com.finguard.transaction.repository.TransactionRepository;

@Service
public class AccountService {
	private final AccountRepository repository;
	private final TransactionRepository transactionRepository;

	public AccountService(AccountRepository repository, TransactionRepository transactionRepository) {
		this.repository = repository;
		this.transactionRepository = transactionRepository;
	}

	public void createAccount(Long userId) {
		if (repository.findByUserId(userId).isPresent()) {
			return;
		}
		Account account = new Account();
		account.setUserId(userId);
		account.setAccountNumber("FG" + System.currentTimeMillis());
		account.setBalance(BigDecimal.ZERO);
		account.setStatus("ACTIVE");
		repository.save(account);
	}

	public Account deposit(Long userId, BigDecimal amount) {
		Account account = repository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Account Not Found"));
		if (amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new RuntimeException("Invalid Amount");
		}
		account.setBalance(account.getBalance().add(amount));
		repository.save(account);
		Transaction tx = new Transaction();
		tx.setUserId(userId);
		tx.setAccountNumber(account.getAccountNumber());
		tx.setTransactionType("DEPOSIT");
		tx.setAmount(amount);
		tx.setTransactionDate(LocalDateTime.now());
		transactionRepository.save(tx);
		return account;
	}

	public Account withdraw(Long userId, BigDecimal amount) {

		Account account = repository.findByUserId(userId)
				.orElseThrow(() -> new RuntimeException("Account Not Found !!"));

		if (account.getBalance().compareTo(amount) < 0) {
			throw new RuntimeException("Insufficient Balance");
		}
		if (amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new RuntimeException("Invalid Amount");
		}

		account.setBalance(account.getBalance().subtract(amount));

		repository.save(account);

		Transaction tx = new Transaction();
		tx.setUserId(userId);
		tx.setAccountNumber(account.getAccountNumber());
		tx.setTransactionType("WITHDRAW");
		tx.setAmount(amount);
		tx.setTransactionDate(LocalDateTime.now());

		transactionRepository.save(tx);

		return account;
	}

	public Account getAccount(Long userId) {

		return repository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Account Not Found"));
	}

	public List<Transaction> getHistory(Long userId) {
		return transactionRepository.findByUserId(userId);
	}
}