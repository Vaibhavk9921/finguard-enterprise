package com.finguard.notification.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.finguard.notification.dto.LoanRejectedEvent;

@Component
public class LoanRejectedConsumer {
	private final JavaMailSender javaMailSender;

	public LoanRejectedConsumer(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	@KafkaListener(topics = "loan-rejected-events", groupId = "notification-service-group", containerFactory = "loanRejectedKafkaListenerContainerFactory")
	public void consume(LoanRejectedEvent event) {
		System.out.println("============");
		System.out.println("LOAN REJECTED");
		System.out.println(event.getEmail());
		System.out.println(event.getLoanAmount());
		System.out.println("============");
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(event.getEmail());
		message.setSubject("FinGuard - Loan Application Rejected");
		message.setText("Dear Customer,.\n\n"
				+ "We Regret to Inform you that your loan application has been rejected.\n\n" + "Loan Amount :₹"
				+ event.getLoanAmount() + "\n\n" + "If you have any questions, please contact our support team.\n\n"
				+ "Thank you for chosing FinGuard.\n\n" + "Regards,\n" + "Finguard Team");
		javaMailSender.send(message);
		System.out.println("Loan Rejection Email Sent Sucessfully");
	}
}
