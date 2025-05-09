package com.ssafy.ws.model.service;

import org.springframework.stereotype.Service;

import com.ssafy.ws.model.dao.MemberDao;
import com.ssafy.ws.model.dto.Member;

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

	public Member login(Member input) {
		Member dbMember = dao.login(input.getId());
		if (dbMember == null) {
			System.out.println("조회안됨");
			return null;
		}

		if (!dbMember.isPasswordMatch(input.getPassword())) {
			System.out.println("비번 틀림");
			return null;
		}

		return dbMember;
	}

	public Member findPassword(Member member) {
		return dao.findPassword(member);
	}
}
