package com.ssafy.ws.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.Base64;
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

		byte[] imageBytes = memberInfo.getImageBytes();

		String mimeType = "image/jpeg";
		if (imageBytes != null) {
			try {
				mimeType = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(imageBytes));
				if (mimeType == null) {
					String str = new String(imageBytes);
					if (str.trim().startsWith("<svg")) {
						mimeType = "image/svg+xml";
					}
				}
			} catch (IOException e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "이미지 처리 실패"));
			}
		}

		String imageBase64 = (imageBytes != null)
				? "data:" + mimeType + ";base64," + Base64.getEncoder().encodeToString(imageBytes)
				: null;

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
