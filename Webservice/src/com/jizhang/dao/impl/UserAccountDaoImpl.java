package com.jizhang.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.jizhang.dao.UserAccountDao;
import com.jizhang.entity.user.UserAccount;
import com.jizhang.util.ReadConfig;

public class UserAccountDaoImpl implements UserAccountDao {
	private ReadConfig readconfig;
	private String config_xml;//配置文件
	
	//构造函数
	public UserAccountDaoImpl(String config_xml) {
		super();
		this.config_xml=config_xml;
    	this.readconfig=new ReadConfig();
	}
	//插入
	@Override	
	public int insert_userAccount(UserAccount account) throws Exception {
		// TODO Auto-generated method stub
		int row=0;
		SqlSession session=readconfig.loadConfigFile(config_xml);
		
		try {
			row=session.insert("com.jizhang.entity.user.UserAccount.insert_userAccount",account);
			session.commit();
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		} finally {
			// TODO: handle finally clause
			session.close();
		}
		return row;
	}
	//删除账户
	@Override
	public int del_userAccount(int id,int user_id) throws Exception {
		// TODO Auto-generated method stub
		int row=0;
		SqlSession session=readconfig.loadConfigFile(config_xml);
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("id", id);
		map.put("user_id", user_id);
		try {
			row=session.delete("com.jizhang.entity.user.UserAccount.del_userAccount", map);
			session.commit();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}finally {
			session.close();
		}
		return row;
	}
	//统计
	@Override
	public int count_userAccount(int user_id) throws Exception {
		// TODO Auto-generated method stub
		int count=0;
		SqlSession sqlSession=readconfig.loadConfigFile(config_xml);
		try {
			count=sqlSession.selectOne("com.jizhang.entity.user.UserAccount.count_userAccount", user_id);
		
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}finally {
			sqlSession.close();
		}
		return count;
	}

}
