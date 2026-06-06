package com.finguard.user.service;

import org.springframework.stereotype.Service;

import com.finguard.user.dto.UserProfileRequest;
import com.finguard.user.entity.KycStatus;
import com.finguard.user.entity.UserProfile;
import com.finguard.user.repository.UserProfileRepository;

@Service
public class UserProfileService {
	private final UserProfileRepository repository;

	public UserProfileService(UserProfileRepository repository) {
		this.repository = repository;
	}

	public UserProfile createProfile(UserProfileRequest request) {
		if (repository.findByUserId(request.getUserId()).isPresent()) {
			throw new RuntimeException("Profile already exists");
		}
		UserProfile profile = new UserProfile();
		profile.setUserId(request.getUserId());
		profile.setFullName(request.getFullName());
		profile.setAddress(request.getAddress());
		profile.setPhoneNumber(request.getPhoneNumber());
		profile.setPanNumber(request.getPanNumber());
		profile.setAadharNumber(request.getAadharNumber());
		profile.setKycStatus(KycStatus.PENDING);
		return repository.save(profile);
	}

	public UserProfile getProfile(Long userId) {
		return repository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Profile Not Found"));
	}

	public UserProfile updateProfile(Long userId, UserProfileRequest request) {

		UserProfile profile = repository.findByUserId(userId)
				.orElseThrow(() -> new RuntimeException("Profile not found"));

		profile.setFullName(request.getFullName());
		profile.setPhoneNumber(request.getPhoneNumber());
		profile.setPanNumber(request.getPanNumber());
		profile.setAadharNumber(request.getAadharNumber());
		profile.setAddress(request.getAddress());

		return repository.save(profile);
	}
}