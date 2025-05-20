package com.ssafy.ws.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.ws.model.dto.Att;
import com.ssafy.ws.model.dto.Comment;
import com.ssafy.ws.model.dto.Hotplace;
import com.ssafy.ws.model.dto.response.HotplacePost;

public interface HotplaceService {

	List<Att> findAllAttractions();

	void createPost(HotplacePost post);

	List<Hotplace> getAllPosts();

	Hotplace getHotplaceById(int hotplaceId);

	void Hotplacelike(int hotplaceId, String memberId) throws SQLException;

	void Hotplacelikecancel(int hotplaceId, String memberId) throws SQLException;

	public boolean hasUserLikedHotplace(int hotplaceId, String memberId);

	public List<Hotplace> findAllByMemberId(String memberId);
	
	void addComment(int hotplaceId, String memberId, String content);
	
    List<Comment> getComments(int hotplaceId);
}
