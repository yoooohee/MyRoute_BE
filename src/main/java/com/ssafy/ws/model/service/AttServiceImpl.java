package com.ssafy.ws.model.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.ws.model.dao.AttDao;
import com.ssafy.ws.model.dto.Att;
import com.ssafy.ws.model.dto.Place;
import com.ssafy.ws.model.dto.Plan;
import com.ssafy.ws.model.dto.request.PlanSaveRequest;

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
		 dao.insertPlan(plan);
		 int planId = plan.getPlanId();

        for (Place place : places) {
            place.setPlanId(planId);
        }

        dao.insertPlace(places);
    }
	 
	 @Override
	 public List<Plan> getPlanByUserId(String userId) throws SQLException {
		 return dao.getPlanByUserId(userId);
	 }
	 
	 @Override
	 public Plan getPlanById(int planId) throws SQLException {
		 return dao.getPlanById(planId); 
	 }
	 
	 @Override
	 public List<Place> getPlacesByPlanId(int planId) throws SQLException {
		 return dao.getPlacesByPlanId(planId);
	 }
	 
	 @Override
	 @Transactional
	    public void updatePlan(int planId, PlanSaveRequest request) {
	        Plan plan = dao.getPlanById(planId);
	        int areacode = dao.sidonum(request.getSido());

	        plan.setPlanName(request.getTitle());
	        plan.setDays(request.getDays());
	        plan.setBudget(request.getBudget());
	        plan.setAreaCode(areacode);
	        plan.setIsPublic(request.getIsPublic());
	        dao.updatePlan(plan);
	        dao.deletePlaceByPlanId(planId);

	        List<Place> newPlaces = new ArrayList<>();
	        for (Place dto : request.getPlaces()) {
	            Place place = new Place();
	            place.setPlanId(planId);
	            place.setAttractionNo(dto.getAttractionNo());
	            place.setPlaceName(dto.getPlaceName());
	            place.setAddr1(dto.getAddr1());
	            place.setFirst_image1(dto.getFirst_image1());
	            place.setLatitude(dto.getLatitude());
	            place.setLongitude(dto.getLongitude());
	            place.setVisitOrder(dto.getVisitOrder());

	            newPlaces.add(place);
	        }
	        
	        dao.insertPlace(newPlaces);
	    }
	 
	 @Override
	 public void deletePlan(int planId) {
		 dao.deletePlaceByPlanId(planId);
		 dao.deletePlanByPlanId(planId);
	 }
	 
	 @Override
	 public List<Plan> getPublicPlans() throws SQLException {
		 return dao.getPublicPlans();
	 }
	 
	 @Override
	 public void Planlike(int planId, String memberId) throws SQLException {
		 dao.Planlike(planId, memberId);
	 }
	 
	 @Override
	 public void Planlikecancel(int planId, String memberId) throws SQLException {
		 dao.Planlikecancel(planId, memberId);
	 }
	 
	 @Override
	 public Plan getPlanByIdWithLike(int planId) throws SQLException {
		 return dao.getPlanByIdWithLike(planId);
	 }
	 
	 @Override
	 public boolean hasUserLikedPlan(int planId, String memberId) {
	     return dao.hasUserLikedPlan(planId, memberId);
	 }

}
