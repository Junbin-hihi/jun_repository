package com.kh.spring.member.model.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.spring.member.model.dao.MemberDao;
import com.kh.spring.member.model.vo.Member;

// 옛날에는 @Component애노테이션 밖에 없었음
// 좀 구체적으로 만들자해서 MVC
@Service
public class MemberServiceImpl implements MemberService {
	
	// private MemberDao memberDao = new MemberDao(); xx
	
	@Autowired
	private MemberDao memberDao;
	
	@Autowired
	private SqlSessionTemplate sqlSession;

	@Override
	public Member loginMember(Member m) {
		//Member loginMember = memberDao.loginMember(sqlSession, m);
		// SqlSessionTemplate객체를 bean으로 등록 @Autowired
		// 스프링이 사용 후 자동으로 객체를 알아서 반납시켜줌.
		// destroy-method="close"를 DB연결 객체를 bean등록하며 속성을 달아줬기떄문에
		// 스프링이 자동으로 객체를 소멸시켜준다. 더이상 close()호출할 일이 없음.
		return memberDao.loginMember(sqlSession, m);
	}
	
	@Transactional
	@Override
	public int insertMember(Member m) {
		// SqlSessionTemplate 객체가 자동으로 commit해줌. 트랜잭션o
		// 자원반납 또한 spring이 대신해줌 destroy 설정을 해둠 root-context.xml에서
		return memberDao.insertMember(sqlSession, m);
	}

	@Override
	public int updateMember(Member m) {
		return 0;
	}

	@Override
	public int deleteMember(Member m) {
		return 0;
	}

	@Override
	public int idCheck(String checkId) {
		return 0;
	}

}
