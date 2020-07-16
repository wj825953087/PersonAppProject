package com.jizhang.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.jizhang.dao.MangerTypeDao;
import com.jizhang.dao.impl.MangerTypeDaoImpl;
import com.jizhang.entity.expend.ExpendType;
import com.jizhang.entity.income.IncomeType;

/**
 * Servlet implementation class MangerTypeServlet
 * ������Servlet
 */
@WebServlet("/MangerTypeServlet")
public class MangerTypeServlet extends BaseServlet implements Servlet {
	private static final long serialVersionUID = 1L;
	private static final String config="sqlconfig.xml"; 
	private static final String head="text/html;charset=utf-8";
	private PrintWriter out=null;
	private Map<String,Object> map=null;
	private List<IncomeType> incomeTypes=null;
	private List<ExpendType> expendTypes=null;
       
    /**
     * @see BaseServlet#BaseServlet()
     */
    public MangerTypeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	/**
	 * 	����:��ʾ�������
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void ShowInAccountType(HttpServletRequest request, HttpServletResponse response)throws Exception{
		
		response.setContentType(head);
		out=response.getWriter();
		map=new HashMap<String, Object>();
		//���ݲ����
		MangerTypeDao dao=new MangerTypeDaoImpl(config);
		incomeTypes=dao.showIncome();
		map.put("income_type_list", incomeTypes);
		//ת����JSON�ַ���
		String json=JSON.toJSONString(map);
		out.print(json);
		out.flush();
		out.close();
		
	}
	/**
	 * 	����:��ʾ֧�����
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void ShowOutAccountType(HttpServletRequest request,HttpServletResponse response)throws Exception{
		response.setContentType(head);
		out=response.getWriter();
		map=new HashMap<String, Object>();
		//���ݲ����
		MangerTypeDao dao=new MangerTypeDaoImpl(config);
		expendTypes=dao.showExpend();
		map.put("expend_type_list", expendTypes);
		//ת����JSON�ַ���
		String json=JSON.toJSONString(map);
		out.print(json);
		out.flush();
		out.close();
	}
	/**
	 * 	����:�����������
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void insert_income_type(HttpServletRequest request,HttpServletResponse response)throws Exception{
		response.setContentType(head);
		out=response.getWriter();
		map=new HashMap<String, Object>();
		//��ȡ���id
		int type_id=Integer.parseInt(request.getParameter("type_id"));
		//��ȡ�Ƿ���Ĭ�����
		int isDefault=Integer.parseInt(request.getParameter("is_default"));//0����, 1��
		//��ȡ�ϼ�id
		int super_id=Integer.parseInt(request.getParameter("super_id"));//0 û��, 0���� �ϼ�id
		//��ȡȨ��
		int permissions=Integer.parseInt(request.getParameter("permissions"));//1 ϵͳĬ��Ȩ��, 2 �Զ����Ȩ��
		//��ȡ����
		String name=request.getParameter("income_name");
		//���ݲ����
		MangerTypeDao dao=new MangerTypeDaoImpl(config);
		IncomeType type=new IncomeType();
			type.setId(type_id);
			type.setIs_default(isDefault);
			type.setSuper_id(super_id);
			type.setPermissions(permissions);
			type.setIncome_name(name);
		int add_success=dao.insertIncome(type);	
		
		map.put("add_success",add_success);
		
		//ת����JSON�ַ���
		String json=JSON.toJSONString(map);
		out.print(json);
		out.flush();
		out.close();
	}
	/**
	 * 	����:����֧�����
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void insert_expend_type(HttpServletRequest request,HttpServletResponse response)throws Exception{
		response.setContentType(head);
		out=response.getWriter();
		map=new HashMap<String, Object>();

		//��ȡ���id
		int type_id=Integer.parseInt(request.getParameter("type_id"));
		//��ȡ�Ƿ���Ĭ�����
		int isDefault=Integer.parseInt(request.getParameter("is_default"));//0����, 1��
		//��ȡ�ϼ�id
		int super_id=Integer.parseInt(request.getParameter("super_id"));//0 û��, 0���� �ϼ�id
		//��ȡȨ��
		int permissions=Integer.parseInt(request.getParameter("permissions"));//1 ϵͳĬ��Ȩ��, 2 �Զ����Ȩ��
		//��ȡ����
		String name=request.getParameter("expend_name");
		//���ݲ����
		MangerTypeDao dao=new MangerTypeDaoImpl(config);
		ExpendType type=new ExpendType();
			type.setId(type_id);
			type.setIs_default(isDefault);
			type.setSuper_id(super_id);
			type.setPermissions(permissions);
			type.setExpend_name(name);
			
		int add_success=dao.insertExpend(type);	
	
		map.put("add_success",add_success);
	
		//ת����JSON�ַ���
		String json=JSON.toJSONString(map);
		out.print(json);
		out.flush();
		out.close();
		
		
	}
	
	/**
	 * 	����:�������������Ϣ
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void update_income_type(HttpServletRequest request,HttpServletResponse response)throws Exception{
		response.setContentType(head);
		out=response.getWriter();
		map=new HashMap<String, Object>();
		//��ȡ���id
		int type_id=Integer.parseInt(request.getParameter("type_id"));
		//��ȡͼƬid
		int pngId=Integer.parseInt(request.getParameter("pngId"));
		//��ȡ�Ƿ���Ĭ�����
		int isDefault=Integer.parseInt(request.getParameter("is_default"));
		String name=null;//�������
		if(isDefault!=1) {
			//��ȡ�������
			name=request.getParameter("income_name");
			
		}
		
		//���ݲ����
		MangerTypeDao dao=new MangerTypeDaoImpl(config);
		IncomeType type=new IncomeType();
		
		type.setId(type_id);
		type.setIncome_pngId(pngId);
		if(isDefault!=1)type.setIncome_name(name);
		
		int row=dao.updateIncome(type);
		map.put("update_success",row );
		//ת����JSON�ַ���
		String json=JSON.toJSONString(map);
		out.print(json);
		out.flush();
		out.close();
	}


	/**
	 *	����:����֧�������Ϣ 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void update_expend_type(HttpServletRequest request,HttpServletResponse response)throws Exception{
		response.setContentType(head);
		out=response.getWriter();
		map=new HashMap<String, Object>();
		//��ȡ֧�����id
		int type_id=Integer.parseInt(request.getParameter("type_id"));
		//��ȡͼƬid
		int pngId=Integer.parseInt(request.getParameter("pngId"));
		//��ȡ�Ƿ���Ĭ�����
		int isDefault=Integer.parseInt(request.getParameter("is_default"));
		String name=null;//�������
		if(isDefault!=1) {
			//��ȡ�������
			name=request.getParameter("expend_name");
			
		}
		
		//���ݲ����
		MangerTypeDao dao=new MangerTypeDaoImpl(config);
		
		ExpendType type=new ExpendType();
		
		type.setId(type_id);
		type.setExpend_pngId(pngId);
		if(isDefault!=1)type.setExpend_name(name);
		
		int row=dao.updateExpend(type);
		map.put("update_success",row );
		//ת����JSON�ַ���
		String json=JSON.toJSONString(map);
		out.print(json);
		out.flush();
		out.close();
	}
	
	/**
	 * ����:ɾ��һ����������
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void del_income_type(HttpServletRequest request,HttpServletResponse response)throws Exception {
		response.setContentType(head);
		out=response.getWriter();
		map=new HashMap<String, Object>();
		int row=0;
		
		//��ȡ���id
		int type_id=Integer.parseInt(request.getParameter("type_id"));
	
		//���ݲ����
		MangerTypeDao dao=new MangerTypeDaoImpl(config);
		row=dao.delIncome(type_id);
		map.put("del_success",row);
		
		//ת����JSON�ַ���
		String json=JSON.toJSONString(map);
		out.print(json);
		out.flush();
		out.close();
		
	}
	/**
	 *  ����:ɾ��һ��֧������
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void del_expend_type(HttpServletRequest request,HttpServletResponse response)throws Exception {
		response.setContentType(head);
		out=response.getWriter();
		map=new HashMap<String, Object>();
		int row=0;
		//��ȡ���id
		int type_id=Integer.parseInt(request.getParameter("type_id"));

		//���ݲ����
		MangerTypeDao dao=new MangerTypeDaoImpl(config);
		row=dao.delExpend(type_id);
		
		map.put("del_success",row);
		
		//ת����JSON�ַ���
		String json=JSON.toJSONString(map);
		out.print(json);
		out.flush();
		out.close();
		
	}
}
