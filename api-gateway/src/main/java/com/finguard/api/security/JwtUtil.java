package com.finguard.api.security;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	private static final String SECRET = "FinGuardEnterpriseSecretKeyForDevelopment123456";

	private final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());

	public boolean validateToken(String token) {

		try {

			Jwts.parser().verifyWith(key).build().parseSignedClaims(token);

			return true;

		} catch (Exception e) {

			return false;
		}
	}
}