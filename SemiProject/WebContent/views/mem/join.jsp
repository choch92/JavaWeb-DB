<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 반복문을 사용하기 위해서 JSTL 태그 라이브러리 추가 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 가입 페이지</title>
</head>
<body>
	<form method="post" enctype="multipart/form-data" id="joinform">
		<fieldset>
			<legend>회원가입</legend>
			<table align ="center" width="90%" height="400" border="1">
				<!-- image 선택 영역 -->
				<tr>
					<td rowspan="8" align="center"><br/>
					<img src="" id="img" width="350" height="350" border="1"/><br/>
					<input type="file" id="image" name="image" accept="image/*"/><br/>
					</td>
				</tr>
				
				<tr>
					<td align="left">이메일</td>
					<td>&nbsp;<input type="text" name="email" id="email" required="required" 
					size="30" maxlength="40"/>@
					<select name="corp" id="corp" required="required">
						<option value="direct">직접입력</option>
						<option value="gmail.com">gmail.com</option>
						<option value="naver.com">naver.com</option>
						<option value="daum.net">daum.net</option>
					</select>
					<div id="emaildisp"></div>
					</td>
				</tr>
				
				<tr>
					<td align="left">비밀번호</td>
					<td>&nbsp;<input type="password" name="password" id="password" required="required" 
					size="30" maxlength="40"/>
					<span id="pwspan"></span><br/>
					<div id="passworddisp"></div>
					</td>
				</tr>
				
				<tr>
					<td align="left">이름</td>
					<td>&nbsp;<input type="text" name="name" id="name" required="required" 
					size="30" maxlength="40"/>
					<div id="namedisp"></div>
					</td>
				</tr>
				
				<tr>
					<td align="left">성별</td>
					<td>&nbsp;<input type="radio" name="gender" id="gender" 
					checked="checked" value ="man"/>남자
					&nbsp;<input type="radio" name="gender" id="gender" 
					value ="woman"/>여자
					<div id="genderdisp"></div>
					</td>
				</tr>
				
				<tr>
					<td align="left">전화번호</td>
					<td>&nbsp;<input type="text" name="phone" id="phone" required="required" 
					size="30" maxlength="40"/>
					<div id="phonedisp"></div>
					</td>
				</tr>
								
				<tr>
					<td align="left">별명</td>
					<td>&nbsp;<input type="text" name="nickname" id="nickname" required="required" 
					size="30" maxlength="40" placeholder="별명은 필수 입력입니다."/>
					<div id="nicknamedisp"></div>
					</td>
				</tr>
				
				<tr>
					<td align ="left">생년월일</td>
					<td>&nbsp;
						<select name="year">
							<c:forEach var="year" begin="1920" end="2019">
								<option value="${year}">${year}</option>
							</c:forEach>
						</select>년
						<select name="month">
							<c:forEach var="month" begin="1" end="12">
								<option value="${month}">${month}</option>
							</c:forEach>
						</select>월
						<select name="day">
							<c:forEach var="day" begin="1" end="31">
								<option value="${day}">${day}</option>
							</c:forEach>
						</select>일
				</tr>
				
				<tr>
					<td colspan = "3" align="center">
						<input type="submit" value="회원가입"/>
						&nbsp;&nbsp;
						<input type="reset" value="작성취소" />
						&nbsp;&nbsp;
						<input type="button" value="메인으로" id="mainbtn"/>
				</tr>
			</table>
		</fieldset>
	</form>
</body>
<script>
	// 메인으로 버튼을 눌렀을 때 메인페이지로 이동하기
	document.getElementById("mainbtn").addEventListener("click", function(e){
		location.href="../";
	});
	// 파일의 선택의 변경된 경우 선택한 파일을 img 태그에 출력하기
	document.getElementById("image").addEventListener('change', function(e){
		// 선택한 파일이 있는지 확인
		if(this.files && this.files[0]){
			filename = this.files[0].name;
			// 파일 읽기 객체 생성
			var reader = new FileReader();
			// 파일 읽기
			reader.readAsDataURL(this.files[0]);
			// 파일을 전부 읽으면 img 태그에 출력
			reader.addEventListener("load", function(e){
				var event = e || window.event;
				document.getElementById("img").src = event.target.result;
			});
		}
	});
	
	// email 입력 영역과 이메일 관련 메시지 출력영역을 찾아오기
	var email = document.getElementById("email");
	var emaildisp = document.getElementById("emaildisp");
	var corp = document.getElementById("corp");
	
	// 이메일 체크 여부를 저장하기 위한 변수
	var emailcheck = false;
	
	function f(e){

		var val = email.value.trim();
		if(val.trim().length <= 0){
			emaildisp.innerHTML = "이메일을 입력하세요";
			emaildisp.style.color = "RED";
			email.focus;
			event.preventDefault();
		}
		//val = val + "@" + corp.value;
		else if(val.length > 1){
			val = val + "@" + corp.value;
			// ajax 객체 생성
			var request = new XMLHttpRequest();
			// ajax 요청 경로 생성
			request.open("GET", "emailcheck?email=" + val);
			// 요청 전송
			request.send("");
			// 응답이 왔을 때 호출되는 콜백(Callback - 이벤트가 발생하면 호출되는) 메소드 등록
			request.onreadystatechange = function(e){
				if(request.readyState == 4){
					// 서버가 정상적으로 처리하고 정상 응답을 하면
					if(request.status >= 200 && request.status < 300){
						// 전송되어 온 데이터 확인
						var result = request.responseText;
						if(result.trim() == "true"){
							emaildisp.innerHTML = "사용 가능한 이메일";
							emaildisp.style.color = "BLUE";
							emailcheck = true;
						}else{
							emaildisp.innerHTML = "사용 불가능한 이메일";
							emaildisp.style.color = "RED";
							emailcheck = false;
						}
					}else{
						alert(request.status);
					}
				}
			};
		}	
	}
	corp.addEventListener("change", function(e){
		f();		
	});
	
	email.addEventListener("focusout", function(e){
		f();			
	});
	
	var nickname = document.getElementById("nickname");
	var nicknamedisp = document.getElementById("nicknamedisp");
	var nicknamecheck = false;
	nickname.addEventListener("focusout", function(e){
		var val = nickname.value;
		// 닉네임을 입력하지 않았으면 닉네임 중복체크를 하지 않는 것
		if(val.trim().length < 1){
			nicknamecheck = false;
			return;
		}
		// 자바스크립트에서의 인코딩
		// URL이나 파라미터에는 영문과 숫자를 제외하고는 인코딩이 되서 대입되어야 합니다.
		// val = escape(val);
		var request = new XMLHttpRequest();
		request.open("GET", "nicknamecheck?nickname=" + val);
		request.send("");
		request.onreadystatechange = function(e){
			// readyState는 서버로 오는 응답의 상태 : 4번이 응답이 완료된 경우
			// 0은 객체만 생성한 상태, 1이면 요청을 한 상태
			// 2이면 send()를 호출, 3이면 서버에서 응답이 오기 시작한 상태
			if(request.readyState == 4){
				// 100번대 처리 중, 200번대 정상응답, 300번대 리다이렉트 중, 400번대 클라이언트 오류, 500번대 서버 오류
				if(request.status >= 200 && request.status < 300){
					var result = request.responseText;
					// JSON Parsing
					var obj = JSON.parse(result);
					// 동일한 닉네임이 없는 경우
					if(obj.result == "true"){
						nicknamedisp.innerHTML = "사용 가능한 닉네임";
						nicknamedisp.style.color = "blue";
						nicknamecheck = true;
					// 동일한 닉네임이 있는 경우
					}else{
						nicknamedisp.innerHTML = "사용 불가능한 닉네임";
						nicknamedisp.style.color = "red";
						nicknamecheck = false;
					}
				}
			}
		}
	});
	
	document.getElementById("joinform").addEventListener("submit", function(e){
		// 이벤트 객체 생성
		var event = e || window.event;
		if(emailcheck == false){
			email.focus();
			emaildisp.innerHTML = "이메일 유효성 검사를 하세요";
			emaildisp.style.color = "red";
			// 기본 이벤트 제거 - 폼의 데이터를 전송하지 않음
			event.preventDefault();
			return;
		}
		if(nicknamecheck == false){
			nickname.focus();
			nicknamedisp.innerHTML = "닉네임 유효성 검사를 하세요";
			nicknamedisp.style.color = "red";
			// 기본 이벤트 제거 - 폼의 데이터를 전송하지 않음
			event.preventDefault();
			return;
		}
	});
</script>
</html>