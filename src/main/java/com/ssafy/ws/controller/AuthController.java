package com.ssafy.ws.controller;

import java.util.EmptyStackException;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ws.model.dto.Member;
import com.ssafy.ws.model.dto.request.EmailVerificationReqeust;
import com.ssafy.ws.model.dto.request.LoginRequest;
import com.ssafy.ws.model.dto.response.EmailVerificationResponse;
import com.ssafy.ws.model.service.MailService;
import com.ssafy.ws.model.service.MemberService;
import com.ssafy.ws.util.JwtUtil;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final MemberService service;
	private final MailService mailService;
	private final JwtUtil jwtUtil;

	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody Member member) {
		service.signIn(member);
		return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 완료되었습니다.");
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
		Member loginMember = service.login(loginRequest);

		if (loginMember == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디 또는 비밀번호가 일치하지 않습니다.");
		}

		String token = jwtUtil.generateToken(loginRequest.getId());
		return ResponseEntity.ok()
				.body(Map.of("token", token, "id", loginMember.getId(), "role", loginMember.getRole()));
	}

	@PostMapping("/send-code")
	public ResponseEntity<?> sendEmail(@RequestBody EmailVerificationReqeust emailRequest, HttpSession session) {
		String email = emailRequest.getEmail();
		String code = mailService.sendVerificationEmail(email);
		session.setAttribute("authCode:" + email, code);
		return ResponseEntity.ok("인증 메일이 전송되었습니다.");
	}

	@PostMapping("/verify-code")
	public ResponseEntity<?> verifyCode(@RequestBody EmailVerificationResponse emailResponse, HttpSession session) {
		String email = emailResponse.getEmail();
		String code = emailResponse.getCode();
		String key = "authCode:" + email;
		String savedCode = (String) session.getAttribute(key);

		if (code.equals(savedCode)) {
			return ResponseEntity.ok("인증 완료");
		} else {
			return ResponseEntity.status(400).body("인증 실패");
		}
	}

}
