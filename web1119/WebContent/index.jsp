<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>데이터베이스 실습</title>
</head>
<body>
	<%@ page import = "java.sql.*" %>
	<%
	 try{
		 // 드라이버 클래스 로드
		 Class.forName("oracle.jdbc.driver.OracleDriver");
		 // 이 메시지가 출력되지 않으면 드라이버 클래스 이름과 드라이버 파일을 확인
		 // out.println("오라클 드라이버 클래스 로드 성공");
		 Connection con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.240:1521:xe", "user10", "user10");
		 out.println("오라클 접속 성공");
		 
		 // SQL 실행 객체 생성
		 // PreparedStatement pstmt = con.prepareStatement("create table sample(num number(10), name varchar(30))");
		 
		 // 테이블에 데이터를 삽입
		 /*
		 PreparedStatement pstmt = con.prepareStatement("insert into sample(num, name) values(?,?)");
		 pstmt.setInt(1, 11);
		 pstmt.setString(2, "류씨가문");
		 */
		 
		 /*
		 // 수정이나 삭제는 0이 나오면 조건에 맞는 데이터가 없는 것임
		 PreparedStatement pstmt = con.prepareStatement("update sample set name = ? where num = ?");
		 pstmt.setString(1, "관리자");
		 pstmt.setInt(2, 10);
		 
		 // 실행
		 int result = pstmt.executeUpdate();
		 out.println("result : " + result);
		 */
		 
		 PreparedStatement pstmt = con.prepareStatement("select num, name from sample");
		 ResultSet rs = pstmt.executeQuery();
		 
		 // 테이블 생성
		 out.println("<table border='1'>");
		 // 행의 제목 생성
		 out.println("<tr><th>번호</th><th>이름</th></tr>");
		 int i = 0;
		 while(rs.next()){
			 // 번호가 짝수면 ORANGE, 홀수면 papayawhip
			 if(i%2 == 0){
				 out.println("<tr bgcolor='ORANGE'>");
			 }else{
				 out.println("<tr bgcolor='papayawhip'>");
			 }
			 out.println("<td>" + rs.getInt("num") + "</td>");
			 out.println("<td>" + rs.getString("name") + "</td>");
			 out.println("</tr>");
			 i = i + 1;
		 }
		 
		 out.println("</table>");
		 
		 rs.close();		 
		 pstmt.close();
		 con.close();
	 }catch(Exception e){
		 out.println(e.getMessage());
	 }
	%>
</body>
</html>
