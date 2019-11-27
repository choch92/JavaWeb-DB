<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>파일 업로드</title>
</head>
<body>
	<form method="post" enctype="multipart/form-data">
		<input type="file" name="file" id="file" /><br/>
		<input type="submit" value="전송"/><br/>
	</form>
</body>
</html>