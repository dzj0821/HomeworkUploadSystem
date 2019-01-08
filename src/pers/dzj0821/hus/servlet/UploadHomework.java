package pers.dzj0821.hus.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pers.dzj0821.hus.dao.HomeworkDao;
import pers.dzj0821.hus.dao.UserDao;
import pers.dzj0821.hus.vo.Homework;
import pers.dzj0821.hus.vo.User;

@WebServlet("/UploadHomework")
public class UploadHomework extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UploadHomework() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO 验证截止时间
		Integer account = (Integer) request.getSession().getAttribute("account");
		String id = request.getParameter("id");
		if (account == null || id == null) {
			response.sendRedirect("index.jsp");
			return;
		}
		Integer idInt = null;
		try {
			idInt = Integer.valueOf(id);
		} catch (NumberFormatException e) {
			response.sendRedirect("index.jsp");
			return;
		}
		// 获取用户所在班级
		User user = null;
		UserDao userDao = new UserDao();
		try {
			user = userDao.getUser(account);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return;
		}
		// 获取需要提交的作业信息
		HomeworkDao homeworkDao = new HomeworkDao();
		Homework homework = null;
		try {
			homework = homeworkDao.getHomework(idInt);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return;
		}
		// 如果作业不存在或用户不属于此作业所在的班级
		if (homework == null || homework.getClassId() != user.getClassId()) {
			response.sendRedirect("index.jsp");
			return;
		}
		// 获取此作业限定的后缀名
		String suffix = homework.getSuffix();
		String[] suffixArray = new String[0];
		// *代表允许全部后缀，多个后缀按照|分隔
		if (!suffix.equals("*")) {
			suffixArray = suffix.split("\\|");
		}
		request.setAttribute("suffixArray", suffixArray);
		request.setAttribute("id", id);
		request.getRequestDispatcher("upload_homework.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
