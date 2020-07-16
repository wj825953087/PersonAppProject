<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="com.alibaba.fastjson.JSONObject" %>    
<!DOCTYPE html>
<%
	//解析
%>
<html>
<head>
<title>Sql注入演示</title>
<meta http-equiv="content-type" content="text/html;charset=utf-8">
</head>
<body>
	<form action="TextServlet?method=text" method="post">
	 	输入搜索
		<input type="text" name="str">
		<br>关键字
		<input type="text" name="keyword">
		<input type="submit" value="查询">
	</form>
	

</body>
</html>