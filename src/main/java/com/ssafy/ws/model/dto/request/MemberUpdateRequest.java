package com.ssafy.ws.model.dto.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberUpdateRequest {
	private String name;
	private String email;
	private String pnumber;
	private MultipartFile profileImage;
}
