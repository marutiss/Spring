package com.board.service;

import java.util.List;
import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.board.domain.BoardVO;
import com.board.domain.ReplyVO;
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

	//미니 게시판 게시물 목록 보기
		public List<BoardVO> list(int displayPost, int postNum, String searchType, String keyword) throws Exception {
			return dao.list(displayPost, postNum, searchType, keyword);
		}

		//게시물 전체 갯수
		@Override
		public int count(String searchType, String keyword) throws Exception {
			return dao.count(searchType, keyword);
		}

		//게시물 이전 페이지
		@Override
		public int viewPrev(int seqno,String searchType,String keyword) throws Exception {
			return dao.viewPrev(seqno,searchType,keyword);
		}

		//게시물 다음 페이지
		@Override
		public int viewNext(int seqno,String searchType,String keyword) throws Exception {
			return dao.viewNext(seqno,searchType,keyword);
		}

	//게시판 게시물 수정
	@Override
	public void modify(BoardVO vo) throws Exception {
		dao.modify(vo);

	}

	//게시판 게시물삭제
	@Override
	public void delete(int seqno) throws Exception {
		dao.delete(seqno);

	}
	
	//미니 게시판 조회수 수정
	@Override
	public void modifyViewcnt(BoardVO vo) throws Exception{
		dao.modifyViewcnt(vo);
	}

	//게시판 게시물목록상세
	@Override
	public BoardVO view(int seqno) throws Exception {
		return dao.view(seqno);
	}

	//게시판 댓글입력
	@Override
	public void replyinsert(ReplyVO vo) throws Exception {
		dao.replyinsert(vo);

	}

	//게시판 댓글 목록
		public List<ReplyVO> replylist(int seqno) throws Exception{
			return dao.replylist(seqno);
		}

		

}
