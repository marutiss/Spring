package com.board.controller;

import java.util.List;

import javax.inject.Inject;



import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.board.domain.BoardVO;
import com.board.service.BoardService;
import com.board.util.Page;

@RestController
@RequestMapping("/board/**")
public class JSONController {
	
	@Inject
	private BoardService service; //의존성 주입으로 객체 생성
	/*private BoardService service;
	  public BoardController(BoardService service){
	  	this.service=service;
	  }*/
	
	//미니 게시판 목록 보기
		@RequestMapping(value = "/jsonlist", method = RequestMethod.GET)
		public List<BoardVO> getList( ) throws Exception {
			
			int num=1;
			String searchType ="title";
			String keyword="";
			
			int postNum = 5; //한 페이지에 게시되는 게시물 갯수
			int displayPost = (num - 1) * postNum; //읽어올 테이블 행의 위치
			
			//System.out.println("searchType = " + searchType);
			//System.out.println("keyword = " + keyword); 
			
		
			
			
			return service.list(displayPost, postNum, searchType, keyword);
			
		}
}
