package com.kh.spring.member.model.service;

import com.kh.spring.member.model.vo.Member;

public interface MemberService {
	
	// 로그인 서비스(SELECT)
	Member loginMember(Member m);
	
	// 회원가입 서비스(INSERT)
	int insertMember(Member m);
	
	// 회원정보 수정(UPDATE)
	int updateMember(Member m);
	
	// 회원탈퇴 서비스(UPDATE)
	int deleteMember(Member m);
	
	//---------
	
	// 아이디 중복체크 서비스(SELECT)
	int idCheck(String checkId);
	
	// 메일 관련..

}
