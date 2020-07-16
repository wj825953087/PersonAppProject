<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>测试</title>
</head>
<body>
	登录测试<br>
	<form action="UserServlet?method=isLogin" method="post">
		用户名:<input type="text" name="username"><br>
		密&nbsp;&nbsp;码:<input type="text" name="password"><br>
		<input type="submit" value="登录">
	</form>
	<br>用户账号注册测试<br>
	<form action="UserServlet?method=Regist" method="post">
		用户名
		<input type="text" name="username"><br>
		密码
		<input type="text" name="password"><br>
		手机号
		<input type="text" name="phone"><br>
		<input type="submit" value="注册">
	</form>
	
		<br>测试用户名是否重复<br>
	<form action="UserServlet?method=repeatUsername" method="post">
		用户名
		<input type="text" name="username"><br>
		<input type="submit" value="查询">
		<img alt="" src="http://111.229.180.123:8080/Webservice/Png/连接数据库.txt">
	</form>
	
	
	
			<br>测试<br>
	<form action="UserServlet?method=add_account_information&&account_money=0&&account_card_number=0000" method="post">
		用户名
		<input type="text" name="username"><br>
		账户名
		<input type="text" name="account_name">
		<input type="submit" value="查询">
		
	</form>
	<br>删除账户测试<br>
	<form action="UserServlet?method=del_account_information" method="post">
		用户id
		<input type="text" name="user_id"><br>
		账户id
		<input type="text" name="id">
		<input type="submit" value="删除">
		
	</form>
</body>
</html>