package com.ssafy.ws.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssafy.ws.model.dto.request.MemberUpdateRequest;
import com.ssafy.ws.model.dto.request.PasswordModifyRequest;
import com.ssafy.ws.model.dto.response.MemberInfoResponse;
import com.ssafy.ws.model.service.MemberService;
import com.ssafy.ws.util.ImageUtil;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService service;

	@GetMapping("/me")
	public ResponseEntity<?> findMyInfoById() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()
				|| authentication.getPrincipal().equals("anonymousUser")) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
		}

		String memberId = authentication.getName();
		MemberInfoResponse memberInfo = service.findMyInfoById(memberId);

		String imageBase64 = ImageUtil.convertImageBytesToBase64(memberInfo.getImageBytes());

		Map<String, Object> result = new HashMap<>();
		result.put("memberInfo", memberInfo);
		result.put("profileImage", imageBase64);

		return ResponseEntity.ok(result);
	}

	@PutMapping("/me")
	public ResponseEntity<?> updateMyInfo(@ModelAttribute MemberUpdateRequest member) throws IOException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
		}

		String memberId = authentication.getName();
		service.updateMemberInfo(memberId, member);

		return ResponseEntity.ok("수정 완료");
	}

	@PutMapping("/me/password")
	public ResponseEntity<?> updatePassword(@RequestBody PasswordModifyRequest password) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
		}

		String memberId = authentication.getName();
		service.updatePassword(memberId, password);

		return ResponseEntity.ok("비밀번호가 변경되었습니다.");
	}
}
