package com.finguard.transaction.repository;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.finguard.transaction.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
	Optional<Account> findByUserId(Long userId);

	@Query("SELECT COALESCE(SUM(a.balance),0) FROM Account a")
	BigDecimal getTotalBalance();
}
