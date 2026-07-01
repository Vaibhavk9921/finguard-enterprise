package com.finguard.loan.dto;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class LoanRejectedEventProducer {

	private final KafkaTemplate<String, Object> kafkaTemplate;

	public LoanRejectedEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public void publishLoanRejectedEvent(LoanRejectedEvent event) {

		System.out.println("Publishing Loan Rejected Event...");
		System.out.println(event.getEmail());

		kafkaTemplate.send("loan-rejected-events", event).whenComplete((result, ex) -> {

			if (ex == null) {
				System.out.println("LOAN REJECTED EVENT SENT SUCCESSFULLY");
				System.out.println(result.getRecordMetadata());
			} else {
				System.out.println("FAILED TO SEND LOAN REJECTED EVENT");
				ex.printStackTrace();
			}
		});
	}
}