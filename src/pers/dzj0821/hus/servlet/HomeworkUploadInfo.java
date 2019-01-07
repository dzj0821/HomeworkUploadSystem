package pers.dzj0821.hus.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pers.dzj0821.hus.dao.HomeworkDao;
import pers.dzj0821.hus.dao.UploadDao;
import pers.dzj0821.hus.dao.UserDao;
import pers.dzj0821.hus.util.Util;
import pers.dzj0821.hus.vo.Homework;
import pers.dzj0821.hus.vo.Upload;
import pers.dzj0821.hus.vo.User;
import pers.dzj0821.hus.vo.UserHomeworkInfo;

/**
 * Servlet implementation class HomeworkUploadInfo
 */
@WebServlet("/HomeworkUploadInfo")
public class HomeworkUploadInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeworkUploadInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//TODO 权限验证
		String id = request.getParameter("id");
		Integer idInt = Integer.parseInt(id);
		//获取作业的班级
		HomeworkDao homeworkDao = new HomeworkDao();
		Homework homework = null;
		try {
			homework = homeworkDao.getHomework(idInt);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return;
		}
		int classId = homework.getClassId();
		UserDao userDao = new UserDao();
		User[] users = null;
		try {
			users = userDao.getUsers(classId);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return;
		}
		UploadDao uploadDao = new UploadDao();
		Upload[] uploads = null;
		try {
			uploads = uploadDao.getUploads(idInt);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return;
		}
		UserHomeworkInfo[] userHomeworkInfos = new UserHomeworkInfo[users.length];
		for(int i = 0; i < userHomeworkInfos.length; i++) {
			userHomeworkInfos[i] = new UserHomeworkInfo();
			int account = users[i].getAccount();
			userHomeworkInfos[i].setUserAccount(account);
			userHomeworkInfos[i].setUserName(users[i].getUserName());
			userHomeworkInfos[i].setUploaded(false);
			for(int j = 0; j < uploads.length; j++) {
				if(uploads[j].getUserAccount() == account) {
					userHomeworkInfos[i].setUploaded(true);
					userHomeworkInfos[i].setUploadTime(Util.getDateFormater().format(uploads[j].getUpdateTime()));
					userHomeworkInfos[i].setUploadFileURL(uploads[j].getPath());
					break;
				}
			}
		}
		request.setAttribute("userHomeworkInfos", userHomeworkInfos);
		request.setAttribute("id", idInt);
		request.getRequestDispatcher("homework_upload_info.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
