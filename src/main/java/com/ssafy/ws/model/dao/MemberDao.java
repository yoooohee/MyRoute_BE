package com.ssafy.ws.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.ws.model.dto.Member;
import com.ssafy.ws.model.dto.request.MemberUpdateRequest;
import com.ssafy.ws.model.dto.request.PasswordChangeRequest;
import com.ssafy.ws.model.dto.response.MemberInfoResponse;

@Mapper
public interface MemberDao {
	/**
	 * 회원 가입
	 * 
	 * @param member
	 */
	public int memberRegister(Member member);

	/**
	 * 회원 조회(마이페이지)
	 * 
	 * @param member
	 */
	public MemberInfoResponse findMyInfoById(String memberId);

	/**
	 * 회원 탈퇴
	 * 
	 * @param member
	 * @return
	 */
	public int unRegisterMember(Member member);

	public int updateMemberInfo(String id, MemberUpdateRequest member);

	public Member findById(String memberId);

	public void updatePassword(String id, String password);

	public void changePassword(PasswordChangeRequest request);

	public byte[] findProfileImageById(String id);

	public void updateProfileImage(String id, byte[] image);
	
	public int deleteMember(String id);
}
