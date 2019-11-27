package service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import vo.Board;

// Board 관련 요청을 처리할 메소드를 소유한 인터페이스
public interface BoardService {
	
	// 게시글 저장을 위한 메소드
	public boolean boardInsert(HttpServletRequest request);
	
	// 게시글 목록을 가져오기 위한 메소드
	public List<Board> boardList(HttpServletRequest request);
	
	// 게시글 한개를 가져오기 위한 메소드
	public Board boardDetail(HttpServletRequest request);
	
	// 게시글 삭제를 위한 메소드
	public boolean boardDelete(HttpServletRequest request);
	
	// 게시글 수정을 위해서 데이터를 가져오는 메소드(조회수를 업데이트 하지 않음)
	public Board boardGet(HttpServletRequest request);
	
	// 게시글 수정을 위한 메소드
	public boolean boardUpdate(HttpServletRequest request);
	
	// 회원가입 처리를 위한 메소드
	public void memInsert(HttpServletRequest request);
	
	// 로그인 처리를 위한 메소드
	public boolean login(HttpServletRequest request);
}
