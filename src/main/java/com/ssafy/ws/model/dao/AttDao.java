package com.ssafy.ws.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.ws.model.dto.Att;
import com.ssafy.ws.model.dto.Place;
import com.ssafy.ws.model.dto.Plan;

@Mapper
public interface AttDao {
	List<Att> searchAtt(String sido, String gugun, int contentType);
	List<Att> searchAttLocation(String sido, String gugun);
	int sidonum(String sido);
	int insertPlan(Plan plan);
    int insertPlace(List<Place> places);
}
