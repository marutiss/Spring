package com.board.dao;

import java.util.List;

import com.board.domain.BoardVO;
import com.board.domain.ReplyVO;

public interface BoardDAO {
	//미니 게시판 게시물 등록
		public void write(BoardVO vo) throws Exception;

		//미니 게시판 게시물 목록 보기
		public List<BoardVO> list(int displayPost, int postNum, String searchType, String keyword) throws Exception;
		
		//게시물 총 갯수
		public int count(String searchType,String keyword) throws Exception;
		
		//게시물 이전 페이지
		public int viewPrev(int seqno, String searchType, String keyword) throws Exception;

		//게시물 다음 페이지
		public int viewNext(int seqno, String searchType, String keyword) throws Exception;

		//미니 게시판 게시물 수정
		public void modify( BoardVO vo) throws Exception;

		//미니 게시판 게시물 삭제
		public void delete(int seqno) throws Exception;
		
		//미니 게시판 조회수 수정
		public void modifyViewcnt(BoardVO vo) throws Exception;

		//미니 게시판 게시물 목록 보기
		public  BoardVO view(int seqno) throws Exception;

		//미니게시판 댓글 입력
		public void replyinsert(ReplyVO vo) throws Exception;

		//미니게시판 댓글 리스트 가져 오기
		public List<ReplyVO> replylist(int seqno) throws Exception;
}
