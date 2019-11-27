<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>채팅페이지</title>
</head>
<body>
	<fieldset style="width:50; height:100; border:1; background:papayawhip">
	<legend align='center'>채팅</legend>
		보내는 메시지<input type="text" id="message" size="50"/>
		<input type="button" id="send" value="전송"/><br/><br/>
		받은 메시지<br/>
		<textarea id="disp" cols="60" rows="30"></textarea>
	</fieldset>
</body>
<script>
	// 웹 소켓 연결
	var webSocket = new WebSocket("ws://192.168.0.151:8080/SemiProject/websocket");
	// 웹 소켓이 연결되면 호출되는 콜백 함수 등록
	webSocket.addEventListener('open', function(e){
		document.getElementById('disp').value = "내가 왔따.";
	});
	
	// 웹 소캣 연결이 해제되면 호출되는 콜백 함수 등록
	webSocket.addEventListener('close', function(e){
		document.getElementById('disp').value = "웹 소캣에 연결이 해제되었습니다.\n"
		+ document.getElementById('disp').value;
	});
	
	var message = document.getElementById("message");
	// 전송 버튼을 눌렀을 때 입력한 메시지를 전송
	document.getElementById("send").addEventListener("click", function(e){
		// 입력한 내용을 웹 소켓 서버에게 전송하기
		webSocket.send('${member.nickname}' + ':' + message.value);
		// 기존 내용 삭제
		message.value = '';
	});
	
	message.addEventListener('keydown', function(e){
		// enter
		if(e.keyCode == 13){
			// 입력한 내용을 웹 소켓 서버에게 전송하기
			webSocket.send('${member.nickname}' + ':' + message.value);
			// 기존 내용 삭제
			message.value = '';
		}
	});
	
	// 메시지가 온 경우 호출되는 이벤트 처리
	webSocket.addEventListener('message', function(e){
		// 전송되어 온 메시지를 출력하고 이전 내용을 출력
		document.getElementById('disp').value = e.data + "\n"
		+ document.getElementById('disp').value;
	});
</script>
</html>