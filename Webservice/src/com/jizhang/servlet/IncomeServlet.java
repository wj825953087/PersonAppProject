package com.jizhang.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.jizhang.dao.IncomeDao;
import com.jizhang.dao.impl.IncomeDaoImpl;
import com.jizhang.entity.income.Income;


/**
 * Servlet implementation class IncomeServlet
 */
@WebServlet("/IncomeServlet")
public class IncomeServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private static final String config="sqlconfig.xml"; 
	private static final String head="text/html;charset=utf-8";
    public IncomeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     *	作用:查询用户收入信息
     *	条件:全部时间段
     * @param request
     * @param response
     * @throws Exception
     */
    public void query_income(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	
    	response.setContentType(head);
    	PrintWriter out=response.getWriter();
    	Map<String, Object> map=new HashMap<String, Object>();
    		
    	//获取用户id
    	int user_id=Integer.parseInt(request.getParameter("user_id"));
    	
    	//数据层操作
    	IncomeDao dao=new IncomeDaoImpl(config);
    	//无条件统计用户收入金额之和,用户收入信息
    	double income_money_sum=dao.income_money_count(user_id);
    	List<Income> list=dao.show_IncomeItem(user_id);
    	
    	//无条件查询
    	map.put("income_money_sum", income_money_sum);
    	map.put("income_list", list);
    	
    	//转换成JSON字符串
    	String mapjson=JSON.toJSONString(map);
    	
    	out.print(mapjson);
    	out.flush();
    	out.close();
    	
    	
    }

    /**
     * 	作用:查询用户收入信息
     * 	条件: 某段时间范围
     * @param request
     * @param response
     * @throws Exception
     */
    public void query_income_time(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	response.setContentType(head);
    	PrintWriter out=response.getWriter();
    	Map<String, Object> map=new HashMap<String, Object>();
    	
    	Map<String,Object> map_condition=new HashMap<String, Object>();
    	
    	//获取用户id
    	int user_id=Integer.parseInt(request.getParameter("user_id"));
    	SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");//时间格式化
    	//获取开始时间
    	Date start_time= sf.parse(request.getParameter("start_time"));
    	//获取结束时间
    	Date end_time=sf.parse(request.getParameter("end_time"));

    	map_condition.put("id", user_id);
    	map_condition.put("start_time", start_time);
    	map_condition.put("end_time", end_time);
    	
    
    	//数据层操作
    	IncomeDao dao=new IncomeDaoImpl(config);
    	//有条件统计用户收入金额
    	double income_money_sum=dao.income_money_count_time(map_condition);
    	List<Income> list=dao.show_IncomeItem_time(map_condition);
    	
    	//有条件查询
    	map.put("income_money_sum", income_money_sum);
    	map.put("income_list", list);
    	//转换成JSON字符串
    	String mapjson=JSON.toJSONString(map);
    	out.print(mapjson);
    	out.flush();
    	out.close();
    	
	}
    /**
     *   	
     * 	作用:记录一条收入账单
     * 	接收参数:用户id,收入金额,收入类型,账户类型,记账时间
     * @param request
     * @param response
     * @throws Exception
     * 
     */
    public void insert_income(HttpServletRequest request,HttpServletResponse response)throws Exception{
    	response.setContentType(head);
    	PrintWriter out=response.getWriter();
    	Map<String, Object> map=new HashMap<String, Object>();
    	/**
    	 *  PrintWriter 类 
    	 *	作用: 可以用来创建一个文件并向文本文件写入数据	  
    	 *	常用方法: 
    	 *	print(String str) :向文件写入一个字符串
    	 *	print(char[] ch) :向文件写入一个字符串数组
    	 *	
    	 *	write() :仅支持输出字符类型数据,字符,字符数组,字符串等
    	 *	print() :可以将各种类型(包括Object)的数据通过默认编码转换成bytes字节形式
    	 */
    	
    	
    	int insert_sucess=0;//是否记账成功

    	//获取用户id
    	int user_id=Integer.parseInt(request.getParameter("user_id"));
    	//获取输入收入金额
    	double in_money=Double.parseDouble(request.getParameter("in_money"));
    	//获取输入收入类型
    	int in_type=Integer.parseInt(request.getParameter("in_type"));
    	//获取输入账户类型
    	int in_account_type=Integer.parseInt(request.getParameter("in_account_type"));
    	//获取输入记账时间
    	
    	String str_time=request.getParameter("in_time");
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");//时间格式转换
    	Date in_time = simpleDateFormat.parse(str_time);
  

    	//将输入值放入实体对象中
    	Income income=new Income();
	    	income.setUser_id(user_id);
	    	income.setIn_money(in_money);
	    	income.setIn_type(in_type);
	    	income.setIn_account_type(in_account_type);
	    	income.setIn_time(in_time);
    	
    	//数据层操作
    	IncomeDao dao=new IncomeDaoImpl(config);
    	insert_sucess=dao.income_insert(income);
    	
    	map.put("insert_sucess",insert_sucess);
    	//转换成json字符串
    	String mapJson=JSON.toJSONString(map);
    	out.print(mapJson);
    	out.flush();
    	out.close();
    }
    /**
     * 	作用:更新一条账单
     * 	接收参数:账单id,用户id,收入金额,收入类型,账号类型,记账时间
     * @param request
     * @param response
     * @throws Exception
     */
    public void update_income(HttpServletRequest request,HttpServletResponse response)throws Exception {
    	response.setContentType(head);
    	PrintWriter out=response.getWriter();
    	Map<String, Object> map=new HashMap<String, Object>();
    	
    	int update_sucess=0;//是否更新成功
    	
    	//获取账单id
    	String in_id=request.getParameter("in_id");
    	//获取用户id
    	int user_id=Integer.parseInt(request.getParameter("user_id"));
    	//获取输入收入金额
    	double in_money=Double.parseDouble(request.getParameter("in_money"));
    	//获取输入收入类型
    	int in_type=Integer.parseInt(request.getParameter("in_type"));
    	//获取输入账户类型
    	int in_account_type=Integer.parseInt(request.getParameter("in_account_type"));
    	//获取输入记账时间
    	String str_time=request.getParameter("in_time");
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");//时间格式转换
    	Date in_time = simpleDateFormat.parse(str_time);
    	
    	Income income=new Income(); 
    	income.setIn_id(in_id);
    	income.setUser_id(user_id);
    	income.setIn_money(in_money);
    	income.setIn_type(in_type);
    	income.setIn_account_type(in_account_type);
    	income.setIn_time(in_time);
    	//数据层操作
    	IncomeDao dao=new IncomeDaoImpl(config);   	
    	update_sucess=dao.income_update(income);
    	map.put("update_sucess",update_sucess);
    	
    	//转换成json字符串
    	String mapJson=JSON.toJSONString(map);
    	out.print(mapJson);
    	out.flush();
    	out.close();
    }
    /**
     * 	作用:根据账单id删除一条账单
     * @param request
     * @param response
     * @throws Exception
     */
    public void del_income(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	response.setContentType(head);
    	PrintWriter out=response.getWriter();
    	Map<String, Object> map=new HashMap<String, Object>();
		/* 获取信息 */
    	String in_id=request.getParameter("in_id");//账单ID
    	int user_id=Integer.parseInt(request.getParameter("user_id"));//用户ID
		/* 数据层操作 */
    	IncomeDao dao=new IncomeDaoImpl(config);
    	Map<String, Object> map_condition=new HashMap<String, Object>();
    	map_condition.put("in_id", in_id);
    	map_condition.put("user_id", user_id);
    	int del_success=dao.income_delete(map_condition);
    	map.put("del_success", del_success);
    	//转成json字符串
    	String mapJson=JSON.toJSONString(map);
    	out.print(mapJson);
    	out.flush();
    	out.close();
    }
    /**
     * 	作用:获取指定收入账单信息
     * 	接收参数:收入账单id 
     * @param request
     * @param response
     * @throws Exception
     */
    public void view_income(HttpServletRequest request,HttpServletResponse response)throws Exception{
    	response.setContentType(head);
    	PrintWriter out=response.getWriter();
    	Map<String, Object> map=new HashMap<String, Object>();
    	/* 获取信息*/ 	
    	String in_id=request.getParameter("in_id");//获取收入账单id

    	/*数据层操作*/
    	IncomeDao dao=new IncomeDaoImpl(config);
    	Income income=dao.getIncomeById(in_id);//根据收入账单id获取账单
    	map.put("income", income);
    	//转换成json字符串
    	String mapjson=JSON.toJSONString(map);
    	out.print(mapjson);
    	out.flush();
    	out.close();
    	
    }
    /**
     * 	作用:统计每种收入的金额之和
     * 	条件:某段时间范围
     * @param request
     * @param response
     * @throws Exception
     */
    public void query_income_type(HttpServletRequest request,HttpServletResponse response)throws Exception{
    	response.setContentType(head);
    	PrintWriter out=response.getWriter();
    	Map<String, Object> map=new HashMap<String, Object>();
    	int count=0;//统计金额之和不为0个数
    	
		/* 获取信息 */
    	int user_id=Integer.parseInt(request.getParameter("user_id"));//用户id
		/* 数据层操作 */
    	IncomeDao dao=new IncomeDaoImpl(config);
    	double sum=dao.income_money_count(user_id);
    	map.put("income_money_sum", sum);
    	//循环
    	for(int i=0;i<=7;i++) {
    		double income_money=dao.income_type_money_count(i, user_id);
    		if(income_money!=0)count++;
    		switch (i) {
    		case 0:
				map.put("paySalary_money", income_money);
				break;
			case 1:
				map.put("interest_money", income_money);
				break;
			case 2:
				map.put("partTime_money", income_money);
				break;
			case 3:
				map.put("taking_money", income_money);
				break;
			case 4:
				map.put("redEnvelope_money", income_money);
				break;
			case 5:
				map.put("sales_money", income_money);
				break;
			case 6:
				map.put("refund_money", income_money);
				break;
			case 7:
				map.put("reimburse_money", income_money);
				break;
			default:
				break;
			}
    	}
    	map.put("count", count);
		/* 转换成json字符串 */
    	String mapJson=JSON.toJSONString(map);
    	out.print(mapJson);
    	out.flush();
    	out.close();
    }
    
    /**
     * 	作用:统计每种收入的金额之和
     * 	条件:某一时间段
     * @param request
     * @param response
     * @throws Exception
     */
    public void query_income_type_time(HttpServletRequest request,HttpServletResponse response)throws Exception{
    	response.setContentType(head);
    	SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");//时间格式化
    	
    	PrintWriter out=response.getWriter();
    	Map<String, Object> map=new HashMap<String, Object>();
    	Map<String,Object> map_condition=new HashMap<String, Object>();
    	
    	int count=0;//统计金额之和不为0个数
    	
		/* 获取信息 */
    	int user_id=Integer.parseInt(request.getParameter("user_id"));//用户id
    	
    	
    	//获取开始时间
    	Date start_time= sf.parse(request.getParameter("start_time"));
    	//获取结束时间
    	Date end_time=sf.parse(request.getParameter("end_time"));

    	map_condition.put("id", user_id);
    	map_condition.put("start_time", start_time);
    	map_condition.put("end_time", end_time);
    	
    	
		/* 数据层操作 */
    	IncomeDao dao=new IncomeDaoImpl(config);
    	double sum=dao.income_money_count_time(map_condition);
    	map.put("income_money_sum", sum);
    	//循环
    	for(int i=0;i<=7;i++) {
    		double income_money=dao.income_type_money_count_time(i, user_id, start_time, end_time);
    		if(income_money!=0)count++;
    		switch (i) {
    		case 0:
				map.put("paySalary_money", income_money);
				break;
			case 1:
				map.put("interest_money", income_money);
				break;
			case 2:
				map.put("partTime_money", income_money);
				break;
			case 3:
				map.put("taking_money", income_money);
				break;
			case 4:
				map.put("redEnvelope_money", income_money);
				break;
			case 5:
				map.put("sales_money", income_money);
				break;
			case 6:
				map.put("refund_money", income_money);
				break;
			case 7:
				map.put("reimburse_money", income_money);
				break;
			default:
				break;
			}
    	}
    	map.put("count", count);
		/* 转换成json字符串 */
    	String mapJson=JSON.toJSONString(map);
    	out.print(mapJson);
    	out.flush();
    	out.close();
    }
}
