package com.ssafy.ws.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.ws.model.dto.Hotplace;

@Mapper
public interface HotplaceDao {
	public List<Hotplace> findAll(int offset, int limit);
	
	public Hotplace findById(int hotplaceId);
	
	public int count();
	
	public void insert(Hotplace hotplace);
	
	public void update(Hotplace hotplace);
	
	public void delete(int hotplaceId);
}
