package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.BoardService;
import service.BoardServiceImpl;
import vo.Board;

@WebServlet("/board/*")
// 서블릿은 WAS가 객체를 1개만 생성해서 사용을 하기 때문에 싱글톤 패턴을 적용하지 않아도 됩니다.
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// 서비스 변수 선언
	private BoardService service;
	
    public BoardController() {
        super();
        service = BoardServiceImpl.sharedInstance();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// 세션 변수를 생성
		HttpSession session = request.getSession();
		// 공통된 URL을 제외한 부분
		// 전체 URL 가져오기
		String requestURI = request.getRequestURI();
		// 마지막 / 찾기
		int idx = requestURI.lastIndexOf("/");
		// 마지막 / 까지 자르기
		String command = requestURI.substring(idx + 1);
		
		switch(command) {
		case "insert":
			if("GET".equals(request.getMethod())) {
				// WebContent/views/insert.jsp 파일로 포워딩
				// ..을 앞에 붙인 이유는 여기서 요청이 /board/insert
				RequestDispatcher dispatcher = request.getRequestDispatcher("../views/insert.jsp");
				dispatcher.forward(request, response);
			}else {
				// 서비스의 메소드 호출
				boolean r = service.boardInsert(request);
				// 게시글 작성에 성공하면 list요청을 보냄
				// 실패하면 다시 insert요청
				if(r==true) {
					response.sendRedirect("list");
				}else {
					response.sendRedirect("insert");
				}
			}
			break;
			
		case "list":
			// 서비스의 메소드 호출
			List<Board> list = service.boardList(request);
			// 포워딩 할 때는 데이터를 request에 저장
			request.setAttribute("list", list);
			// 데이터를 읽어와서 출력할 때는 포워딩을 하는 것이 일반적입니다.
			RequestDispatcher dispatcher = request.getRequestDispatcher("../views/list.jsp");
			dispatcher.forward(request, response);
			break;
			
		case "detail":
			Board board = service.boardDetail(request);
			// 데이터를 읽었을 때는 데이터를 저장하고 포워딩
			request.setAttribute("board", board);
			// 데이터를 읽어와서 출력할 때는 포워딩을 하는 것이 일반적입니다.
			RequestDispatcher dispatcher1 = request.getRequestDispatcher("../views/detail.jsp");
			dispatcher1.forward(request, response);
			break;
			
		case "delete":
			boolean r = service.boardDelete(request);
			if(r == true) {
				response.sendRedirect("list");
			}else {
				response.sendRedirect("detail");
			}
			break;
		case "update":
			if("GET".equals(request.getMethod())) {
				Board board1 = service.boardGet(request);
				request.setAttribute("board", board1);
				RequestDispatcher dispatcher2 = request.getRequestDispatcher("../views/update.jsp");
				dispatcher2.forward(request, response);
			}else {
				boolean r1 = service.boardUpdate(request);
				if(r1 == true) {
					response.sendRedirect("list");
				}else {
					response.sendRedirect("detail");
				}
			}
			break;
		case "join":
			if("GET".equals(request.getMethod())) {
				RequestDispatcher dispatcher3 = request.getRequestDispatcher("../views/join.jsp");
				dispatcher3.forward(request, response);
			}else {
				service.memInsert(request);
				response.sendRedirect("../");
			}
			break;
		case "login":
			if("GET".equals(request.getMethod())) {
				RequestDispatcher dispatcher4 = request.getRequestDispatcher("../views/login.jsp");
				dispatcher4.forward(request, response);
			}else {
				boolean result = service.login(request);
				if(result == true) {
					response.sendRedirect("../");
				}else {
					response.sendRedirect("login");
				}
			}
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
	}
}
