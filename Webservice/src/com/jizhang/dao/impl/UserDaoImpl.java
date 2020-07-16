package com.jizhang.dao.impl;

import java.io.IOException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.jizhang.dao.UserDao;
import com.jizhang.entity.user.User;
import com.jizhang.util.ReadConfig;

/**
 * 用户管理接口实现
 * @author Administrator
 *
 */
public class UserDaoImpl  implements UserDao{
	private ReadConfig readconfig;
	private String config_xml;//配置文件
	private User user=null;//用户实体类
	private List<User> list=null;//用户集合
    public  UserDaoImpl(String config_xml) {
		// TODO Auto-generated constructor stub
    	this.config_xml=config_xml;
    	this.readconfig=new ReadConfig();
	}
    /**
     * 根据用户id查询用户信息
     */
	@Override
	public User getUserById(int id) throws IOException {
		// TODO Auto-generated method stub
		SqlSession session=readconfig.loadConfigFile(config_xml);
		try {
			user=session.selectOne("com.jizhang.entity.user.User.getUserById",id);
		}catch (Exception e) {
			// TODO: handle exception'
			System.out.println(e.toString());
		}
		session.close();
		return user;
	}
	/**
	 * 根据用户名查询
	 */
	@Override
	public User getUserByName(String name) throws IOException {
		SqlSession session=readconfig.loadConfigFile(config_xml);

		try {
			user=session.selectOne("com.jizhang.entity.user.User.getNameByUser",name);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}
		
		session.close();
		return user;
	}
	
	//查询用户名是否重复
	@Override
	public int query_repeat(String username) throws Exception {
		// TODO Auto-generated method stub
		SqlSession  session=readconfig.loadConfigFile(config_xml);
		int flag=0;
		String Name="";
		try {
			Name=session.selectOne("com.jizhang.entity.user.User.query_repeat", username);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}finally {
			if(Name.equals(username)) {
				//用户名重复
				flag=1;
			}
			session.close();
		}
		
		return flag;
	}

	/**
	 * 无条件查询
	 */
	@Override
	public List<User> getUser() throws Exception {
		// TODO Auto-generated method stub
		SqlSession session=readconfig.loadConfigFile(config_xml);
		try {
			list=session.selectList("com.jizhang.entity.user.User.getUser");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}
		session.close();
		return list;
	}
	
	/**
	 * 注册
	 * @return 
	 */
	@Override
	public int insertUser(User user) throws Exception {
		// TODO Auto-generated method stub
		int is_success=0;//是否注册成功
		SqlSession session=readconfig.loadConfigFile(config_xml);
		try {
			is_success=session.insert("com.jizhang.entity.user.User.insertUser", user);
			session.commit();//提交事务
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}
		
		session.close();//关闭资源
		return is_success;
	}
	/**
	 * 修改用户信息
	 */
	@Override
	public void updateUser(User user) throws Exception {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 用户注销
	 */
	@Override
	public void deleteUser(int id) throws Exception {
		// TODO Auto-generated method stub
		
	}
	//根据用户名查询
	@Override
	public int getUserIdByUsername(String username) throws Exception {
		// TODO Auto-generated method stub
		int user_id=0;
		SqlSession  session=readconfig.loadConfigFile(config_xml);
		try {
			user_id=session.selectOne("com.jizhang.entity.user.User.getUserIdByUsername",username);
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}finally {
			session.close();
		}
		return user_id;
	}


}
