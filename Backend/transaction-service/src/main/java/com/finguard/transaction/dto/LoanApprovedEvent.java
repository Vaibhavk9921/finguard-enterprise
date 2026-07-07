package com.finguard.transaction.dto;

import java.math.BigDecimal;

public class LoanApprovedEvent {
	private Long loanid;
	private Long userId;
	private String email;
	private BigDecimal loanAmount;
	private BigDecimal emi;

	public LoanApprovedEvent() {
	}

	public LoanApprovedEvent(Long loanid, Long userId, String email, BigDecimal loanAmount, BigDecimal emi) {
		super();
		this.loanid = loanid;
		this.userId = userId;
		this.email = email;
		this.loanAmount = loanAmount;
		this.emi = emi;
	}

	public Long getLoanid() {
		return loanid;
	}

	public void setLoanid(Long loanid) {
		this.loanid = loanid;
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