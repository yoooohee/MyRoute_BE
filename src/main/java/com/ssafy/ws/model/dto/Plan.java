package com.ssafy.ws.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Plan {
	private Integer planId;
	private String planName;
	private String memberId;
	private int budget;
	private Integer areaCode;
	private Integer days;
}
