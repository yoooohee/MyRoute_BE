package com.ssafy.ws.model.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Notice {
	private Integer noticeId;
	private String title;
	private String content;
	private LocalDateTime createAt;
}
