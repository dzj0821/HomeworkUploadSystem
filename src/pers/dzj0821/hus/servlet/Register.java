package pers.dzj0821.hus.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("account") != null) {
		    request.setAttribute("message", "你已登录！");
		    return;
		}
		$conn = new mysqli($database_host, $database_account, $database_password, $database_name);
		if ($conn->connect_error) {
		    die("连接失败: " . $conn->connect_error);
		}
		//设置编码否则默认以GBK读出导致乱码
		$conn->query("SET NAMES UTF8");
		$stmt = $conn->prepare("SELECT id, class_name FROM class");
		$stmt->execute();
		$stmt->store_result();
		$stmt->bind_result($id, $class_name);
		for($i = 0; $stmt->fetch(); $i++){
		    $class_list[$i]['id'] = $id;
		    $class_list[$i]['name'] = $class_name;
		}
		$stmt->close();
		$conn->close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
