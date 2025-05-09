package com.ssafy.ws.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ws.model.dto.Member;
import com.ssafy.ws.model.service.MemberService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final MemberService service;

	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody Member member) {
		service.signIn(member);
		return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 완료되었습니다.");
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(HttpSession session, @RequestBody Member member) {
		Member loginMember = service.login(member);

		if (loginMember == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디 또는 비밀번호가 일치하지 않습니다.");
		}

		session.setAttribute("id", loginMember.getId());
		session.setAttribute("loginUser", loginMember);

		// TODO JWT 사용 후 변경
		String dummyToken = "dummy-token-for-" + loginMember.getId();
		return ResponseEntity.ok()
				.body(Map.of("token", dummyToken, "id", loginMember.getId(), "role", loginMember.getRole()));
	}
}
