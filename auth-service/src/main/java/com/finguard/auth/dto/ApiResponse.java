package com.finguard.auth.dto;

public class ApiResponse<T> {
	private boolean sucess;
	private String message;
	private T data;

	public ApiResponse() {
	}

	public ApiResponse(boolean sucess, String message, T data) {
		super();
		this.sucess = sucess;
		this.message = message;
		this.data = data;
	}

	public boolean isSucess() {
		return sucess;
	}

	public void setSucess(boolean sucess) {
		this.sucess = sucess;
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
	
}