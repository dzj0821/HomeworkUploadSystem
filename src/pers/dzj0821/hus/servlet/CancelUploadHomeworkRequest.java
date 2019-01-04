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

/**
 * Servlet implementation class CancelUploadHomeworkRequest
 */
@WebServlet("/CancelUploadHomeworkRequest")
public class CancelUploadHomeworkRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CancelUploadHomeworkRequest() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//TODO 验证作业是否超时
		Integer account = (Integer)request.getSession().getAttribute("account");
		String id = request.getParameter("id");
		if(account == null || id == null) {
			response.sendRedirect("index.jsp");
			return;
		}
		int idInt = Integer.parseInt(id);
		//查询是否已经上传过
		UploadDao uploadDao = new UploadDao();
		Upload[] uploads = null;
		try {
			uploads = uploadDao.getUpload(account, idInt);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return;
		}
		//如果没有上传记录
		if(uploads.length == 0) {
			response.sendRedirect("index.jsp");
			return;
		}
		File file = new File(uploads[0].getPath());
		file.delete();
		try {
			uploadDao.delete(uploads[0].getId());
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return;
		}
		request.setAttribute("message", "文件删除成功!");
        request.setAttribute("url", "List");
        request.getRequestDispatcher("message.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
