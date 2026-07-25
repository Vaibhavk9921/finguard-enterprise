package com.finguard.auth.common;

import java.time.LocalDateTime;

public class ApiResponse<T> {
	private boolean success;
	private String message;
	private T data;
	private LocalDateTime timeStamp;

	public ApiResponse() {
	}

	public ApiResponse(boolean success, String message, T data, LocalDateTime timeStamp) {
		super();
		this.success = success;
		this.message = message;
		this.data = data;
		this.timeStamp = timeStamp;
	}

	public static <T> ApiResponse<T> success(String message, T data) {
		return new ApiResponse<>(true, message, data, LocalDateTime.now());
	}

	public static <T> ApiResponse<T> success(T data) {
		return new ApiResponse<>(true, "Success", data, LocalDateTime.now());
	}

	public static <T> ApiResponse<T> successMessage(String message) {
		return new ApiResponse<>(true, message, null, LocalDateTime.now());
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

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}

}