package com.jizhang.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jizhang.entity.income.Income;

public interface IncomeDao {
	/**
	 *	 无条件显示所有收入金额列表
	 * @param user_id 用户id 
	 * @return
	 * @throws Exception
	 */
	public List<Income> show_IncomeItem(int user_id)throws Exception;
	/**
	 * 	显示用户某段时间内的收入列表
	 * @param map 条件
	 * @return
	 * @throws Exception
	 */
	public List<Income> show_IncomeItem_time(Map<String, Object> map)throws Exception;
	//public List<Income> show_IncomeItem_day(Map<String, Object> map)throws Exception;//显示用户某天的收入信息
	/**
	 * 	无条件统计收入金额之和
	 * @param user_id 用户id
	 * @return
	 * @throws Exception
	 */
	public double income_money_count(int user_id)throws Exception;
	/**
	 *	 统计某段时间内的收入金额之和
	 * @param map 条件
	 * @return
	 * @throws Exception
	 */
	public double income_money_count_time(Map<String, Object> map)throws Exception;
	/**
	 * 	记录一条收入账单
	 * @param income
	 * @return
	 * @throws Exception
	 */
	public int income_insert(Income income) throws Exception;
	/**
	 * 	更新一条收入账单
	 * @param in_id
	 * @return
	 * @throws Exception
	 */
	public int income_update(Income income)throws Exception;

	/**
	 * 	根据账单id删除账单
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int income_delete(Map<String,Object> map)throws Exception;
	/**
	 *	根据收入账单id查询账单
	 * @param id 收入账单id
	 * @return
	 * @throws Exception
	 */
	public Income getIncomeById(String id) throws Exception;
	/**
	 * 	统计用户各种收入的金额之和
	 * 	条件:全部时间
	 * @param in_type
	 * @param user_id
	 * @return
	 * @throws Exception
	 */
	public double income_type_money_count(int in_type,int user_id)throws Exception;
	/**
	 * 	统计用户各种收入的金额之和
	 * 	条件:某一时间段
	 * @param ex_type
	 * @param user_id
	 * @return
	 * @throws Exception
	 */
	public double income_type_money_count_time(int in_type,int user_id,Date start_time,Date end_time)throws Exception;
}
