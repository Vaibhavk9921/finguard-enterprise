package com.finguard.api.security;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
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

	public String extractEmail(String token) {

		return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();
	}

	public String extractRole(String token) {

		Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();

		return claims.get("role", String.class);
	}
}