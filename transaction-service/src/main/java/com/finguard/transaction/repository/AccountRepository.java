package com.finguard.transaction.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finguard.transaction.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
	Optional<Account> findByUserId(Long userId);
}
