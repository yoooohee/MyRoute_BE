package com.ssafy.ws.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.ws.model.dto.Att;
import com.ssafy.ws.model.dto.Hotplace;
import com.ssafy.ws.model.dto.response.HotplaceDetailResponse;
import com.ssafy.ws.model.dto.response.HotplacePost;
import com.ssafy.ws.model.service.HotplaceServiceImpl;

import lombok.RequiredArgsConstructor;
import java.sql.SQLException;

@RestController
@RequestMapping("/api/hotplace")
@RequiredArgsConstructor
public class HotPlaceController {
	private final HotplaceServiceImpl hService;

	@GetMapping("/allAttractions")
	public ResponseEntity<List<Att>> getAllAttractions() throws SQLException {
		List<Att> attractions = hService.findAllAttractions();
		return ResponseEntity.ok(attractions);
	}

	@PostMapping("/upload")
	public ResponseEntity<?> createPost(@RequestParam("no") int attractionNo, @RequestParam String title,
			@RequestParam double rating, @RequestParam String content,
			@RequestParam(required = false) MultipartFile images, @RequestParam("writerId") String memberId) {
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
	public ResponseEntity<List<Hotplace>> getAllHotplacePosts() {
		List<Hotplace> posts = hService.getAllPosts();
		return ResponseEntity.ok(posts);
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

		HotplaceDetailResponse response = HotplaceDetailResponse.builder().hotplace(hotplace).likedByUser(likedByUser)
				.imageBase64(imageBase64).build();

		if (response.getHotplace() != null) {
			response.getHotplace().setImage(null);
		}

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
}
