package com.board.dao;

import java.util.HashMap;
import java.util.Map;

import com.board.domain.MemberVO;

public interface MemberDAO {

	//사용자 등록
	public void signup(MemberVO vo) throws Exception;

	//사용자 사진 등록
	public void registerProfileImg(MemberVO vo) throws Exception;

	//사용자 사진 수정
	public void modifyProfileImg(MemberVO vo) throws Exception; 

	//마지막 로그인 날짜 등록
	public void modifyLoginInfo(MemberVO vo) throws Exception;

	//사용자정보 보기
	public MemberVO viewMemberInfo(MemberVO vo) throws Exception;

	//사용자정보 수정
	public void modifyMemberInfo(MemberVO vo) throws Exception;

	//사용자 암호 수정
	public void modifyPassword(MemberVO vo) throws Exception;

	//사용자정보 삭제
	public void deleteMemberInfo(MemberVO vo) throws Exception;
	
	//댓글 전체 삭제
	public void deleteAllReply(MemberVO vo) throws Exception;

	//로그인 
	public MemberVO login(MemberVO vo) throws Exception;
	
	//패스워드 변경 기간 확인
	public HashMap<String,Object> checkPasswordModification(MemberVO vo) throws Exception;

	//30일 이후 패스워드 변경 날짜 등록
	public void modifyPasswordAfter30(MemberVO vo) throws Exception;

	//패스워드 찾기 
	public int searchPassword(MemberVO vo) throws Exception;

	//아이디 찾기
	public String searchID(MemberVO vo) throws Exception;

	//아이디 중복 체크
	public int idCheck(MemberVO vo) throws Exception;
	
	
}
