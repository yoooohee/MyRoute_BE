package com.ssafy.ws.util;

import com.ssafy.ws.config.JwtProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {

	private final JwtProperties jwtProperties;
	private Key key;

	@PostConstruct
	public void init() {
		this.key = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes());
	}

	public String generateToken(String memberId) {
		return Jwts.builder().setSubject(memberId).setIssuer(jwtProperties.getIssuer()).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getExpiration()))
				.signWith(key, SignatureAlgorithm.HS256).compact();
	}

	public boolean validToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}

	public String getMemberId(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
	}
}