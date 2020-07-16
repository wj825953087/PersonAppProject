package com.text;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



/**
 * 原生JDBC
 * @author administrator
 *
 */
public class DBTool {

	private Connection conn=null;
	private Statement stm=null;
	private ResultSet rs=null;
	private PreparedStatement ps=null;//占位符
	private List<News> list=new ArrayList<News>();
	private static final String GET_DATEBASE="jdbc:mysql://111.229.180.123:3306/Demo?characterEncoding=UTF-8";
	private static final String NAME="root";
	private static final String PASSWORD="wujian2639898!@#$";
	private static final String CONN="conn";
	private static final String STM="stm";
	private static final String RS="rs";
	private static final String PS="ps";
	/**
	 * get
	 * 
	 */
	public Connection getConn() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn=DriverManager.getConnection(GET_DATEBASE, NAME,PASSWORD);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	public Statement getStm() {
		try {
			stm=conn.createStatement();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stm;
	}

	public PreparedStatement getPs(String sql) {
		try {
			ps=conn.prepareStatement(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ps;
	}
	public ResultSet getRs(String sql) {
		try {
			rs=stm.executeQuery(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	/**
	 * set
	 * 
	 */
	public void setConn(Connection conn) {
		this.conn = conn;
	}
	public void setStm(Statement stm) {
		this.stm = stm;
	}
	public void setRs(ResultSet rs) {
		this.rs = rs;
	}
	public void setPs(PreparedStatement ps) {
		this.ps = ps;
	}
	//List
	
	public  List<News> query(String title)throws Exception{
		
		try {
			//concat(concat('%',?,'%'))
			//String sql="select *from news where title like concat('%',?,'%') limit ?,?" ;
			String sql="select *from news where id="+title;
		
			conn=getConn();
		
			ps=getPs(sql);
			//ps.setString(1, title);
     		System.out.println(ps.toString());
//			//ps.setInt(2, 0);
//			//ps.setInt(3, 5);
     		ps.executeUpdate();
			rs=ps.executeQuery();
			

			while (rs.next()) {
				News news=new News();
				news.setId(rs.getInt("id"));
				news.setTitle(rs.getString("title"));
				System.out.println(rs.toString());
				list.add(news);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
			System.out.println("list"+" :"+list);
			System.out.println(CONN+" :"+conn);
			System.out.println(PS+" :"+ps);
			System.out.println(RS+" :"+rs);
		}finally {
			close();
		}
		return list;
	}
	
	//关闭资源
		public void close() {
			try {
				
				if(conn!=null)conn.close();
				if(stm!=null)stm.close();
				if(rs!=null)rs.close();
				if(ps!=null)ps.close();
			}catch(Exception e) {
				
			}

		}

}
