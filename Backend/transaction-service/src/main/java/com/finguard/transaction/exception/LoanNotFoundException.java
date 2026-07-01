package com.finguard.transaction.exception;

public class LoanNotFoundException extends RuntimeException {
	public LoanNotFoundException() {
		super("Loan Not Found");
	}
}
