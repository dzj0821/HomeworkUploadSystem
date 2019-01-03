package pers.dzj0821.hus.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegisterRequest
 */
@WebServlet("/RegisterRequest")
public class RegisterRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterRequest() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

				if(isset($_SESSION['account']) || !isset($_POST['account']) || !isset($_POST['password']) || !isset($_POST['user_name']) || !isset($_POST['class_id'])
				                               || !preg_match($account_pattern, $_POST['account']) || !preg_match($password_pattern, $_POST['password']) || !preg_match($user_name_pattern_php, $_POST['user_name'])
				                               || !preg_match($class_id_pattern, $_POST['class_id'])){
				    header('Location: '.'/index.php');
				    exit();
				}
				$account = intval($_POST['account']);
				$password = $_POST['password'];
				$user_name = $_POST['user_name'];
				$class_id = intval($_POST['class_id']);

				$conn = new mysqli($database_host, $database_account, $database_password, $database_name);
				if ($conn->connect_error) {
				    die("连接失败: " . $conn->connect_error);
				}
				$password_md5 = md5(md5($password.$salt).$salt);
				//设置编码否则默认以GBK读出导致乱码
				$conn->query("SET NAMES UTF8");
				$stmt = $conn->prepare("SELECT user_name FROM user WHERE account = ?");
				$stmt->bind_param("i", $account);
				$stmt->execute();
				$stmt->store_result();
				if($stmt->num_rows() != 0){
				    ?>
				<script>
				    alert("此学号已被注册！");
				    window.location.href = '/register.php';
				</script>
				<?php
				}
				else{
				    $stmt->close();
				    $stmt = $conn->prepare("INSERT INTO user(account, password_md5, user_name, class_id) VALUES(?, ?, ?, ?)");
				    $stmt->bind_param("issi",$account, $password_md5, $user_name, $class_id);
				    $stmt->execute();
				    if($stmt->error){
				        $stmt->close();
				        $conn->close();
				        header('Location: '.'/index.php');
				        exit();
				    }
				    else{
				        header('Location: '.'/login.php');
				    }
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
