package com.board.dao;

import java.util.List;

import javax.inject.Inject;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import com.board.domain.BoardVO;

@Repository
public class BoardDAOImpl implements BoardDAO {
	
	@Inject
	private SqlSession sql;
	
	private static String namespace = "com.board.mappers.board";
	
	//게시판 게시물등록
	@Override
	public void write(BoardVO vo) throws Exception {
		sql.insert(namespace + ".write", vo);

	}

	//게시판 게시물 목록보기
	@Override
	public List<BoardVO> list(BoardVO vo) throws Exception {
		return sql.selectList(namespace + ".list", vo);
		
	}

	//게시판 게시물수정
	@Override
	public void modify(BoardVO vo) throws Exception {
		sql.update(namespace + ".modify", vo);

	}

	//게시판 게시물삭제
	@Override
	public void delete(int seqno) throws Exception {
		sql.delete(namespace + ".delete", seqno);

	}

	//게시판 댓글목록보기
	@Override
	public BoardVO view(int seqno) throws Exception {
		return sql.selectOne(namespace + ".view", seqno); 
	}
	
	//게시판 댓글입력
	@Override
	public void ReplyInsert(BoardVO vo) throws Exception {
		sql.insert(namespace + ".ReplyInsert", vo);

	}

	//게시판 댓글리스트 보기
	@Override
	public List<BoardVO> ReplyList(int seqno) throws Exception {
		return sql.selectList(namespace + ".ReplyList", seqno);
	}

}
