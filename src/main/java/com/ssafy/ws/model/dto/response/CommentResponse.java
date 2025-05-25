package com.ssafy.ws.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentResponse {
	private int commentId;
	private String memberName;
	private String content;
	private LocalDateTime createdAt;
	private boolean editable;
	private String profileImage;
}
