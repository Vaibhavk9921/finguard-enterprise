package com.finguard.auth.service;

import org.springframework.stereotype.Service;

import com.finguard.auth.dto.UserStatsResponse;
import com.finguard.auth.repository.UserRepository;

@Service
public class AdminStatsService {

	private final UserRepository userRepository;

	public AdminStatsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public UserStatsResponse getUserStats() {
		return new UserStatsResponse(userRepository.count());
	}
}