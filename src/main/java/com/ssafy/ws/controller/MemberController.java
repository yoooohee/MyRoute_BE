package com.ssafy.ws.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssafy.ws.model.dto.request.MemberUpdateRequest;
import com.ssafy.ws.model.dto.request.PasswordModifyRequest;
import com.ssafy.ws.model.dto.response.MemberInfoResponse;
import com.ssafy.ws.model.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService service;

	@GetMapping("/me")
	private ResponseEntity<?> findMyInfoById() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		System.out.println("authentication: " + authentication);

		if (authentication == null || !authentication.isAuthenticated()
				|| authentication.getPrincipal().equals("anonymousUser")) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
		}

		String memberId = authentication.getName();
		MemberInfoResponse memberInfo = service.findMyInfoById(memberId);

		return ResponseEntity.ok(memberInfo);
	}

	@PutMapping("/me")
	private ResponseEntity<?> updateMyInfo(@RequestBody MemberUpdateRequest member) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
		}

		String memberId = authentication.getName();
		service.updateMemberInfo(memberId, member);

		return ResponseEntity.ok("수정 완료");
	}

	@PutMapping("/me/password")
	private ResponseEntity<?> updatePassword(@RequestBody PasswordModifyRequest password) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
		}

		String memberId = authentication.getName();
		service.updatePassword(memberId, password);

		return ResponseEntity.ok("비밀번호가 변경되었습니다.");
	}
}
