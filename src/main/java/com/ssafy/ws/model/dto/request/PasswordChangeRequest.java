package com.ssafy.ws.model.dto.request;

import lombok.Getter;

@Getter
public class PasswordChangeRequest {
	private String id;
	private String email;
	private String password;
}
