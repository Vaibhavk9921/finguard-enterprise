package com.finguard.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finguard.user.entity.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
	Optional<UserProfile> findByUserId(Long userId);

}
