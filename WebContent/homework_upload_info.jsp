<%@page import="pers.dzj0821.hus.vo.UserHomeworkInfo"%>
<%@page import="pers.dzj0821.hus.vo.HomeworkStatus"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
UserHomeworkInfo[] userHomeworkInfos = (UserHomeworkInfo[])request.getAttribute("userHomeworkInfos");
Integer id = (Integer)request.getAttribute("id");
%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <%@ include file = "hidden/header_head.jsp" %>
        <title>作业上传系统-作业提交详情</title>
        <style>
            table{
                text-align: center;
            }
            th{
                text-align: center;
            }
        </style>
    </head>

    <body>
        <%@ include file = "hidden/header_body.jsp" %>
        <div class="main" style="text-align: center;">
            <h1 style="text-align:center;">作业提交详情</h1>
            <form action="DownloadRequest" method="GET">
            <input type="text" name="id" value="<%=id %>" hidden="true" />
            <div class="table-responsive">
                <table class="table table-striped" style="margin:0 auto;">
                    <thead>
                        <tr>
                        	<th>批量下载</th>
                            <th>用户姓名</th>
                            <th>是否已经提交</th>
                            <th>提交时间</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                    <tbody>
                    <% for (UserHomeworkInfo userHomeworkInfo : userHomeworkInfos) { %>
                        	<tr>
                        		<td><input type="checkbox" name="accounts" value="<%=userHomeworkInfo.getUserAccount() %>" <%=userHomeworkInfo.isUploaded() ? "" : "disabled" %>/></td>
                                <td><%=userHomeworkInfo.getUserName() %></td>
                                <td><%=userHomeworkInfo.isUploaded() ? "是" : "否" %></td>
                                <td><%=userHomeworkInfo.isUploaded() ? userHomeworkInfo.getUploadTime() : "-" %></td>
                                <td><%=userHomeworkInfo.isUploaded() ? "<a href='" + userHomeworkInfo.getUploadFileURL() +"'>点此下载</a>" : "" %></td>
                            </tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
            <div>
            <button type="submit" class="btn btn-primary" style="float:left;">批量下载</button>
            </div>
            </form>
            <% 
            //分页
            /*
              <div style="text-align: center;">
              <ul class="pagination">
              <li><a href='./classlist.php?page=1'>&lt;&lt;</a></li>
              <?php
              for ($i = $page - 2; $i <= $page + 2; $i++) {
              if ($i >= 1 && $i <= $max_page) {
              if ($i == $page) {
              echo "<li class='active'><a href='./classlist.php?page=$i'>$i</a></li>";
              } else {
              echo "<li><a href='./classlist.php?page=$i'>$i</a></li>";
              }
              }
              }
              ?>
              <li><a href='./classlist.php?page=<?php echo $max_page; ?>'>&gt;&gt;</a></li>
              </ul>
              </div>
             * 
             */
            %>
    </div>
</body>

</html>