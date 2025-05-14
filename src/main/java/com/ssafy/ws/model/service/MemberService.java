package com.ssafy.ws.model.service;

import org.springframework.stereotype.Service;

import com.ssafy.ws.model.dao.MemberDao;
import com.ssafy.ws.model.dto.Member;
import com.ssafy.ws.model.dto.request.LoginRequest;
import com.ssafy.ws.model.dto.response.MemberInfoResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberDao dao;

	public int signIn(Member member) {
		member.encodePassword(member.getPassword());
		member.initializeUserRole();
		return dao.memberRegister(member);
	}

	public int unRegister(Member member) {
		return dao.unRegisterMember(member);
	}

	public Member login(LoginRequest input) {
		Member dbMember = dao.login(input.getId());
		if (dbMember == null) {
			return null;
		}

		if (!dbMember.isPasswordMatch(input.getPassword())) {
			return null;
		}

		return dbMember;
	}

	public MemberInfoResponse findMyInfoById(String id) {
		return dao.findMyInfoById(id);
	}
}
