package com.finguard.loan.dto;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class LoanEventProducer {
	private final KafkaTemplate<String, LoanApprovedEvent> kafkaTemplate;

	public LoanEventProducer(KafkaTemplate<String, LoanApprovedEvent> kafkaTemplate) {
		super();
		this.kafkaTemplate = kafkaTemplate;
	}

	public void publishLoanApprovedEvent(LoanApprovedEvent event) {

		System.out.println("Publishing Loan Approved Event...");
		System.out.println(event.getEmail());

		kafkaTemplate.send("loan-events", event).whenComplete((result, ex) -> {

			if (ex == null) {
				System.out.println("LOAN EVENT SENT SUCCESSFULLY");
				System.out.println(result.getRecordMetadata());
			} else {
				System.out.println("FAILED TO SEND LOAN EVENT");
				ex.printStackTrace();
			}

		});
	}
}