package pers.dzj0821.hus.servlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pers.dzj0821.hus.dao.UploadDao;

@WebServlet("/DownloadRequest")
public class DownloadRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DownloadRequest() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 验证权限
		if (!"administrator".equals(request.getSession().getAttribute("permission"))) {
			response.sendRedirect("index.jsp");
			return;
		}
		String[] accounts = request.getParameterValues("accounts");
		String id = request.getParameter("id");
		Integer idInt = Integer.parseInt(id);
		response.reset();
		response.setContentType("application/x-msdownload");
		response.setHeader("Content-disposition", "attachment;filename=file.zip");
		ServletOutputStream servletOutputStream = response.getOutputStream();
		ZipOutputStream zipOutputStream = new ZipOutputStream(servletOutputStream);
		UploadDao uploadDao = new UploadDao();
		// 遍历学号
		for (String string : accounts) {
			int accountInt = Integer.parseInt(string);
			// 获取文件存放路径
			String rerlativePath = null;
			try {
				rerlativePath = uploadDao.getUpload(accountInt, idInt).getPath();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				return;
			}
			String absolutePath = getServletContext().getRealPath("/");
			File file = new File(absolutePath + rerlativePath);
			// 放入zip中
			ZipEntry zipEntry = new ZipEntry(file.getName());
			zipEntry.setSize(file.length());
			zipEntry.setTime(file.lastModified());
			zipOutputStream.putNextEntry(zipEntry);
			// 放入数据
			BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
			int readLength = -1;
			byte[] buf = new byte[2048];
			while ((readLength = bufferedInputStream.read(buf, 0, 2048)) != -1) {
				zipOutputStream.write(buf, 0, readLength);
			}
			bufferedInputStream.close();
		}
		zipOutputStream.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
