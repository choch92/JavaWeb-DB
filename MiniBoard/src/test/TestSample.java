package test;

import org.junit.Test;

import dao.BoardRepository;
import vo.Board;

public class TestSample {
	@Test
	public void testMethod() {
		/*
		BoardRepository dao = BoardRepository.sharedInstance();
		// 게시글 데이터 생성
		Board board = new Board();
		board.setTitle("게시글 제목");
		board.setContent("게시글 내용");
		board.setName("관리자");
		// 게시글을 저장하는 메소드를 호출하고 결과 확인
		int r = dao.boardInsert(board);
		System.out.println("결과 : " + r);
		*/
		
		/*
		// 전체 데이터를 가져오는 메소드 테스트
		BoardRepository dao = BoardRepository.sharedInstance();
		System.out.println(dao.boardList());
		*/
		
		BoardRepository dao = BoardRepository.sharedInstance();
		// System.out.println(dao.cntupdate(1));
		// System.out.println(dao.boardDetail(5));
		//System.out.println(dao.boardDelete(5));
		Board board = new Board();
		board.setNum(22);
		board.setTitle("제목 변경");
		board.setContent("내용 변경");
		System.out.println(dao.boardUpdate(board));
	}
}
