package com.ssafy.ws.model.dto;

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

	@Builder
	public Member(String id, String name, String email, String password, String pnumber, String role) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.pnumber = pnumber;
		this.role = role;
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
}
