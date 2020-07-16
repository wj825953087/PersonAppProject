package com.jizhang.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.jizhang.dao.UserAccountDao;
import com.jizhang.dao.UserDao;
import com.jizhang.dao.impl.UserAccountDaoImpl;
import com.jizhang.dao.impl.UserDaoImpl;
import com.jizhang.entity.user.User;
import com.jizhang.entity.user.UserAccount;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/UserServlet")
public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private static final String config="sqlconfig.xml";   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

  
   	/**
   	 * 作用:用户登录
   	 * @param request
   	 * @param response
   	 * @return
   	 * @throws Exception
   	 */
    public void isLogin(HttpServletRequest request,HttpServletResponse response)throws Exception{
 	    response.setContentType("text/html;charset=utf-8");
 	    Map<String, Object> map=new HashMap<String, Object>();
 		PrintWriter out = response.getWriter();
 		/*客户端传来的用户名和密码*/
    	String username=request.getParameter("username");
    	String password=request.getParameter("password");
    	 
    	//数据层操作
    	UserDao dao=new UserDaoImpl(config);
    	//根据用户名查询该用户信息
    	User user=dao.getUserByName(username);

    	/*登录*/
    	if(user!=null&&username.equals(user.getUsername()) && password.equals(user.getPassword())) {
    		//生成JSON字符串
    		
    		map.put("user", user);
    		map.put("is_login", "1");
    		String mapJson=JSON.toJSONString(map);
    		out.print(mapJson);
    		out.flush();
    		out.close();
    	
    	}else {
    		//登录失败
    		map.put("is_login", "0");
    		String mapJson=JSON.toJSONString(map);
    		out.print(mapJson);
    		out.flush();
    		out.close();

    	}
    }
    /**
     * 作用:用户注册功能
     * @param request
     * @param response
     * @throws Exception
     */
    public void Regist(HttpServletRequest request,HttpServletResponse response)throws Exception{
    	response.setContentType("text/html;charset=utf-8");
    	Map<String, Object> map=new HashMap<String, Object>();
    	PrintWriter out=response.getWriter();
    	//接受客户端传来的注册信息
    	String username=request.getParameter("username");
    	String password=request.getParameter("password");
    	String phone=request.getParameter("phone");
    	
    	User user=new User();
    	user.setUsername(username);
    	user.setPassword(password);
    	user.setPhone(phone);
    	
    	//数据层操作
    	UserDao dao=new UserDaoImpl(config); 
    	
    	int row=dao.insertUser(user);
    	map.put("is_regist", row);
    	//生成JSON字符串
    	String jsonStr=JSON.toJSONString(map);
    	out.print(jsonStr);
    	out.flush();
    	out.close();
    	
    	
    }
    /**
     * 作用:查询输入用户名是否重复
     * @param request
     * @param response
     * @throws Exception
     */
    public void repeatUsername(HttpServletRequest request,HttpServletResponse response)throws Exception{
    	response.setContentType("text/html;charset=utf-8");
    	Map<String, Object> map=new HashMap<String, Object>();
    	PrintWriter out=response.getWriter();
    	
    	int is_repeat=0;//0代表不重复 , 1代表重复
		//接收用户名
    	String username=request.getParameter("username");
    	try {
        	//数据层操作
        	UserDao dao=new UserDaoImpl(config);
        	is_repeat=dao.query_repeat(username);
        	
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}
    
    	map.put("is_repeat", is_repeat);
    	//生成json字符串
    	String str=JSON.toJSONString(map);
    	out.print(str);
    	out.flush();
    	out.close();
    	
    }
    /**
     * 作用:新增用户账户信息
     * @param request
     * @param response
     * @throws Exception
     */
   public void add_account_information(HttpServletRequest request,HttpServletResponse response)throws Exception{
	   response.setContentType("text/html;charset=utf-8");
	   Map<String, Object> map=new HashMap<String, Object>();
	   PrintWriter out=response.getWriter();
	
	   //获取用户名
	   String username=request.getParameter("username");
	   
	   /*获取用户账户信息*/
	   //获取账户名
	   String account_name=request.getParameter("account_name");
	   //获取账户余额
	   double money=Double.parseDouble(request.getParameter("account_money"));
	   //获取账户卡号
	   String account_card_number=request.getParameter("account_card_number");

	   try {
		//数据层操作
		   UserDao  userDao=new UserDaoImpl(config);
		   UserAccountDao accountDao=new UserAccountDaoImpl(config);

		   int user_id=userDao.getUserIdByUsername(username);
		   //生成id
		   int id=accountDao.count_userAccount(user_id)+1;
		   UserAccount  account=new UserAccount();
		   	   account.setId(id);
			   account.setUser_id(user_id);
			   account.setAccount_name(account_name);
			   account.setAccount_money(money);
			   account.setAccount_card_number(account_card_number);
		   int insert_flag=accountDao.insert_userAccount(account);
		   map.put("insert_success",insert_flag);
		   //生成json字符串
		   String json=JSON.toJSONString(map);
		   out.print(json);
		   out.flush();
		   out.close();
		   
	} catch (Exception e) {
		// TODO: handle exception
		System.out.println(e.toString());
	}
	   
    }
   /**
    * 作用:删除用户账户信息
    * @param request
    * @param response
    * @throws Exception
    */
   public void del_account_information(HttpServletRequest request,HttpServletResponse response)throws Exception{
	   response.setContentType("text/html;charset=utf-8");
	   Map<String, Object> map=new HashMap<String, Object>();
	   PrintWriter out=response.getWriter();
	   int del_success=0;
	   //获取账户id
	   int id=Integer.parseInt(request.getParameter("id"));
	   //获取用户id
	   int user_id=Integer.parseInt("user_id");
	   
	   try {
		   //数据层操作
		   UserAccountDao accountDao=new UserAccountDaoImpl(config);
		   del_success=accountDao.del_userAccount(id,user_id);
	} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
	}
		   
	   map.put("del_success", del_success);
	   //生成json字符串
	   String json=JSON.toJSONString(map);
	   out.print(json);
	   out.flush();
	   out.close();
  }
}
