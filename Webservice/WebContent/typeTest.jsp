<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
</head>
<body>
收入类别查询测试<br>
 <form  action="MangerTypeServlet?method=ShowInAccountType"  method="post">

        <input    type="submit" value="查询">
      </form>
 支出类别查询测试<br>
  <form  action="MangerTypeServlet?method=ShowOutAccountType"  method="post">

        <input    type="submit" value="查询">
      </form>
      收入类别新增测试<br>
       <form  action="MangerTypeServlet?method=insert_income_type&&is_default=0&&super_id=0&&permissions=2"  
       				 method="post">
		类别id:
		<input type="text" name="type_id"><br>
		类别名:
		<input type="text" name="income_name"><br>
        <input  type="submit" value="添加">
      </form>
    
          支出类别新增测试<br>
       <form  action="MangerTypeServlet?method=insert_expend_type&&is_default=0&&super_id=0&&permissions=2"  
       				method="post">
		类别id:
		<input type="text" name="type_id"><br>
		类别名:
		<input type="text" name="expend_name"><br>
        <input  type="submit" value="添加">
      </form>
      <br><br><br>  
 系统默认收入类别更新测试<br>
   <form  action="MangerTypeServlet?method=update_income_type&&is_default=1"  method="post">
		类别id:
		<input type="text" name="type_id"><br>
		图片id:
		<input type="text" name="pngId"><br>
        <input  type="submit" value="更新">
      </form>
自定义收入类别更新测试<br>
   <form  action="MangerTypeServlet?method=update_income_type&&is_default=0"  method="post">
		类别id:
		<input type="text" name="type_id"><br>
		图片id:
		<input type="text" name="pngId"><br>
		类别名字:
		<input type="text" name="income_name"><br>
		
        <input  type="submit" value="更新">
      </form>
      
系统默认支出类别更新测试<br>
   <form  action="MangerTypeServlet?method=update_expend_type&&is_default=1"  method="post">
		类别id:
		<input type="text" name="type_id"><br>
		图片id:
		<input type="text" name="pngId"><br>
        <input  type="submit" value="更新">
      </form>
自定义支出类别更新测试<br>
   <form  action="MangerTypeServlet?method=update_expend_type&&is_default=0"  method="post">
		类别id:
		<input type="text" name="type_id"><br>
		图片id:
		<input type="text" name="pngId"><br>
		类别名字:
		<input type="text" name="expend_name"><br>
		
        <input  type="submit" value="更新">
      </form>
      
     --------------------------------------------------------------<br>

     自定义收入类别删除测试<br>
   <form  action="MangerTypeServlet?method=del_income_type&&permissions=2"  method="post">
		类别id:
		<input type="text" name="type_id"><br>

		
        <input  type="submit" value="删除">
      </form> 
      
       自定义支出类别删除测试<br>
   <form  action="MangerTypeServlet?method=del_expend_type&&permissions=2"  method="post">
		类别id:
		<input type="text" name="type_id"><br>

		
        <input  type="submit" value="删除">
      </form> 
</body>
</html>