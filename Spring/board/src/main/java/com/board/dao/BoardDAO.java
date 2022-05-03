package com.board.dao;

import java.util.List;

import com.board.domain.BoardVO;

public interface BoardDAO {
	//미니 게시판 게시물 등록
		public void write(BoardVO vo) throws Exception;

		//미니 게시판 게시물 목록 보기
		public List<BoardVO> list( BoardVO vo) throws Exception;

		//미니 게시판 게시물 수정
		public void modify( BoardVO vo) throws Exception;

		//미니 게시판 게시물 삭제
		public void delete(int seqno) throws Exception;

		//미니 게시판 게시물 목록 보기
		public  BoardVO view(int seqno) throws Exception;

		//미니게시판 댓글 입력
		public void ReplyInsert(BoardVO vo) throws Exception;

		//미니게시판 댓글 리스트 가져 오기
		public List<BoardVO> ReplyList(int seqno) throws Exception;
}
