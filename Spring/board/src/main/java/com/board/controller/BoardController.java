package com.board.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.board.domain.BoardVO;
import com.board.service.BoardService;

@Controller
@RequestMapping("/board/*")
public class BoardController {

	@Inject
	private BoardService service; //의존성 주입으로 객체 생성
	
	//미니 게시판 보기
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void getList(BoardVO vo, Model model) throws Exception {
			
		List<BoardVO> list = service.list(vo);
		
			
		model.addAttribute("list", list);
	
	}


}