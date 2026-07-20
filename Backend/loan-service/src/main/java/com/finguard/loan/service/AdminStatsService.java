package com.finguard.loan.service;

import org.springframework.stereotype.Service;

import com.finguard.loan.dto.LoanStatsResponse;
import com.finguard.loan.repository.LoanRepository;
import com.finguard.loan.entity.LoanStatus;

@Service
public class AdminStatsService {

	private final LoanRepository repository;

	public AdminStatsService(LoanRepository repository) {
		this.repository = repository;
	}

	public LoanStatsResponse getLoanStats() {

		return new LoanStatsResponse(repository.count(), repository.countByStatus(LoanStatus.APPROVED),
				repository.countByStatus(LoanStatus.REJECTED));
	}
}