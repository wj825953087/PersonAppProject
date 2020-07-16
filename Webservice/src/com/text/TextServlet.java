package com.text;

import com.alibaba.fastjson.JSON;
import com.jizhang.servlet.BaseServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TextServlet
 */
@WebServlet("/TextServlet")
public class TextServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private static final String config = "sqlconfig.xml";
    /**
     * @see BaseServlet#BaseServlet()
     */
    public TextServlet() {
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
	public String text(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String t=request.getParameter("str");
		String keyword=request.getParameter("keyword");
		DataConn conn=new DataConn(config);
		List<News> list=conn.query(t,keyword);
		
		//DBTool tool=new DBTool();
		//List<News> list=tool.query(t);
		
		
		Map<String, Object> map=new HashMap<String, Object>();
		PrintWriter out=response.getWriter();
		map.put("list", list);
		String str=JSON.toJSONString(map);
		out.print(str);
		out.flush();
		out.close();
		return "r:/index.jsp";
	}

}
