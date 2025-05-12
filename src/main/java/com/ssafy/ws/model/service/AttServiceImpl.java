package com.ssafy.ws.model.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.ws.model.dao.AttDao;
import com.ssafy.ws.model.dto.Att;
import com.ssafy.ws.model.dto.Place;
import com.ssafy.ws.model.dto.Plan;

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
	 
	 @Override
	 	public int sidonum(String sido) throws SQLException {
		 return dao.sidonum(sido);
	 }
	 
	 @Override
    @Transactional
    public void savePlan(Plan plan, List<Place> places) throws SQLException {
		 dao.insertPlan(plan); // planId 자동 생성
		 int planId = plan.getPlanId();

        for (Place place : places) {
            place.setPlanId(planId);
        }

        dao.insertPlace(places);
    }

}
