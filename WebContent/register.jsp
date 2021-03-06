<%@page import="pers.dzj0821.hus.vo.Class"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	Class[] classes = (Class[]) request.getAttribute("classes");
%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="hidden/header_head.jsp"%>
<title>作业上传系统-注册</title>
<link rel="stylesheet"
	href="http://fonts.googleapis.com/css?family=PT+Sans:400,700">
<link rel="stylesheet" href="assets/css/reset.css">
<link rel="stylesheet" href="assets/css/supersized.css">
<link rel="stylesheet" href="assets/css/style.css">

<!-- <link rel="stylesheet" href="css/register.css" />  -->
<script>
	function check(account, password, user_name, class_id) {
		var reg_account = /^[0-9]{6,10}$/;
		if (!reg_account.test(account)) {
			alert("学号格式有误！");
			return false;
		}
		var reg_password = /^[A-Za-z0-9]{6,18}$/;
		if (!reg_password.test(password)) {
			alert("密码必须为字母或数字且长度在6-18字符之间！");
			return false;
		}
		var password_once = document.getElementById("password_once").value;
		if (password !== password_once) {
			alert("两次密码输入不相同！");
			return false;
		}
		var reg_user_name = /^[\u4E00-\u9FFF]{2,4}/;
		if (!reg_user_name.test(user_name)) {
			alert("姓名格式有误！");
			return false;
		}
		return true;
	}
</script>
</head>

<body>
	<%@ include file="hidden/header_body.jsp"%>
	<div class="main" style="text-align: center;">
		<div id="register">
			<h1>Register</h1>
			<form method="POST" action="RegisterRequest" role="form">
				<input type="text" required="required" placeholder="学号"	name="account" /> 
				<input type="password" required="required" placeholder="密码" name="password" /> 
				<input type="password" required="required" placeholder="确认密码" id="password_once" /> 
				<input type="text" required="required" placeholder="姓名" name="user_name" />
				<div class="form-group">
				<br>
					<label for="name">选择你的班级：</label> 
					<br><br>
					<select name="class_id"	class="form-control" style="width: 270px; margin: 0 auto;">
						<%
							for (Class singleClass : classes) {
						%>
						<option value=<%=singleClass.getId()%>><%=singleClass.getClassName()%></option>
						<%
							}
						%>
					</select>
				</div>
				<button class="but" type="submit"
					onClick="return check(account.value, password.value, user_name.value, class_id.value);">注册</button>
			</form>
		</div>
	</div>
	<script src="assets/js/jquery-1.8.2.min.js"></script>
	<script src="assets/js/supersized.3.2.7.min.js"></script>
	<script src="assets/js/supersized-init.js"></script>
	<script src="assets/js/scripts.js"></script>

</body>

</html>