package com.text_to_sql.text_to_sql.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@Slf4j
public class JwtUtil {

	private static final String SECRET = "text-to-sql-jwt-secret-key-2026-very-long-secret";
	private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000;

	private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

	public static String generateToken(Long userId, String account, Integer userType) {
		return Jwts.builder()
				.setSubject(String.valueOf(userId))
				.claim("account", account)
				.claim("userType", userType)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(KEY, SignatureAlgorithm.HS256)
				.compact();
	}

	public static Claims parseToken(String token) {
		try {
			return Jwts.parserBuilder()
					.setSigningKey(KEY)
					.build()
					.parseClaimsJws(token)
					.getBody();
		} catch (Exception e) {
			log.error("JWT解析失败: {}", e.getMessage());
			return null;
		}
	}

	public static Long getUserIdFromToken(String token) {
		Claims claims = parseToken(token);
		if (claims == null) {
			return null;
		}
		String subject = claims.getSubject();
		try {
			return Long.parseLong(subject);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static boolean isTokenExpired(String token) {
		Claims claims = parseToken(token);
		if (claims == null) {
			return true;
		}
		return claims.getExpiration().before(new Date());
	}
}
