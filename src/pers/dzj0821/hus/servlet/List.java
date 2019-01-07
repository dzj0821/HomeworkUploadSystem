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
import pers.dzj0821.hus.dao.UploadDao;
import pers.dzj0821.hus.dao.UserDao;
import pers.dzj0821.hus.util.Util;
import pers.dzj0821.hus.vo.Homework;
import pers.dzj0821.hus.vo.HomeworkStatus;
import pers.dzj0821.hus.vo.Upload;
import pers.dzj0821.hus.vo.User;
import pers.dzj0821.hus.vo.UserClassInfo;

/**
 * Servlet implementation class List
 */
@WebServlet("/List")
public class List extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public List() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Integer account = (Integer)session.getAttribute("account");
		//如果未登录返回主页面
		if(account == null) {
			response.sendRedirect("index.jsp");
			return;
		}
		Integer classId = (Integer)session.getAttribute("class_id");
		//如果session中未存储用户所属的class_id则获取
		if(classId == null) {
			UserDao dao = new UserDao();
			User user = null;
			try {
				user = dao.getUser(account);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				return;
			}
			if(user == null) {
				response.sendRedirect("index.jsp");
				return;
			}
			classId = user.getClassId();
		}
		//获取用户所在班级的作业列表
		HomeworkDao homeworkDao = new HomeworkDao();
		Homework[] homeworks = null;
		try {
			homeworks = homeworkDao.getHomeworks(classId);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return;
		}
		//构建用于JSP显示的班级列表数组
		UserClassInfo[] userClassInfos = new UserClassInfo[homeworks.length];
		UploadDao uploadDao = new UploadDao();
		UserDao userDao = new UserDao();
		for(int i = 0; i < homeworks.length; i++) {
			userClassInfos[i] = new UserClassInfo();
			int homeworkId = homeworks[i].getId();
			userClassInfos[i].setHomeworkId(homeworkId);
			userClassInfos[i].setHomeworkName(homeworks[i].getHomeworkName());
			//对每个作业查找用户是否已经提交过
			Upload upload = null;
			try {
				upload = uploadDao.getUpload(account, homeworkId);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				return;
			}
			userClassInfos[i].setUploadId(upload == null ? null : upload.getId());
			Date date = homeworks[i].getDeadline();
			if(date == null) {
				userClassInfos[i].setDeadline("无时间限制");
			} else {
				userClassInfos[i].setDeadline(Util.getDateFormater().format(date));
			}
			
			if(upload != null) {
				userClassInfos[i].setHomeworkStatus(HomeworkStatus.UPLOADED);
			} else {
				//如果未超时
				if(date == null || date.after(new Date())) {
					userClassInfos[i].setHomeworkStatus(HomeworkStatus.UNDO);
				} else {
					userClassInfos[i].setHomeworkStatus(HomeworkStatus.OVERTIME);
				}
			}
			//对每个作业查找发布者名字
			User user = null;
			try {
				user = userDao.getUser(homeworks[i].getPublisherAccount());
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				return;
			}
			userClassInfos[i].setPublisherName(user.getUserName());
			userClassInfos[i].setPublisher(user.getAccount() == account);
			
			//如果是管理员还需要传递完成作业的人数和总人数
			if("administrator".equals(request.getSession().getAttribute("permission"))) {
				try {
					userClassInfos[i].setTotalNum(userDao.getUsers(classId).length);
					userClassInfos[i].setUploadedNum(uploadDao.getUploads(homeworkId).length);
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
					return;
				}
			}
		}
		request.setAttribute("userClassInfos", userClassInfos);
		request.getRequestDispatcher("list.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
