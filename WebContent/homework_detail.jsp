<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String homeworkName = (String)request.getAttribute("homeworkName");
String text = (String)request.getAttribute("text");
Integer id = (Integer)request.getAttribute("id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="hidden/header_head.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>作业上传系统-作业详情</title>
</head>
<body>
	<%@ include file="hidden/header_body.jsp"%>
	<div class="main" style="text-align: center;">
		<h1><%=homeworkName %></h1>
		<hr>
		<p><%=text == null ? "无正文内容" : text %><p>
		<a href="UploadHomework?id=<%=id %>">提交作业</a>
	</div>
</body>
</html>