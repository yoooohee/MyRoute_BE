package com.ssafy.ws.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ws.model.dto.Notice;
import com.ssafy.ws.model.dto.request.NoticeInsertRequest;
import com.ssafy.ws.model.dto.request.NoticeUpdateRequest;
import com.ssafy.ws.model.service.NoticeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/notices")
@RequiredArgsConstructor
public class NoticeController {

	private final NoticeService service;

	@GetMapping
	public ResponseEntity<List<Notice>> findAll() {
		return ResponseEntity.ok(service.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Notice> findById(@PathVariable int id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@PostMapping
	public ResponseEntity<?> insert(@RequestBody NoticeInsertRequest notice) {
		service.insert(notice);
		return ResponseEntity.ok("등록 되었습니다.");
	}

	@PutMapping
	public ResponseEntity<?> update(@RequestBody NoticeUpdateRequest notice) {
		service.update(notice);
		return ResponseEntity.ok("수정 되었습니다.");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable int id) {
		service.delete(id);
		return ResponseEntity.ok("삭제 되었습니다.");
	}
}
