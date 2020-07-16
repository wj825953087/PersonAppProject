package com.jizhang.dao.impl;

import java.io.IOException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.jizhang.dao.UserDao;
import com.jizhang.entity.user.User;
import com.jizhang.util.ReadConfig;

/**
 * �û�����ӿ�ʵ��
 * @author Administrator
 *
 */
public class UserDaoImpl  implements UserDao{
	private ReadConfig readconfig;
	private String config_xml;//�����ļ�
	private User user=null;//�û�ʵ����
	private List<User> list=null;//�û�����
    public  UserDaoImpl(String config_xml) {
		// TODO Auto-generated constructor stub
    	this.config_xml=config_xml;
    	this.readconfig=new ReadConfig();
	}
    /**
     * �����û�id��ѯ�û���Ϣ
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
	 * �����û�����ѯ
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
	
	//��ѯ�û����Ƿ��ظ�
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
				//�û����ظ�
				flag=1;
			}
			session.close();
		}
		
		return flag;
	}

	/**
	 * ��������ѯ
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
	 * ע��
	 * @return 
	 */
	@Override
	public int insertUser(User user) throws Exception {
		// TODO Auto-generated method stub
		int is_success=0;//�Ƿ�ע��ɹ�
		SqlSession session=readconfig.loadConfigFile(config_xml);
		try {
			is_success=session.insert("com.jizhang.entity.user.User.insertUser", user);
			session.commit();//�ύ����
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}
		
		session.close();//�ر���Դ
		return is_success;
	}
	/**
	 * �޸��û���Ϣ
	 */
	@Override
	public void updateUser(User user) throws Exception {
		// TODO Auto-generated method stub
		
	}
	/**
	 * �û�ע��
	 */
	@Override
	public void deleteUser(int id) throws Exception {
		// TODO Auto-generated method stub
		
	}
	//�����û�����ѯ
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
