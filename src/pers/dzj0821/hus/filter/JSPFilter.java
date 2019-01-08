package pers.dzj0821.hus.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("*.jsp")
public class JSPFilter implements Filter {

    public JSPFilter() {
    }

	public void destroy() {
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//如果请求的是主页，放行
		if(request instanceof HttpServletRequest && ((HttpServletRequest)request).getServletPath().equals("/index.jsp")) {
			chain.doFilter(request, response);
			return;
		}
		if(response instanceof HttpServletResponse) {
			HttpServletResponse httpServletResponse = (HttpServletResponse)response;
			
			httpServletResponse.sendError(404);
		}
	}


	public void init(FilterConfig fConfig) throws ServletException {}

}
