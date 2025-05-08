package com.ssafy.ws.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
	private String id;
	private String name;
	private String email;
	private String password;
	private String pnumber;
	private String role;
}
