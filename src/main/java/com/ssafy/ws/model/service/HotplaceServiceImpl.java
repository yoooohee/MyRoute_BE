package com.ssafy.ws.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.ws.model.dao.HotplaceDao;
import com.ssafy.ws.model.dto.Hotplace;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HotplaceServiceImpl implements HotplaceService {
	
	private final HotplaceDao hotplaceDao;
	
	@Override
	public void createHotplace(Hotplace hotplace) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Hotplace> getHotplaces(int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getHotplaceCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Hotplace getHotplaceById(int hotplaceId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateHotplace(Hotplace hotplace) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteHotplace(int hotplaceId) {
		// TODO Auto-generated method stub
		
	}
	
}
