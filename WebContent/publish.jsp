<%@page import="pers.dzj0821.hus.vo.Class"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
Class[] classes = (Class[])session.getAttribute("manageClass");
%>
<html>
    <head>
        <%@ include file = "hidden/header_head.jsp" %>
        <title>作业上传系统-发布作业</title>
    </head>

    <body>
        <%@ include file = "hidden/header_body.jsp" %>
        <div class="main">
            <form method="POST" action="PublishRequest" role="form">
                <div class="form-group">
                    <label for="name">选择发布的班级：</label>
                    <div>
                    <% for (Class singleClass : classes) { %>
                    	<label class="checkbox-inline">
                        	<input type="checkbox" name="class_id[]" value="<%=singleClass.getId() %>"><%=singleClass.getClassName() %>
                    	</label>
                    <% } %>
                    </div>
                </div>
                <div class="form-group">
                    <label for="name">作业名称</label>
                    <input type="text" class="form-control" name="homework_name" placeholder="请输入作业名称">
                </div>
                <div class="form-group">
                    <label for="name">作业内容（非必填）</label>
                    <textarea class="form-control" name="text"></textarea>
                </div>
                <div class="form-group">
                    <label style="color: red;" for="name">注意：目前仅允许上传zip文件，以下限制暂时无效</label>
                    <label for="name">限定作业后缀</label>
                    <input type="text" class="form-control" name="suffix" placeholder="如doc，不要输入点，如允许多个后缀用|隔开，如允许所有后缀输入*">
                </div>
                <button type="submit" class="btn btn-default">提交</button>
            </form>
        </div>
    </body>
</html>