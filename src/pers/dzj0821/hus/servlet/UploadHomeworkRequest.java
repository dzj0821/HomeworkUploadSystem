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
import org.apache.commons.fileupload.FileUploadException;
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
		//TODO 验证截止时间
		HttpSession session = request.getSession();
		Integer account = (Integer) session.getAttribute("account");
		String name = (String) session.getAttribute("name");
		DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
        ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
        servletFileUpload.setHeaderEncoding("UTF-8"); 
        List<FileItem> formItems = null;
		try {
			formItems = servletFileUpload.parseRequest(request);
		} catch (FileUploadException e) {
			e.printStackTrace();
			return;
		}
		String id = null;
		for (FileItem fileItem : formItems) {
			if(fileItem.getFieldName().equals("id")) {
				id = fileItem.getString();
			}
		}
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
			suffixArray = suffix.split("\\|");
		}
		UploadDao uploadDao = new UploadDao();
		Upload upload = null;
		try {
			upload = uploadDao.getUpload(account, idInt);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return;
		}
		//如果已经存在上传记录
		if(upload != null) {
			response.sendRedirect("index.jsp");
			return;
		}
		String relativePath = "hidden\\upload\\" + idInt;
        String uploadPath = getServletContext().getRealPath("/");
        File uploadDir = new File(uploadPath + relativePath);
        //如果保存路径不存在则先创建
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        try {
            
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
                        String fileSuffix = fileName.substring(pos + 1);
                        if(!suffix.equals("*")) {
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
                        relativePath += File.separator + account + user.getUserName() + '.' + fileSuffix;
                        String filePath = uploadPath + relativePath;
                        File storeFile = new File(filePath);
                        item.write(storeFile);
                        uploadDao.insert(account, idInt, relativePath);
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
