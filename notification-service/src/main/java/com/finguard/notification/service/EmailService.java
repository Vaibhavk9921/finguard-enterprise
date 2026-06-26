package com.finguard.notification.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	private final JavaMailSender mailSender;

	public EmailService(JavaMailSender mailSender) {

		this.mailSender = mailSender;
	}

	public void sendWelcomeMail(String email) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject("Welcome to Finguard");
		message.setText("Hello ,\n\n" + "Welcome to Finguard. \n\n" + "Your Account has Been Created Sucessfully. \n\n"
				+ "Thank you for Choosing Finguard");
		mailSender.send(message);
		System.out.println("Email Sent Sucessfully To ->" + email);
	}
}