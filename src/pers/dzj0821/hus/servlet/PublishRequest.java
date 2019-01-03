package pers.dzj0821.hus.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pers.dzj0821.hus.dao.HomeworkDao;
import pers.dzj0821.hus.dao.PermissionDao;

/**
 * Servlet implementation class PublishRequest
 */
@WebServlet("/PublishRequest")
public class PublishRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PublishRequest() {
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
		HttpSession session = request.getSession();
		String account = (String)session.getAttribute("account");
		String permission = (String)session.getAttribute("permission");
		String[] classIds = request.getParameterValues("class_id");
		String homeworkName = request.getParameter("homework_name");
		String suffix = request.getParameter("suffix");
		if(account == null || "administrator".equals(permission) || classIds == null || homeworkName == null || suffix == null) {
			response.sendRedirect("index.jsp");
			return;
		}
		int accountInt = Integer.parseInt(account);
		int[] classIdsInt = new int[classIds.length];
		for(int i = 0; i < classIds.length; i++) {
			classIdsInt[i] = Integer.parseInt(classIds[i]);
		}
		//作业正文内容允许空
		String text = (String)request.getParameter("text");
		PermissionDao permissionDao = new PermissionDao();
		HomeworkDao homeworkDao = new HomeworkDao();
		for(int i = 0; i < classIdsInt.length; i++) {
			//对于每个班级判断权限
			boolean havePermission = false;
			try {
				havePermission = permissionDao.isManageThisClass(accountInt, classIdsInt[i]);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				return;
			}
			if(!havePermission) {
				continue;
			}
			try {
				homeworkDao.insert(homeworkName, text, suffix, accountInt, classIdsInt[i]);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				//TODO 回滚操作
				return;
			}
		}
		request.setAttribute("message", "发布成功！");
		request.setAttribute("url", "List");
		request.getRequestDispatcher("message.jsp").forward(request, response);
	}

}
