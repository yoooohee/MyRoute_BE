package com.ssafy.ws.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NoticeInsertRequest {
	private String title;
	private String content;
	private String memberId;
}
