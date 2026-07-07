package com.finguard.transaction.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.finguard.transaction.dto.LoanApprovedEvent;
import com.finguard.transaction.entity.Account;
import com.finguard.transaction.entity.Transaction;
import com.finguard.transaction.repository.AccountRepository;
import com.finguard.transaction.repository.TransactionRepository;
import com.finguard.transaction.util.AccountStatus;

import jakarta.transaction.Transactional;

import com.finguard.transaction.exception.AccountFrozenException;
import com.finguard.transaction.exception.AccountNotFoundException;
import com.finguard.transaction.exception.InsufficientBalanceException;
import com.finguard.transaction.exception.InvalidAmountException;

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
		account.setStatus(AccountStatus.ACTIVE);
		repository.save(account);
	}

	public Account deposit(Long userId, BigDecimal amount) {
		Account account = repository.findByUserId(userId).orElseThrow(AccountNotFoundException::new);
		if (amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new InvalidAmountException();
		}
		if (AccountStatus.FROZEN.equals(account.getStatus())) {
			throw new AccountFrozenException();
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

		Account account = repository.findByUserId(userId).orElseThrow(AccountNotFoundException::new);
		if (AccountStatus.FROZEN.equals(account.getStatus())) {
			throw new AccountFrozenException();
		}
		if (account.getBalance().compareTo(amount) < 0) {
			throw new InsufficientBalanceException();
		}
		if (amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new InvalidAmountException();
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

		return repository.findByUserId(userId).orElseThrow(AccountNotFoundException::new);
	}

	public List<Transaction> getHistory(Long userId) {
		return transactionRepository.findByUserId(userId);
	}

	public Account freezeAccount(Long accountId) {
		Account account = repository.findById(accountId).orElseThrow(AccountNotFoundException::new);
		account.setStatus(AccountStatus.FROZEN);
		return repository.save(account);
	}

	public Account unfreezeAccount(Long accountId) {
		Account account = repository.findById(accountId).orElseThrow(AccountNotFoundException::new);
		account.setStatus(AccountStatus.ACTIVE);
		return repository.save(account);
	}

	@Transactional
	public void creditLoan(LoanApprovedEvent event) {
		Account account = repository.findByUserId(event.getUserId()).orElseThrow(AccountNotFoundException::new);
		if (AccountStatus.FROZEN.equals(account.getStatus())) {
			throw new AccountFrozenException();
		}
		account.setBalance(account.getBalance().add(event.getLoanAmount()));
		repository.save(account);
		Transaction transaction = new Transaction();
		transaction.setUserId(event.getUserId());
		transaction.setAccountNumber(account.getAccountNumber());
		transaction.setTransactionType("LOAN_CREDIT");
		transaction.setAmount(event.getLoanAmount());
		transaction.setTransactionDate(LocalDateTime.now());
		transactionRepository.save(transaction);
		System.out.println("LOAN AMOUNT CREDITED :" + event.getLoanAmount());

	}
}