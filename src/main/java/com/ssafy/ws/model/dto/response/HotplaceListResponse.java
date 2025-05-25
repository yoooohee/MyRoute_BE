package com.ssafy.ws.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HotplaceListResponse {
	private Integer hotplaceId;
	private String memberName;
	private String attractionName;
	private String title;
	private double starPoint;
	private byte[] image;
	private int likeCount;
	private String profileImage;
}
