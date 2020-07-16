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
		//��ȡ�����ʶ��
		String methodName=request.getParameter("method");
		//��ȡָ������ֽ������,thisָ�̳�BaseServlet����
		Class<?extends BaseServlet> clazz=this.getClass();
		//ͨ������ֽ�������ȡ�������ֽ������

		try {
			//ͨ�������ȡ��ǰ��������ָ���ķ���
			Method method =clazz.getMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
			//ִ�з��䷽��
			String result=(String) method.invoke(this, request,response);
		
			/*
			 *  trim() ɾ����ԭʼ�ַ���ͷ����β���Ŀո�
			 * 	�û����ص��ַ���Ϊnull����������
			**/
			if(result==null ||result.trim().isEmpty())
				return ;
			/*
			 * �鿴���ص��ַ������Ƿ������ð��,û�б�ʾת��
			 *  ����У�ʹ��ð�ŷָ��ַ��� �õ�ǰ׺�ͺ�׺ 
			 *  ǰ׺ "f" ��ʾת��, "r"��ʾ�ض��� 
			 *  ��׺ת��·��
			 */
			
			if(result.contains(":")) {
				int index=result.indexOf(":");//��ȡð��λ��
				String preStr=result.substring(0,index);//��ȡǰ׺�ַ���
				String sufPath=result.substring(index+1);
				if(preStr.equalsIgnoreCase("r"))//�ض���
					response.sendRedirect(request.getContextPath()+sufPath);
				else if(preStr.equalsIgnoreCase("f"))//ת��
					request.getRequestDispatcher(sufPath).forward(request, response);
				else throw new RuntimeException("ָ������"+preStr+"��ǰ�汾��֧��");
			}else {//ûð��Ĭ��ת��
				request.getRequestDispatcher(result).forward(request, response);
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
		
		}
	}

}
