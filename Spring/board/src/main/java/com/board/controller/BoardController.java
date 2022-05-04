package com.board.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.board.domain.BoardVO;
import com.board.domain.ReplyVO;
import com.board.service.BoardService;
import com.board.util.Page;

@Controller
@RequestMapping("/board/*")
public class BoardController {

	@Inject
	private BoardService service; //의존성 주입으로 객체 생성
	
	//미니 게시판 목록 보기
		@RequestMapping(value = "/list", method = RequestMethod.GET)
		public void postList(Model model, @RequestParam(name="num",required=false) int num, 
				@RequestParam(name="searchType", defaultValue="title", required=false) String searchType, 
				@RequestParam(name="keyword", defaultValue="", required=false) String keyword ) throws Exception {
			
			int postNum = 5; //한 페이지에 게시되는 게시물 갯수
			int listCount = 5; //페이지리스트에 게시되는 페이지 갯수
			int displayPost = (num - 1) * postNum; //읽어올 테이블 행의 위치
			
			System.out.println("searchType = " + searchType);
			System.out.println("keyword = " + keyword); 
			
			List<BoardVO> list = service.list(displayPost, postNum, searchType, keyword);
			int totalCount = service.count(searchType, keyword);
			Page page = new Page();
			
			model.addAttribute("list", list);
			model.addAttribute("pageList", page.getPageList(num, postNum, totalCount, listCount, searchType, keyword));
			model.addAttribute("num", num);
			model.addAttribute("searchType", searchType);
			model.addAttribute("keyword", keyword);
		}
	
	//게시판 등록(GET) >>입력화면보기
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public void getWrite(BoardVO vo) throws Exception {
		
	}
	
	
	//미니 게시판 등록(POST)
		@RequestMapping(value = "/write", method = RequestMethod.POST)
		public String postWrite(BoardVO vo, @RequestParam(name="uploadFile", required=false) MultipartFile multipartFile) throws Exception {
			
			String filePath = "c:\\Repository\\file\\"; //역슬래시는 2개로해야 인식
			File targetFile = new File(filePath); 
			if(!multipartFile.isEmpty()) {
					
						String originalFileName = multipartFile.getOriginalFilename();	
						long filesize = multipartFile.getSize();				
						targetFile = new File(filePath + originalFileName);
						multipartFile.transferTo(targetFile);
						
						vo.setFilename(originalFileName);
						vo.setFilesize(filesize);
			}
			
			//LocalDateTime now = LocalDateTime.now();
			//String mregDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			
			//vo.setMregDate(mregDate);
			service.write(vo);
			return "redirect:/board/list?num=1";
			
		}
	
		
		//미니 게시판 내용 보기
		@RequestMapping(value = "/view", method = RequestMethod.GET)
		public void getView(Model model, @RequestParam(name="seqno", required=false) int seqno, RedirectAttributes rttr) throws Exception {
			
			BoardVO list = service.view(seqno);
			
			if(list.getFilename() != null) 	rttr.addFlashAttribute("message", false);
			model.addAttribute("list", list);
			
		}
		
		
		//게시판 댓글
		@ResponseBody //Ajax를 이용해서 비동기처리를 수행
		@RequestMapping(value = "/reply", method = RequestMethod.POST)
		public List<ReplyVO> postReply(ReplyVO vo, @RequestParam("option") String option) throws Exception {
			System.out.println("---------------1--------------");
			List<ReplyVO> ReplyData = null;
			if(option.equals("I")) { 
				System.out.println("---------------2--------------");
				
//				LocalDateTime now = LocalDateTime.now();
//				String mreplyregDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				//vo.setReplyregDate(mreplyregDate);
				
				System.out.println("---------------3--------------");
				service.replyinsert(vo);
				System.out.println("---------------4--------------");
			}
			ReplyData = service.replylist(vo.getSeqno());
			
			System.out.println(vo.toString());
			System.out.println("---------------5--------------");
			return ReplyData;

		}
		
		
		//게시판 내용 수정
		@RequestMapping(value = "/modify", method = RequestMethod.GET)
		public void getMModify(Model model, @RequestParam(name="seqno", required=false) int seqno) throws Exception {
			
			BoardVO list = service.view(seqno);
			
			model.addAttribute("list", list);
		}
		
		//미니 게시판 내용 수정
		@RequestMapping(value = "/modify", method = RequestMethod.POST)
		public String postMModify(Model model, BoardVO vo) throws Exception {
			
			service.modify(vo);
			return "redirect:/board/view?seqno=" + vo.getSeqno();	

		}
		
		//미니 게시판 내용 삭제
		@RequestMapping(value = "/delete", method = RequestMethod.GET)
		public String postDelete(Model model, @RequestParam(name="seqno", required=false) int seqno) throws Exception {
			
			BoardVO vo = service.view(seqno);
			String filepath = "c:\\Repository\\file\\";
			
			if(vo.getFilesize() != 0) { //파일삭제구간
				String filename = vo.getFilename();
			
				File file = new File(filepath + filename);
				file.delete();
			}
			
			service.delete(seqno);
			return "redirect:/board/list?num=1";	

		}
		

		//파일 다운로드
		@RequestMapping(value = "/fileDownload", method = RequestMethod.GET)
		public void fileDownload(@RequestParam(name="seqno" , required=false) int seqno, HttpServletResponse rs) throws Exception {
			
			BoardVO fileInfo = service.view(seqno);
			String orgFileName = fileInfo.getFilename();
			
			byte fileByte[] = org.apache.commons.io.FileUtils.readFileToByteArray(new File("c:\\Repository\\file\\"+orgFileName));
			
			rs.setContentType("application/octet-stream");
			rs.setContentLength(fileByte.length);
			rs.setHeader("Content-Disposition",  "attachment; fileName=\""+URLEncoder.encode(orgFileName, "UTF-8")+"\";");
			rs.getOutputStream().write(fileByte);
			rs.getOutputStream().flush();
			rs.getOutputStream().close();
			
		}
		
		
}