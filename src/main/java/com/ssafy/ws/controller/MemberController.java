package com.ssafy.ws.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssafy.ws.model.dto.Notification;
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
	
	@DeleteMapping("/me")
	public ResponseEntity<?> deleteMember() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	    if (authentication == null || !authentication.isAuthenticated()) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
	    }

	    String memberId = authentication.getName(); // JWT에서 추출한 ID

	    int result = service.deleteMember(memberId);

	    if (result > 0) {
	        return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
	    } else {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원 탈퇴에 실패했습니다.");
	    }
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
	
	@GetMapping("/notification")
	public ResponseEntity<List<Notification>> getUserNotifications() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String memberId = authentication.getName();
	    List<Notification> notifications = service.getNotifications(memberId);
	    return ResponseEntity.ok(notifications);
	}

	@PostMapping("/notification/read/{id}")
	public ResponseEntity<?> markAsRead(@PathVariable Long id) {
	    service.markAsRead(id);
	    return ResponseEntity.ok().build();
	}

	@DeleteMapping("/notification/delete/{id}")
	public ResponseEntity<?> deleteNotification(@PathVariable Long id) {
	    service.deleteNotification(id);
	    return ResponseEntity.ok().build();
	}
}
