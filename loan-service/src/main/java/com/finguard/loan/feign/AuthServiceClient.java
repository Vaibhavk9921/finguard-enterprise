package com.finguard.loan.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.finguard.loan.dto.UserValidationResponse;

@FeignClient(name = "AUTH-SERVICE")
public interface AuthServiceClient {
	@GetMapping("/api/auth/validate/{userId}")
	UserValidationResponse validateUser(@PathVariable("userId") Long userId);
}