package com.jizhang.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * 
 * @ClassName:ReadConfig
 * @Description: ∂¡»°sqlconfig.xml
 * @author Administrator
 *
 */
public class ReadConfig {
	
	
	public SqlSession loadConfigFile(String config_xml) throws IOException {
		SqlSession session=null;
		try {
			InputStream io=Resources.getResourceAsStream(config_xml);
			SqlSessionFactory factory=new SqlSessionFactoryBuilder().build(io);
			session=factory.openSession();
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}
	

		return session;
	
	}
}
