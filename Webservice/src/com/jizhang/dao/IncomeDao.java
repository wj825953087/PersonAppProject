package com.jizhang.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jizhang.entity.income.Income;

public interface IncomeDao {
	/**
	 *	 ��������ʾ�����������б�
	 * @param user_id �û�id 
	 * @return
	 * @throws Exception
	 */
	public List<Income> show_IncomeItem(int user_id)throws Exception;
	/**
	 * 	��ʾ�û�ĳ��ʱ���ڵ������б�
	 * @param map ����
	 * @return
	 * @throws Exception
	 */
	public List<Income> show_IncomeItem_time(Map<String, Object> map)throws Exception;
	//public List<Income> show_IncomeItem_day(Map<String, Object> map)throws Exception;//��ʾ�û�ĳ���������Ϣ
	/**
	 * 	������ͳ��������֮��
	 * @param user_id �û�id
	 * @return
	 * @throws Exception
	 */
	public double income_money_count(int user_id)throws Exception;
	/**
	 *	 ͳ��ĳ��ʱ���ڵ�������֮��
	 * @param map ����
	 * @return
	 * @throws Exception
	 */
	public double income_money_count_time(Map<String, Object> map)throws Exception;
	/**
	 * 	��¼һ�������˵�
	 * @param income
	 * @return
	 * @throws Exception
	 */
	public int income_insert(Income income) throws Exception;
	/**
	 * 	����һ�������˵�
	 * @param in_id
	 * @return
	 * @throws Exception
	 */
	public int income_update(Income income)throws Exception;

	/**
	 * 	�����˵�idɾ���˵�
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int income_delete(Map<String,Object> map)throws Exception;
	/**
	 *	���������˵�id��ѯ�˵�
	 * @param id �����˵�id
	 * @return
	 * @throws Exception
	 */
	public Income getIncomeById(String id) throws Exception;
	/**
	 * 	ͳ���û���������Ľ��֮��
	 * 	����:ȫ��ʱ��
	 * @param in_type
	 * @param user_id
	 * @return
	 * @throws Exception
	 */
	public double income_type_money_count(int in_type,int user_id)throws Exception;
	/**
	 * 	ͳ���û���������Ľ��֮��
	 * 	����:ĳһʱ���
	 * @param ex_type
	 * @param user_id
	 * @return
	 * @throws Exception
	 */
	public double income_type_money_count_time(int in_type,int user_id,Date start_time,Date end_time)throws Exception;
}
