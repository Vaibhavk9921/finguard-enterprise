package com.finguard.auth.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.finguard.auth.dto.UserRegisteredEvent;

@Service
public class UserEventProducer {

	private final KafkaTemplate<String, UserRegisteredEvent> kafkaTemplate;

	public UserEventProducer(KafkaTemplate<String, UserRegisteredEvent> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public void publishUserRegisteredEvent(UserRegisteredEvent event) {

		System.out.println("Publishing event...");
		System.out.println(event.getUserId());
		System.out.println(event.getEmail());
		System.out.println(event.getRole());

		kafkaTemplate.send("user-events", event).whenComplete((result, ex) -> {
			if (ex == null) {
				System.out.println("MESSAGE SENT SUCCESSFULLY");
				System.out.println(result.getRecordMetadata());
			} else {
				System.out.println("MESSAGE FAILED");
				ex.printStackTrace();
			}
		});
	}
}