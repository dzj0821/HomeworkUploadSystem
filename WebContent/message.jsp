<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
    alert(${message });
    window.location.href = <% 
    							String url = (String)request.getAttribute("url");
    							out.print(url == null ? "/index.jsp" : url);
    						%>;
</script>