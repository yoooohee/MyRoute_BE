package com.ssafy.ws.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.ws.model.dto.Att;
import com.ssafy.ws.model.dto.Comment;
import com.ssafy.ws.model.dto.Hotplace;
import com.ssafy.ws.model.dto.Member;
import com.ssafy.ws.model.dto.request.CommentRequest;
import com.ssafy.ws.model.dto.response.CommentResponse;
import com.ssafy.ws.model.dto.response.HotplaceDetailResponse;
import com.ssafy.ws.model.dto.response.HotplaceListResponse;
import com.ssafy.ws.model.dto.response.HotplacePost;
import com.ssafy.ws.model.service.HotplaceServiceImpl;
import com.ssafy.ws.model.service.MemberService;
import com.ssafy.ws.util.ImageUtil;

import lombok.RequiredArgsConstructor;
import java.sql.SQLException;

@RestController
@RequestMapping("/api/hotplace")
@RequiredArgsConstructor
public class HotPlaceController {
	private final HotplaceServiceImpl hService;
	private final MemberService memberService;

	@GetMapping("/allAttractions")
	public ResponseEntity<List<Att>> getAllAttractions() throws SQLException {
		List<Att> attractions = hService.findAllAttractions();
		return ResponseEntity.ok(attractions);
	}

	@PostMapping("/upload")
	public ResponseEntity<?> createPost(@RequestParam("no") int attractionNo, @RequestParam String title,
			@RequestParam double rating, @RequestParam String content,
			@RequestParam(required = false) MultipartFile images) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String memberId = (authentication != null && authentication.isAuthenticated()
				&& !"anonymousUser".equals(authentication.getPrincipal())) ? authentication.getName() : null;

		byte[] imageBytes = null;
		if (images != null && !images.isEmpty()) {
			try {
				imageBytes = images.getBytes();
			} catch (IOException e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이미지 처리 실패");
			}
		}

		HotplacePost post = new HotplacePost();
		post.setMemberId(memberId);
		post.setAttractionNo(attractionNo);
		post.setTitle(title);
		post.setRating(rating);
		post.setContent(content);
		post.setImage(imageBytes);

		hService.createPost(post);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/posts")
	public ResponseEntity<List<HotplaceListResponse>> getAllHotplacePosts() {
		List<Hotplace> posts = hService.getAllPosts();
		List<HotplaceListResponse> response = new ArrayList<>();

		for (Hotplace hotplace : posts) {
			Member member = memberService.findById(hotplace.getMemberId());
			String imageBase64 = ImageUtil.convertImageBytesToBase64(member.getProfileImage());

			HotplaceListResponse dto = new HotplaceListResponse(hotplace.getHotplaceId(), member.getName(),
					hotplace.getAttractionName(), hotplace.getTitle(), hotplace.getStarPoint(), hotplace.getImage(),
					hotplace.getLikeCount(), imageBase64);
			response.add(dto);
		}

		return ResponseEntity.ok(response);
	}

	@GetMapping("/detail/{hotplaceId}")
	public ResponseEntity<?> gethotplaceDetail(@PathVariable int hotplaceId) throws SQLException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String memberId = (authentication != null && authentication.isAuthenticated()
				&& !"anonymousUser".equals(authentication.getPrincipal())) ? authentication.getName() : null;

		boolean likedByUser = false;
		if (memberId != null) {
			likedByUser = hService.hasUserLikedHotplace(hotplaceId, memberId);
		}

		Hotplace hotplace = hService.getHotplaceById(hotplaceId);
		if (hotplace == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 게시물이 존재하지 않습니다.");
		}

		Member writer = memberService.findById(hotplace.getMemberId());
		String profileImage = ImageUtil.convertImageBytesToBase64(writer.getProfileImage());

		String imageBase64 = ImageUtil.convertImageBytesToBase64(hotplace.getImage());

		boolean myPost = memberId != null && memberId.equals(hotplace.getMemberId());

		HotplaceDetailResponse response = HotplaceDetailResponse.builder()
				.hotplaceId(String.valueOf(hotplace.getHotplaceId())).memberName(writer.getName()) // 작성자 이름
				.attractionName(hotplace.getAttractionName()).title(hotplace.getTitle()).content(hotplace.getContent())
				.createdAt(hotplace.getCreatedAt()).updatedAt(hotplace.getUpdatedAt())
				.starPoint(hotplace.getStarPoint()).likeCount(hotplace.getLikeCount()).imageBase64(imageBase64)
				.likedByUser(likedByUser).myPost(myPost).profileImage(profileImage).build();

		return ResponseEntity.ok(response);
	}

	@PostMapping("/hotplacelike/{hotplaceId}")
	public ResponseEntity<?> likeHotplace(@PathVariable int hotplaceId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()
				|| authentication.getPrincipal().equals("anonymousUser")) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
		}

		String memberId = authentication.getName();

		try {
			hService.Hotplacelike(hotplaceId, memberId);
			return ResponseEntity.ok("추천 완료");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("추천 처리 중 오류 발생");
		}
	}

	@PostMapping("/hotplacelike/cancel/{hotplaceId}")
	public ResponseEntity<?> cancelLike(@PathVariable int hotplaceId) throws SQLException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()
				|| authentication.getPrincipal().equals("anonymousUser")) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
		}

		String memberId = authentication.getName();

		hService.Hotplacelikecancel(hotplaceId, memberId);
		return ResponseEntity.ok("좋아요 취소 완료");
	}

	@GetMapping("/myPosts")
	public ResponseEntity<?> findAllByMemberId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()
				|| authentication.getPrincipal().equals("anonymousUser")) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
		}

		String memberId = authentication.getName();

		return ResponseEntity.ok(hService.findAllByMemberId(memberId));
	}

	@PostMapping("/posts/{hotplaceId}/comments")
	public ResponseEntity<?> createComment(@PathVariable int hotplaceId, @RequestBody CommentRequest request) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()
				|| authentication.getPrincipal().equals("anonymousUser")) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
		}

		String memberId = authentication.getName();

		if (memberId == null)
			return ResponseEntity.status(401).body("Unauthorized");

		hService.addComment(hotplaceId, memberId, request.getContent());
		return ResponseEntity.ok().build();
	}

	@GetMapping("/posts/{hotplaceId}/comments")
	public ResponseEntity<?> getComments(@PathVariable int hotplaceId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String memberId = authentication.getName();

		List<Comment> comments = hService.getComments(hotplaceId);

		List<CommentResponse> result = comments.stream().map(comment -> {
			Member member = memberService.findById(comment.getMemberId());
			String profileImage = ImageUtil.convertImageBytesToBase64(member.getProfileImage());

			return new CommentResponse(comment.getCommentId(), member.getName(), comment.getContent(),
					comment.getCreatedAt(), memberId != null && memberId.equals(comment.getMemberId()), profileImage);
		}).collect(Collectors.toList());

		return ResponseEntity.ok(result);
	}

	@DeleteMapping("/commentdelete/{commentId}")
	public ResponseEntity<?> deleteComment(@PathVariable int commentId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()
				|| authentication.getPrincipal().equals("anonymousUser")) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
		}

		String memberId = authentication.getName();
		hService.deleteComment(commentId, memberId);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/edit/{id}")
	public ResponseEntity<?> getEditForm(@PathVariable("id") int hotplaceId) throws SQLException {
		Hotplace hotplace = hService.getHotplaceById(hotplaceId);
		if (hotplace == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("게시글을 찾을 수 없습니다.");
		}

		String mimeType = "image/jpeg";
		byte[] imageBytes = hotplace.getImage();
		if (imageBytes != null) {
			try {
				mimeType = java.net.URLConnection
						.guessContentTypeFromStream(new java.io.ByteArrayInputStream(imageBytes));
				if (mimeType == null) {
					String str = new String(imageBytes);
					if (str.trim().startsWith("<svg")) {
						mimeType = "image/svg+xml";
					}
				}
			} catch (IOException e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이미지 처리 오류");
			}
		}

		String imageBase64 = (imageBytes != null)
				? "data:" + mimeType + ";base64," + Base64.getEncoder().encodeToString(imageBytes)
				: null;

		Map<String, Object> result = new HashMap<>();
		result.put("title", hotplace.getTitle());
		result.put("content", hotplace.getContent());
		result.put("rating", hotplace.getStarPoint());
		result.put("no", hotplace.getAttractionNo());
		result.put("attractionName", hotplace.getAttractionName());
		result.put("imageBase64", imageBase64);

		return ResponseEntity.ok(result);
	}

	@PutMapping("/edit/{id}")
	public ResponseEntity<?> updatePost(@PathVariable("id") int hotplaceId, @RequestParam("no") int attractionNo,
			@RequestParam String title, @RequestParam double rating, @RequestParam String content,
			@RequestParam(required = false) MultipartFile images, @RequestParam("writerId") String memberId) {

		byte[] imageBytes = null;
		if (images != null && !images.isEmpty()) {
			try {
				imageBytes = images.getBytes();
			} catch (IOException e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이미지 처리 실패");
			}
		} else {
			Hotplace origin = hService.getHotplaceById(hotplaceId);
			imageBytes = origin != null ? origin.getImage() : null;
		}

		HotplacePost updatedPost = new HotplacePost();
		updatedPost.setId(hotplaceId);
		updatedPost.setMemberId(memberId);
		updatedPost.setAttractionNo(attractionNo);
		updatedPost.setTitle(title);
		updatedPost.setRating(rating);
		updatedPost.setContent(content);
		updatedPost.setImage(imageBytes);

		boolean result = hService.updatePost(updatedPost);
		if (!result) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("수정할 게시글이 없습니다.");
		}

		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/delete/{hotplaceId}")
	public ResponseEntity<String> deletePost(@PathVariable int hotplaceId) throws SQLException {
		hService.deletePost(hotplaceId);

		return ResponseEntity.ok("삭제 완료");
	}

	@GetMapping("/posts/liked")
	public ResponseEntity<List<HotplaceListResponse>> getLikedHotplaces() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String memberId = authentication.getName();

		List<Hotplace> likedPosts = hService.findLikedPostsByMemberId(memberId);
		List<HotplaceListResponse> response = new ArrayList<>();

		for (Hotplace hotplace : likedPosts) {
			Member member = memberService.findById(hotplace.getMemberId());
			String imageBase64 = ImageUtil.convertImageBytesToBase64(member.getProfileImage());

			HotplaceListResponse dto = new HotplaceListResponse(hotplace.getHotplaceId(), member.getName(),
					hotplace.getAttractionName(), hotplace.getTitle(), hotplace.getStarPoint(), hotplace.getImage(),
					hotplace.getLikeCount(), imageBase64);
			response.add(dto);
		}

		return ResponseEntity.ok(response);
	}

}
