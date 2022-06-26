<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>게시물 수정</title>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script>
$(document).ready(function(){
	
	$("#btn_modify").click(function(){
		
		if($("#writer").val()=="") { alert("이름을 입력하세요!!!"); $("#writer").focus(); return false;  }
		if($("#title").val()=="") { alert("제목을 입력하세요!!!");  $("#title").focus(); return false;  }
		if($("#content").val()=="") { alert("내용을 입력하세요!!!");  $("#content").focus(); return false;  }
	
		$("#ModifyForm").attr("action", "/board/modify").submit();
	
	}) //End of $("btn_write")

}) //End of $(document).ready(function)

</script>

<style>
body { font-family: "나눔고딕", "맑은고딕" }
h1 { font-family: "HY견고딕" }
a:link { color: black; }
a:visited { color: black; }
a:hover { color: red; }
a:active { color: red; }
#hypertext { text-decoration-line: none; cursor: hand; }
#topBanner {
       margin-top:10px;
       margin-bottom:10px;
       max-width: 500px;
       height: auto;
       display: block; margin: 0 auto;
}

.ModifyForm {
  width:900px;
  height:auto;
  padding: 20px, 20px;
  background-color:#FFFFFF;
  text-align: center;
  border:4px solid gray;
  border-radius: 30px;
}

#writer, #title {
  width: 90%;
  border:none;
  border-bottom: 2px solid #adadad;
  margin: 20px;
  padding: 10px 10px;
  outline:none;
  color: #636e72;
  font-size:16px;
  height:25px;
  background: none;
}

#content{
  width: 850px;
  height: 300px;
  padding: 10px;
  box-sizing: border-box;
  border: solid #adadad;
  font-size: 16px;
  resize: both;
}

.btn_modify  {
  position:relative;
  left:20%;
  transform: translateX(-50%);
  margin-top: 20px;
  margin-bottom: 10px;
  width:40%;
  height:40px;
  background: red;
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

	<h1>게시물 수정</h1>
	<br>

<form id="ModifyForm" class="ModifyForm" method="POST">

	<input type="text" id="writer" name="writer" value="${list.writer}">
	<input type="text" id="title" name="title" value="${list.title}" >
	<input type="hidden" name="seqno" value="${list.seqno}">
	<input type="hidden" name="num" value="${num}">
	<input type="hidden" name="searchType" value="${searchType}">
	<input type="hidden" name="keyword" value="${keyword}">
	<textarea id="content" cols="100" row="500" name="content">${list.content}</textarea>
	<button id="btn_modify" class="btn_modify">수정</button>
</form>

</body>
</html>