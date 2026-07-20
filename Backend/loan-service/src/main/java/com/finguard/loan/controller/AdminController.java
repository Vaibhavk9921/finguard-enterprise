package com.finguard.loan.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finguard.loan.dto.LoanStatsResponse;
import com.finguard.loan.entity.Loan;
import com.finguard.loan.service.AdminStatsService;
import com.finguard.loan.service.LoanService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
	private final LoanService loanService;
	private final AdminStatsService adminStatsService;

	public AdminController(LoanService loanService, AdminStatsService adminStatsService) {
		this.loanService = loanService;
		this.adminStatsService = adminStatsService;
	}

	@GetMapping("/loans")
	public List<Loan> getAllLoans() {
		return loanService.getAllLoans();
	}

	@GetMapping("/stats/loans")
	public LoanStatsResponse getLoanStats() {
		return adminStatsService.getLoanStats();
	}
}