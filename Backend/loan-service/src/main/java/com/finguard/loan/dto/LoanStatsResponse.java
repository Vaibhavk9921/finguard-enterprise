package com.finguard.loan.dto;

public class LoanStatsResponse {
	private long totalLoans;
	private long approvedLoans;
	private long rejectedLoans;

	public LoanStatsResponse() {
	}

	public LoanStatsResponse(long totalLoans, long approvedLoans, long rejectedLoans) {
		super();
		this.totalLoans = totalLoans;
		this.approvedLoans = approvedLoans;
		this.rejectedLoans = rejectedLoans;
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
