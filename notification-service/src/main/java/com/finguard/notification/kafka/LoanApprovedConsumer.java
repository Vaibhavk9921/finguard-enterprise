package com.finguard.notification.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.finguard.notification.dto.LoanApprovedEvent;
import com.finguard.notification.service.LoanEmailService;

@Component
public class LoanApprovedConsumer {

	private final LoanEmailService loanEmailService;

	public LoanApprovedConsumer(LoanEmailService loanEmailService) {
		this.loanEmailService = loanEmailService;
	}

	@KafkaListener(topics = "loan-events", groupId = "notification-service-group", containerFactory = "loanKafkaListenerContainerFactory")
	public void consume(LoanApprovedEvent event) {

		System.out.println("=======================");
		System.out.println("LOAN APPROVED");
		System.out.println(event.getEmail());
		System.out.println(event.getLoanAmount());
		System.out.println(event.getEmi());
		System.out.println("=======================");

		loanEmailService.sendLoanApprovedEmail(event);
	}
}