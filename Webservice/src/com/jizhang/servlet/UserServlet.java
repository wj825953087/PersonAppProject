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
   	 * ����:�û���¼
   	 * @param request
   	 * @param response
   	 * @return
   	 * @throws Exception
   	 */
    public void isLogin(HttpServletRequest request,HttpServletResponse response)throws Exception{
 	    response.setContentType("text/html;charset=utf-8");
 	    Map<String, Object> map=new HashMap<String, Object>();
 		PrintWriter out = response.getWriter();
 		/*�ͻ��˴������û���������*/
    	String username=request.getParameter("username");
    	String password=request.getParameter("password");
    	 
    	//���ݲ����
    	UserDao dao=new UserDaoImpl(config);
    	//�����û�����ѯ���û���Ϣ
    	User user=dao.getUserByName(username);

    	/*��¼*/
    	if(user!=null&&username.equals(user.getUsername()) && password.equals(user.getPassword())) {
    		//����JSON�ַ���
    		
    		map.put("user", user);
    		map.put("is_login", "1");
    		String mapJson=JSON.toJSONString(map);
    		out.print(mapJson);
    		out.flush();
    		out.close();
    	
    	}else {
    		//��¼ʧ��
    		map.put("is_login", "0");
    		String mapJson=JSON.toJSONString(map);
    		out.print(mapJson);
    		out.flush();
    		out.close();

    	}
    }
    /**
     * ����:�û�ע�Ṧ��
     * @param request
     * @param response
     * @throws Exception
     */
    public void Regist(HttpServletRequest request,HttpServletResponse response)throws Exception{
    	response.setContentType("text/html;charset=utf-8");
    	Map<String, Object> map=new HashMap<String, Object>();
    	PrintWriter out=response.getWriter();
    	//���ܿͻ��˴�����ע����Ϣ
    	String username=request.getParameter("username");
    	String password=request.getParameter("password");
    	String phone=request.getParameter("phone");
    	
    	User user=new User();
    	user.setUsername(username);
    	user.setPassword(password);
    	user.setPhone(phone);
    	
    	//���ݲ����
    	UserDao dao=new UserDaoImpl(config); 
    	
    	int row=dao.insertUser(user);
    	map.put("is_regist", row);
    	//����JSON�ַ���
    	String jsonStr=JSON.toJSONString(map);
    	out.print(jsonStr);
    	out.flush();
    	out.close();
    	
    	
    }
    /**
     * ����:��ѯ�����û����Ƿ��ظ�
     * @param request
     * @param response
     * @throws Exception
     */
    public void repeatUsername(HttpServletRequest request,HttpServletResponse response)throws Exception{
    	response.setContentType("text/html;charset=utf-8");
    	Map<String, Object> map=new HashMap<String, Object>();
    	PrintWriter out=response.getWriter();
    	
    	int is_repeat=0;//0�����ظ� , 1�����ظ�
		//�����û���
    	String username=request.getParameter("username");
    	try {
        	//���ݲ����
        	UserDao dao=new UserDaoImpl(config);
        	is_repeat=dao.query_repeat(username);
        	
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}
    
    	map.put("is_repeat", is_repeat);
    	//����json�ַ���
    	String str=JSON.toJSONString(map);
    	out.print(str);
    	out.flush();
    	out.close();
    	
    }
    /**
     * ����:�����û��˻���Ϣ
     * @param request
     * @param response
     * @throws Exception
     */
   public void add_account_information(HttpServletRequest request,HttpServletResponse response)throws Exception{
	   response.setContentType("text/html;charset=utf-8");
	   Map<String, Object> map=new HashMap<String, Object>();
	   PrintWriter out=response.getWriter();
	
	   //��ȡ�û���
	   String username=request.getParameter("username");
	   
	   /*��ȡ�û��˻���Ϣ*/
	   //��ȡ�˻���
	   String account_name=request.getParameter("account_name");
	   //��ȡ�˻����
	   double money=Double.parseDouble(request.getParameter("account_money"));
	   //��ȡ�˻�����
	   String account_card_number=request.getParameter("account_card_number");

	   try {
		//���ݲ����
		   UserDao  userDao=new UserDaoImpl(config);
		   UserAccountDao accountDao=new UserAccountDaoImpl(config);

		   int user_id=userDao.getUserIdByUsername(username);
		   //����id
		   int id=accountDao.count_userAccount(user_id)+1;
		   UserAccount  account=new UserAccount();
		   	   account.setId(id);
			   account.setUser_id(user_id);
			   account.setAccount_name(account_name);
			   account.setAccount_money(money);
			   account.setAccount_card_number(account_card_number);
		   int insert_flag=accountDao.insert_userAccount(account);
		   map.put("insert_success",insert_flag);
		   //����json�ַ���
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
    * ����:ɾ���û��˻���Ϣ
    * @param request
    * @param response
    * @throws Exception
    */
   public void del_account_information(HttpServletRequest request,HttpServletResponse response)throws Exception{
	   response.setContentType("text/html;charset=utf-8");
	   Map<String, Object> map=new HashMap<String, Object>();
	   PrintWriter out=response.getWriter();
	   int del_success=0;
	   //��ȡ�˻�id
	   int id=Integer.parseInt(request.getParameter("id"));
	   //��ȡ�û�id
	   int user_id=Integer.parseInt("user_id");
	   
	   try {
		   //���ݲ����
		   UserAccountDao accountDao=new UserAccountDaoImpl(config);
		   del_success=accountDao.del_userAccount(id,user_id);
	} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
	}
		   
	   map.put("del_success", del_success);
	   //����json�ַ���
	   String json=JSON.toJSONString(map);
	   out.print(json);
	   out.flush();
	   out.close();
  }
}
