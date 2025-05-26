package com.ssafy.ws.model.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NoticeListResponse {
	private Integer noticeId;
	private String title;
	private String memberName;
	private LocalDateTime createAt;
}
