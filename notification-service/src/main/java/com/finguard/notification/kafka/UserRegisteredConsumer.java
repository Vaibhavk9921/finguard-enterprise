package com.finguard.notification.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.finguard.notification.dto.UserRegisteredEvent;
import com.finguard.notification.service.EmailService;

@Component
public class UserRegisteredConsumer {
	private final EmailService emailService;

	public UserRegisteredConsumer(EmailService emailService) {
		this.emailService = emailService;
	}

	@KafkaListener(topics = "user-events", groupId = "notification-service-group", containerFactory = "kafkaListenerContainerFactory")
	public void consume(UserRegisteredEvent event) {

		System.out.println("================================");
		System.out.println("WELCOME EMAIL SENT");
		System.out.println(event.getEmail());
		System.out.println("================================");
		emailService.sendWelcomeMail(event.getEmail());
	}
}