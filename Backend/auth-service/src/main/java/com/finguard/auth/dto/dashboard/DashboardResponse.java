package com.finguard.auth.dto.dashboard;

import java.math.BigDecimal;

public class DashboardResponse {
	private long totalUsers;
	private long totalAccounts;
	private BigDecimal totalBalance;
	private long totalTransactions;
	private long totalLoans;
	private long approvedLoans;
	private long rejectedLoans;

	public DashboardResponse() {
	}

	public DashboardResponse(long totalUsers, long totalAccounts, BigDecimal totalBalance, long totalTransactions,
			long totalLoans, long approvedLoans, long rejectedLoans) {
		super();
		this.totalUsers = totalUsers;
		this.totalAccounts = totalAccounts;
		this.totalBalance = totalBalance;
		this.totalTransactions = totalTransactions;
		this.totalLoans = totalLoans;
		this.approvedLoans = approvedLoans;
		this.rejectedLoans = rejectedLoans;
	}

	public long getTotalUsers() {
		return totalUsers;
	}

	public void setTotalUsers(long totalUsers) {
		this.totalUsers = totalUsers;
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

	public long getTotalLoans() {
		return totalLoans;
	}

	public void setTotalLoans(long totalLoans) {
		this.totalLoans = totalLoans;
	}

	public long getApprovedLoans() {
		return approvedLoans;
	}

	public void setApprovedLoans(long approvedLoans) {
		this.approvedLoans = approvedLoans;
	}

	public long getRejectedLoans() {
		return rejectedLoans;
	}

	public void setRejectedLoans(long rejectedLoans) {
		this.rejectedLoans = rejectedLoans;
	}

}