package com.ssafy.ws.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.ws.model.dto.Att;

@Mapper
public interface AttDao {
	List<Att> searchAtt(String sido, String gugun, int contentType);
	List<Att> searchAttLocation(String sido, String gugun);
}
