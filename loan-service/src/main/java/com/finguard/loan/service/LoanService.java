package com.finguard.loan.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.finguard.loan.dto.ApplyLoanRequest;
import com.finguard.loan.dto.LoanApprovedEvent;
import com.finguard.loan.dto.LoanEventProducer;
import com.finguard.loan.entity.Loan;
import com.finguard.loan.entity.LoanStatus;
import com.finguard.loan.feign.AuthServiceClient;
import com.finguard.loan.repository.LoanRepository;
import com.finguard.loan.util.EmiCalculator;
import com.finguard.loan.dto.UserValidationResponse;

@Service
public class LoanService {
	private final LoanRepository repository;
	private final LoanEventProducer loanEventProducer;
	private final AuthServiceClient authServiceClient;

	public LoanService(LoanRepository repository, LoanEventProducer loanEventProducer,
			AuthServiceClient authServiceClient) {
		this.repository = repository;
		this.loanEventProducer = loanEventProducer;
		this.authServiceClient = authServiceClient;
	}

	public Loan applyLoan(ApplyLoanRequest request) {
		Loan loan = new Loan();
		loan.setUserId(request.getUserId());
		loan.setLoanType(request.getLoanType());
		loan.setLoanAmount(request.getLoanAmount());
		loan.setTenureMonths(request.getTenureMonths());
		loan.setInterestRate(10.5);
		loan.setEmi(
				EmiCalculator.calculateEmi(request.getLoanAmount(), loan.getInterestRate(), loan.getTenureMonths()));
		loan.setStatus(LoanStatus.PENDING);
		loan.setAppliedDate(LocalDateTime.now());
		return repository.save(loan);
	}

	public List<Loan> getLoans(Long userId) {
		return repository.findByUserId(userId);
	}

	public Loan approveLoan(Long loanId) {
		Loan loan = repository.findById(loanId).orElseThrow(() -> new RuntimeException("Loan Not Found"));
		loan.setStatus(LoanStatus.APPROVED);
		repository.save(loan);
		LoanApprovedEvent event = new LoanApprovedEvent();
		event.setLoanId(loan.getId());
		event.setUserId(loan.getUserId());
		event.setLoanAmount(loan.getLoanAmount());
		event.setEmi(loan.getEmi());
		UserValidationResponse response = authServiceClient.validateUser(loan.getUserId());
		System.out.println("Loan User ID : " + loan.getUserId());
		System.out.println("Feign Response Email : " + response.getEmail());
		System.out.println("Feign Response Valid : " + response.isExists());
		if (!response.isExists()) {
			throw new RuntimeException("User not Found");
		}
		event.setEmail(response.getEmail());
		loanEventProducer.publishLoanApprovedEvent(event);
		return loan;
	}
}