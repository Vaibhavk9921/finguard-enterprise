package com.finguard.notification.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.finguard.notification.dto.LoanApprovedEvent;

@Service
public class LoanEmailService {
	private final JavaMailSender javaMailSender;

	public LoanEmailService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public void sendLoanApprovedEmail(LoanApprovedEvent event) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(event.getEmail());
		message.setSubject("Loan Approved Sucessfully");
		message.setText("Dear Customer,\n\n" + "Your Loan has Been Approved.\n\n" + "Loan Amount : ₹"
				+ event.getLoanAmount() + "\nMonthly EMI : ₹" + event.getEmi()
				+ "\n\nThank You for Chosing Finguard Enterprise. \n\n" + "Regards\n" + "Finguard Team");
		javaMailSender.send(message);
		System.out.println("Loan Approval Email Sent Sucessfully");
	}
}