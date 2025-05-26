package com.ssafy.ws.model.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.ws.model.dto.Att;
import com.ssafy.ws.model.dto.Comment;
import com.ssafy.ws.model.dto.Hotplace;
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

	public List<Hotplace> findAllByMemberId(String memberId);

	void insertComment(Comment comment);

	List<Comment> getCommentsByHotplaceId(int hotplaceId);

	void deleteComment(int commentId, String memberId);

	boolean updatePost(HotplacePost updatePost);

	void deletePost(int hotplaceId);

	List<Hotplace> findLikedPostsByMemberId(String memberId);
}
