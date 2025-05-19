package com.ssafy.ws.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NoticeUpdateRequest {
	private int id;
	private String title;
	private String content;
}
