<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	
%>
<html>
	<head>
	<%@ include file="hidden/header_head.jsp"%>
	<title>delete</title>
	
	<script type="text/javascript">
	function check(account, class_id){
		var del_account = /^[0-9]{6,10}$/;
		if (!del_account.test(account)) {
			response.sendRedirect("index.jsp");
			return false;
		}
	}
	</script>


	</head>
	<body>
		<%@ include file="hidden/header_body.jsp"%>
	</body>
</html>