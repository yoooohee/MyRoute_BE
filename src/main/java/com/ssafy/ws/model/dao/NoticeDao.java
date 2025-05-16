package com.ssafy.ws.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.ws.model.dto.Notice;
import com.ssafy.ws.model.dto.request.NoticeInsertRequest;
import com.ssafy.ws.model.dto.request.NoticeUpdateRequest;

@Mapper
public interface NoticeDao {
	public List<Notice> findAll();

	public Notice findById(int id);

	public void insert(NoticeInsertRequest notice);

	public void update(NoticeUpdateRequest notice);

	public void delete(int id);
}
