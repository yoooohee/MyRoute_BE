package com.ssafy.ws.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.ws.model.dto.Att;

public interface AttService {

	List<Att> searchAtt(String sido, String gugun, int contentType) throws SQLException;
	List<Att> searchAttLocation(String sido, String gugun) throws SQLException;
}
