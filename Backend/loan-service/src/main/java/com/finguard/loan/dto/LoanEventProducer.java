package com.finguard.loan.dto;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class LoanEventProducer {
	private final KafkaTemplate<String, Object> kafkaTemplate;
	private static final Logger logger = LoggerFactory.getLogger(LoanEventProducer.class);

	public LoanEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
		super();
		this.kafkaTemplate = kafkaTemplate;
	}

	public void publishLoanApprovedEvent(LoanApprovedEvent event) {

		logger.info("Publishing Loan Approved Event...");
		logger.info("Customer Email: {}", event.getEmail());
		kafkaTemplate.send("loan-events", event).whenComplete((result, ex) -> {

			if (ex == null) {
				logger.info("Loan event sent successfully.");
				logger.info("Kafka Metadata: {}", result.getRecordMetadata());
			} else {
				logger.error("Failed to publish loan event.", ex);
			}

		});
	}

}