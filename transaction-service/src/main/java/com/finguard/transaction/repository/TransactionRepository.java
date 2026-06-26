package com.finguard.transaction.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finguard.transaction.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	List<Transaction> findByUserId(Long userId);
}