<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="UTF-8">
<head>
<script src="http://code.jquery.com/jquery-1.11.3.js"></script>

<title>게시물 목록</title>

<style>
body { font-family: "나눔고딕", "맑은고딕" }
h1 { font-family: "HY견고딕" }
a:link { color: black; }
a:visited { color: black; }
a:hover { color: red; }
a:active { color: red; }
#hypertext { text-decoration-line: none; cursor: hand; }

.tableDiv {
	text-align: center;
}

.InfoTable {
      border-collapse: collapse;
      border-top: 3px solid #168;
      width: 800px;  
      margin-left: auto; margin-right: auto;
    }  
    .InfoTable th {
      color: #168;
      background: #f0f6f9;
      text-align: center;
    }
    .InfoTable th, .InfoTable td {
      padding: 10px;
      border: 1px solid #ddd;
    }
    .InfoTable th:first-child, .InfoTable td:first-child {
      border-left: 0;
    }
    .InfoTable th:last-child, .InfoTable td:last-child {
      border-right: 0;
    }
    .InfoTable tr td:first-child{
      text-align: center;
    }
    .InfoTable caption{caption-side: top; }

.navi_top {	width: 1350px; text-align: right; }
.navi_bottom { text-align: center; }

</style>

</head>

<body>

<div class="tableDiv">

	<h1>게시물 목록 보기</h1>
	<table class="InfoTable">
  		<tr>
   			<th>번호</th>
   			<th>제목</th>
   			<th>작성자</th>
   			<th>작성일</th>
  		</tr>

 		<tbody>
			<c:forEach items="${list}" var="list"> <!-- for문 -->
 				<tr onMouseover="this.style.background='#46D2D2';" onmouseout="this.style.background='white';">
  					<td>${list.seqno}</td>
  					<td style="text-align:left;"><a id="hypertext" href="/board/view?seqno=${list.seqno}" onMouseover='this.style.textDecoration="underline"'  
  							onmouseout="this.style.textDecoration='none';">${list.title}</a></td>
  					<td>${list.writer}</td>
  					<td>${list.regdate}</td> 
 				</tr>
			</c:forEach>
		</tbody>

	</table>
	<p style="text-align: center">[ <a href="/board/write">글쓰기</a> ]</p>
</div>
</body>
</html>