package com.ssafy.ws.model.dto.response;

import lombok.Getter;

@Getter
public class EmailVerificationResponse {
	private String email;
	private String code;
}
