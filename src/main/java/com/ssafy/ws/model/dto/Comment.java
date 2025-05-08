package com.ssafy.ws.model.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
	private Integer commentId;
	private Integer hotplaceId;
	private String memberId;
	private String content;
	private LocalDateTime createdAt;
}
