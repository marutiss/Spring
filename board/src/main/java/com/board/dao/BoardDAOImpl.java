package com.board.dao;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.board.domain.BoardVO;
import com.board.domain.ReplyVO;

@Repository
public class BoardDAOImpl implements BoardDAO {

	@Inject
	private SqlSession sql;
	
	private static String namespace = "com.board.mappers.board";
	
	//게시판 게시물 등록
	@Override
	public void write(BoardVO vo) throws Exception {
		sql.insert(namespace + ".write", vo);

	}

	//게시판 게시물 목록 보기
	@Override
	public List<BoardVO> list(int displayPost, int postNum, String searchType, String keyword) throws Exception {
		
		HashMap<String, Object> data = new HashMap<String, Object>();
		
		data.put("displayPost", displayPost);
		data.put("postNum", postNum);
		data.put("searchType", searchType);
		data.put("keyword", keyword);
		
		return sql.selectList(namespace + ".list", data);
	}

	//전체게시물 갯수
	@Override
	public int count(String searchType, String keyword) throws Exception {
		HashMap<String,String> data = new HashMap<String, String>();
		data.put("searchType", searchType);
		data.put("keyword", keyword);
		
		return sql.selectOne(namespace + ".count",data); 
	}
	
	//게시물 이전 페이지
	@Override
	public int viewPrev(int seqno, String searchType, String keyword) throws Exception {
		HashMap data = new HashMap();
		data.put("seqno", seqno);
		data.put("searchType", searchType);
		data.put("keyword", keyword);
		return sql.selectOne(namespace + ".viewPrev", data);
	}

	//게시물 다음 페이지
	@Override
	public int viewNext(int seqno, String searchType, String keyword) throws Exception {
		HashMap data = new HashMap();
		data.put("seqno", seqno);
		data.put("searchType", searchType);
		data.put("keyword", keyword);	
		return sql.selectOne(namespace + ".viewNext", data);
	}
	
	//게시판 게시물 수정
	@Override
	public void modify(BoardVO vo) throws Exception {
		sql.update(namespace + ".modify", vo);	

	}

	//게시판 게시물 삭제
	@Override
	public void delete(int seqno) throws Exception {
		sql.delete(namespace + ".delete", seqno);
	}
	
	//미니 게시판 조회수 수정
	@Override
	public void modifyViewcnt(BoardVO vo) throws Exception{
		sql.update(namespace + ".modifyViewcnt", vo);
	}

	//게시판 게시물 목록 보기
	@Override
	public BoardVO view(int seqno) throws Exception {
		return sql.selectOne(namespace + ".view", seqno); 
	}

	//게시판 댓글 입력
	@Override
	public void replyinsert(ReplyVO vo) throws Exception {
		sql.insert(namespace + ".replyinsert", vo);

	}

	//게시판 댓글 리스트 가져 오기
	@Override
	public List<ReplyVO> replylist(int seqno) throws Exception {
		return sql.selectList(namespace + ".replylist", seqno);
	}

}
