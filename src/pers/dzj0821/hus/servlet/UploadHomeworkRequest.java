package pers.dzj0821.hus.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import pers.dzj0821.hus.dao.HomeworkDao;
import pers.dzj0821.hus.dao.UploadDao;
import pers.dzj0821.hus.dao.UserDao;
import pers.dzj0821.hus.vo.Homework;
import pers.dzj0821.hus.vo.Upload;
import pers.dzj0821.hus.vo.User;

/**
 * Servlet implementation class UploadHomeworkRequest
 */
@WebServlet("/UploadHomeworkRequest")
public class UploadHomeworkRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadHomeworkRequest() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("index.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Integer account = (Integer) session.getAttribute("account");
		String name = (String) session.getAttribute("name");
		String id = request.getParameter("id");
		// 判断参数是否正确/表单中是否含有文件
		if (account == null || name == null || id == null || !ServletFileUpload.isMultipartContent(request)) {
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
			suffixArray = suffix.split("|");
		}
		UploadDao uploadDao = new UploadDao();
		Upload[] uploads = null;
		try {
			uploads = uploadDao.getUpload(account, idInt);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return;
		}
		//如果已经存在上传记录
		if(uploads.length != 0) {
			response.sendRedirect("index.jsp");
			return;
		}
		DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("UTF-8"); 
        String uploadPath = getServletContext().getRealPath("/") + "/upload/" + idInt;
        File uploadDir = new File(uploadPath);
        //如果保存路径不存在则先创建
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        try {
            List<FileItem> formItems = upload.parseRequest(request);
            if (formItems != null && formItems.size() > 0) {
            	//检查每个参数是否为文件
                for (FileItem item : formItems) {
                    if (!item.isFormField()) {
                    	//验证大小
                    	if(item.getSize() / 1024 / 1024 > 128) {
                    		request.setAttribute("message", "上传的文件大小超过限制！");
                    		request.getRequestDispatcher("message.jsp").forward(request, response);
                    		return;
                    	}
                        String fileName = new File(item.getName()).getName();
                        //验证后缀
                        int pos = fileName.lastIndexOf('.');
                        if(pos == -1) {
                        	request.setAttribute("message", "不允许上传无后缀名的文件！");
                    		request.getRequestDispatcher("message.jsp").forward(request, response);
                    		return;
                        }
                        String fileSuffix = fileName.substring(pos);
                        if(suffix != "*") {
                        	boolean allowSuffix = false;
                        	for (String singleSuffix : suffixArray) {
								if(singleSuffix.equals(fileSuffix)) {
									allowSuffix = true;
									break;
								}
							}
                        	if(!allowSuffix) {
                        		request.setAttribute("message", "文件后缀名不在允许上传的后缀列表中！");
                        		request.getRequestDispatcher("message.jsp").forward(request, response);
                        		return;
                        	}
                        }
                        String filePath = uploadPath + File.separator + account + user.getUserName() + '.' + fileSuffix;
                        File storeFile = new File(filePath);
                        item.write(storeFile);
                        uploadDao.insert(account, idInt, filePath);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        request.setAttribute("message", "文件上传成功!");
        request.setAttribute("url", "List");
        request.getRequestDispatcher("message.jsp").forward(request, response);
	}

}
