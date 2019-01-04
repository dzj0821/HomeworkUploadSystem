package pers.dzj0821.hus.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pers.dzj0821.hus.dao.HomeworkDao;
import pers.dzj0821.hus.dao.PermissionDao;
import pers.dzj0821.hus.util.Util;

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
		Integer account = (Integer)session.getAttribute("account");//获取session中的账号
		String permission = (String)session.getAttribute("permission");//获取session中的权限
		String[] classIds = request.getParameterValues("class_id[]");//获取班级id
		String homeworkName = request.getParameter("homework_name");//获取作业的名�
		String suffix = request.getParameter("suffix");//获取文件的后缀
		Integer account = (Integer)session.getAttribute("account");
		String permission = (String)session.getAttribute("permission");
		String[] classIds = request.getParameterValues("class_id[]");
		String homeworkName = request.getParameter("homework_name");
		String suffix = request.getParameter("suffix");
		String deadline = request.getParameter("deadline");
		if(account == null || !"administrator".equals(permission) || classIds == null || homeworkName == null || suffix == null) {
			response.sendRedirect("index.jsp");
			return;
		}//close if 判断信息是否为空
		int[] classIdsInt = new int[classIds.length];//创建int类型的classID数组
		for(int i = 0; i < classIds.length; i++) {
			classIdsInt[i] = Integer.parseInt(classIds[i]);
		}
		//作业正文内容和截止时间允许空
		String text = request.getParameter("text");
		Date deadlineDate = null;
		if(text.equals("")) {
			text = null;
		}
		try {
			deadline = deadline.replaceAll("T", " ") + ":00";
			deadlineDate = Util.getDateFormater().parse(deadline);
		} catch (Exception e) {
			return;
		}
		
		//标题和正文可能存在中文进行转�
		homeworkName = Util.parseUTF8(homeworkName);
		if(text != null) {
			text = Util.parseUTF8(text);
		}
		
		PermissionDao permissionDao = new PermissionDao();
		HomeworkDao homeworkDao = new HomeworkDao();
		for(int i = 0; i < classIdsInt.length; i++) {
			//对于每个班级判断权限
			boolean havePermission = false;
			try {
				havePermission = permissionDao.isManageThisClass(account, classIdsInt[i]);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				return;
			}
			if(!havePermission) {
				continue;
			}
			try {
				homeworkDao.insert(homeworkName, text, suffix, account, classIdsInt[i], deadlineDate);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				//TODO 回滚操作
				return;
			}
		}
		request.setAttribute("message", "发布成功�);
		request.setAttribute("url", "List");
		request.getRequestDispatcher("message.jsp").forward(request, response);
	}

}
