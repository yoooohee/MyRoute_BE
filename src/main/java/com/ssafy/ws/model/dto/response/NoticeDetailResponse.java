package com.ssafy.ws.model.dto.response;

import com.ssafy.ws.model.dto.Notice;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NoticeDetailResponse {
	private Notice notice;
	private String memberName;
}
