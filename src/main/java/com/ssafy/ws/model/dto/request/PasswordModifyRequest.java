package com.ssafy.ws.model.dto.request;

import lombok.Getter;

@Getter
public class PasswordModifyRequest {
	private String currentPassword;
	private String newPassword;
}
