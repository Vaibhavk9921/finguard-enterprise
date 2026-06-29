package com.finguard.loan.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finguard.loan.dto.ApplyLoanRequest;
import com.finguard.loan.entity.Loan;
import com.finguard.loan.service.LoanService;

@RestController
@RequestMapping("/api/loans")
public class LoanController {
	private final LoanService loanService;

	public LoanController(LoanService loanService) {
		this.loanService = loanService;
	}

	@GetMapping("/hello")
	public String hello() {
		return "Loan Service Running Sucessfully !!";
	}

	@PostMapping("/apply")
	public Loan applyLoan(@RequestBody ApplyLoanRequest request) {
		return loanService.applyLoan(request);
	}

	@GetMapping("/{userId}")
	public List<Loan> getLoans(@PathVariable("userId") Long userId) {
		return loanService.getLoans(userId);
	}

	@PutMapping("/{loanId}/approve")
	public Loan approveLoan(@PathVariable("loanId") Long loanId) {
		return loanService.approveLoan(loanId);
	}
}
