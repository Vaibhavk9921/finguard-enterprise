package com.finguard.notification.dto;

import java.math.BigDecimal;

public class LoanApprovedEvent {
	private Long loanId;
	private Long userId;
	private String email;
	private BigDecimal loanAmount;
	private BigDecimal emi;

	public LoanApprovedEvent() {
		// TODO Auto-generated constructor stub
	}

	public LoanApprovedEvent(Long loanId, Long userId, String email, BigDecimal loanAmount, BigDecimal emi) {
		super();
		this.loanId = loanId;
		this.userId = userId;
		this.email = email;
		this.loanAmount = loanAmount;
		this.emi = emi;
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

	public BigDecimal getEmi() {
		return emi;
	}

	public void setEmi(BigDecimal emi) {
		this.emi = emi;
	}
	
}
