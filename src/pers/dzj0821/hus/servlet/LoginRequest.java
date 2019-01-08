package pers.dzj0821.hus.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pers.dzj0821.hus.dao.UserDao;

@WebServlet("/LoginRequest")
public class LoginRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginRequest() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("index.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		//如果已登录或帐号密码为空
		if(request.getSession().getAttribute("account") != null || account == null || password == null) {
			response.sendRedirect("index.jsp");
			return;
		}
		//如果不符合规则
		if(!Pattern.matches("^[0-9]{6,10}$", account) || !Pattern.matches("^[A-Za-z0-9]{6,18}$", password)) {
			response.sendRedirect("index.jsp");
			return;
		}
		int accountInt = Integer.parseInt(account);
		UserDao dao = new UserDao();
		boolean loginSuccess = false;
		try {
			loginSuccess = dao.login(accountInt, password);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return;
		}
		if(loginSuccess) {
			try {
				request.getSession().setAttribute("name", dao.getUser(accountInt).getUserName());
			} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				return;
			}
			request.getSession().setAttribute("account", accountInt);
			response.sendRedirect("List");
		} else {
			request.setAttribute("message", "用户名或密码错误！");
			request.setAttribute("url", "Login");
			request.getRequestDispatcher("message.jsp").forward(request, response);
		}
		
	}

}
