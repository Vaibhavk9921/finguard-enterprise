package com.finguard.transaction.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.finguard.transaction.dto.LoanApprovedEvent;
import com.finguard.transaction.service.AccountService;

@Component
public class LoanApprovedConsumer {
	private final AccountService accountService;

	public LoanApprovedConsumer(AccountService accountService) {
		this.accountService = accountService;
	}

	@KafkaListener(topics = "loan-events", groupId = "transaction-service-group", containerFactory = "loanKafkaListenerContainerFactory")
	public void consume(LoanApprovedEvent event) {
		System.out.println("LOAN APPROVED EVENT RECEIVED ->" + event.getEmail());
		accountService.creditLoan(event);
	}
}