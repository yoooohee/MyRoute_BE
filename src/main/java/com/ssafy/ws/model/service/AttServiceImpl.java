package com.ssafy.ws.model.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.ws.model.dao.AttDao;
import com.ssafy.ws.model.dto.Att;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttServiceImpl implements AttService {
	private final AttDao dao;
	
	 @Override
	    public List<Att> searchAtt(String sido, String gugun, int contentType) throws SQLException {
	       	 return dao.searchAtt(sido, gugun, contentType);
	    }
	 
	 @Override
	    public List<Att> searchAttLocation(String sido, String gugun) throws SQLException {
	       	 return dao.searchAttLocation(sido, gugun);
	    }

}
