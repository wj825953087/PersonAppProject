package com.jizhang.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BaseServlet
 */
@WebServlet("/BaseServlet")
public abstract class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public BaseServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		service(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		//获取请求标识符
		String methodName=request.getParameter("method");
		//获取指定类的字节码对象,this指继承BaseServlet对象
		Class<?extends BaseServlet> clazz=this.getClass();
		//通过类的字节码对象获取方法的字节码对象

		try {
			//通过反射获取当前运行类中指定的方法
			Method method =clazz.getMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
			//执行反射方法
			String result=(String) method.invoke(this, request,response);
		
			/*
			 *  trim() 删除了原始字符串头部和尾部的空格
			 * 	用户返回的字符串为null，不做操作
			**/
			if(result==null ||result.trim().isEmpty())
				return ;
			/*
			 * 查看返回的字符串中是否包含有冒号,没有表示转发
			 *  如果有，使用冒号分割字符串 得到前缀和后缀 
			 *  前缀 "f" 表示转发, "r"表示重定向 
			 *  后缀转发路径
			 */
			
			if(result.contains(":")) {
				int index=result.indexOf(":");//获取冒号位置
				String preStr=result.substring(0,index);//截取前缀字符串
				String sufPath=result.substring(index+1);
				if(preStr.equalsIgnoreCase("r"))//重定向
					response.sendRedirect(request.getContextPath()+sufPath);
				else if(preStr.equalsIgnoreCase("f"))//转发
					request.getRequestDispatcher(sufPath).forward(request, response);
				else throw new RuntimeException("指定操作"+preStr+"当前版本不支持");
			}else {//没冒号默认转发
				request.getRequestDispatcher(result).forward(request, response);
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
		
		}
	}

}
