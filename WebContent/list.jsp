<%@page import="pers.dzj0821.hus.vo.UserClassInfo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
UserClassInfo[] userClassInfos = (UserClassInfo[])request.getAttribute("userClassInfos");
%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <%@ include file = "hidden/header_head.jsp" %>
        <title>作业上传系统-作业列表</title>
        <style>
            table{
                text-align: center;
            }
            th{
                text-align: center;
            }
        </style>
        <script>
            function del(id) {
                if (confirm("你确定要删除这个提交吗？")) {
                    window.location.href = "CancelUploadHomeworkRequest?id=" + id;
                }
            }
        </script>
    </head>

    <body>
        <%@ include file = "hidden/header_body.jsp" %>
        <div class="main" style="text-align: center;">
            <h1 style="text-align:center;">作业列表</h1>

            <div class="table-responsive">
                <table class="table table-striped" style="margin:0 auto;">
                    <thead>
                        <tr>
                            <th>作业名称</th>
                            <th>发布者</th>
                            <th>状态</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                    <tbody>
                    <% for (UserClassInfo userClassInfo : userClassInfos) { %>
                        	<tr>
                                <td><%=userClassInfo.getHomeworkName() %></td>
                                <td><%=userClassInfo.getPublisherName() %></td>
                                <td><%=userClassInfo.getUploadId() != null ? "已提交" : "未提交" %></td>
                                <td><% if (userClassInfo.getUploadId() == null) { %>
                                    	<a href="UploadHomework?id=<%=userClassInfo.getHomeworkId() %>">提交作业</a>
                                    <% } else { %>
                                        <a href="javascript:void(0)" onclick="del(<%=userClassInfo.getHomeworkId() %>)">删除提交</a>
                                    <% } %>
                                </td>
                            </tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
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