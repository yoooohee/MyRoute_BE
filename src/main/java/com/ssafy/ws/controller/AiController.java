package com.ssafy.ws.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ws.model.service.AiService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;

    @PostMapping("/generate-description")
    public ResponseEntity<String> generateDescription(@RequestBody Map<String, String> request) {
        String title = request.get("title");
        try {
            String result = aiService.generateTourDescription(title);
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("GPT API 호출 실패: " + e.getMessage());
        }
    }
    
    @PostMapping("/recommend-course")
    public ResponseEntity<String> recommendCourse(@RequestBody Map<String, String> request) {
        String area = request.get("area");
        String days = request.get("days");
        String userType = request.get("userType");
        try {
            String result = aiService.recommendCourse(area, days, userType);
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("GPT API 호출 실패: " + e.getMessage());
        }
    }


}