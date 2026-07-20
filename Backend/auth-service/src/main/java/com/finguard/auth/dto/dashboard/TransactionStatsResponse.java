package com.finguard.auth.dto.dashboard;

import java.math.BigDecimal;

public class TransactionStatsResponse {
	private long totalAccounts;
	private BigDecimal totalBalance;
	private long totalTransactions;

	public TransactionStatsResponse() {
	}

	public TransactionStatsResponse(long totalAccounts, BigDecimal totalBalance, long totalTransactions) {
		super();
		this.totalAccounts = totalAccounts;
		this.totalBalance = totalBalance;
		this.totalTransactions = totalTransactions;
	}

	public long getTotalAccounts() {
		return totalAccounts;
	}

	public void setTotalAccounts(long totalAccounts) {
		this.totalAccounts = totalAccounts;
	}

	public BigDecimal getTotalBalance() {
		return totalBalance;
	}

	public void setTotalBalance(BigDecimal totalBalance) {
		this.totalBalance = totalBalance;
	}

	public long getTotalTransactions() {
		return totalTransactions;
	}

	public void setTotalTransactions(long totalTransactions) {
		this.totalTransactions = totalTransactions;
	}

}