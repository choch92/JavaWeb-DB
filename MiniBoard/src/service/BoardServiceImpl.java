package service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.mindrot.jbcrypt.BCrypt;

import dao.BoardRepository;
import vo.Board;

// 서비스의 메소드를 구현할 클래스
public class BoardServiceImpl implements BoardService {
	// DAO 클래스의 변수를 선언
	private BoardRepository dao;
	
	// 싱글톤 패턴을 처리하기 위한 코드
	private BoardServiceImpl () {
		// DAO 객체 생성
		dao = BoardRepository.sharedInstance();
	};
	private static BoardService boardService;
	public static BoardService sharedInstance() {
		if(boardService == null) {
			boardService = new BoardServiceImpl();
		}
		return boardService;
	}
	@Override
	public boolean boardInsert(HttpServletRequest request) {
		boolean result = false;

		// 파라미터 전부 읽기
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String name = request.getParameter("name");
		
		// 필요한 작업 수행 - 파일 업로드, 데이터 형변환, 데이터 계산 등
		if(content.trim().length() < 1) {
			content = "냉무";
		}
		// DAO의 매개변수 만들기
		Board board = new Board();
		board.setTitle(title);
		board.setContent(content);
		board.setName(name);
		// DAO의 메소드 호출
		int r = dao.boardInsert(board);
		// 결과를 가지고 필요한 작업 수행
		if(r>0) {
			result = true;
		}else {
			result = false;
		}
		return result;
	}
	@Override
	public List<Board> boardList(HttpServletRequest request) {
		// DAO 메소드에 파라미터가 없으면 DAO메소드를 호출해서 결과만 리턴
		List<Board> list = dao.boardList();
		// 현재 시간
		Date today = new Date();
		// 날짜 문자열 가져오기
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String todayString = sdf.format(today);
		for(Board board : list) {
			// 작성일을 yyyy-MM-dd 형식의 문자열로 가져오기
			String writedate = sdf.format(board.getWritedate());
			// 오늘 작성한 글
			if(todayString.equals(writedate)) {
				// 시간과 분을 가져와서 dispdate에 설정
				SimpleDateFormat f = new SimpleDateFormat("hh:mm");
				board.setDispdate(f.format(board.getWritedate()));
			}else {
				// 오늘 작성하지 않은 글은 날짜 그대로 dispdate에 설정
				board.setDispdate(writedate);
			}
		}
		return list;
	}
	@Override
	public Board boardDetail(HttpServletRequest request) {
		Board board = null;

		// 파라미터 읽기
		String num = request.getParameter("num");
		// 파라미터를 DAO에서 사용할 수 있도록 변환하기
		
		// DAO 메소드 호출해서 결과 가져오기
		int r = dao.cntupdate(Integer.parseInt(num));
		board = dao.boardDetail(Integer.parseInt(num));
		
		return board;
	}
	@Override
	public boolean boardDelete(HttpServletRequest request) {
		boolean result = false;

		// 파라미터 읽기
		String num = request.getParameter("num");
		
		int r = dao.boardDelete(Integer.parseInt(num));
		if(r >= 0) {
			result = true;
		}
		
		return result;
	}
	@Override
	public Board boardGet(HttpServletRequest request) {
		Board  board = null;

		// 파라미터 읽기
		String num = request.getParameter("num");
		// 파라미터를 DAO 메소드에 맞게 변환해서 DAO 메소드 호출
		board = dao.boardDetail(Integer.parseInt(num));
		
		return board;
	}
	@Override
	public boolean boardUpdate(HttpServletRequest request) {
		boolean result = false;

		// 파라미터 읽기
		String num = request.getParameter("num");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		// 파라미터를 DAO 메소드에 맞게 변환해서 DAO 메소드 호출
		Board board = new Board();
		board.setNum(Integer.parseInt(num));
		board.setTitle(title);
		board.setContent(content);
		// DAO 메소드 호출
		int r = dao.boardUpdate(board);
		if(r > 0) {
			result = true;
		}
		return result;
	}
	@Override
	public void memInsert(HttpServletRequest request) {
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		// pw를 암호화
		pw = BCrypt.hashpw(pw, BCrypt.gensalt());
		// 데이터베이스에 저장
		dao.memInsert(id, pw);
	}
	@Override
	public boolean login(HttpServletRequest request) {
		boolean result = false;
		// 입력한 아이디와 비밀번호 읽어오기
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		// id를 가지고 비밀번호 찾기
		String password = dao.getPw(id);
		
		// 원문과 암호화된 문자열을 비교
		if(password != null && BCrypt.checkpw(pw, password) == true) {
			// 로그인 성공
			request.getSession().setAttribute("id", id);
			result = true;
		}
		
		return result;
	}
}
