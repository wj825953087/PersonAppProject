package com.jizhang.dao;

import com.jizhang.entity.user.UserAccount;

public interface UserAccountDao {
	/**
	 *	ͳ��ĳ�û����˻�����
	 * @param user_id
	 * @return
	 * @throws Exception
	 */
	public int count_userAccount(int user_id)throws Exception;
	/**
	 * �����û��˻���Ϣ
	 * @param account
	 * @return
	 * @throws Exception
	 */
	public int insert_userAccount(UserAccount  account)throws Exception;
	/**
	 * 	�����˻�id���û�idɾ���˻�
	 * @param id
	 * @param user_id
	 * @return
	 * @throws Exception
	 */
	public int del_userAccount(int id,int user_id)throws Exception;
	
}
