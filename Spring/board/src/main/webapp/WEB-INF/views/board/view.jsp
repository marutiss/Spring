<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<title>게시물 내용 보기</title>
</head>

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

.boardView {
  width:900px;
  height:auto;
  padding: 20px, 20px;
  background-color:#FFFFFF;
  border:4px solid gray;
  border-radius: 20px;
}

.mwriter, .mtitle, .mregDate, .mcontent, .filename {
  width: 90%;
  height:25px;
  outline:none;
  color: #636e72;
  font-size:16px;
  background: none;
  border-bottom: 2px solid #adadad;
  margin: 30px;
  padding: 10px 10px;
}

.textArea {
  width: 90%;
  height: 350px;
  overflow: auto;
  margin: 22px;
  padding: 10px;
  box-sizing: border-box;
  border: solid #adadad;
  text-align: left;
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

.btn_delete  {
  position:relative;
  left:20%;
  transform: translateX(-50%);
  margin-top: 20px;
  margin-bottom: 10px;
  width:40%;
  height:40px;
  background: pink;
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

<script>

$(document).ready(function(){
	
	$("#btn_modify").click(function(){
			location.href="/miniBoard/mModify?seqno=${list.seqno}"
	}) //End of $("btn_modify")

	$("#btn_delete").click(function(){
		if(confirm("정말로 삭제하시겠습니까?") == true)
			location.href="/miniBoard/mDelete?seqno=${list.seqno}"
    }) //End of $("btn_delete")
	
    $("#btn_mreply").click(function(){
    	replyRegister();	
    }) //End of $("#btn_mreply")
	
	
}) //End of $(document).ready(function)

function replyRegister() { //form 데이터 전송 --> 반드시 serialize()를 해야 한다.
	
	if($("#replyContent").val() == "") {alert("댓글을 입력하세요."); $("#replyContent").focus();return false;}
	
	var queryString = $("form[name=formReply]").serialize();
	$.ajax({
		url : "/miniBoard/mReply?option=I",
		type : "post",
		dataType : "json",
		data : queryString,
		success : replyList,
		error : function(data) {
						alert("서버오류 문제로 댓글 등록이 실패 했습니다. 잠시 후 다시 시도해주시기 바랍니다.");
              	    	return false;
				}
	}); //End of ajax
	$("#replyContent").val("");	
}

function replyList(list){
	
	var result = "";
	$.each(list, function(index,item){		
		
		result += "<div id='replyListView'>";
			result += "작성자 : " + item["replywriter"];
			result += "<div style='width:40%; height: auto; border-top: 1px solid gray; overflow: auto;'>";
			result += "<pre>" + item["replyContent"] + "</pre></div>";
			result += "</div>";
			result += "</div><br>";
	})
	$("#replyListView").remove();
	$("#replyList").html(result);
}

function startupPage(){
	
	var queryString = { "seqno": ${list.seqno} };
	$.ajax({
		url : "/miniBoard/mReply?option=L",
		type : "post",
		dataType : "json",
		data : queryString,
		success : replyList,
		error : function(data) {
						alert("서버 오류로 댓글 불러 오기가 실패했습니다.");
              	    	return false;
				}
	}); //End od ajax
}

function fileDownload(){
	
	location.href="/miniBoard/fileDownload?seqno=${list.seqno}"
}

</script>
<body onload="startupPage()">

<div>
	<img id="topBanner" src ="/resources/images/logo.jpg" title="서울기술교육센터" >
</div>

	<h1>게시물 내용 보기</h1>
	<br>
	<div class="boardView">
		<div class="mwriter">이름 : ${list.mwriter}</div>
		<div class="mtitle">제목 : ${list.mtitle}</div>
		<div class="mregDate">날짜 : ${list.mregDate}</div>
		<div class="textArea"><pre>${list.mcontent}</pre></div>
		<div class="filename">파일명 : <a href="javascript:fileDownload()">${list.filename}</a></div>
		<center><div><button class="btn_modify" id="btn_modify">수정</button> 
		<button class="btn_delete" id="btn_delete">삭제</button></div></center>
	</div>

<div class="right">

	<p id="replyNotice">댓글을 작성해 주세요</p>
	<form id="formReply" name="formReply" method="POST"> 
		작성자 : <input type="text" id="replywriter" name="replywriter"><br>
    	<textarea id='replyContent' name='replyContent' cols='80' rows='5' maxlength='150' placeholder='글자수:150자 이내'></textarea><br>
    	<input type="hidden" name="seqno" value="${list.seqno}">
	</form>
	<button id="btn_mreply">댓글등록</button>
	<hr>
	
	<div id="replyList" style="width:100%; height:600px; overflow:auto;">
		<div id="replyListView"></div> 
	</div><!-- replyList End  -->		
</div>

</body>
</html>