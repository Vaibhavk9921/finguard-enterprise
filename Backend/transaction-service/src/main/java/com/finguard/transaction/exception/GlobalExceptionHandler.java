package com.finguard.transaction.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ApiError> handleRuntimeException(RuntimeException ex) {
		ApiError error = new ApiError(false, ex.getMessage(), LocalDateTime.now());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiError> handleException(Exception ex) {
		ApiError error = new ApiError(false, "Something went Wrong", LocalDateTime.now());
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(AccountNotFoundException.class)
	public ResponseEntity<ApiError> handleAccountNotFound(AccountNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new ApiError(false, ex.getMessage(), LocalDateTime.now()));
	}

	@ExceptionHandler(AccountFrozenException.class)
	public ResponseEntity<ApiError> handleFrozen(AccountFrozenException ex) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN)
				.body(new ApiError(false, ex.getMessage(), LocalDateTime.now()));
	}

	@ExceptionHandler(InvalidAmountException.class)
	public ResponseEntity<ApiError> handleInvalidAmount(InvalidAmountException ex) {
		return ResponseEntity.badRequest().body(new ApiError(false, ex.getMessage(), LocalDateTime.now()));
	}

	@ExceptionHandler(InsufficientBalanceException.class)
	public ResponseEntity<ApiError> handleBalance(InsufficientBalanceException ex) {
		return ResponseEntity.badRequest().body(new ApiError(false, ex.getMessage(), LocalDateTime.now()));
	}
}