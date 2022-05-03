package com.board.service;

import java.util.List;
import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.board.domain.BoardVO;
import com.board.dao.BoardDAO;

@Service
public class BoardServiceImpl implements BoardService {

	@Inject
	private BoardDAO dao;
	
	//게시판 게시물등록
	@Override
	public void write(BoardVO vo) throws Exception {
		dao.write(vo);

	}

	//게시판 게시물목록
	@Override
	public List<BoardVO> list(BoardVO vo) throws Exception {
		return dao.list(vo);
	}

	//게시판 게시물 등록
	@Override
	public void modify(BoardVO vo) throws Exception {
		dao.modify(vo);

	}

	//게시판 게시물삭제
	@Override
	public void delete(int seqno) throws Exception {
		dao.delete(seqno);

	}

	//게시판 게시물목록상세
	@Override
	public BoardVO view(int seqno) throws Exception {
		return dao.view(seqno);
	}

	//게시판 댓글입력
	@Override
	public void ReplyInsert(BoardVO vo) throws Exception {
		dao.ReplyInsert(vo);

	}

	//게시판 댓글 목록
	@Override
	public List<BoardVO> ReplyList(int seqno) throws Exception {
		return dao.ReplyList(seqno);
	}

}
