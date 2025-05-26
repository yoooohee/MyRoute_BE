package com.ssafy.ws.config;

import java.util.Date;
import java.util.Set;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jakarta.annotation.PostConstruct;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@Component
@RequiredArgsConstructor
public class TokenProvider {

	private final JwtProperties jwtProperties;
	private Key key;

	@PostConstruct
	public void init() {
		this.key = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));
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
			System.out.println("JWT 검증 실패: " + e.getClass().getSimpleName() + " - " + e.getMessage());
			return false;
		}
	}

	public Authentication getAuthentication(String token) {
		Claims claims = getClaims(token);

		String role = claims.get("role", String.class);
		var authorities = Set.of(new SimpleGrantedAuthority("ROLE_" + role));

		var principal = new org.springframework.security.core.userdetails.User(claims.getSubject(), "", authorities);

		return new UsernamePasswordAuthenticationToken(principal, token, authorities);
	}

	public String getUserId(String token) {
		Claims claims = getClaims(token);
		return claims.get("id", String.class);
	}

	private Claims getClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	}
}
