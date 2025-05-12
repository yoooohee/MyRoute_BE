package com.ssafy.ws.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**") // 모든 경로에 대해
				.allowedOrigins("http://localhost:5173").allowedMethods("GET", "POST", "PUT", "DELETE")
				.allowedHeaders("*").allowCredentials(true);
	}
}
