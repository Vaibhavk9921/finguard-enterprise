package com.finguard.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finguard.user.dto.UserProfileRequest;
import com.finguard.user.entity.UserProfile;
import com.finguard.user.service.UserProfileService;

@RestController
@RequestMapping("/api/users")
public class UserProfileController {
	private final UserProfileService service;

	public UserProfileController(UserProfileService service) {
		this.service = service;
	}

	@PostMapping("/profile")
	public UserProfile createProfile(@RequestBody UserProfileRequest request) {
		return service.createProfile(request);
	}

	@GetMapping("profile/{userId}")
	public UserProfile getProfile(@PathVariable("userId") Long userId) {
		return service.getProfile(userId);
	}

	public UserProfile updateProfile(@PathVariable("userId") Long userId, @RequestBody UserProfileRequest request) {
		return service.updateProfile(userId, request);
	}
}