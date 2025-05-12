package com.ssafy.ws.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ws.config.TokenProvider;
import com.ssafy.ws.model.dto.Member;
import com.ssafy.ws.model.dto.request.LoginRequest;
import com.ssafy.ws.model.service.MemberService;
import com.ssafy.ws.util.JwtUtil;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final MemberService service;
	private final JwtUtil jwtUtil;
	
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody Member member) {
		service.signIn(member);
		return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 완료되었습니다.");
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(HttpSession session, @RequestBody LoginRequest loginRequest) {
		Member loginMember = service.login(loginRequest);

		if (loginMember == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디 또는 비밀번호가 일치하지 않습니다.");
		}

		String token = jwtUtil.generateToken(loginRequest.getId());
		return ResponseEntity.ok()
				.body(Map.of("token", token, "id", loginMember.getId(), "role", loginMember.getRole()));
	}
}
