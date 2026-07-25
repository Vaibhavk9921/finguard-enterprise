package com.finguard.transaction.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class TransactionRequest {
	private Long userId;
	@NotNull(message = "Amount is Required")
	@DecimalMin(value = "1.0", message = "Amount must be greater than 0")
	private BigDecimal amount;

	public TransactionRequest() {
	}

	public TransactionRequest(Long userId, BigDecimal amount) {
		super();
		this.userId = userId;
		this.amount = amount;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}