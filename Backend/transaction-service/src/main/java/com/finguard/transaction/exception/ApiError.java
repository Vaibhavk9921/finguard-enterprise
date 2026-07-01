package com.finguard.transaction.exception;

import java.time.LocalDateTime;

public class ApiError {
	private boolean success;
	private String message;
	private LocalDateTime timeStamp;

	public ApiError() {
	}

	public ApiError(boolean success, String message, LocalDateTime timeStamp) {
		super();
		this.success = success;
		this.message = message;
		this.timeStamp = timeStamp;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}

}