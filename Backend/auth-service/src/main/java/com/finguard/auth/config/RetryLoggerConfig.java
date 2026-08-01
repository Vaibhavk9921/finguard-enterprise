package com.finguard.auth.config;

import org.springframework.context.annotation.Configuration;

import io.github.resilience4j.retry.RetryRegistry;
import jakarta.annotation.PostConstruct;

@Configuration
public class RetryLoggerConfig {

    private final RetryRegistry retryRegistry;

    public RetryLoggerConfig(RetryRegistry retryRegistry) {
        this.retryRegistry = retryRegistry;
    }

    @PostConstruct
    public void init() {

        retryRegistry.retry("loanService")
                .getEventPublisher()
                .onRetry(event ->
                        System.out.println("Loan Retry Attempt: "
                                + event.getNumberOfRetryAttempts()));

        retryRegistry.retry("transactionService")
                .getEventPublisher()
                .onRetry(event ->
                        System.out.println("Transaction Retry Attempt: "
                                + event.getNumberOfRetryAttempts()));
    }
}