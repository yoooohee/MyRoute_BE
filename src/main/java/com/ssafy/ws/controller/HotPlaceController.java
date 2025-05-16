package com.ssafy.ws.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.ws.model.dto.Att;
import com.ssafy.ws.model.dto.Hotplace;
import com.ssafy.ws.model.dto.Parking;
import com.ssafy.ws.model.dto.Place;
import com.ssafy.ws.model.dto.Plan;
import com.ssafy.ws.model.dto.request.AttPlanRequest;
import com.ssafy.ws.model.dto.request.ParkingSearchRequest;
import com.ssafy.ws.model.dto.request.PlaceRequest;
import com.ssafy.ws.model.dto.request.PlanSaveRequest;
import com.ssafy.ws.model.dto.response.HotplaceDetailResponse;
import com.ssafy.ws.model.dto.response.HotplacePost;
import com.ssafy.ws.model.dto.response.PlanDetailResponse;
import com.ssafy.ws.model.service.AttServiceImpl;
import com.ssafy.ws.model.service.HotplaceServiceImpl;
import com.ssafy.ws.util.ParkingJsonParser;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    public ResponseEntity<?> createPost(
            @RequestParam("no") int attractionNo,
            @RequestParam("title") String title,
            @RequestParam("rating") double rating,
            @RequestParam("content") String content,
            @RequestParam(value = "images", required = false) MultipartFile images,
            @RequestParam("writerId") String memberId
    ) {
		byte[] imageBytes = null;
	    if (images != null && !images.isEmpty()) {
	        try {
	            imageBytes = images.getBytes(); // 이미지 → byte[]
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
	    HotplaceDetailResponse detail = hService.getHotplaceById(hotplaceId);
	    if (detail == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 게시물이 존재하지 않습니다.");
	    }
	    
	    if (detail.getImage() != null) {
	        String base64Image = Base64.getEncoder().encodeToString(detail.getImage());
	        detail.setImageBase64("data:image/jpeg;base64," + base64Image);
	        System.out.println("image byte length = " + detail.getImageBase64());
	        detail.setImage(null);
	    }

	    return ResponseEntity.ok(detail);
	}
}
