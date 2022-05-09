package com.board.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		public String postList(HttpSession session,Model model, @RequestParam(name="num",required=false) int num, 
				@RequestParam(name="searchType", defaultValue="title", required=false) String searchType, 
				@RequestParam(name="keyword", defaultValue="", required=false) String keyword ) throws Exception {
			
			if(session.getAttribute("userid") == null)
				return "redirect:/";
			
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
			
			return null;
			
		}
	
	//게시판 등록(GET) >>입력화면보기
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String getWrite(HttpSession session,BoardVO vo) throws Exception {
		if(session.getAttribute("userid") == null)
			return "redirect:/";
		
		return null;
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
	
		
		//미니 게시판 내용 상세보기
		@RequestMapping(value = "/view", method = RequestMethod.GET)
		public String getView(HttpSession session,Model model, @RequestParam(name="seqno", required=false) int seqno,
				@RequestParam(name="num",required=false) int num, 
				@RequestParam(name="searchType", defaultValue="title", required=false) String searchType, 
				@RequestParam(name="keyword", defaultValue="", required=false) String keyword
				, RedirectAttributes rttr) throws Exception {
			
			if(session.getAttribute("userid") == null)
				return "redirect:/";
			
			String sessionUserid = (String)session.getAttribute("userid");
			BoardVO list = service.view(seqno);
			
			if(list.getFilename() != null) 	rttr.addFlashAttribute("message", false);
			model.addAttribute("list", list);
			
			//시용자 권한을 가진 사람은 타인이 쓴 글을 수정/삭제 할수 없음.
			if(!sessionUserid.equals(list.getUserid()) && !session.getAttribute("authority_code").equals("01") ) 
				rttr.addFlashAttribute("message", "NOT_OWNER"); 
			else model.addAttribute("message", "OWNER");
			
			// 본인이 쓴 게시물은 조회수 증가가 안됨. 
			if(!sessionUserid.equals(list.getUserid())) { 	
				int viewcnt = list.getViewcnt() + 1;
				list.setViewcnt(viewcnt);
				service.modifyViewcnt(list);
			}
			
			
			int viewPrev = service.viewPrev(seqno,searchType,keyword); //이전 게시물 번호를 가져 온다.
			int viewNext =  service.viewNext(seqno,searchType,keyword); //다음 게시물 정보를 가져 온다.
		
			
			if(list.getFilename() != null) 	rttr.addFlashAttribute("message", false);
			model.addAttribute("list", list);
			model.addAttribute("num", num);
			model.addAttribute("searchType", searchType);
			model.addAttribute("keyword", keyword);
			model.addAttribute("viewPrev", viewPrev);
			model.addAttribute("viewNext", viewNext);
		
			return null;
		}
		
		
		
		
		//게시판 댓글
		@ResponseBody //Ajax를 이용해서 비동기처리를 수행
		@RequestMapping(value = "/reply", method = RequestMethod.POST)
		public List<ReplyVO> postReply(ReplyVO vo, @RequestParam("option") String option) throws Exception {
			
			List<ReplyVO> ReplyData = null;
			if(option.equals("I")) { 
				
				
//				LocalDateTime now = LocalDateTime.now();
//				String mreplyregDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				//vo.setReplyregDate(mreplyregDate);
				
				
				service.replyinsert(vo);
				
			}
			ReplyData = service.replylist(vo.getSeqno());
			
			
			
			return ReplyData;

		}
		
		
		//게시판 내용 수정
		@RequestMapping(value = "/modify", method = RequestMethod.GET)
		public String getMModify(HttpSession session,Model model,
				@RequestParam(name="seqno", required=false) int seqno,
				@RequestParam(name="num",required=false) int num, 
				@RequestParam(name="searchType", defaultValue="title", required=false) String searchType, 
				@RequestParam(name="keyword", defaultValue="", required=false) String keyword) throws Exception {
			
			if(session.getAttribute("userid") == null)
				return "redirect:/" ;
			
			BoardVO list = service.view(seqno);
			
			model.addAttribute("list", list);
			model.addAttribute("num", num);
			model.addAttribute("searchType", searchType);
			model.addAttribute("keyword", keyword);
		
			return null;
		}
		
		//미니 게시판 내용 수정
		@RequestMapping(value = "/modify", method = RequestMethod.POST)
		public String postMModify(HttpSession session,Model model, BoardVO vo,
				@RequestParam(name="num",required=false) int num, 
				@RequestParam(name="searchType", defaultValue="title", required=false) String searchType, 
				@RequestParam(name="keyword", defaultValue="", required=false) String keyword) throws Exception {
			
			
			service.modify(vo);
			return "redirect:/board/view?seqno=" + vo.getSeqno() + "&num=" + Integer.toString(num) 
			+ "&searchType=" + searchType + "&keyword=" + keyword;	

			
		}
		
		//미니 게시판 내용 삭제
		@RequestMapping(value = "/delete", method = RequestMethod.GET)
		public String postDelete(Model model, @RequestParam(name="seqno", required=false) int seqno) throws Exception {
			
			BoardVO vo = service.view(seqno);
			String filepath = "c:\\Repository\\file\\";
			
			if(vo.getFilesize() != 0) {
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