package com.board.service;

import java.util.HashMap;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.board.dao.MemberDAO;
import com.board.domain.MemberVO;

@Service
public class MemberServiceImpl implements MemberService{

	@Inject
	 private MemberDAO dao;

	//사용자 등록
	@Override
	public void signup(MemberVO vo) throws Exception {
		dao.signup(vo);		
	}

	//사용자 프로필 이미지 등록
	@Override
	public void registerProfileImg(MemberVO vo) throws Exception {
		dao.registerProfileImg(vo);		
	}

	//사용자 프로필 이미지 수정
	@Override
	public void modifyProfileImg(MemberVO vo) throws Exception {
		dao.modifyProfileImg(vo);		
	}

	//로그인 마지막 날짜 등록
	@Override
	public void modifyLoginInfo(MemberVO vo) throws Exception {
		dao.modifyLoginInfo(vo);		
	}

	//사용자 정보 보기
	@Override
	public MemberVO viewMemberInfo(MemberVO vo) throws Exception {
		return dao.viewMemberInfo(vo);
	}

	//사용자 정보 수정
	@Override
	public void modifyMemberInfo(MemberVO vo) throws Exception {
		dao.modifyMemberInfo(vo);
	}
	
	//패스워드 수정
	@Override
	public void modifyPassword(MemberVO vo) throws Exception {
		dao.modifyPassword(vo);		
	}

	//사용자 정보 삭제(회원 탈퇴)
	@Override
	public void deleteMemberInfo(MemberVO vo) throws Exception {
		dao.deleteMemberInfo(vo);
	}

	//사용자가 등록한 모근 댓글 삭제
	@Override
	public void deleteAllReply(MemberVO vo) throws Exception {
		dao.deleteAllReply(vo);
	}

	//로그인
	@Override
	public MemberVO login(MemberVO vo) throws Exception {
		return dao.login(vo);
	}

	//패스워드 변경 기간 확인
	@Override
	public HashMap<String,Object> checkPasswordModification(MemberVO vo) throws Exception {
		return dao.checkPasswordModification(vo);
	}

	//30일 이후 패스워드 변경
	@Override
	public void modifyPasswordAfter30(MemberVO vo) throws Exception {
		dao.modifyPasswordAfter30(vo);		
	}

	//패스워드 찾기
	@Override
	public int searchPassword(MemberVO vo) throws Exception {
		return dao.searchPassword(vo);
	}

	//아이디 찾기
	@Override
	public String searchID(MemberVO vo) throws Exception {
		return dao.searchID(vo);
	}

	//아이디 중복 확인
	@Override
	public int idCheck(MemberVO vo) throws Exception {
		return dao.idCheck(vo);
	}
	
}
