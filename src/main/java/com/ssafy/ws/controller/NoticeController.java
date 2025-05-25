package com.ssafy.ws.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ssafy.ws.model.dto.Member;
import com.ssafy.ws.model.dto.Notice;
import com.ssafy.ws.model.dto.request.NoticeInsertRequest;
import com.ssafy.ws.model.dto.request.NoticeUpdateRequest;
import com.ssafy.ws.model.service.MemberService;
import com.ssafy.ws.model.service.NoticeService;
import com.ssafy.ws.util.ImageUtil;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/notices")
@RequiredArgsConstructor
public class NoticeController {

	private final NoticeService service;
	private final MemberService memberService;

	@GetMapping
	public ResponseEntity<List<Notice>> findAll() {
		return ResponseEntity.ok(service.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable int id) {
		Notice notice = service.findById(id);
		Member admin = memberService.findById(notice.getMemberId());

		String imageBase64 = ImageUtil.convertImageBytesToBase64(admin.getProfileImage());

		Map<String, Object> result = new HashMap<>();
		result.put("notice", notice);
		result.put("memberName", admin.getName());
		result.put("profileImage", imageBase64);

		return ResponseEntity.ok(result);
	}

	@PostMapping
	public ResponseEntity<?> insert(@RequestBody NoticeInsertRequest notice) {
		checkAdmin();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String memberId = authentication.getName();
		notice.setMemberId(memberId);
		service.insert(notice);
		return ResponseEntity.ok("등록 되었습니다.");
	}

	@PutMapping
	public ResponseEntity<?> update(@RequestBody NoticeUpdateRequest notice) {
		checkAdmin();
		service.update(notice);
		return ResponseEntity.ok("수정 되었습니다.");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable int id) {
		checkAdmin();
		service.delete(id);
		return ResponseEntity.ok("삭제 되었습니다.");
	}

	private void checkAdmin() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()) {
			throw new ResponseStatusException(UNAUTHORIZED, "로그인이 필요합니다.");
		}

		String memberId = authentication.getName();

		Member member = memberService.findById(memberId);

		if (!member.getRole().equals("ADMIN")) {
			throw new ResponseStatusException(FORBIDDEN, "관리자 권한이 필요합니다.");
		}
	}
	
}
