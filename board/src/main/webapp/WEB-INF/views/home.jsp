<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>자바 스프링을 이용한 게시판 작성</title>

<script>
function loginCheck(){

	if(document.loginForm.userid.value == '') 
		{
		alert('아이디를 입력하세요.');
		document.loginForm.userid.focus();
		return false;
		}
	var Passwd = document.getElementById('userpassword').value;
    if(Passwd == '')
    	{
    	alert('비밀번호를 입력하세요.');
    	document.loginForm.password.focus();
    	return false;
    	}
    document.loginForm.action = '/member/login';
    document.loginForm.submit();
}

function press() {
	
	if(event.keyCode == 13){ loginCheck(); }
	
}

</script>
<style>
body { font-family: "나눔고딕", "맑은고딕" }
h1 { font-family: "HY견고딕" }
a:link { color: black; }
a:visited { color: black; }
a:hover { color: blue; }
a:active { color: red; }
#hypertext { text-decoration-line: none; cursor: hand; }
#topBanner {
       margin-top:10px;
       margin-bottom:10px;
       max-width: 500px;
       height: auto;
       display: block; margin: 0 auto;
}
   
.login {
  width:900px;
  height:auto;
  padding: 20px, 20px;
  background-color:#FFFFFF;
  text-align:center;
  border:5px solid gray;
  border-radius: 30px;
}   
.userid, .username, .userpassword {
  width: 80%;
  border: none;
  border-bottom: 2px solid #adadad;
  outline:none;
  color: #636e72;
  font-size:16px;
  height:25px;
  background: none;
  margin-top: 20px;
}
.bottomText {
  text-align: center;
  font-size: 20px;
}
.login_btn  {
  position:relative;
  left:40%;
  transform: translateX(-50%);
  margin-bottom: 40px;
  width:80%;
  height:40px;
  background: linear-gradient(125deg,#81ecec,#6c5ce7,#81ecec);
  background-position: left;
  background-size: 200%;
  color:white;
  font-weight: bold;
  border:none;
  cursor:pointer;
  transition: 0.4s;
  display:inline;
}

</style>
</head>

<body>

<div>
	<img id="topBanner" src ="/resources/images/logo.jpg" title="서울기술교육센터" >
</div>

<div class="main" align="center">
	<div class="login">
		<h1>로그인</h1>
		<form name="loginForm" class="loginForm" method="post" onsubmit="loginCheck()"> 
			<input type="text" name="userid" id="userid" class="userid" placeholder="아이디를 입력하세요.">
         	<input type="password" id="userpassword" name="userpassword" class="userpassword" onkeydown="press(this.form)" placeholder="비밀번호를 입력하세요.">
         	<br><br>
         	<c:if test="${message == 'ID_NOT_FOUND'}">
          		<p style="color:red;" >아이디 입력이 잘못되었습니다.</p>
          	</c:if>
          	<c:if test="${message == 'PASSWORD_NOT_FOUND'}">
          		<p style="color:red;" >패스워드 입력이 잘못되었습니다.</p>
          	</c:if>
     		<input type="button" id="login_btn" class="login_btn" value="로그인" onclick="loginCheck()"> 
		</form>
        <div class="bottomText">사용자가 아니면 ▶<a id="hypertext" href="/member/signup" 
           onMouseover="this.style.background='pink'; this.style.textDecoration='underline';" 
           onmouseout="this.style.background='white'; this.style.textDecoration='none';">여기</a>를 눌러 등록을 해주세요.<br><br>
     	      [<a href="/member/searchID" onMouseover="this.style.background='pink'; this.style.textDecoration='underline';" 
     	       onmouseout="this.style.background='white'; this.style.textDecoration='none';">아이디</a> | 
     	       <a href="/member/searchPassword" onMouseover="this.style.background='pink'; this.style.textDecoration='underline';" 
     	       onmouseout="this.style.background='white'; this.style.textDecoration='none';">패스워드</a>  찾기]  <br><br>    
    	  </div>
	</div>

</div>

</body>
</html>