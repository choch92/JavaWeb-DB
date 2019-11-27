<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 목록 보기</title>
<style>
	#boardnum{
		text-decoration:none;
	}
</style>
</head>
<body>	
	<h3 align="center">게시판</h3>
	<table align="center" border="1" width="600">
		<tr bgcolor="ORANGE">
			<th width="10%">글번호</th>
			<th width="40%">제목</th>
			<th width="15%">작성자</th>
			<th width="10%">조회수</th>
			<th width="20%">작성일</th>
		</tr>
	<!-- list 순회 -->
	<c:forEach var="board" items="${list}">
		<tr bgcolor="papayawhip">
			<td align="right">${board.num}&nbsp;</td>
			<td><a href='detail?num=${board.num}' id="boardnum">&nbsp;&nbsp;${board.title}</a></td>
			<td>&nbsp;&nbsp;${board.name}</td>
			<td align="right">${board.cnt}&nbsp;&nbsp;</td>
			<td align="center">${board.dispdate}</td>
		</tr>
	</c:forEach>
	</table>
</body>
</html>