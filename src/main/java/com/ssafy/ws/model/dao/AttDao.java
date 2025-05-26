package com.ssafy.ws.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.ws.model.dto.Att;
import com.ssafy.ws.model.dto.Notification;
import com.ssafy.ws.model.dto.Place;
import com.ssafy.ws.model.dto.Plan;
import com.ssafy.ws.model.dto.response.PlanResponse;

@Mapper
public interface AttDao {
	List<Att> searchAtt(String sido, String gugun, int contentType);

	List<Att> searchAttLocation(String sido, String gugun);

	int sidonum(String sido);

	int insertPlan(Plan plan);

	int insertPlace(List<Place> places);

	List<Plan> getPlanByUserId(String userId);

	Plan getPlanById(int planId);

	List<Place> getPlacesByPlanId(int planId);

	void deletePlaceByPlanId(int planId);

	void deletePlanByPlanId(int planId);

	void updatePlan(Plan plan);

	List<Plan> getPublicPlans();

	void Planlike(int planId, String memberId);

	void Planlikecancel(int planId, String memberId);

	Plan getPlanByIdWithLike(int planId);

	boolean hasUserLikedPlan(@Param("planId") int planId, @Param("memberId") String memberId);

	List<Att> findAllAttractions();

	double getAvgRating(int attractionNo);

	void insertFavorite(String memberId, int attractionNo);

	void deleteFavorite(String memberId, int attractionNo);

	boolean isFavorite(String memberId, int attractionNo);

	List<Integer> getAllFavoriteAttractionNos(String memberId);

	void insertNotification(Notification notification);

	List<PlanResponse> findPlansLikedByMemberId(String memberId);

	Att findAttById(int attractionNo);
}
