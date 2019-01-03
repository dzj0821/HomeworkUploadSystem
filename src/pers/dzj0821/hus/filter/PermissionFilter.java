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
@WebFilter("/PermissionFilter")
public class PermissionFilter implements Filter {

    /**
     * Default constructor. 
     */
    public PermissionFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if(request instanceof HttpServletRequest) {
			HttpServletRequest httpServletRequest = (HttpServletRequest)request;
			HttpSession session = httpServletRequest.getSession();
			String account = (String) session.getAttribute("account");
			if(account != null && session.getAttribute("permission") == null) {
				int accountInt = Integer.parseInt(account);
				PermissionDao dao = new PermissionDao();
				pers.dzj0821.hus.vo.Class[] classes = null;
				try {
					classes = dao.getManageClass(accountInt);
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
					return;
				}
				if(classes.length == 0) {
					session.setAttribute("permission", "general");
				} else {
					session.setAttribute("permission", "administrator");
				}
				request.setAttribute("manageClass", classes);
			}
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
