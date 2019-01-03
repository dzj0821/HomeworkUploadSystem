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

/**
 * Servlet implementation class RegisterRequest
 */
@WebServlet("/RegisterRequest")
public class RegisterRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterRequest() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.sendRedirect("index.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		String user_name = request.getParameter("user_name");
		String class_id = request.getParameter("class_id");
		
		if(request.getSession().getAttribute("account") != null || account == null || password == null || user_name == null || class_id == null
                || !Pattern.matches("^[0-9]{6,10}$", account) || !Pattern.matches("^[A-Za-z0-9]{6,18}$", password)
                || !Pattern.matches("^[\\x{4e00}-\\x{9fa5}]{2,4}", user_name) || !Pattern.matches("^[0-9]+$", class_id)){

			response.sendRedirect("index.jsp");
			return;
		}
		
		UserDao dao = new UserDao();
		boolean registFail = false;
		try {
			registFail = dao.login(Integer.parseInt(account), password);//查找账号是否已经存在
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return;
		}
		System.out.println(registFail);
		if(registFail) {
			request.setAttribute("message", "此学号已被注册！");
			request.getRequestDispatcher("register.jsp").forward(request, response);
		} else {
			try {
				dao.regist(account, password, user_name, class_id);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				return;
			}
			request.setAttribute("message", "注册成功！");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}

	}

}
