package com.ssafy.ws.model.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.ws.model.dao.HotplaceDao;
import com.ssafy.ws.model.dto.Att;
import com.ssafy.ws.model.dto.Hotplace;
import com.ssafy.ws.model.dto.response.HotplacePost;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HotplaceServiceImpl implements HotplaceService {

	private final HotplaceDao hotplaceDao;

	@Override
	public List<Att> findAllAttractions() {
		return hotplaceDao.findAllAttractions();
	}

	@Override
	public void createPost(HotplacePost post) {
		hotplaceDao.createPost(post);
	}

	@Override
	public List<Hotplace> getAllPosts() {
		return hotplaceDao.getAllPosts();
	}

	@Override
	public Hotplace getHotplaceById(int hotplaceId) {
		return hotplaceDao.getHotplaceById(hotplaceId);
	}

	@Override
	public void Hotplacelike(int hotplaceId, String memberId) throws SQLException {
		hotplaceDao.Hotplacelike(hotplaceId, memberId);
	}

	@Override
	public void Hotplacelikecancel(int hotplaceId, String memberId) throws SQLException {
		hotplaceDao.Hotplacelikecancel(hotplaceId, memberId);
	}

	@Override
	public boolean hasUserLikedHotplace(int hotplaceId, String memberId) {
		return hotplaceDao.hasUserLikedHotplace(hotplaceId, memberId);
	}

	@Override
	public List<Hotplace> findAllByMemberId(String id) {
		return hotplaceDao.findAllByMemberId(id);
	}
}
