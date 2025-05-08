package com.ssafy.ws.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ws.model.dto.Member;
import com.ssafy.ws.model.service.MemberService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final MemberService service;

	// 회원가입
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody Member member) {
		service.signIn(member);
		return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 완료되었습니다.");
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(HttpSession session, @RequestParam String id, @RequestParam String password)
			throws Exception {
		Member member = new Member();
		member.setId(id);
		member.setPassword(password);

		member = service.login(member);

		if (member == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디 또는 비밀번호가 일치하지 않습니다.");
		}

		session.setAttribute("id", member.getId());
		session.setAttribute("loginUser", member);

		return ResponseEntity.ok("로그인 성공");
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpSession session) {
		session.invalidate();
		return ResponseEntity.ok("로그아웃 완료");
	}
}
