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
 * 类别管理Servlet
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
	 * 	作用:显示收入类别
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void ShowInAccountType(HttpServletRequest request, HttpServletResponse response)throws Exception{
		
		response.setContentType(head);
		out=response.getWriter();
		map=new HashMap<String, Object>();
		//数据层操作
		MangerTypeDao dao=new MangerTypeDaoImpl(config);
		incomeTypes=dao.showIncome();
		map.put("income_type_list", incomeTypes);
		//转换成JSON字符串
		String json=JSON.toJSONString(map);
		out.print(json);
		out.flush();
		out.close();
		
	}
	/**
	 * 	作用:显示支出类别
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void ShowOutAccountType(HttpServletRequest request,HttpServletResponse response)throws Exception{
		response.setContentType(head);
		out=response.getWriter();
		map=new HashMap<String, Object>();
		//数据层操作
		MangerTypeDao dao=new MangerTypeDaoImpl(config);
		expendTypes=dao.showExpend();
		map.put("expend_type_list", expendTypes);
		//转换成JSON字符串
		String json=JSON.toJSONString(map);
		out.print(json);
		out.flush();
		out.close();
	}
	/**
	 * 	作用:新增收入类别
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void insert_income_type(HttpServletRequest request,HttpServletResponse response)throws Exception{
		response.setContentType(head);
		out=response.getWriter();
		map=new HashMap<String, Object>();
		//获取类别id
		int type_id=Integer.parseInt(request.getParameter("type_id"));
		//获取是否是默认类别
		int isDefault=Integer.parseInt(request.getParameter("is_default"));//0不是, 1是
		//获取上级id
		int super_id=Integer.parseInt(request.getParameter("super_id"));//0 没有, 0以外 上级id
		//获取权限
		int permissions=Integer.parseInt(request.getParameter("permissions"));//1 系统默认权限, 2 自定义的权限
		//获取名字
		String name=request.getParameter("income_name");
		//数据层操作
		MangerTypeDao dao=new MangerTypeDaoImpl(config);
		IncomeType type=new IncomeType();
			type.setId(type_id);
			type.setIs_default(isDefault);
			type.setSuper_id(super_id);
			type.setPermissions(permissions);
			type.setIncome_name(name);
		int add_success=dao.insertIncome(type);	
		
		map.put("add_success",add_success);
		
		//转换成JSON字符串
		String json=JSON.toJSONString(map);
		out.print(json);
		out.flush();
		out.close();
	}
	/**
	 * 	作用:新增支出类别
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void insert_expend_type(HttpServletRequest request,HttpServletResponse response)throws Exception{
		response.setContentType(head);
		out=response.getWriter();
		map=new HashMap<String, Object>();

		//获取类别id
		int type_id=Integer.parseInt(request.getParameter("type_id"));
		//获取是否是默认类别
		int isDefault=Integer.parseInt(request.getParameter("is_default"));//0不是, 1是
		//获取上级id
		int super_id=Integer.parseInt(request.getParameter("super_id"));//0 没有, 0以外 上级id
		//获取权限
		int permissions=Integer.parseInt(request.getParameter("permissions"));//1 系统默认权限, 2 自定义的权限
		//获取名字
		String name=request.getParameter("expend_name");
		//数据层操作
		MangerTypeDao dao=new MangerTypeDaoImpl(config);
		ExpendType type=new ExpendType();
			type.setId(type_id);
			type.setIs_default(isDefault);
			type.setSuper_id(super_id);
			type.setPermissions(permissions);
			type.setExpend_name(name);
			
		int add_success=dao.insertExpend(type);	
	
		map.put("add_success",add_success);
	
		//转换成JSON字符串
		String json=JSON.toJSONString(map);
		out.print(json);
		out.flush();
		out.close();
		
		
	}
	
	/**
	 * 	作用:更新收入类别信息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void update_income_type(HttpServletRequest request,HttpServletResponse response)throws Exception{
		response.setContentType(head);
		out=response.getWriter();
		map=new HashMap<String, Object>();
		//获取类别id
		int type_id=Integer.parseInt(request.getParameter("type_id"));
		//获取图片id
		int pngId=Integer.parseInt(request.getParameter("pngId"));
		//获取是否是默认类别
		int isDefault=Integer.parseInt(request.getParameter("is_default"));
		String name=null;//类别名字
		if(isDefault!=1) {
			//获取类别名字
			name=request.getParameter("income_name");
			
		}
		
		//数据层操作
		MangerTypeDao dao=new MangerTypeDaoImpl(config);
		IncomeType type=new IncomeType();
		
		type.setId(type_id);
		type.setIncome_pngId(pngId);
		if(isDefault!=1)type.setIncome_name(name);
		
		int row=dao.updateIncome(type);
		map.put("update_success",row );
		//转换成JSON字符串
		String json=JSON.toJSONString(map);
		out.print(json);
		out.flush();
		out.close();
	}


	/**
	 *	作用:更新支出类别信息 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void update_expend_type(HttpServletRequest request,HttpServletResponse response)throws Exception{
		response.setContentType(head);
		out=response.getWriter();
		map=new HashMap<String, Object>();
		//获取支出类别id
		int type_id=Integer.parseInt(request.getParameter("type_id"));
		//获取图片id
		int pngId=Integer.parseInt(request.getParameter("pngId"));
		//获取是否是默认类别
		int isDefault=Integer.parseInt(request.getParameter("is_default"));
		String name=null;//类别名字
		if(isDefault!=1) {
			//获取类别名字
			name=request.getParameter("expend_name");
			
		}
		
		//数据层操作
		MangerTypeDao dao=new MangerTypeDaoImpl(config);
		
		ExpendType type=new ExpendType();
		
		type.setId(type_id);
		type.setExpend_pngId(pngId);
		if(isDefault!=1)type.setExpend_name(name);
		
		int row=dao.updateExpend(type);
		map.put("update_success",row );
		//转换成JSON字符串
		String json=JSON.toJSONString(map);
		out.print(json);
		out.flush();
		out.close();
	}
	
	/**
	 * 作用:删除一条收入类型
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void del_income_type(HttpServletRequest request,HttpServletResponse response)throws Exception {
		response.setContentType(head);
		out=response.getWriter();
		map=new HashMap<String, Object>();
		int row=0;
		
		//获取类别id
		int type_id=Integer.parseInt(request.getParameter("type_id"));
	
		//数据层操作
		MangerTypeDao dao=new MangerTypeDaoImpl(config);
		row=dao.delIncome(type_id);
		map.put("del_success",row);
		
		//转换成JSON字符串
		String json=JSON.toJSONString(map);
		out.print(json);
		out.flush();
		out.close();
		
	}
	/**
	 *  作用:删除一条支出类型
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void del_expend_type(HttpServletRequest request,HttpServletResponse response)throws Exception {
		response.setContentType(head);
		out=response.getWriter();
		map=new HashMap<String, Object>();
		int row=0;
		//获取类别id
		int type_id=Integer.parseInt(request.getParameter("type_id"));

		//数据层操作
		MangerTypeDao dao=new MangerTypeDaoImpl(config);
		row=dao.delExpend(type_id);
		
		map.put("del_success",row);
		
		//转换成JSON字符串
		String json=JSON.toJSONString(map);
		out.print(json);
		out.flush();
		out.close();
		
	}
}
