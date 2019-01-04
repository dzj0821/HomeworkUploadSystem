<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" class="no-js">

<head>
<%@ include file="hidden/header_head.jsp"%>
<title>作业上传系统-登录</title>
<script>
	function check(account, password) {
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
		return true;
	}
</script>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<link rel="stylesheet"
	href="http://fonts.googleapis.com/css?family=PT+Sans:400,700">
<link rel="stylesheet" href="assets/css/reset.css">
<link rel="stylesheet" href="assets/css/supersized.css">
<link rel="stylesheet" href="assets/css/style.css">

</head>

<body>
	<%@ include file="hidden/header_body.jsp"%>
	<div class="page-container">
		<div id="login">
			<h1>Login</h1>
			<form method="POST" action="LoginRequest">
				<input type="text" required="required" placeholder="学号"
					name="account"></input> 
				<input type="password" required="required"
					placeholder="密码" name="password"></input>
				<button class="but" type="submit"
					onClick="return check(account.value,password.value);">登录</button>
			</form>
		</div>
	</div>
	<script src="assets/js/jquery-1.8.2.min.js"></script>
	<script src="assets/js/supersized.3.2.7.min.js"></script>
	<script src="assets/js/supersized-init.js"></script>
	<script src="assets/js/scripts.js"></script>

</body>

</html>