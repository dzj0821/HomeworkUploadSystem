<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
    alert("${message }");
    window.location.href = '${url == null ? "index.jsp" : url }';
</script>