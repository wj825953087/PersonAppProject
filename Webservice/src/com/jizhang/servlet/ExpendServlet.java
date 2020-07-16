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
 * ֧��Servlet
 */
@WebServlet("/ExpendServlet")
public class ExpendServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private static final String config = "sqlconfig.xml";

	private Map<String, Object> map = null;
	private PrintWriter out = null;
	private double expend_money_sum = 0;// ֧�����֮��
	private List<Expend> list = null;

	/**
	 * @see BaseServlet#BaseServlet()
	 */
	public ExpendServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * ����:��������ѯ�û�֧����Ϣ
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */

	public void query_expend(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		map = new HashMap<String, Object>();
		out = response.getWriter();

		/* ��ȡ��Ϣ */
		int user_id = Integer.parseInt(request.getParameter("user_id"));// ��ȡ�û�id
		/* ���ݲ���� */
		ExpendDao dao = new ExpendDaoImpl(config);

		expend_money_sum = dao.expend_money_count(user_id);//ͳ��ȫ��ʱ�����û�֧�����֮��
		list = dao.show_ExpendItem(user_id);// ��ѯ�û�ȫ��ʱ����ڵ�����֧����Ϣ
		/* �����ݷ���Map */
		map.put("expend_money_sum", expend_money_sum);
		map.put("expend_list", list);
		/* ��Mapת��JSON�ַ��� */
		String mapJson = JSON.toJSONString(map);
		out.print(mapJson);
		out.flush();
		out.close();

	}

	/**
	 * ����:��������ѯ�û�֧����Ϣ 
	 * condition:ʱ��
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
		/* ��ȡ��Ϣ */
		int user_id = Integer.parseInt(request.getParameter("user_id"));// ��ȡ�û�id
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");// ʱ���ʽ��
		Date start_time = sf.parse(request.getParameter("start_time"));// ��ȡ��ʼʱ��
		Date end_time = sf.parse(request.getParameter("end_time"));// ��ȡ����ʱ��
			map_condition.put("id", user_id);
			map_condition.put("start_time", start_time);
			map_condition.put("end_time", end_time);

		/* ���ݲ���� */
		ExpendDao dao = new ExpendDaoImpl(config);
		/* ͳ��ĳ��ʱ�����û���֧�����֮��,�Լ��û�֧����Ϣ */
		expend_money_sum = dao.expend_money_count_time(map_condition);
		list = dao.show_ExpendItem_time(map_condition);
		/* ��list��expend_money_sum �Ž�Map��,����JSON�ַ��� */
		map.put("expend_money_sum", expend_money_sum);
		map.put("expend_list", list);

		/* ��Mapת��JSON�ַ��� */
		String mapJson = JSON.toJSONString(map);
		out.print(mapJson);
		out.flush();
		out.close();
	}

	/**
	 * 
	 * ����:��¼һ��֧���˵� ����:�û�id,֧�����,֧������,�˻�����,����ʱ��
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

		int insert_sucess = 0;// �Ƿ����ɹ�
		/* ��ȡ��Ϣ */
		int user_id = Integer.parseInt(request.getParameter("user_id"));// ��ȡ�û�id
		double ex_money = Double.parseDouble(request.getParameter("ex_money"));// ��ȡ����֧�����
		int ex_type = Integer.parseInt(request.getParameter("ex_type"));// ��ȡ����֧������
		int ex_account_type = Integer.parseInt(request.getParameter("ex_account_type"));// ��ȡ�����˻�����		 
		String str_time = request.getParameter("ex_time");//��ȡ�������ʱ�� 
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");// ʱ���ʽת��
			Date ex_time = simpleDateFormat.parse(str_time);

		/* ������ֵ����ʵ������� */
		Expend expend = new Expend();
		expend.setUser_id(user_id);
		expend.setEx_money(ex_money);
		expend.setEx_type(ex_type);
		expend.setEx_account_type(ex_account_type);
		expend.setEx_time(ex_time);
		/* ���ݲ���� */
		ExpendDao dao = new ExpendDaoImpl(config);
		insert_sucess = dao.expend_insert(expend);

		map.put("insert_sucess", insert_sucess);
		/* ת����json�ַ��� */
		String mapJson = JSON.toJSONString(map);
		out.print(mapJson);
		out.flush();
		out.close();
	}

	/**
	 * ����:����һ���˵� ����:
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void update_expend(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int update_sucess = 0;// �Ƿ���³ɹ�
		response.setContentType("text/html;charset=utf-8");
		map = new HashMap<String, Object>();
		out = response.getWriter();
		/* ��ȡ��Ϣ */

		String ex_id = request.getParameter("ex_id");// ��ȡ�˵�id
		int user_id = Integer.parseInt(request.getParameter("user_id")); // ��ȡ�û�id
		double ex_money = Double.parseDouble(request.getParameter("ex_money"));// ��ȡ����֧�����
		int ex_type = Integer.parseInt(request.getParameter("ex_type"));// ��ȡ����֧������
		int ex_account_type = Integer.parseInt(request.getParameter("ex_account_type"));// ��ȡ�����˻�����
		String str_time = request.getParameter("ex_time");// ��ȡ�������ʱ��
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");// ʱ���ʽת��
		Date ex_time = simpleDateFormat.parse(str_time);

		/* �����ݷ���ʵ������� */
		Expend expend = new Expend();
		expend.setEx_id(ex_id);
		expend.setUser_id(user_id);
		expend.setEx_money(ex_money);
		expend.setEx_type(ex_type);
		expend.setEx_account_type(ex_account_type);
		expend.setEx_time(ex_time);
		/* ���ݲ���� */
		ExpendDao dao = new ExpendDaoImpl(config);
		update_sucess = dao.expend_update(expend);// ���²���
		map.put("update_sucess", update_sucess);

		/* ת����json�ַ��� */
		String mapJson = JSON.toJSONString(map);
		out.print(mapJson);
		out.flush();
		out.close();
	}

	/**
	 * ����:��ȡָ��֧���˵���Ϣ 
	 * ����:֧���˵�id
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void view_expend(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		map = new HashMap<String, Object>();
		out = response.getWriter();
		/* ��ȡ��Ϣ */
		String ex_id = request.getParameter("ex_id");// ��ȡ֧���˵�id
		/* ���ݲ���� */
		ExpendDao dao = new ExpendDaoImpl(config);
		Expend expend = dao.getExpendById(ex_id);// ����֧���˵�id��ȡ�˵�
		map.put("expend", expend);
		/* ת����json�ַ��� */
		String mapjson = JSON.toJSONString(map);
		out.print(mapjson);
		out.flush();
		out.close();
	}
	/**
	 * ����:ɾ���˵�
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void del_expend(HttpServletRequest request,HttpServletResponse response)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		out = response.getWriter();
		map = new HashMap<String, Object>();
		/* ��ȡ��Ϣ */
    	String ex_id=request.getParameter("ex_id");//�˵�ID
    	int user_id=Integer.parseInt(request.getParameter("user_id"));//�û�ID
		/* ���ݲ���� */
    	ExpendDao dao=new ExpendDaoImpl(config);
    	Map<String, Object> map_condition=new HashMap<String, Object>();
    	map_condition.put("ex_id", ex_id);
    	map_condition.put("user_id", user_id);
    	int del_success=dao.expend_delete(map_condition);
    	map.put("del_success", del_success);
    	//ת��json�ַ���
    	String mapJson=JSON.toJSONString(map);
    	out.print(mapJson);
    	out.flush();
    	out.close();
		
	}
	/**
	 * ����:ͳ��ÿ�����ѵĽ��֮�� 
	 * ����:ȫ��ʱ���
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void query_expend_type(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		map = new HashMap<String, Object>();
		out = response.getWriter();
		int count=0;//ͳ�ƽ��֮�Ͳ�Ϊ0����
		/* ��ȡ��Ϣ */
		int user_id = Integer.parseInt(request.getParameter("user_id"));// ��ȡ�û�id
		/* ���ݲ���� */
		ExpendDao dao = new ExpendDaoImpl(config);
		
		double sum=dao.expend_money_count(user_id);
			map.put("expend_money_sum", sum);
		// ѭ��
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
		/* ת����json�ַ��� */
		String mapJson = JSON.toJSONString(map);
		out.print(mapJson);
		out.flush();
		out.close();
	}
	
	/**
	 * ����:ͳ��ÿ�����ѵĽ��֮�� 
	 * ����:ĳ��ʱ�䷶Χ��
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void query_expend_type_time(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		Map<String,Object> map_condition=new HashMap<String, Object>();
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");//ʱ���ʽ��
		map = new HashMap<String, Object>();
		out = response.getWriter();
		//----------------------------------------------------------
		int count=0;//ͳ�ƽ��֮�Ͳ�Ϊ0����
		/* ��ȡ��Ϣ */
		int user_id = Integer.parseInt(request.getParameter("user_id"));// ��ȡ�û�id
    	Date start_time= sf.parse(request.getParameter("start_time"));//��ȡ��ʼʱ��
    	Date end_time=sf.parse(request.getParameter("end_time"));//��ȡ����ʱ��
    	//--------------------------------------------------------
    	map_condition.put("id", user_id);
    	map_condition.put("start_time", start_time);
    	map_condition.put("end_time", end_time);
    	
		/* ���ݲ���� */
		ExpendDao dao = new ExpendDaoImpl(config);
		
		double sum=dao.expend_money_count_time(map_condition);
			map.put("expend_money_sum", sum);
		// ѭ��
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
		/* ת����json�ַ��� */
		String mapJson = JSON.toJSONString(map);
		out.print(mapJson);
		out.flush();
		out.close();
	}
	
}
