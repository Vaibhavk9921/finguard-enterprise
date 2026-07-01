package com.finguard.transaction.exception;

public class InvalidAmountException extends RuntimeException {

    public InvalidAmountException() {
        super("Invalid transaction amount.");
    }

}