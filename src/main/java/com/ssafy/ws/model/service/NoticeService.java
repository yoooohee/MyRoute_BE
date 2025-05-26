package com.ssafy.ws.model.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.ws.model.dao.AttDao;
import com.ssafy.ws.model.dao.NoticeDao;
import com.ssafy.ws.model.dto.Hotplace;
import com.ssafy.ws.model.dto.Notice;
import com.ssafy.ws.model.dto.Notification;
import com.ssafy.ws.model.dto.request.NoticeInsertRequest;
import com.ssafy.ws.model.dto.request.NoticeUpdateRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeService {

	private final NoticeDao dao;
	private final AttDao attdao;

	public List<Notice> findAll() {
		return dao.findAll();
	}

	public Notice findById(int id) {
		return dao.findById(id);
	}
	
	public int findlastnoticeno() {
		return dao.findlastnoticeno();
	}

	public void insert(NoticeInsertRequest notice) {
		dao.insert(notice);
		int notice_no = dao.findlastnoticeno();
		List<String> memberIds = dao.findAllUserIds();
		
		for (String memberId : memberIds) {
	        Notification notification = Notification.builder()
	                .memberId(memberId)
	                .type("NOTICE")
	                .content("📢 새로운 공지사항이 등록되었습니다.")
	                .url("/notices/" + notice_no)
	                .isRead(false)
	                .createdAt(LocalDateTime.now())
	                .build();

	       attdao.insertNotification(notification);
	    }
	}

	public void update(NoticeUpdateRequest notice) {
		dao.update(notice);
	}

	public void delete(int id) {
		dao.delete(id);
	}
}
