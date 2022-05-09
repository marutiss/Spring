package com.board.controller;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.board.domain.MemberVO;
import com.board.service.MemberService;

@Controller
@RequestMapping("/member/*")
public class MemberController {
	
	@Inject
	MemberService service;
	
	@Inject
	 private BCryptPasswordEncoder pwdEncoder;
	
	//사용자 등록(GET 방식)
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public void getRegisterMemberInfo(MemberVO vo) throws Exception { }
		 
	//사용자 등록(POST 방식)
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String postRegisterMemberInfo(MemberVO vo, HttpSession session) throws Exception {
	    
		int result = service.idCheck(vo);//아이디 중복 확인
		
		//중복된 아이디가 있으면 초기 화면으로 리다이렉트
		if(result == 1) return "redirect:/board/signup";
			else if(result == 0) { // 회원 가입 절차 진행
			    	
			String inputPass = vo.getUserpassword(); //로그인 창에서 입력한 암호 저장
			String pwd = pwdEncoder.encode(inputPass); 
			vo.setUserpassword(pwd);
			
			LocalDateTime currentTime = LocalDateTime.now();
	  		String FormattedCurrentTime = currentTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	  		
	  		vo.setRegdate(FormattedCurrentTime); //등록일
	  		vo.setLastlogindate(FormattedCurrentTime); //마지막 로그인 날짜 
	  		vo.setPwmodidate(FormattedCurrentTime); //패스워드 등록/변경일
			
			service.signup(vo); //사용자 정보 등록 >>jsp파일
			
			//HttpSession session = req.getSession(); //세션 방식
			session.setAttribute("userid",vo.getUserid()); //속성셋팅
			session.setAttribute("username",vo.getUsername());
			session.setAttribute("authority_code","02");
			
		}
		return "redirect:/board/list?num=1";	
	}
	
	//사용자 프로파일 사진업로드(Ajax)
	@ResponseBody
	@RequestMapping(value = "/imgUpload") 
	public String imgUpload(MemberVO vo, @RequestParam("imgUpload") MultipartFile multipartFile, 
			@RequestParam("userid") String userid, @RequestParam("kinds") String kinds ) 
	{ 
	 
		String strResult = "{ \"result\":\"FAIL\" }";
		String filePath = "c:\\Repository\\profile\\";
					
		File targetFile;
		
		if(!multipartFile.isEmpty()) {
				
				String originalFileName = multipartFile.getOriginalFilename();	
				String originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));	
				String storedFileName =  UUID.randomUUID().toString().replaceAll("-", "") + originalFileExtension;	
								
				try {
					targetFile = new File(filePath + storedFileName);
					
					multipartFile.transferTo(targetFile);
					
					vo.setOrg_img(originalFileName);
					vo.setStored_img(storedFileName);
					vo.setImg_size(multipartFile.getSize());
					vo.setUserid(userid);
					
					if(kinds.equals("R")) service.registerProfileImg(vo); // 프로파일 이미지 신규 등록
						else if(kinds.equals("M")) { //이미지 수정 : 기존 파일 삭제하고 신규 파일 등록 
														
							String old_img_stored_file = service.viewMemberInfo(vo).getStored_img();
							File file = new File(filePath + old_img_stored_file);
							file.delete();
							
							service.modifyProfileImg(vo);						
					}
					
					strResult = "{ \"result\":\"OK\" }";
															
				} catch (Exception e ) { e.printStackTrace(); }
		}	

		return strResult;
	}
		
	//사용자 정보 보기(GET 방식)
	@RequestMapping(value = "/viewMemberInfo", method = RequestMethod.GET)
	public void GetViewMemberInfo(MemberVO vo, Model model, HttpSession session) throws Exception {
	  
		String a_code = (String)session.getAttribute("authority_code");
		vo.setUserid((String)session.getAttribute("userid")); //세션 userid를 읽어 온다.
		
		MemberVO memberInfo = service.viewMemberInfo(vo);
		model.addAttribute("view", memberInfo);
				
		if(a_code.equals("01")) model.addAttribute("a_code", "Master 관리자"); 
		else if(a_code.equals("02")) model.addAttribute("a_code", "일반 사용자"); 
		 			
	}
	
	//사용자 정보 수정(GET)
	@RequestMapping(value = "/modifyMemberInfo", method = RequestMethod.GET)
	public void GetModifyMemberInfo(MemberVO vo, HttpSession session, Model model) throws Exception {
		
		vo.setUserid((String)session.getAttribute("userid"));
		model.addAttribute("view", service.viewMemberInfo(vo));
		
	}
	
	//사용자 정보 수정(POST)
	@RequestMapping(value = "/modifyMemberInfo", method = RequestMethod.POST)
	public String PostModifyMembetInfo(MemberVO vo, HttpSession session) throws Exception {
		  service.modifyMemberInfo(vo);
		  
		  return "redirect:/member/viewMemberInfo";
	}
	
	//사용자 암호 수정(GET)
	@RequestMapping(value = "/modifyPassword", method = RequestMethod.GET)
	public void GetdModifyPassword() throws Exception { }
	
	//사용자 암호 수정(POST)
	@RequestMapping(value = "/modifyPassword", method = RequestMethod.POST)
	public String PostModifyPassword(MemberVO vo, HttpSession session, 
			@RequestParam("olduserpassword") String olduserpassword, RedirectAttributes rttr) throws Exception {
		
		  	vo.setUserid((String)session.getAttribute("userid"));
		  	MemberVO memberInfo = service.viewMemberInfo(vo);
		  		  	
		  	if(!pwdEncoder.matches(olduserpassword, memberInfo.getUserpassword())) {
		  		
		  		rttr.addFlashAttribute("msg", false);
		  		return "redirect:/member/modifyPassword";
		  		
		  	} else {
		  		
		  		memberInfo.setUserpassword(pwdEncoder.encode(vo.getUserpassword())); 
		  		LocalDateTime currentTime = LocalDateTime.now();
		  		String FormattedCurrentTime = currentTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		  		memberInfo.setPwmodidate(FormattedCurrentTime);
		  		
		  		service.modifyPassword(memberInfo);

		  		return "redirect:/member/logout";
		  	}
	}
	
	//로그인(GET)
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public void login() throws Exception {	}

	//로그인(POST)
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(MemberVO loginPostData, Model model,HttpSession session, RedirectAttributes rttr) throws Exception {
				
		try {
			
			int sessionInterval = 60*60*24; //세션 유지 기간 : 1일
			session.setMaxInactiveInterval(sessionInterval);
			session.getAttribute("member");
			
			int result = service.idCheck(loginPostData);
			
			if(result == 0) { //존재하지 않는 아이디가 입력될때 
				rttr.addFlashAttribute("message", "ID_NOT_FOUND");
				return "redirect:/";			
			} 		
			MemberVO memberData = service.login(loginPostData);	
			
			boolean pwdMatch = pwdEncoder.matches(loginPostData.getUserpassword(),memberData.getUserpassword());
			
			if(memberData != null && pwdMatch) {
				String userid = memberData.getUserid();
				session.setAttribute("userid",userid);
				session.setAttribute("username",memberData.getUsername());
				session.setAttribute("authority_code",memberData.getAuthority_code());
				
				LocalDateTime currentTime = LocalDateTime.now();
				String FormattedCurrentTime = currentTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

				memberData.setLastlogindate(FormattedCurrentTime);
				service.modifyLoginInfo(memberData);
				
				//사용자 암호 변경 공지 확인
				Map<String, Object> pwCheck = new HashMap<String, Object>();
				pwCheck = service.checkPasswordModification(memberData);
				
				int DiffDate = 0;
				int WaitDate = -1;
				DiffDate = (int)pwCheck.get("DiffDate");
				if(pwCheck.get("WaitDate") != null) WaitDate = (int)pwCheck.get("WaitDate");
				
				if(DiffDate >= 30 && WaitDate == 0 ) return "redirect:/member/pwCheckNotice";
				if(DiffDate >= 30 && WaitDate > 0 ) return "redirect:/board/list?num=1";
				return "redirect:/board/list?num=1";
			
			} else {
				rttr.addFlashAttribute("message", "PASSWORD_NOT_FOUND");
				return "redirect:/";
			}
		} catch(Exception e) {e.printStackTrace(); }
		
		return null;
	}

	//패스워드 변경 안내 공지창 열기
	@RequestMapping(value = "/pwCheckNotice", method = RequestMethod.GET)
	public void pwCheckNotice(HttpSession session) throws Exception{
	}

	//패스워드 30일 이후 변경 등록
	@RequestMapping(value = "/modifyPasswordAfter30", method = RequestMethod.GET)
	public String pwChangeAfter30(MemberVO vo, HttpSession session) throws Exception{
		
		vo.setUserid((String)session.getAttribute("userid"));
		
		service.modifyPasswordAfter30(vo);
		
		return "redirect:/board/list?num=1";
	}

	//임시 패스워드 생성(GET)
	@RequestMapping(value = "/searchPassword", method = RequestMethod.GET)
	public void getPWSearch() throws Exception{ }

	//임시 패스워드 생성(POST)
	@RequestMapping(value = "/searchPassword", method = RequestMethod.POST)
	public String postPWSearch(MemberVO vo, Model model, RedirectAttributes rttr, HttpSession session) throws Exception{

		//아이디, 이름 확인
		int pwCheck = service.searchPassword(vo);
		
		//조건에 해당하는 사용자가 아닐 경우 
		if(pwCheck == 0 ) { 
			rttr.addFlashAttribute("message", false);
			return "redirect:/member/searchPassword"; 
		}
		
		//숫자 + 영문대소문자 7자리 임시패스워드 생성
		StringBuffer tempPW = new StringBuffer();
		Random rnd = new Random();
		for (int i = 0; i < 7; i++) {
		    int rIndex = rnd.nextInt(3);
		    switch (rIndex) {
		    case 0:
		        // a-z : 아스키코드 97~122
		    	tempPW.append((char) ((int) (rnd.nextInt(26)) + 97));
		        break;
		    case 1:
		        // A-Z : 아스키코드 65~122
		    	tempPW.append((char) ((int) (rnd.nextInt(26)) + 65));
		        break;
		    case 2:
		        // 0-9
		    	tempPW.append((rnd.nextInt(10)));
		        break;
		    }
		}
		
		String pwd = pwdEncoder.encode(tempPW); //생성한 임시 패스워드를 비대칭 암호화
		vo.setUserpassword(pwd);
		
		LocalDateTime currentTime = LocalDateTime.now();
		String FormattedCurrentTime = currentTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		vo.setPwmodidate(FormattedCurrentTime);
		service.modifyPassword(vo);
				
		session.setAttribute("tempPW", tempPW);
			
		return "redirect:/member/tempPWView"; 
	}

	//임시 패스워드 보기(GET)
	@RequestMapping(value = "/tempPWView", method = RequestMethod.GET)
	public void getTempPWView(Model model, HttpSession session) throws Exception{

			String tempPW = String.valueOf(session.getAttribute("tempPW"));
			
			System.out.println("tempPW = " + tempPW);
			model.addAttribute("tempPW", tempPW);
			session.removeAttribute("tempPW");
	}

	//아이디 찾기(GET)
	@RequestMapping(value = "/searchID", method = RequestMethod.GET)
	public void getIDSearch() throws Exception{ }

	//아이디 찾기(POST)
	@RequestMapping(value = "/searchID", method = RequestMethod.POST)
	public String postIDSearch(MemberVO vo, Model model, HttpSession session, RedirectAttributes rttr) throws Exception{

		String userid = service.searchID(vo);
		
		//조건에 해당하는 사용자가 아닐 경우 
		if(userid == null ) { 
			rttr.addFlashAttribute("message", false);
			return "redirect:/member/searchID"; 
		}
			
		session.setAttribute("userid", userid);
		
		return "redirect:/member/IDView"; 
	}

	//찾은 아이디 보기
	@RequestMapping(value = "/IDView", method = RequestMethod.GET)
	public void getIDView(Model model, HttpSession session) throws Exception{

			String userid = String.valueOf(session.getAttribute("userid"));
				
			model.addAttribute("userid", userid);
			session.removeAttribute("userid");
	}

	//로그 아웃
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) throws Exception{
		
		session.invalidate();
		
		return "redirect:/";
	}

	//아이디 중복 체크
	@ResponseBody
	@RequestMapping(value = "/idCheck", method = RequestMethod.POST)
	public int idCheck(MemberVO vo) throws Exception {
		int result = service.idCheck(vo);
		return result;
	}	
	
	
}
