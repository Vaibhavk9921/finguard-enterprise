package com.finguard.auth.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.finguard.auth.dto.UserStatsResponse;
import com.finguard.auth.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class AdminStatsServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private AdminStatsService adminStatsService;

	@Test
	void shouldReturnUserStatistics() {

		when(userRepository.count()).thenReturn(125L);

		UserStatsResponse response = adminStatsService.getUserStats();

		assertEquals(125L, response.getTotalUsers());

		verify(userRepository).count();
	}
}