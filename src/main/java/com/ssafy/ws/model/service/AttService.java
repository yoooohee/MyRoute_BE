package com.ssafy.ws.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.ws.model.dto.Att;
import com.ssafy.ws.model.dto.Place;
import com.ssafy.ws.model.dto.Plan;

public interface AttService {

	List<Att> searchAtt(String sido, String gugun, int contentType) throws SQLException;
	List<Att> searchAttLocation(String sido, String gugun) throws SQLException;
	int sidonum(String sido) throws SQLException;
	void savePlan(Plan plan, List<Place> places) throws SQLException;
}
