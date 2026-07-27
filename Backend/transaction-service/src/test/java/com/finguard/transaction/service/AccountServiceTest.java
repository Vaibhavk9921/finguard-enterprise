package com.finguard.transaction.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.finguard.transaction.dto.LoanApprovedEvent;
import com.finguard.transaction.entity.Account;
import com.finguard.transaction.entity.Transaction;
import com.finguard.transaction.exception.AccountFrozenException;
import com.finguard.transaction.exception.AccountNotFoundException;
import com.finguard.transaction.exception.InvalidAmountException;
import com.finguard.transaction.repository.AccountRepository;
import com.finguard.transaction.repository.TransactionRepository;
import com.finguard.transaction.util.AccountStatus;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

	@Mock
	private AccountRepository repository;

	@Mock
	private TransactionRepository transactionRepository;

	@InjectMocks
	private AccountService accountService;

	private Account account;

	@BeforeEach
	void setUp() {

		account = new Account();

		account.setId(1L);
		account.setUserId(100L);
		account.setAccountNumber("FG123456");
		account.setBalance(new BigDecimal("1000"));
		account.setStatus(AccountStatus.ACTIVE);
	}

	@Test
	void shouldCreateAccountSuccessfully() {

		when(repository.findByUserId(100L)).thenReturn(Optional.empty());

		when(repository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));

		accountService.createAccount(100L);

		verify(repository).save(any(Account.class));
	}

	@Test
	void shouldNotCreateDuplicateAccount() {

		when(repository.findByUserId(100L)).thenReturn(Optional.of(account));

		accountService.createAccount(100L);

		verify(repository, never()).save(any(Account.class));
	}

	@Test
	void shouldDepositSuccessfully() {

		when(repository.findByUserId(100L)).thenReturn(Optional.of(account));

		when(repository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));

		Account updated = accountService.deposit(100L, new BigDecimal("500"));

		assertEquals(new BigDecimal("1500"), updated.getBalance());

		verify(repository).save(account);

		verify(transactionRepository).save(any());
	}

	@Test
	void shouldThrowAccountNotFoundExceptionWhenDepositing() {

		when(repository.findByUserId(100L)).thenReturn(Optional.empty());

		assertThrows(AccountNotFoundException.class, () -> accountService.deposit(100L, new BigDecimal("500")));

		verify(repository, never()).save(any());
	}

	@Test
	void shouldThrowInvalidAmountExceptionForDeposit() {

		when(repository.findByUserId(100L)).thenReturn(Optional.of(account));

		assertThrows(InvalidAmountException.class, () -> accountService.deposit(100L, BigDecimal.ZERO));

		verify(repository, never()).save(any());
	}

	@Test
	void shouldThrowAccountFrozenExceptionForDeposit() {

		account.setStatus(AccountStatus.FROZEN);

		when(repository.findByUserId(100L)).thenReturn(Optional.of(account));

		assertThrows(AccountFrozenException.class, () -> accountService.deposit(100L, new BigDecimal("100")));

		verify(repository, never()).save(any());
	}

	@Test
	void shouldWithdrawSuccessfully() {

		when(repository.findByUserId(100L)).thenReturn(Optional.of(account));

		when(repository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));

		Account updated = accountService.withdraw(100L, new BigDecimal("400"));

		assertEquals(new BigDecimal("600"), updated.getBalance());

		verify(repository).save(account);
		verify(transactionRepository).save(any());
	}

	@Test
	void shouldThrowAccountNotFoundExceptionWhenWithdrawing() {

		when(repository.findByUserId(100L)).thenReturn(Optional.empty());

		assertThrows(AccountNotFoundException.class, () -> accountService.withdraw(100L, new BigDecimal("100")));

		verify(repository, never()).save(any());
		verify(transactionRepository, never()).save(any());
	}

	@Test
	void shouldThrowInsufficientBalanceException() {

		account.setBalance(new BigDecimal("200"));

		when(repository.findByUserId(100L)).thenReturn(Optional.of(account));

		assertThrows(com.finguard.transaction.exception.InsufficientBalanceException.class,
				() -> accountService.withdraw(100L, new BigDecimal("500")));

		verify(repository, never()).save(any());
		verify(transactionRepository, never()).save(any());
	}

	@Test
	void shouldThrowAccountFrozenExceptionWhenWithdrawing() {

		account.setStatus(AccountStatus.FROZEN);

		when(repository.findByUserId(100L)).thenReturn(Optional.of(account));

		assertThrows(AccountFrozenException.class, () -> accountService.withdraw(100L, new BigDecimal("100")));

		verify(repository, never()).save(any());
		verify(transactionRepository, never()).save(any());
	}

	@Test
	void shouldThrowInvalidAmountExceptionWhenWithdrawAmountIsZero() {

		when(repository.findByUserId(100L)).thenReturn(Optional.of(account));

		assertThrows(InvalidAmountException.class, () -> accountService.withdraw(100L, BigDecimal.ZERO));

		verify(repository, never()).save(any());
		verify(transactionRepository, never()).save(any());
	}

	@Test
	void shouldThrowInvalidAmountExceptionWhenWithdrawAmountIsNegative() {

		when(repository.findByUserId(100L)).thenReturn(Optional.of(account));

		assertThrows(InvalidAmountException.class, () -> accountService.withdraw(100L, new BigDecimal("-100")));

		verify(repository, never()).save(any());
		verify(transactionRepository, never()).save(any());
	}

	@Test
	void shouldTransferSuccessfully() {

		Account sender = new Account();
		sender.setId(1L);
		sender.setUserId(100L);
		sender.setAccountNumber("FG100");
		sender.setBalance(new BigDecimal("1000"));
		sender.setStatus(AccountStatus.ACTIVE);

		Account receiver = new Account();
		receiver.setId(2L);
		receiver.setUserId(200L);
		receiver.setAccountNumber("FG200");
		receiver.setBalance(new BigDecimal("500"));
		receiver.setStatus(AccountStatus.ACTIVE);

		when(repository.findByUserId(100L)).thenReturn(Optional.of(sender));

		when(repository.findByUserId(200L)).thenReturn(Optional.of(receiver));

		accountService.transfer(100L, 200L, new BigDecimal("300"));

		assertEquals(new BigDecimal("700"), sender.getBalance());

		assertEquals(new BigDecimal("800"), receiver.getBalance());

		verify(repository, times(2)).save(any(Account.class));
		verify(transactionRepository, times(2)).save(any());
	}

	@Test
	void shouldThrowExceptionWhenTransferToSameAccount() {

		RuntimeException exception = assertThrows(RuntimeException.class,
				() -> accountService.transfer(100L, 100L, new BigDecimal("100")));

		assertEquals("Cannot transfer to the same account.", exception.getMessage());

		verify(repository, never()).save(any());
		verify(transactionRepository, never()).save(any());
	}

	@Test
	void shouldThrowInvalidAmountExceptionWhenTransferAmountIsZero() {

		assertThrows(InvalidAmountException.class, () -> accountService.transfer(100L, 200L, BigDecimal.ZERO));

		verify(repository, never()).save(any());
		verify(transactionRepository, never()).save(any());
	}

	@Test
	void shouldThrowAccountNotFoundExceptionWhenSenderDoesNotExist() {

		when(repository.findByUserId(100L)).thenReturn(Optional.empty());

		assertThrows(AccountNotFoundException.class, () -> accountService.transfer(100L, 200L, new BigDecimal("100")));

		verify(repository, never()).save(any());
	}

	@Test
	void shouldThrowAccountNotFoundExceptionWhenReceiverDoesNotExist() {

		when(repository.findByUserId(100L)).thenReturn(Optional.of(account));

		when(repository.findByUserId(200L)).thenReturn(Optional.empty());

		assertThrows(AccountNotFoundException.class, () -> accountService.transfer(100L, 200L, new BigDecimal("100")));

		verify(repository, never()).save(any());
	}

	@Test
	void shouldThrowInsufficientBalanceExceptionDuringTransfer() {

		account.setBalance(new BigDecimal("100"));

		Account receiver = new Account();
		receiver.setUserId(200L);
		receiver.setAccountNumber("FG200");
		receiver.setBalance(new BigDecimal("500"));
		receiver.setStatus(AccountStatus.ACTIVE);

		when(repository.findByUserId(100L)).thenReturn(Optional.of(account));

		when(repository.findByUserId(200L)).thenReturn(Optional.of(receiver));

		assertThrows(com.finguard.transaction.exception.InsufficientBalanceException.class,
				() -> accountService.transfer(100L, 200L, new BigDecimal("500")));

		verify(repository, never()).save(any());
		verify(transactionRepository, never()).save(any());
	}

	@Test
	void shouldThrowAccountFrozenExceptionWhenSenderIsFrozen() {

		account.setStatus(AccountStatus.FROZEN);

		Account receiver = new Account();
		receiver.setUserId(200L);
		receiver.setAccountNumber("FG200");
		receiver.setBalance(new BigDecimal("100"));
		receiver.setStatus(AccountStatus.ACTIVE);

		when(repository.findByUserId(100L)).thenReturn(Optional.of(account));

		when(repository.findByUserId(200L)).thenReturn(Optional.of(receiver));

		assertThrows(AccountFrozenException.class, () -> accountService.transfer(100L, 200L, new BigDecimal("50")));

		verify(repository, never()).save(any());
	}

	@Test
	void shouldThrowAccountFrozenExceptionWhenReceiverIsFrozen() {

		Account receiver = new Account();
		receiver.setUserId(200L);
		receiver.setAccountNumber("FG200");
		receiver.setBalance(new BigDecimal("100"));
		receiver.setStatus(AccountStatus.FROZEN);

		when(repository.findByUserId(100L)).thenReturn(Optional.of(account));

		when(repository.findByUserId(200L)).thenReturn(Optional.of(receiver));

		assertThrows(AccountFrozenException.class, () -> accountService.transfer(100L, 200L, new BigDecimal("50")));

		verify(repository, never()).save(any());
	}

	@Test
	void shouldCreditLoanSuccessfully() {

		LoanApprovedEvent event = new LoanApprovedEvent();
		event.setUserId(100L);
		event.setLoanAmount(new BigDecimal("5000"));

		when(repository.findByUserId(100L)).thenReturn(Optional.of(account));

		accountService.creditLoan(event);

		assertEquals(new BigDecimal("6000"), account.getBalance());

		verify(repository).save(account);
		verify(transactionRepository).save(any());
	}

	@Test
	void shouldThrowAccountFrozenExceptionDuringLoanCredit() {

		account.setStatus(AccountStatus.FROZEN);

		LoanApprovedEvent event = new LoanApprovedEvent();
		event.setUserId(100L);
		event.setLoanAmount(new BigDecimal("5000"));

		when(repository.findByUserId(100L)).thenReturn(Optional.of(account));

		assertThrows(AccountFrozenException.class, () -> accountService.creditLoan(event));

		verify(repository, never()).save(any());
		verify(transactionRepository, never()).save(any());
	}

	@Test
	void shouldFreezeAccountSuccessfully() {

		when(repository.findById(1L)).thenReturn(Optional.of(account));

		when(repository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));

		Account updated = accountService.freezeAccount(1L);

		assertEquals(AccountStatus.FROZEN, updated.getStatus());

		verify(repository).save(account);
	}

	@Test
	void shouldThrowAccountNotFoundWhenFreezing() {

		when(repository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(AccountNotFoundException.class, () -> accountService.freezeAccount(1L));

		verify(repository, never()).save(any());
	}

	@Test
	void shouldUnfreezeAccountSuccessfully() {

		account.setStatus(AccountStatus.FROZEN);

		when(repository.findById(1L)).thenReturn(Optional.of(account));

		when(repository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));

		Account updated = accountService.unfreezeAccount(1L);

		assertEquals(AccountStatus.ACTIVE, updated.getStatus());

		verify(repository).save(account);
	}

	@Test
	void shouldThrowAccountNotFoundWhenUnfreezing() {

		when(repository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(AccountNotFoundException.class, () -> accountService.unfreezeAccount(1L));

		verify(repository, never()).save(any());
	}

	@Test
	void shouldGetAccountSuccessfully() {

		when(repository.findByUserId(100L)).thenReturn(Optional.of(account));

		Account result = accountService.getAccount(100L);

		assertEquals(account, result);
	}

	@Test
	void shouldThrowAccountNotFoundWhenGettingAccount() {

		when(repository.findByUserId(100L)).thenReturn(Optional.empty());

		assertThrows(AccountNotFoundException.class, () -> accountService.getAccount(100L));
	}

	@Test
	void shouldReturnTransactionHistory() {

		when(transactionRepository.findByUserId(100L)).thenReturn(List.of(new Transaction(), new Transaction()));

		List<Transaction> history = accountService.getHistory(100L);

		assertEquals(2, history.size());

		verify(transactionRepository).findByUserId(100L);
	}

	@Test
	void shouldReturnAllAccounts() {

		when(repository.findAll()).thenReturn(List.of(account));

		List<Account> accounts = accountService.getAllAccounts();

		assertEquals(1, accounts.size());

		verify(repository).findAll();
	}

	@Test
	void shouldReturnAllTransactions() {

		when(transactionRepository.findAll()).thenReturn(List.of(new Transaction()));

		List<Transaction> transactions = accountService.getallTransactions();

		assertEquals(1, transactions.size());

		verify(transactionRepository).findAll();
	}
}