package com.finguard.notification.dto;

import java.math.BigDecimal;

public class LoanRejectedEvent {

	private Long loanId;
	private Long userId;
	private String email;
	private BigDecimal loanAmount;

	public LoanRejectedEvent() {
	}

	public LoanRejectedEvent(Long loanId, Long userId, String email, BigDecimal loanAmount) {
		this.loanId = loanId;
		this.userId = userId;
		this.email = email;
		this.loanAmount = loanAmount;
	}

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public BigDecimal getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}
}