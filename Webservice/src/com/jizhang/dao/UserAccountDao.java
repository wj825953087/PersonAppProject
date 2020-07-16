package com.jizhang.dao;

import com.jizhang.entity.user.UserAccount;

public interface UserAccountDao {
	/**
	 *	统计某用户的账户个数
	 * @param user_id
	 * @return
	 * @throws Exception
	 */
	public int count_userAccount(int user_id)throws Exception;
	/**
	 * 新增用户账户信息
	 * @param account
	 * @return
	 * @throws Exception
	 */
	public int insert_userAccount(UserAccount  account)throws Exception;
	/**
	 * 	根据账户id和用户id删除账户
	 * @param id
	 * @param user_id
	 * @return
	 * @throws Exception
	 */
	public int del_userAccount(int id,int user_id)throws Exception;
	
}
