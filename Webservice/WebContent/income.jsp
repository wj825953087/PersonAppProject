<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
</head>
<body>
      <form  action="IncomeServlet?method=query_income&&user_id=1" method="post">

        <input   class="btn btn-large btn-primary" type="submit" value="无条件查询用户收入信息">
      </form>

      
            <form  action="IncomeServlet?method=query_income_time&&user_id=1" method="post">
                      开始时间
			<input type="text" name="start_time"></br>
			结束时间
			<input type="text" name="end_time"></br>
        	<input   class="btn btn-large btn-primary" type="submit" value="有条件查询用户收入信息">
      </form>
      
            </form>

      
            <form  action="IncomeServlet?method=insert_income&&user_id=1" method="post">
                     输入收入金额
			<input type="text" name="in_money"></br>
			输入收入类型
			<input type="text" name="in_type"></br>
			输入账户类型
			<input type="text" name="in_account_type"></br>
			输入记账时间
			<input type="text" name="in_time">
			
        	<input   class="btn btn-large btn-primary" type="submit" value="记录收入">
      </form>
                更新</br>
            <form  action="IncomeServlet?method=update_income&&user_id=1" method="post">
                    输入账单id
            
            <input type="text" name="in_id"></br>
                     输入收入金额
			<input type="text" name="in_money"></br>
			输入收入类型
			<input type="text" name="in_type"></br>
			输入账户类型
			<input type="text" name="in_account_type"></br>
			输入记账时间
			<input type="text" name="in_time"></br>
        	<input   class="btn btn-large btn-primary" type="submit" value="记录收入">
      </form>
      
      <form action="IncomeServlet?method=view_income" method="post">
      	根据收入表id查询<br>
      	收入表id
      	<input type="text" name="in_id">
      	<input type="submit" value="查询">
      </form>
      
        <form action="IncomeServlet?method=del_income&&user_id=1" method="post">
      	根据收入表id删除<br>
      	收入表id
      	<input type="text" name="in_id">
      	<input type="submit" value="删除">
      </form>
      <form action="IncomeServlet?method=query_income_type&&user_id=1" method="post">
      	无条件统计<br>
    
      	
      	<input type="submit" value="查询">
      </form>
      
     <form action="IncomeServlet?method=query_income_type_time&&user_id=1" method="post">
      	无条件统计<br>
   		 <input type="text" name="start_time"><br>
      	 <input type="text" name="end_time">
      	<input type="submit" value="查询">
      </form>
</body>
</html>