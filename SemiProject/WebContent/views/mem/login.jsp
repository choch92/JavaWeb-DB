<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<style>
	#msg{color:red}
</style>
</head>
<body>
	<h3 align='center'>로그인</h3>
	<div align='center' id='msg'>${msg}</div>
	<form method="post" id="loginform">
		<table align="center">
			<tr>
				<td align='left'>e-mail &nbsp;&nbsp;</td>
				<td><input type="email" size="40" 
				name="email" id="email" required="required"/></td>
			</tr>
			
			<tr>
				<td align='left'>비밀번호 &nbsp;&nbsp;</td>
				<td><input type="password" size="40" 
				name="password" id="password" required="required"/></td>
			</tr>
			
			<tr>
				<td colspan='2' align='center'>
				<input type="submit" value="로그인" />&nbsp;&nbsp;
				<input type="button" value="회원가입" id="joinbtn"/>&nbsp;&nbsp;
				<input type="button" value="메인화면" id="mainbtn"/>
			</tr>
		</table>
	</form>
</body>
<script>
	document.getElementById("joinbtn").addEventListener("click", function(e){
		location.href = "join";
	});
	document.getElementById("mainbtn").addEventListener("click", function(e){
		location.href = "../";
	});
</script>
</html>