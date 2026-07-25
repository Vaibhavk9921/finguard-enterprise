package com.finguard.loan.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ApplyLoanRequest {
	private Long userId;
	@NotBlank(message = "Loan type is Required")
	private String loanType;
	@NotNull(message = "Loan amount is Required")
	@DecimalMin(value = "1000.0", message = "minimum loan amount is 1000")
	private BigDecimal loanAmount;
	@NotNull(message = "Tenure is Required")
	@Min(value = 1, message = "Tenure must be at least 1 month ")
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