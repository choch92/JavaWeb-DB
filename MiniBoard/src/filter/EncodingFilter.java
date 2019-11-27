package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter("/*")
public class EncodingFilter implements Filter {

    public EncodingFilter() {
    }

	public void destroy() {
	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		// Controller 가기 전에 처리 - 파라미터 인코딩 설정
		HttpServletRequest req = (HttpServletRequest)request;
		req.setCharacterEncoding("utf-8");
		
		chain.doFilter(request, response);
		// Controller에서 처리하고 난 후 처리
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}
}
