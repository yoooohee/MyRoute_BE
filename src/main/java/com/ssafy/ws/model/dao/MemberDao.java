package com.ssafy.ws.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.ws.model.dto.Member;

@Mapper
public interface MemberDao {
	/**
	 * 회원 가입
	 * @param member
	 */
	public int memberRegister(Member member);
	
	/**
	 * 회원 정보 수정
	 * @param member
	 */
	public int memberInfoUpdate(Member member);
	
	/**
	 * 회원 조회(마이페이지)
	 * @param member
	 */
	public Member getMypageInfo(Member member);
	
	/**
	 * 회원 탈퇴
	 * @param member
	 * @return
	 */
	public int unRegisterMember(Member member);
	
	/**
	 * 로그인
	 * @param member(id, pw만 존재)
	 * @return
	 */
	public Member login(Member member);
	
	/**
	 * 비밀번호 찾기
	 * @param member(사용자가 입력한 이름, 아이디, 이메일 주소만 담음)
	 * @return
	 */
	public Member findPassword(Member member);
	
	public List<Member> findAll();
}
