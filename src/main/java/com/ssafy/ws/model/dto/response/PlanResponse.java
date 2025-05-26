package com.ssafy.ws.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlanResponse {
	private int planId;
	private String planName;
	private String memberName;
	private String areaName;
	private int likeCount;
}
