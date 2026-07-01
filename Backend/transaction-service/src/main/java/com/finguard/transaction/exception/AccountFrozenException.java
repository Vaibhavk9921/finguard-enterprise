package com.finguard.transaction.exception;

public class AccountFrozenException extends RuntimeException {

	public AccountFrozenException() {
		super("Account is frozen. Transaction is not allowed.");
	}

}