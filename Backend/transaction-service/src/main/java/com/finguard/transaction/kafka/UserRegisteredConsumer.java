package com.finguard.transaction.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.finguard.transaction.dto.UserRegisteredEvent;
import com.finguard.transaction.service.AccountService;

@Component
public class UserRegisteredConsumer {
	private final AccountService accountService;

	public UserRegisteredConsumer(AccountService accountService) {
		this.accountService = accountService;
	}

	@KafkaListener(topics = "user-events", groupId = "transaction-service-group")
	public void consume(UserRegisteredEvent event) {
		System.out.println("ACCOUNT CREATION EVENT ->" + event.getEmail());
		accountService.createAccount(event.getUserId());
	}
}