<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String account = (String)session.getAttribute("account");
%>
<nav class="navbar navbar-inverse" role="navigation">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="index.jsp">作业上传系统</a>
		</div>
		<div>
			<ul class="nav navbar-nav">
				<% if (account != null) { %>
					<li><a href="List">作业列表</a></li>
					<% if (account.equals("administrator")) { %>
					<li><a href="Publish">发布作业</a></li>
                	<% }
				} %>
			</ul>
			<ul class="nav navbar-nav navbar-right">
			<% if (account != null) { %>
				<li><a href="javascript:void(0)">欢迎，${account }</a>
				</li>
				<li><a href="Exit">退出登录</a></li>
			<% } else { %>
				<li><a href="Login">登录</a></li>
				<li><a href="Register">注册</a></li>
			<% } %>
			</ul>
		</div>
	</div>
</nav>