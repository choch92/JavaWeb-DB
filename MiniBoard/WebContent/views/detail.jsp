<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상세보기</title>
</head>
<body>
	<p>제목:${board.title}</p>
	<p>작성자:${board.name}</p>
	<p>조회수:${board.cnt}</p>
	<p>내용:${board.content}</p>
	<a href="update?num=${board.num}">데이터 수정</a>
	<input type="button" id="delbtn" value="삭제"/>
	
</body>
<script>
	document.getElementById("delbtn").addEventListener("click", function(e){
		var r = confirm("정말로 삭제할꺼냐?");
		if(r == true){
			location.href="delete?num=${board.num}";
		}
	});
</script>
</html>