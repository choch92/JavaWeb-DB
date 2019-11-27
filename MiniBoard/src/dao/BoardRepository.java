package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.sql.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import vo.Board;

// Board 테이블 작업을 위한 클래스
public class BoardRepository {
	// 클래스가 처음 호출될 때 수행하는 코드
	static {
		try {
			// 드라이버 클래스를 로드
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	// 싱글톤 패턴을 처리하기 위한 코드
	private BoardRepository () {
		try {
			// 데이터 베이스 연결
			// con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.240:1521:xe", "user10", "user10");
			
			// ConnectionPool을 이용해서 Connection 만들기
			Context init = new InitialContext();
			DataSource ds = (DataSource)init.lookup("java:comp/env/DBConn");
			con = ds.getConnection();
			
			// 트랜잭션 관련 메소드를 직접 호출하기 위해서 autoCommit 해제
			// 데이터베이스 프로그래밍을 하다가 작업한 곳에서는 데이터 작업이 반영되는데
			// 원본 데이터베이스에는 반영이 안되어 있으면 commit 여부를 확인해야 합니다.
			con.setAutoCommit(false);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private static BoardRepository boardRepository;
	public static BoardRepository sharedInstance() {
		if(boardRepository == null) {
			boardRepository = new BoardRepository();
		}
		return boardRepository;
	}
	
	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	// 게시물을 데이터베이스에 저장하는 메소드
	// DTO 클래스를 매개변수로 받아서 영향받은 행의 개수를 리턴
	public int boardInsert(Board board) {
		int result = -1;
		try {
			// SQL 실행객체
			pstmt = con.prepareStatement("insert into board(num, title, content, name, writedate) "
					+ "values(boardseq.nextval, ?, ?, ?, ?)");
			// ?가 있으면 데이터 바인딩
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getContent());
			pstmt.setString(3, board.getName());
			// 현재 시간을 만들어서 대입
			// 날짜를 Date를 저장하지 않고 문자열로 저장하기도 합니다.
			Calendar cal = new GregorianCalendar();
			pstmt.setDate(4, new Date(cal.getTimeInMillis()));
			// SQL 실행
			result = pstmt.executeUpdate();
			
			// 사용한 객체 닫기
			pstmt.close();
			// 성공적으로 작업이 수행되면 commit
			con.commit();
		}catch(Exception e) {
			try {
				// 작업도중 예외가 발생하면 rollback 호출
				con.rollback();
			}catch(Exception e1) {
				System.out.println(e1.getMessage());
			}
			System.out.println(e.getMessage());
			e.printStackTrace();
		}	
		return result;
	}
	
	// 게시물 전체를 가져오는 메소드
	public List<Board> boardList(){
		List<Board> list = new ArrayList<Board>();
		try {
			// SQL 실행 객체 생성
			// 전체 데이터를 가져오는데 작성일의 역순으로 가져오기
			pstmt = con.prepareStatement("select * from board order by num desc");
			// 실행
			rs = pstmt.executeQuery();
			// 데이터 순회하면서 읽기
			while(rs.next()) {
				Board board = new Board();
				board.setNum(rs.getInt("num"));
				board.setTitle(rs.getString("title"));
				board.setName(rs.getString("name"));
				board.setCnt(rs.getInt("cnt"));
				board.setWritedate(rs.getDate("writedate"));
				
				list.add(board);
			}
			rs.close();
			pstmt.close();
		}catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return list;
	}	
	// 글번호에 해당하는 게시물의 조회수를 증가시켜주는 메소드
	public int cntupdate(int num) {
		int result = -1;
		try {
			pstmt = con.prepareStatement("update board set cnt = cnt + 1 where num = ?");
			pstmt.setInt(1, num);
			result = pstmt.executeUpdate();
			pstmt.close();
			
			con.commit();
		}catch(Exception e) {
			try {
				con.rollback();
			}catch(Exception e1) {
				System.out.println(e1.getMessage());
			}
			System.out.println(e.getMessage());
		}
		return result;
	}
	// 글번호에 해당하는 게시물을 찾아서 리턴하는 메소드
	public Board boardDetail(int num) {
		Board board = null;
		try {
			pstmt = con.prepareStatement("select * from board where num = ?");
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				board = new Board();
				board.setNum(rs.getInt("num"));
				board.setTitle(rs.getString("title"));
				board.setContent(rs.getString("content"));
				board.setName(rs.getString("name"));
				board.setCnt(rs.getInt("cnt"));
				board.setWritedate(rs.getDate("writedate"));
			}
			rs.close();
			pstmt.close();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return board;
	}
	// 데이터를 삭제하는 메소드
	// 삽입, 삭제, 갱신은 영향받은 행의 개수를 리턴(행의 개수를 리턴하여 public int <- 이것임)
	public int boardDelete(int num) {
		int result = -1;
		try {
			pstmt = con.prepareStatement("delete from board where num = ?");
			pstmt.setInt(1, num);
			
			result = pstmt.executeUpdate();
			
			pstmt.close();
			con.commit();
		}catch(Exception e) {
			try {
				con.rollback();
			}catch(Exception e1) {
				System.out.println(e1.getMessage());
			}
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	// 데이터 수정하는 메소드
	public int boardUpdate(Board board) {
		int result = -1;
		try {
			// 데이터 수정하는 구문
			pstmt = con.prepareStatement("update board set title=?, content=? where num = ?");
			
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getContent());
			pstmt.setInt(3, board.getNum());
			
			result = pstmt.executeUpdate();

			pstmt.close();
			con.commit();
		}catch(Exception e) {
			try {
				con.rollback();
			}catch(Exception e1) {
				System.out.println(e1.getMessage());
			}
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	// id와 password를 받아서 mem 테이블에 저장하는 메소드
	public void memInsert(String id, String pw) {
		try {
			pstmt = con.prepareStatement("insert into mem(id, pw) values(?,?)");
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			pstmt.executeUpdate();
			// 작업에 성공하면 데이터베이스 반영
			con.commit();
			pstmt.close();
		}catch(Exception e) {
			
			System.out.println(e.getMessage());
		}
	}
	// id를 가지고 비밀번호를 찾아오는 메소드
	public String getPw(String id) {
		String pw = null;
		try {
			pstmt = con.prepareStatement("select pw from mem where id=?");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				pw = rs.getString("pw");
			}
			rs.close();
			pstmt.close();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return pw;
	}
}
