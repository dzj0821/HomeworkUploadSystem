package pers.dzj0821.hus.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pers.dzj0821.hus.dao.UploadDao;
import pers.dzj0821.hus.vo.Upload;

@WebServlet("/CancelUploadHomeworkRequest")
public class CancelUploadHomeworkRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CancelUploadHomeworkRequest() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO 验证作业是否超时
		Integer account = (Integer) request.getSession().getAttribute("account");
		String id = request.getParameter("id");
		if (account == null || id == null) {
			response.sendRedirect("index.jsp");
			return;
		}
		int idInt = Integer.parseInt(id);
		// 查询是否已经上传过
		UploadDao uploadDao = new UploadDao();
		Upload upload = null;
		try {
			upload = uploadDao.getUpload(account, idInt);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return;
		}
		// 如果没有上传记录
		if (upload == null) {
			response.sendRedirect("index.jsp");
			return;
		}
		String uploadPath = getServletContext().getRealPath("/");
		File file = new File(uploadPath + upload.getPath());
		file.delete();
		try {
			uploadDao.delete(upload.getId());
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return;
		}
		request.setAttribute("message", "文件删除成功!");
		request.setAttribute("url", "List");
		request.getRequestDispatcher("message.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
