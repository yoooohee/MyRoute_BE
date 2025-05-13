package com.ssafy.ws.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ws.model.dto.Att;
import com.ssafy.ws.model.dto.Parking;
import com.ssafy.ws.model.dto.Place;
import com.ssafy.ws.model.dto.Plan;
import com.ssafy.ws.model.dto.response.PlanDetailResponse;
import com.ssafy.ws.model.service.AttServiceImpl;
import com.ssafy.ws.util.ParkingJsonParser;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import java.io.Reader;
import java.sql.SQLException;

@RestController
@RequestMapping("/api/att")
@RequiredArgsConstructor
public class AttController {
	private final AttServiceImpl aService;

	@GetMapping("/list")
	private String registMemberForm() {
		return "att/att-list-form";
	}

	@PostMapping("/search")
	public String searchAtt(@RequestParam String sido, @RequestParam String gugun, @RequestParam int contentType,
			Model model) {
		try {
			List<Att> atts = aService.searchAtt(sido, gugun, contentType);
			model.addAttribute("atts", atts);
			return "att/att-list-form";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", e.getMessage());
			return "att/att-list-form";
		}
	}

	@PostMapping("/search-parking")
	public ResponseEntity<?> searchParkingJson(@RequestParam double lat, @RequestParam double lon) throws IOException {
		try {
			ClassPathResource resource = new ClassPathResource("/static/resource/parking.json");
			Reader reader = new InputStreamReader(resource.getInputStream());
			List<Parking> allParkings = ParkingJsonParser.parse(reader);
			List<Parking> nearby = findNearbyParkings(lat, lon, allParkings);

			return ResponseEntity.ok(nearby); // JSON 응답
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("주차장 조회 실패: " + e.getMessage());
		}
	}

	public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
		final int R = 6371; // 지구 반지름 km
		double dLat = Math.toRadians(lat2 - lat1);
		double dLon = Math.toRadians(lon2 - lon1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		return R * c;
	}

	public static List<Parking> findNearbyParkings(double attLat, double attLon, List<Parking> allParkings) {
		List<Parking> filtered = new ArrayList<>();

		for (Parking p : allParkings) {
			double distance = calculateDistance(attLat, attLon, p.getLatitude(), p.getLongitude());
			if (distance <= 1.0) { // 1km 이내만
				p.setDistance(distance);
				filtered.add(p);
			}
		}

		return filtered.stream().sorted(Comparator.comparingDouble(p -> p.distance)).limit(10)
				.collect(Collectors.toList());
	}

	@PostMapping("/attplan")
	public ResponseEntity<List<Att>> getAttractions(@RequestBody Map<String, Object> params) throws SQLException {
		String sido = (String) params.get("sido");
		String gugun = (String) params.get("gugun");
		int attId = (int) params.get("att_id");

		System.out.println("sido = " + sido + ", gugun = " + gugun + ", att_id = " + attId);

		List<Att> atts;
		if (attId == 0) {
			atts = aService.searchAttLocation(sido, gugun);
		} else {
			atts = aService.searchAtt(sido, gugun, attId);
		}

		return ResponseEntity.ok(atts);
	}

	@PostMapping("/savePlan")
	public ResponseEntity<String> savePlan(@RequestBody Map<String, Object> request, HttpSession session)
			throws SQLException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
		}

		String memberId = authentication.getName();

		String title = (String) request.get("title");
		int days = (int) request.get("days");
		int budget = (int) request.get("budget");
		String sido = (String) request.get("sido");
		int areacode = aService.sidonum(sido);

		// TODO: memberId, areaCode는 임시값 or 실제 값으로 수정
		Plan plan = Plan.builder().planName(title).memberId(memberId).budget(budget).areaCode(areacode).days(days)
				.build();

		List<Map<String, Object>> planItems = (List<Map<String, Object>>) request.get("plans");
		List<Place> places = new ArrayList<>();

		for (int i = 0; i < planItems.size(); i++) {
			Map<String, Object> item = planItems.get(i);
			Integer attNo = (Integer) item.get("no");

			Place place = Place.builder().attractionNo(attNo).visitOrder(i + 1).build();
			places.add(place);
		}

		aService.savePlan(plan, places);

		return ResponseEntity.ok("저장 성공");
	}
	
	@GetMapping("/planlist")
	public ResponseEntity<?> myPlanList() throws SQLException {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	    if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
	    }

	    String memberId = authentication.getName();
	    
	    List<Plan> plans = aService.getPlanByUserId(memberId);
	    
	    return ResponseEntity.ok(plans);
	}
	
	@GetMapping("/plan/{planId}")
	public ResponseEntity<?> getPlanDetail(@PathVariable int planId) throws SQLException {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
	    }

	    Plan plan = aService.getPlanById(planId);
	    if (plan == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 계획이 존재하지 않습니다.");
	    }
	    
	    List<Place> places = aService.getPlacesByPlanId(planId);

	    PlanDetailResponse response = PlanDetailResponse.builder()
	        .plan(plan)
	        .places(places)
	        .build();
	    
	    System.out.println(planId);

	    return ResponseEntity.ok(response);
	}


}
