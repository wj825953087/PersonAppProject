package com.text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.jizhang.util.ReadConfig;

public class DataConn {
	private ReadConfig readconfig;//初始化配置
	private String config_xml;//配置文件
	private List<News> list=null;
	public DataConn(String config_xml) {
		super();
		this.config_xml=config_xml;
		this.readconfig=new ReadConfig();
	}
	public List<News> query(String title,String keyword)throws Exception{
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("title", title);
		map.put("keyword", keyword);
		
		SqlSession session=readconfig.loadConfigFile(config_xml);
		try {
			list=session.selectList("com.text.query",map);
			//list=session.selectList("com.text.query2");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}finally {
			session.close();
		}
		return list;
		
	}
}
