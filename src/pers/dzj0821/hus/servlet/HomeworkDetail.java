package pers.dzj0821.hus.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pers.dzj0821.hus.dao.HomeworkDao;
import pers.dzj0821.hus.vo.Homework;

/**
 * Servlet implementation class HomeworkDetail
 */
@WebServlet("/HomeworkDetail")
public class HomeworkDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeworkDetail() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//TODO 权限验证
		String id = request.getParameter("id");
		Integer idInt = Integer.parseInt(id);
		HomeworkDao homeworkDao = new HomeworkDao();
		Homework homework;
		try {
			homework = homeworkDao.getHomework(idInt);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return;
		}
		request.setAttribute("homeworkName", homework.getHomeworkName());
		request.setAttribute("text", homework.getText());
		request.setAttribute("id", idInt);
		request.getRequestDispatcher("homework_detail.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
