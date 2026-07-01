package com.finguard.user.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.finguard.user.dto.UserRegisteredEvent;

@Service
public class UserEventConsumer {
	@KafkaListener(topics = "user-events", groupId = "user-service-group")
	public void consume(UserRegisteredEvent event) {
		System.out.println("EVENT RECEIVED->" + event.getEmail());
	}
}