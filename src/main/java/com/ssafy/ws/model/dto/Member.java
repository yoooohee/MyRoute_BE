package com.ssafy.ws.model.dto;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import com.ssafy.ws.util.PasswordUtil;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Member {
	private String id;
	private String name;
	private String email;
	private String password;
	private String pnumber;
	private String role;
	private byte[] profileImage;

	@Builder
	public Member(String id, String name, String email, String password, String pnumber, String role,
			byte[] profileImage) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.pnumber = pnumber;
		this.role = role;
		this.profileImage = profileImage;
	}

	public void encodePassword(String rawPassword) {
		this.password = PasswordUtil.hashPassword(rawPassword);
	}

	public void initializeUserRole() {
		this.role = "USER";
	}

	public void changePassword(String newPassword) {
		this.password = PasswordUtil.hashPassword(newPassword);
	}

	public boolean isPasswordMatch(String rawPassword) {
		return PasswordUtil.checkPassword(rawPassword, this.password);
	}

	public void setDefaultImage() throws Exception {
		ClassPathResource resource = new ClassPathResource("static/img/default.png");
		byte[] imageBytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
		this.profileImage = imageBytes;
	}
}
