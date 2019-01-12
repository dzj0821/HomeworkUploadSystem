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
import pers.dzj0821.hus.vo.User;

@WebServlet("/RegisterRequest")
public class RegisterRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RegisterRequest() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.sendRedirect("index.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sessionAccount = (String) request.getSession().getAttribute("account");
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		String userName = request.getParameter("user_name");
		String classId = request.getParameter("class_id");

		if (!verify(sessionAccount, account, password, userName, classId)) {
			response.sendRedirect("index.jsp");
			return;
		}

		userName = new String(userName.getBytes("ISO-8859-1"), "UTF-8");

		int accountInt = Integer.parseInt(account);
		UserDao dao = new UserDao();
		// 查找账号是否已经存在
		User user = null;
		try {
			user = dao.getUser(accountInt);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return;
		}
		if (user != null) {
			request.setAttribute("message", "此学号已被注册！");
			request.setAttribute("url", "Register");
			request.getRequestDispatcher("message.jsp").forward(request, response);
			return;
		}
		try {
			dao.regist(account, password, userName, classId);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return;
		}
		request.setAttribute("message", "注册成功！");
		request.setAttribute("url", "Login");
		request.getRequestDispatcher("message.jsp").forward(request, response);

	}
	
	private boolean verify(String sessionAccount, String account, String password, String userName, String classId) {
		if (sessionAccount != null || account == null || password == null
				|| userName == null || classId == null || !Pattern.matches("^[0-9]{6,10}$", account)
				|| !Pattern.matches("^[A-Za-z0-9]{6,18}$", password) || !Pattern.matches("^[0-9]+$", classId) 
				|| !Pattern.matches("^[\u4E00-\u9FA5]{2,4}$", userName)) {
			return false;
		}
		return true;
	}
	
}
