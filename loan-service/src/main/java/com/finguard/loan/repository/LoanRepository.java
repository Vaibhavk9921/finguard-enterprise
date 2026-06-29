package com.finguard.loan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finguard.loan.entity.Loan;

public interface LoanRepository extends JpaRepository<Loan, Long> {
	List<Loan> findByUserId(Long userId);
}