package com.ssafy.ws.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.ws.model.dao.HotplaceDao;
import com.ssafy.ws.model.dto.Att;
import com.ssafy.ws.model.dto.Hotplace;
import com.ssafy.ws.model.dto.response.HotplaceDetailResponse;
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
	public HotplaceDetailResponse getHotplaceById(int hotplaceId) {
		return hotplaceDao.getHotplaceById(hotplaceId);
	}
}
