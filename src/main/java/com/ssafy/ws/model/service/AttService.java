package com.ssafy.ws.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.ws.model.dto.Att;
import com.ssafy.ws.model.dto.Place;
import com.ssafy.ws.model.dto.Plan;
import com.ssafy.ws.model.dto.request.PlanSaveRequest;

public interface AttService {

	List<Att> searchAtt(String sido, String gugun, int contentType) throws SQLException;
	List<Att> searchAttLocation(String sido, String gugun) throws SQLException;
	int sidonum(String sido) throws SQLException;
	void savePlan(Plan plan, List<Place> places) throws SQLException;
	List<Plan> getPlanByUserId(String userId) throws SQLException;
	Plan getPlanById(int planId) throws SQLException;
	List<Place> getPlacesByPlanId(int planId) throws SQLException;
	void updatePlan(int planId, PlanSaveRequest places) throws SQLException;
	void deletePlan(int planId);
	List<Plan> getPublicPlans() throws SQLException;
	void Planlike(int planId, String userId) throws SQLException;
	void Planlikecancel(int planId, String userId) throws SQLException;
	Plan getPlanByIdWithLike(int planId) throws SQLException;
	public boolean hasUserLikedPlan(int planId, String memberId);
	List<Att> findAllAttractions() throws SQLException;
}
