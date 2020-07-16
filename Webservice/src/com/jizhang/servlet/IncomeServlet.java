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
     *	����:��ѯ�û�������Ϣ
     *	����:ȫ��ʱ���
     * @param request
     * @param response
     * @throws Exception
     */
    public void query_income(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	
    	response.setContentType(head);
    	PrintWriter out=response.getWriter();
    	Map<String, Object> map=new HashMap<String, Object>();
    		
    	//��ȡ�û�id
    	int user_id=Integer.parseInt(request.getParameter("user_id"));
    	
    	//���ݲ����
    	IncomeDao dao=new IncomeDaoImpl(config);
    	//������ͳ���û�������֮��,�û�������Ϣ
    	double income_money_sum=dao.income_money_count(user_id);
    	List<Income> list=dao.show_IncomeItem(user_id);
    	
    	//��������ѯ
    	map.put("income_money_sum", income_money_sum);
    	map.put("income_list", list);
    	
    	//ת����JSON�ַ���
    	String mapjson=JSON.toJSONString(map);
    	
    	out.print(mapjson);
    	out.flush();
    	out.close();
    	
    	
    }

    /**
     * 	����:��ѯ�û�������Ϣ
     * 	����: ĳ��ʱ�䷶Χ
     * @param request
     * @param response
     * @throws Exception
     */
    public void query_income_time(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	response.setContentType(head);
    	PrintWriter out=response.getWriter();
    	Map<String, Object> map=new HashMap<String, Object>();
    	
    	Map<String,Object> map_condition=new HashMap<String, Object>();
    	
    	//��ȡ�û�id
    	int user_id=Integer.parseInt(request.getParameter("user_id"));
    	SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");//ʱ���ʽ��
    	//��ȡ��ʼʱ��
    	Date start_time= sf.parse(request.getParameter("start_time"));
    	//��ȡ����ʱ��
    	Date end_time=sf.parse(request.getParameter("end_time"));

    	map_condition.put("id", user_id);
    	map_condition.put("start_time", start_time);
    	map_condition.put("end_time", end_time);
    	
    
    	//���ݲ����
    	IncomeDao dao=new IncomeDaoImpl(config);
    	//������ͳ���û�������
    	double income_money_sum=dao.income_money_count_time(map_condition);
    	List<Income> list=dao.show_IncomeItem_time(map_condition);
    	
    	//��������ѯ
    	map.put("income_money_sum", income_money_sum);
    	map.put("income_list", list);
    	//ת����JSON�ַ���
    	String mapjson=JSON.toJSONString(map);
    	out.print(mapjson);
    	out.flush();
    	out.close();
    	
	}
    /**
     *   	
     * 	����:��¼һ�������˵�
     * 	���ղ���:�û�id,������,��������,�˻�����,����ʱ��
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
    	 *  PrintWriter �� 
    	 *	����: ������������һ���ļ������ı��ļ�д������	  
    	 *	���÷���: 
    	 *	print(String str) :���ļ�д��һ���ַ���
    	 *	print(char[] ch) :���ļ�д��һ���ַ�������
    	 *	
    	 *	write() :��֧������ַ���������,�ַ�,�ַ�����,�ַ�����
    	 *	print() :���Խ���������(����Object)������ͨ��Ĭ�ϱ���ת����bytes�ֽ���ʽ
    	 */
    	
    	
    	int insert_sucess=0;//�Ƿ���˳ɹ�

    	//��ȡ�û�id
    	int user_id=Integer.parseInt(request.getParameter("user_id"));
    	//��ȡ����������
    	double in_money=Double.parseDouble(request.getParameter("in_money"));
    	//��ȡ������������
    	int in_type=Integer.parseInt(request.getParameter("in_type"));
    	//��ȡ�����˻�����
    	int in_account_type=Integer.parseInt(request.getParameter("in_account_type"));
    	//��ȡ�������ʱ��
    	
    	String str_time=request.getParameter("in_time");
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");//ʱ���ʽת��
    	Date in_time = simpleDateFormat.parse(str_time);
  

    	//������ֵ����ʵ�������
    	Income income=new Income();
	    	income.setUser_id(user_id);
	    	income.setIn_money(in_money);
	    	income.setIn_type(in_type);
	    	income.setIn_account_type(in_account_type);
	    	income.setIn_time(in_time);
    	
    	//���ݲ����
    	IncomeDao dao=new IncomeDaoImpl(config);
    	insert_sucess=dao.income_insert(income);
    	
    	map.put("insert_sucess",insert_sucess);
    	//ת����json�ַ���
    	String mapJson=JSON.toJSONString(map);
    	out.print(mapJson);
    	out.flush();
    	out.close();
    }
    /**
     * 	����:����һ���˵�
     * 	���ղ���:�˵�id,�û�id,������,��������,�˺�����,����ʱ��
     * @param request
     * @param response
     * @throws Exception
     */
    public void update_income(HttpServletRequest request,HttpServletResponse response)throws Exception {
    	response.setContentType(head);
    	PrintWriter out=response.getWriter();
    	Map<String, Object> map=new HashMap<String, Object>();
    	
    	int update_sucess=0;//�Ƿ���³ɹ�
    	
    	//��ȡ�˵�id
    	String in_id=request.getParameter("in_id");
    	//��ȡ�û�id
    	int user_id=Integer.parseInt(request.getParameter("user_id"));
    	//��ȡ����������
    	double in_money=Double.parseDouble(request.getParameter("in_money"));
    	//��ȡ������������
    	int in_type=Integer.parseInt(request.getParameter("in_type"));
    	//��ȡ�����˻�����
    	int in_account_type=Integer.parseInt(request.getParameter("in_account_type"));
    	//��ȡ�������ʱ��
    	String str_time=request.getParameter("in_time");
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");//ʱ���ʽת��
    	Date in_time = simpleDateFormat.parse(str_time);
    	
    	Income income=new Income(); 
    	income.setIn_id(in_id);
    	income.setUser_id(user_id);
    	income.setIn_money(in_money);
    	income.setIn_type(in_type);
    	income.setIn_account_type(in_account_type);
    	income.setIn_time(in_time);
    	//���ݲ����
    	IncomeDao dao=new IncomeDaoImpl(config);   	
    	update_sucess=dao.income_update(income);
    	map.put("update_sucess",update_sucess);
    	
    	//ת����json�ַ���
    	String mapJson=JSON.toJSONString(map);
    	out.print(mapJson);
    	out.flush();
    	out.close();
    }
    /**
     * 	����:�����˵�idɾ��һ���˵�
     * @param request
     * @param response
     * @throws Exception
     */
    public void del_income(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	response.setContentType(head);
    	PrintWriter out=response.getWriter();
    	Map<String, Object> map=new HashMap<String, Object>();
		/* ��ȡ��Ϣ */
    	String in_id=request.getParameter("in_id");//�˵�ID
    	int user_id=Integer.parseInt(request.getParameter("user_id"));//�û�ID
		/* ���ݲ���� */
    	IncomeDao dao=new IncomeDaoImpl(config);
    	Map<String, Object> map_condition=new HashMap<String, Object>();
    	map_condition.put("in_id", in_id);
    	map_condition.put("user_id", user_id);
    	int del_success=dao.income_delete(map_condition);
    	map.put("del_success", del_success);
    	//ת��json�ַ���
    	String mapJson=JSON.toJSONString(map);
    	out.print(mapJson);
    	out.flush();
    	out.close();
    }
    /**
     * 	����:��ȡָ�������˵���Ϣ
     * 	���ղ���:�����˵�id 
     * @param request
     * @param response
     * @throws Exception
     */
    public void view_income(HttpServletRequest request,HttpServletResponse response)throws Exception{
    	response.setContentType(head);
    	PrintWriter out=response.getWriter();
    	Map<String, Object> map=new HashMap<String, Object>();
    	/* ��ȡ��Ϣ*/ 	
    	String in_id=request.getParameter("in_id");//��ȡ�����˵�id

    	/*���ݲ����*/
    	IncomeDao dao=new IncomeDaoImpl(config);
    	Income income=dao.getIncomeById(in_id);//���������˵�id��ȡ�˵�
    	map.put("income", income);
    	//ת����json�ַ���
    	String mapjson=JSON.toJSONString(map);
    	out.print(mapjson);
    	out.flush();
    	out.close();
    	
    }
    /**
     * 	����:ͳ��ÿ������Ľ��֮��
     * 	����:ĳ��ʱ�䷶Χ
     * @param request
     * @param response
     * @throws Exception
     */
    public void query_income_type(HttpServletRequest request,HttpServletResponse response)throws Exception{
    	response.setContentType(head);
    	PrintWriter out=response.getWriter();
    	Map<String, Object> map=new HashMap<String, Object>();
    	int count=0;//ͳ�ƽ��֮�Ͳ�Ϊ0����
    	
		/* ��ȡ��Ϣ */
    	int user_id=Integer.parseInt(request.getParameter("user_id"));//�û�id
		/* ���ݲ���� */
    	IncomeDao dao=new IncomeDaoImpl(config);
    	double sum=dao.income_money_count(user_id);
    	map.put("income_money_sum", sum);
    	//ѭ��
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
		/* ת����json�ַ��� */
    	String mapJson=JSON.toJSONString(map);
    	out.print(mapJson);
    	out.flush();
    	out.close();
    }
    
    /**
     * 	����:ͳ��ÿ������Ľ��֮��
     * 	����:ĳһʱ���
     * @param request
     * @param response
     * @throws Exception
     */
    public void query_income_type_time(HttpServletRequest request,HttpServletResponse response)throws Exception{
    	response.setContentType(head);
    	SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");//ʱ���ʽ��
    	
    	PrintWriter out=response.getWriter();
    	Map<String, Object> map=new HashMap<String, Object>();
    	Map<String,Object> map_condition=new HashMap<String, Object>();
    	
    	int count=0;//ͳ�ƽ��֮�Ͳ�Ϊ0����
    	
		/* ��ȡ��Ϣ */
    	int user_id=Integer.parseInt(request.getParameter("user_id"));//�û�id
    	
    	
    	//��ȡ��ʼʱ��
    	Date start_time= sf.parse(request.getParameter("start_time"));
    	//��ȡ����ʱ��
    	Date end_time=sf.parse(request.getParameter("end_time"));

    	map_condition.put("id", user_id);
    	map_condition.put("start_time", start_time);
    	map_condition.put("end_time", end_time);
    	
    	
		/* ���ݲ���� */
    	IncomeDao dao=new IncomeDaoImpl(config);
    	double sum=dao.income_money_count_time(map_condition);
    	map.put("income_money_sum", sum);
    	//ѭ��
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
		/* ת����json�ַ��� */
    	String mapJson=JSON.toJSONString(map);
    	out.print(mapJson);
    	out.flush();
    	out.close();
    }
}
