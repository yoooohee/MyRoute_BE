package com.ssafy.ws.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.ws.model.dao.NoticeDao;
import com.ssafy.ws.model.dto.Notice;
import com.ssafy.ws.model.dto.request.NoticeInsertRequest;
import com.ssafy.ws.model.dto.request.NoticeUpdateRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeService {

	private final NoticeDao dao;

	public List<Notice> findAll() {
		return dao.findAll();
	}

	public Notice findById(int id) {
		return dao.findById(id);
	}

	public void insert(NoticeInsertRequest notice) {
		dao.insert(notice);
	}

	public void update(NoticeUpdateRequest notice) {
		dao.update(notice);
	}

	public void delete(int id) {
		dao.delete(id);
	}
}
