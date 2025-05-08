package com.ssafy.ws.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.ws.model.dao.MemberDao;
import com.ssafy.ws.model.dto.Member;
import com.ssafy.ws.util.PasswordUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberDao dao;
	
	public int signIn(Member member) {
        String hashed = PasswordUtil.hashPassword(member.getPassword());
        member.setPassword(hashed);

		return dao.memberRegister(member);
	}
	
	public int updateInfo(Member member) {
        String hashed = PasswordUtil.hashPassword(member.getPassword());
        member.setPassword(hashed);
		return dao.memberInfoUpdate(member);
	}
	
	public Member getMyInfo(Member member) {
		return dao.getMypageInfo(member);
	}
	
	public int unRegister(Member member) {
		return dao.unRegisterMember(member);
	}
	
	public Member login(Member member) {
		Member result = dao.login(member);
		if (result == null) return null;
		
        boolean match = PasswordUtil.checkPassword(member.getPassword(), result.getPassword());
        if (!match) return null;
        
        result.setPassword(null);
        return result;
	}
	
	public Member findPassword(Member member) {
		return dao.findPassword(member);
	}
	
	public List<Member> findAll() {
		return dao.findAll();
	}
}
