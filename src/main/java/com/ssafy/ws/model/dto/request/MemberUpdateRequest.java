package com.ssafy.ws.model.dto.request;

import lombok.Getter;

@Getter
public class MemberUpdateRequest {
	private String name;
	private String email;
	private String pnumber;
}
