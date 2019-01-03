<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String[] suffixArray = (String[])request.getAttribute("suffixArray");
%>
<html>
    <head>
        <%@ include file = "hidden/header_head.jsp" %>
        <title>作业上传系统-上传作业</title>
    </head>

    <body>
        <%@ include file = "hidden/header_body.jsp" %>
        <div class="main">
            <form method="POST" action="upload_homework_request.php" role="form" enctype="multipart/form-data">
                <div class="form-group">
                    <label for="name">选择需要提交的文件（大小应小于128M）</label>
                    <input type="text" name="id" value="<?php echo $id; ?>" hidden="hidden" />
                </div>
                <div class="form-group">
                    <label style="color: red;" for="name">注意：目前仅允许上传zip文件，以下限制暂时无效</label>
                    <label for="name">后缀名限制为
                        <%= suffixArray.length == 0 ? "任意" : ""%>
                        <%
                        for (String suffix : suffixArray) {
                            out.print(suffix + "&nbsp");
                        }
                        %></label>
                    <input type="file" name="file" id="file" /> 
                </div>

                <button type="submit" class="btn btn-default">提交</button>
                <label style="color: red;" for="name">注意：点击后请注意浏览器左下角的上传进度，刷新或关闭页面会导致上传失败</label>
            </form>
        </div>
    </body>
</html>
