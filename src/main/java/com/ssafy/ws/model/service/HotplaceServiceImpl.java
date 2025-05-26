package com.ssafy.ws.model.service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.ws.model.dao.AttDao;
import com.ssafy.ws.model.dao.HotplaceDao;
import com.ssafy.ws.model.dto.Att;
import com.ssafy.ws.model.dto.Comment;
import com.ssafy.ws.model.dto.Hotplace;
import com.ssafy.ws.model.dto.Notification;
import com.ssafy.ws.model.dto.response.HotplacePost;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HotplaceServiceImpl implements HotplaceService {

	private final HotplaceDao hotplaceDao;
	private final AttDao dao;

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
		Hotplace hotplace = hotplaceDao.getHotplaceById(hotplaceId);
		if (!hotplace.getMemberId().equals(memberId)) {
			Notification notification = Notification.builder().memberId(hotplace.getMemberId())
					.type("LIKE").content(memberId + "님이 \"" + hotplace.getTitle() + "\" 글을 추천했습니다.")
					.url("/hotplaceDetail/" + hotplaceId).isRead(false).createdAt(LocalDateTime.now()).build();

			dao.insertNotification(notification);
		}
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

	@Override
	public void addComment(int hotplaceId, String memberId, String content) {
		Comment comment = Comment.builder().hotplaceId(hotplaceId).memberId(memberId).content(content).build();
		hotplaceDao.insertComment(comment);
		Hotplace hotplace = hotplaceDao.getHotplaceById(hotplaceId);
		
		if (!hotplace.getMemberId().equals(memberId)) {
			Notification notification = Notification.builder().memberId(hotplace.getMemberId())
					.type("COMMENT").content(memberId + "님이 \"" + hotplace.getTitle() + "\" 글에 댓글을 달았습니다 ' " + content + "'")
					.url("/hotplaceDetail/" + hotplaceId).isRead(false).createdAt(LocalDateTime.now()).build();

			dao.insertNotification(notification);
		}
	}

	@Override
	public List<Comment> getComments(int hotplaceId) {
		return hotplaceDao.getCommentsByHotplaceId(hotplaceId);
	}

	@Override
	public void deleteComment(int commentId, String memberId) {
		hotplaceDao.deleteComment(commentId, memberId);
	}

	@Override
	public boolean updatePost(HotplacePost updatePost) {
		return hotplaceDao.updatePost(updatePost);
	}

	@Override
	public void deletePost(int hotplaceId) {
		hotplaceDao.deletePost(hotplaceId);
		hotplaceDao.deletelikesByHotplaceId(hotplaceId);
		hotplaceDao.deletecommentsByHotplaceId(hotplaceId);
	}

	@Override
	public List<Hotplace> findLikedPostsByMemberId(String memberId) {
		return hotplaceDao.findLikedPostsByMemberId(memberId);
	}
}
