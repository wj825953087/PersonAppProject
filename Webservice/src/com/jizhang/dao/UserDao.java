package com.jizhang.dao;

import java.io.IOException;
import java.util.List;

import com.jizhang.entity.user.User;
/**
 * 用户管理接口
 * @author Administrator
 *
 */
public interface UserDao {
	/**
	 * 根据用户名返回用户id
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public int getUserIdByUsername(String username) throws Exception;
	
	/**
	 * 
	 * @Description: 根据用户id查询一个用户
	 * @Time:2020-3-27
	 * @param id 用户id
	 * @return 返回查询到的用户信息
	 * @throws IOException
	 */
	public User getUserById(int id) throws IOException;

	/**
	 * 
	 * @Description: 根据用户名name查询一个用户
	 * @Time:2020-3-27
	 * @param name 用户名
	 * @return  返回查询到的用户信息
	 * @throws IOException
	 */
	public User getUserByName(String name) throws IOException;
	/**
	 * 	查用户名是否重复
	 * 	0 代表不重复 ,1代表重复
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public int query_repeat(String username) throws Exception; 
	
	/**
	 * 
	 * @Description: 查询所有用户的信息(无条件查询)
	 * @Time:2020-3-27  
	 * @return 返回所有用户的集合list
	 * @throws Exception
	 */
	public List<User> getUser()throws Exception;//遍历用户表所有信息
	/**
	 * 
	 * @Description: 用户注册
	 * @Time:2020-3-27
	 * @param user 新增用户
	 * @return 
	 * @throws Exception
	 */
	public int  insertUser(User user)throws Exception;//新增一个用户
	/**
	 * 
	 * @Description: 修改用户信息
	 * @Time:2020-3-27
	 * @param user 要修改的用户
	 * @throws Exception
	 */
	public void  updateUser(User user)throws Exception;//修改一个用户
	/**
	 * 
	 * @Description: 注销
	 * @Time:2019-11-30  下午8:27:07
	 * @param id 用户id
	 * @throws Exception
	 */
	public void  deleteUser(int id)throws Exception;//删除一个用户
}
