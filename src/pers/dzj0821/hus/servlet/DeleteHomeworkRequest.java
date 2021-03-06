package pers.dzj0821.hus.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pers.dzj0821.hus.dao.HomeworkDao;

@WebServlet("/DeleteHomeworkRequest")
public class DeleteHomeworkRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DeleteHomeworkRequest() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO 验证权限
		if(!"administrator".equals(request.getSession().getAttribute("permission"))) {
			return;
		}
		String id = request.getParameter("id");
		Integer idInt = Integer.parseInt(id);
		HomeworkDao homeworkDao = new HomeworkDao();
		try {
			homeworkDao.delete(idInt);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return;
		}
		request.setAttribute("message", "删除成功！");
		request.setAttribute("url", "List");
		request.getRequestDispatcher("message.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
