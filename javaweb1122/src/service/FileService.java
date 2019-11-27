package service;

import javax.servlet.http.HttpServletRequest;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class FileService {
	public void fileUpload(HttpServletRequest request) {
		// 파일을 저장할 경로 만들기
		// 현재 프로젝트의 WebContent/images 디렉토리에 저장
		// 절대 경로 만들기
		// 서블릿이 2.5버전이면 request.getRealPath("/images");
		String uploadPath = request.getServletContext().getRealPath("/images");
		System.out.println(uploadPath);
		// 파일 업로드
		try {
			// 첫번째 매개변수는 HttpServletRequest 객체
			// 두번째 매개변수는 파일이 저장될 위치
			// 세번째 매개변수는 파일의 최대 크기
			// 네번째 매개변수는 인코딩 방식
			// 다섯번째 매개변수는 동일한 이름의 파일이 업로드 될 때 이름 지정 방식
			MultipartRequest multi = 
					new MultipartRequest(request, uploadPath, 1024*1024*10, "utf-8", new DefaultFileRenamePolicy());
		}catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
