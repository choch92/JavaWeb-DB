<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 수정</title>
</head>
<body>
	<h3>게시글 수정</h3>
	<form method="post" id="boardform">
		제목:<input type="text" value="${board.title}" name="title" id="title" size="30"/><br/><br/>
		이름:<input type="text" value="${board.name}" name="name" id="name" readonly="readonly" size="30"/><br/><br/>
		내용:<textarea name="content" id="content" rows="10" cols="30">${board.content}</textarea><br/>
	
		<!-- 글번호는 서버에게 넘기기는 하고 화면에는 출력하지 않도록 설정 -->
		<input type="hidden" name="num" id="num" value="${board.num}" /><br/><br/>
		
		<input type="submit" value="데이터 수정"/>
		<input type="button" value="게시물 목록" id="listbtn" />
		<input type="button" value="메인 화면" id="mainbtn"/>
	</form>
</body>
<script>
	document.getElementById("listbtn").addEventListener("click", function(e){
		location.href="list";
	});
	document.getElementById("mainbtn").addEventListener("click", function(e){
		location.href="../";
	});
</script>
</html>