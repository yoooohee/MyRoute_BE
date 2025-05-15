package com.ssafy.ws.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberInfoResponse {
	private String id;
	private String name;
	private String email;
	private String pnumber;
}
