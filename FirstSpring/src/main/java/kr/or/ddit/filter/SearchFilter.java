package kr.or.ddit.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SearchFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("SearchFilter 초기화...!");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		System.out.println("SearchFilter 실행...!");
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		
		String type = request.getParameter("type");
		String searchType = request.getParameter("searchType");
		String searchWord = request.getParameter("searchWord");

		if(searchWord != null) {
			session.setAttribute("type", type);
			session.setAttribute("searchType", searchType);
			session.setAttribute("searchWord", searchWord);
		}
		
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		System.out.println("SearchFilter 해제...!");
	}
}
