package pers.dzj0821.hus.filter;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import pers.dzj0821.hus.dao.PermissionDao;

/**
 * Servlet Filter implementation class PermissionFilter
 */
@WebFilter("/*")
public class PermissionFilter implements Filter {

	public PermissionFilter() {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (request instanceof HttpServletRequest) {
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			HttpSession session = httpServletRequest.getSession();
			Integer account = (Integer) session.getAttribute("account");
			if (account != null
					&& (session.getAttribute("permission") == null || session.getAttribute("manageClass") == null)) {
				PermissionDao dao = new PermissionDao();
				pers.dzj0821.hus.vo.Class[] classes = null;
				try {
					classes = dao.getManageClass(account);
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
					return;
				}
				if (classes.length == 0) {
					session.setAttribute("permission", "general");
				} else {
					session.setAttribute("permission", "administrator");
					session.setAttribute("manageClass", classes);
				}
			}
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
