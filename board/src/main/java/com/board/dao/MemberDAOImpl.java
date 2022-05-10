package com.board.dao;

import java.util.HashMap;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.board.domain.MemberVO;

@Repository
public class MemberDAOImpl implements MemberDAO {

	@Inject
	private SqlSession sql;
	
	private static String namespace = "com.board.mappers.Member";

	//사용자 등록
	@Override
	public void signup(MemberVO vo) throws Exception {
		sql.update(namespace + ".signup", vo);
	}

	//사용자 프로필 사진 등록
	@Override
	public void registerProfileImg(MemberVO vo) throws Exception {
		sql.insert(namespace + ".registerProfileImg", vo);
	}

	//사용자 프로필 사진 수정
	@Override
	public void modifyProfileImg(MemberVO vo) throws Exception {
		sql.update(namespace+ ".modifyProfileImg", vo);
	}

	//마지막 로그인 날짜 수정
	@Override
	public void modifyLoginInfo(MemberVO vo) throws Exception {
		sql.update(namespace + ".modifyLoginInfo", vo);
	}

	//사용자 정보 보기
	@Override
	public MemberVO viewMemberInfo(MemberVO vo) throws Exception {
		return sql.selectOne(namespace + ".viewMemberInfo", vo);
	}

	//사용자 정보 수정
	@Override
	public void modifyMemberInfo(MemberVO vo) throws Exception {
		sql.update(namespace + ".modifyMemberInfo", vo);
	}

	//사용자 패스워드 수정
	@Override
	public void modifyPassword(MemberVO vo) throws Exception {
		sql.update(namespace + ".modifyPassword", vo);
	}

	//사용자 정부 삭제
	@Override
	public void deleteMemberInfo(MemberVO vo) throws Exception {
		sql.delete(namespace + ".deleteMemberInfo", vo);
	}

	//사용자 등록 댓글 전체 삭제
	@Override
	public void deleteAllReply(MemberVO vo) throws Exception {
		sql.delete(namespace + ".deleteAllReply", vo);
	}

	//로그인 처리
	@Override
	public MemberVO login(MemberVO vo) throws Exception {
		return sql.selectOne(namespace + ".login", vo);
	}

	//패스워드 변경 기간 확인
	@Override
	public HashMap<String, Object> checkPasswordModification(MemberVO vo) throws Exception {
		return sql.selectOne(namespace + ".checkPasswordModification", vo);
	}

	//30일 이후 패스워드 변경 등록
	@Override
	public void modifyPasswordAfter30(MemberVO vo) throws Exception {
		sql.update(namespace + ".modifyPasswordAfter30", vo);
	}

	//패스워드 찾기
	@Override
	public int searchPassword(MemberVO vo) throws Exception {
		return sql.selectOne(namespace + ".searchPassword", vo);
	}

	//아이디 찾기
	@Override
	public String searchID(MemberVO vo) throws Exception {
		return sql.selectOne(namespace + ".searchID", vo);
	}

	//아이디 중복 여부 확인
	@Override
	public int idCheck(MemberVO vo) throws Exception {
		return sql.selectOne(namespace + ".idCheck", vo);
	}
	
}
