package com.jizhang.servlet;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.jizhang.dao.ExpendDao;
import com.jizhang.dao.IncomeDao;
import com.jizhang.dao.impl.ExpendDaoImpl;
import com.jizhang.dao.impl.IncomeDaoImpl;
import com.jizhang.entity.expend.Expend;
import com.jizhang.entity.income.Income;

/**
 * Servlet implementation class ExpendServlet
 * 支出Servlet
 */
@WebServlet("/ExpendServlet")
public class ExpendServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private static final String config = "sqlconfig.xml";

	private Map<String, Object> map = null;
	private PrintWriter out = null;
	private double expend_money_sum = 0;// 支出金额之和
	private List<Expend> list = null;

	/**
	 * @see BaseServlet#BaseServlet()
	 */
	public ExpendServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 作用:无条件查询用户支出信息
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */

	public void query_expend(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		map = new HashMap<String, Object>();
		out = response.getWriter();

		/* 获取信息 */
		int user_id = Integer.parseInt(request.getParameter("user_id"));// 获取用户id
		/* 数据层操作 */
		ExpendDao dao = new ExpendDaoImpl(config);

		expend_money_sum = dao.expend_money_count(user_id);//统计全部时间内用户支出金额之和
		list = dao.show_ExpendItem(user_id);// 查询用户全部时间段内的所有支出信息
		/* 将数据放入Map */
		map.put("expend_money_sum", expend_money_sum);
		map.put("expend_list", list);
		/* 将Map转成JSON字符串 */
		String mapJson = JSON.toJSONString(map);
		out.print(mapJson);
		out.flush();
		out.close();

	}

	/**
	 * 作用:有条件查询用户支出信息 
	 * condition:时间
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void query_expend_time(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		map = new HashMap<String, Object>();
		out = response.getWriter();
		Map<String, Object> map_condition = new HashMap<String, Object>();
		/* 获取信息 */
		int user_id = Integer.parseInt(request.getParameter("user_id"));// 获取用户id
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");// 时间格式化
		Date start_time = sf.parse(request.getParameter("start_time"));// 获取开始时间
		Date end_time = sf.parse(request.getParameter("end_time"));// 获取结束时间
			map_condition.put("id", user_id);
			map_condition.put("start_time", start_time);
			map_condition.put("end_time", end_time);

		/* 数据层操作 */
		ExpendDao dao = new ExpendDaoImpl(config);
		/* 统计某段时间内用户的支出金额之和,以及用户支出信息 */
		expend_money_sum = dao.expend_money_count_time(map_condition);
		list = dao.show_ExpendItem_time(map_condition);
		/* 将list和expend_money_sum 放进Map里,生成JSON字符串 */
		map.put("expend_money_sum", expend_money_sum);
		map.put("expend_list", list);

		/* 将Map转成JSON字符串 */
		String mapJson = JSON.toJSONString(map);
		out.print(mapJson);
		out.flush();
		out.close();
	}

	/**
	 * 
	 * 作用:记录一条支出账单 参数:用户id,支出金额,支出类型,账户类型,记账时间
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 * 
	 */
	public void insert_expend(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		map = new HashMap<String, Object>();
		out = response.getWriter();

		int insert_sucess = 0;// 是否插入成功
		/* 获取信息 */
		int user_id = Integer.parseInt(request.getParameter("user_id"));// 获取用户id
		double ex_money = Double.parseDouble(request.getParameter("ex_money"));// 获取输入支出金额
		int ex_type = Integer.parseInt(request.getParameter("ex_type"));// 获取输入支出类型
		int ex_account_type = Integer.parseInt(request.getParameter("ex_account_type"));// 获取输入账户类型		 
		String str_time = request.getParameter("ex_time");//获取输入记账时间 
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 时间格式转换
			Date ex_time = simpleDateFormat.parse(str_time);

		/* 将输入值放入实体对象中 */
		Expend expend = new Expend();
		expend.setUser_id(user_id);
		expend.setEx_money(ex_money);
		expend.setEx_type(ex_type);
		expend.setEx_account_type(ex_account_type);
		expend.setEx_time(ex_time);
		/* 数据层操作 */
		ExpendDao dao = new ExpendDaoImpl(config);
		insert_sucess = dao.expend_insert(expend);

		map.put("insert_sucess", insert_sucess);
		/* 转换成json字符串 */
		String mapJson = JSON.toJSONString(map);
		out.print(mapJson);
		out.flush();
		out.close();
	}

	/**
	 * 作用:更新一条账单 参数:
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void update_expend(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int update_sucess = 0;// 是否更新成功
		response.setContentType("text/html;charset=utf-8");
		map = new HashMap<String, Object>();
		out = response.getWriter();
		/* 获取信息 */

		String ex_id = request.getParameter("ex_id");// 获取账单id
		int user_id = Integer.parseInt(request.getParameter("user_id")); // 获取用户id
		double ex_money = Double.parseDouble(request.getParameter("ex_money"));// 获取输入支出金额
		int ex_type = Integer.parseInt(request.getParameter("ex_type"));// 获取输入支出类型
		int ex_account_type = Integer.parseInt(request.getParameter("ex_account_type"));// 获取输入账户类型
		String str_time = request.getParameter("ex_time");// 获取输入记账时间
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 时间格式转换
		Date ex_time = simpleDateFormat.parse(str_time);

		/* 将数据放入实体类对象 */
		Expend expend = new Expend();
		expend.setEx_id(ex_id);
		expend.setUser_id(user_id);
		expend.setEx_money(ex_money);
		expend.setEx_type(ex_type);
		expend.setEx_account_type(ex_account_type);
		expend.setEx_time(ex_time);
		/* 数据层操作 */
		ExpendDao dao = new ExpendDaoImpl(config);
		update_sucess = dao.expend_update(expend);// 更新操作
		map.put("update_sucess", update_sucess);

		/* 转换成json字符串 */
		String mapJson = JSON.toJSONString(map);
		out.print(mapJson);
		out.flush();
		out.close();
	}

	/**
	 * 作用:获取指定支出账单信息 
	 * 参数:支出账单id
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void view_expend(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		map = new HashMap<String, Object>();
		out = response.getWriter();
		/* 获取信息 */
		String ex_id = request.getParameter("ex_id");// 获取支出账单id
		/* 数据层操作 */
		ExpendDao dao = new ExpendDaoImpl(config);
		Expend expend = dao.getExpendById(ex_id);// 根据支出账单id获取账单
		map.put("expend", expend);
		/* 转换成json字符串 */
		String mapjson = JSON.toJSONString(map);
		out.print(mapjson);
		out.flush();
		out.close();
	}
	/**
	 * 作用:删除账单
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void del_expend(HttpServletRequest request,HttpServletResponse response)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		out = response.getWriter();
		map = new HashMap<String, Object>();
		/* 获取信息 */
    	String ex_id=request.getParameter("ex_id");//账单ID
    	int user_id=Integer.parseInt(request.getParameter("user_id"));//用户ID
		/* 数据层操作 */
    	ExpendDao dao=new ExpendDaoImpl(config);
    	Map<String, Object> map_condition=new HashMap<String, Object>();
    	map_condition.put("ex_id", ex_id);
    	map_condition.put("user_id", user_id);
    	int del_success=dao.expend_delete(map_condition);
    	map.put("del_success", del_success);
    	//转成json字符串
    	String mapJson=JSON.toJSONString(map);
    	out.print(mapJson);
    	out.flush();
    	out.close();
		
	}
	/**
	 * 作用:统计每种消费的金额之和 
	 * 条件:全部时间段
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void query_expend_type(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		map = new HashMap<String, Object>();
		out = response.getWriter();
		int count=0;//统计金额之和不为0个数
		/* 获取信息 */
		int user_id = Integer.parseInt(request.getParameter("user_id"));// 获取用户id
		/* 数据层操作 */
		ExpendDao dao = new ExpendDaoImpl(config);
		
		double sum=dao.expend_money_count(user_id);
			map.put("expend_money_sum", sum);
		// 循环
		for (int i = 0; i <= 7; i++) {
			double expend_money = dao.expend_type_money_count(i, user_id);
			if(expend_money!=0)count++;
			switch (i) {
			case 0:
				map.put("breakfast_money", expend_money);
				break;
			case 1:
				map.put("lunch_money", expend_money);
				break;
			case 2:
				map.put("dinner_money", expend_money);
				break;
			case 3:
				map.put("drinkAndFruit_money", expend_money);
				break;
			case 4:
				map.put("buyFood_money", expend_money);
				break;
			case 5:
				map.put("taxi_money", expend_money);
				break;
			case 6:
				map.put("bus_money", expend_money);
				break;
			case 7:
				map.put("oil_money", expend_money);
				break;
			default:
				break;
			}
		}
		map.put("count", count);
		/* 转换成json字符串 */
		String mapJson = JSON.toJSONString(map);
		out.print(mapJson);
		out.flush();
		out.close();
	}
	
	/**
	 * 作用:统计每种消费的金额之和 
	 * 条件:某段时间范围内
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void query_expend_type_time(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		Map<String,Object> map_condition=new HashMap<String, Object>();
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");//时间格式化
		map = new HashMap<String, Object>();
		out = response.getWriter();
		//----------------------------------------------------------
		int count=0;//统计金额之和不为0个数
		/* 获取信息 */
		int user_id = Integer.parseInt(request.getParameter("user_id"));// 获取用户id
    	Date start_time= sf.parse(request.getParameter("start_time"));//获取开始时间
    	Date end_time=sf.parse(request.getParameter("end_time"));//获取结束时间
    	//--------------------------------------------------------
    	map_condition.put("id", user_id);
    	map_condition.put("start_time", start_time);
    	map_condition.put("end_time", end_time);
    	
		/* 数据层操作 */
		ExpendDao dao = new ExpendDaoImpl(config);
		
		double sum=dao.expend_money_count_time(map_condition);
			map.put("expend_money_sum", sum);
		// 循环
		for (int i = 0; i <= 7; i++) {
			double expend_money = dao.expend_type_money_count_time(i, user_id,start_time,end_time);
			if(expend_money!=0)count++;
			switch (i) {
			case 0:
				map.put("breakfast_money", expend_money);
				break;
			case 1:
				map.put("lunch_money", expend_money);
				break;
			case 2:
				map.put("dinner_money", expend_money);
				break;
			case 3:
				map.put("drinkAndFruit_money", expend_money);
				break;
			case 4:
				map.put("buyFood_money", expend_money);
				break;
			case 5:
				map.put("taxi_money", expend_money);
				break;
			case 6:
				map.put("bus_money", expend_money);
				break;
			case 7:
				map.put("oil_money", expend_money);
				break;
			default:
				break;
			}
		}
		map.put("count", count);
		/* 转换成json字符串 */
		String mapJson = JSON.toJSONString(map);
		out.print(mapJson);
		out.flush();
		out.close();
	}
	
}
