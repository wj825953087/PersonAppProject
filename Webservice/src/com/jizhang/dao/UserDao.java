package com.jizhang.dao;

import java.io.IOException;
import java.util.List;

import com.jizhang.entity.user.User;
/**
 * �û�����ӿ�
 * @author Administrator
 *
 */
public interface UserDao {
	/**
	 * �����û��������û�id
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public int getUserIdByUsername(String username) throws Exception;
	
	/**
	 * 
	 * @Description: �����û�id��ѯһ���û�
	 * @Time:2020-3-27
	 * @param id �û�id
	 * @return ���ز�ѯ�����û���Ϣ
	 * @throws IOException
	 */
	public User getUserById(int id) throws IOException;

	/**
	 * 
	 * @Description: �����û���name��ѯһ���û�
	 * @Time:2020-3-27
	 * @param name �û���
	 * @return  ���ز�ѯ�����û���Ϣ
	 * @throws IOException
	 */
	public User getUserByName(String name) throws IOException;
	/**
	 * 	���û����Ƿ��ظ�
	 * 	0 �����ظ� ,1�����ظ�
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public int query_repeat(String username) throws Exception; 
	
	/**
	 * 
	 * @Description: ��ѯ�����û�����Ϣ(��������ѯ)
	 * @Time:2020-3-27  
	 * @return ���������û��ļ���list
	 * @throws Exception
	 */
	public List<User> getUser()throws Exception;//�����û���������Ϣ
	/**
	 * 
	 * @Description: �û�ע��
	 * @Time:2020-3-27
	 * @param user �����û�
	 * @return 
	 * @throws Exception
	 */
	public int  insertUser(User user)throws Exception;//����һ���û�
	/**
	 * 
	 * @Description: �޸��û���Ϣ
	 * @Time:2020-3-27
	 * @param user Ҫ�޸ĵ��û�
	 * @throws Exception
	 */
	public void  updateUser(User user)throws Exception;//�޸�һ���û�
	/**
	 * 
	 * @Description: ע��
	 * @Time:2019-11-30  ����8:27:07
	 * @param id �û�id
	 * @throws Exception
	 */
	public void  deleteUser(int id)throws Exception;//ɾ��һ���û�
}
