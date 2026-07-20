package com.finguard.auth.dto;

public class UserStatsResponse {

	private long totalUsers;

	public UserStatsResponse() {
	}

	public UserStatsResponse(long totalUsers) {
		this.totalUsers = totalUsers;
	}

	public long getTotalUsers() {
		return totalUsers;
	}

	public void setTotalUsers(long totalUsers) {
		this.totalUsers = totalUsers;
	}
}