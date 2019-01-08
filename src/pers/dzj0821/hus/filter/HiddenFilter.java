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

@WebFilter("/hidden/*")
public class HiddenFilter implements Filter {

	public HiddenFilter() {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (request instanceof HttpServletRequest
				&& "administrator".equals(((HttpServletRequest) request).getSession().getAttribute("permission"))) {
			chain.doFilter(request, response);
			return;
		}
		if (response instanceof HttpServletResponse) {
			((HttpServletResponse) response).sendError(403);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
