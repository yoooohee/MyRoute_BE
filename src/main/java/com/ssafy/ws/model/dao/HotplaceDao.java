package com.ssafy.ws.model.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.ws.model.dto.Att;
import com.ssafy.ws.model.dto.Hotplace;
import com.ssafy.ws.model.dto.response.HotplaceDetailResponse;
import com.ssafy.ws.model.dto.response.HotplacePost;

@Mapper
public interface HotplaceDao {
	
	public List<Att> findAllAttractions();
	void createPost(HotplacePost post);
	List<Hotplace> getAllPosts();
	Hotplace getHotplaceById(int hotplaceId);
	void Hotplacelike(int hotplaceId, String memberId) throws SQLException;
	void Hotplacelikecancel(int hotplaceId, String memberId) throws SQLException;
	public boolean hasUserLikedHotplace(int hotplaceId, String memberId);

}
