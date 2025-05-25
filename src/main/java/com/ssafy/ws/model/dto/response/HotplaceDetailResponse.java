package com.ssafy.ws.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotplaceDetailResponse {
	private String hotplaceId;
	private String memberName;
	private String attractionName;
	private String title;
	private String content;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private double starPoint;
	private int likeCount;
	private String imageBase64;
	private boolean likedByUser;
	private boolean myPost;
	private String profileImage;
}
