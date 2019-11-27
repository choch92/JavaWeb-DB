package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.FileService;

@WebServlet("/file/*")
public class FileController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public FileController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// 전체 요청 경로에서 마지막 /를 제외한 부분을 가져오기
		String requestURI = request.getRequestURI();
		int idx = requestURI.lastIndexOf("/");
		String command = requestURI.substring(idx + 1);
		
		// 포워딩에 사용할 변수
		RequestDispatcher dispatcher;
		
		
		// 요청에 따른 분기문 생성
		switch(command) {
		case "upload":
			// 페이지 이동할 때는 GET, FORM에서 이동할 때는 POST
			if("GET".equals(request.getMethod())) {
				dispatcher = request.getRequestDispatcher("../views/upload.jsp");
				dispatcher.forward(request, response);
			}else {
				FileService service = new FileService();
				service.fileUpload(request);
			}
			break;
			
		case "list":
			if("GET".equals(request.getMethod())) {
				dispatcher = request.getRequestDispatcher("../views/list.jsp");
				dispatcher.forward(request, response);
			}else {
				
			}
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
	}

}
