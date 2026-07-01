package com.finguard.loan.dto;

import java.math.BigDecimal;

public class ApplyLoanRequest {
	private Long userId;
	private String loanType;
	private BigDecimal loanAmount;
	private Integer tenureMonths;

	public ApplyLoanRequest() {
	}

	public ApplyLoanRequest(Long userId, String loanType, BigDecimal loanAmount, Integer tenureMonths) {
		super();
		this.userId = userId;
		this.loanType = loanType;
		this.loanAmount = loanAmount;
		this.tenureMonths = tenureMonths;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	public BigDecimal getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	public Integer getTenureMonths() {
		return tenureMonths;
	}

	public void setTenureMonths(Integer tenureMonths) {
		this.tenureMonths = tenureMonths;
	}

}