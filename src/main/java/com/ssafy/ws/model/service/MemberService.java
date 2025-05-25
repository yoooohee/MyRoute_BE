package com.ssafy.ws.model.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.ssafy.ws.model.dao.MemberDao;
import com.ssafy.ws.model.dto.Member;
import com.ssafy.ws.model.dto.request.LoginRequest;
import com.ssafy.ws.model.dto.request.MemberUpdateRequest;
import com.ssafy.ws.model.dto.request.PasswordChangeRequest;
import com.ssafy.ws.model.dto.request.PasswordModifyRequest;
import com.ssafy.ws.model.dto.response.MemberInfoResponse;
import com.ssafy.ws.util.PasswordUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberDao dao;

	public void signIn(Member member) throws Exception {
		member.encodePassword(member.getPassword());
		member.initializeUserRole();
		member.setDefaultImage();
		
		dao.memberRegister(member);
	}

	public int unRegister(Member member) {
		return dao.unRegisterMember(member);
	}

	public Member login(LoginRequest input) {
		Member dbMember = dao.findById(input.getId());
		if (dbMember == null) {
			return null;
		}

		if (!dbMember.isPasswordMatch(input.getPassword())) {
			return null;
		}

		return dbMember;
	}

	public Member findById(String id) {
		return dao.findById(id);
	}

	public MemberInfoResponse findMyInfoById(String id) {
		Member member = dao.findById(id);
		MemberInfoResponse response = new MemberInfoResponse(member.getId(), member.getName(), member.getEmail(),
				member.getPnumber(), member.getProfileImage());

		return response;
	}

	public void updateMemberInfo(String memberId, MemberUpdateRequest member) throws IOException {
		dao.updateMemberInfo(memberId, member);
		
		if (member.getProfileImage() != null && !member.getProfileImage().isEmpty()) {
			byte[] imageBytes = member.getProfileImage().getBytes();
			dao.updateProfileImage(memberId, imageBytes);	
		}
	}

	public void updatePassword(String memberId, PasswordModifyRequest password) {
		Member member = dao.findById(memberId);

		if (!PasswordUtil.checkPassword(password.getCurrentPassword(), member.getPassword())) {
			throw new IllegalArgumentException("기존 비밀번호가 일치하지 않습니다.");
		}

		dao.updatePassword(memberId, PasswordUtil.hashPassword(password.getNewPassword()));
	}

	public void changePassword(PasswordChangeRequest request) {
		String id = request.getId();
		Member member = dao.findById(id);

		if (!member.getEmail().equals(request.getEmail())) {
			throw new IllegalArgumentException("일치하는 회원 정보가 없습니다.");
		}

		String password = request.getPassword();
		dao.updatePassword(id, PasswordUtil.hashPassword(password));
	}
	
	public byte[] findProfileImageById(String id) {
		return dao.findProfileImageById(id);
	}
	
	public int deleteMember(String id) {
		return dao.deleteMember(id);
	}
}
