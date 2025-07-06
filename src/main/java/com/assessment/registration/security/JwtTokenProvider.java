package com.assessment.registration.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtTokenProvider {

	private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

	private static final long EXPIRATION_TIME_MS = 24 * 60 * 60 * 1000;

	public static String generateToken(String subject) {
		Date now = new Date();
		Date expiry = new Date(now.getTime() + EXPIRATION_TIME_MS);

		return Jwts.builder()
				.setSubject(subject)
				.setIssuedAt(now)
				.setExpiration(expiry)
				.signWith(SECRET_KEY).compact();

	}
}
