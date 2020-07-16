package com.jizhang.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jizhang.entity.expend.Expend;
import com.jizhang.entity.income.Income;

public interface ExpendDao {
	/**
	 * 	无条件显示用户所有的支出金额信息
	 * @param user_id 用户id 
	 * @return
	 * @throws Exception
	 */
	public List<Expend> show_ExpendItem(int user_id)throws Exception;
	/**
	 * 	显示用户某段时间内的收入列表
	 * @param map 条件
	 * @return
	 * @throws Exception
	 */
	public List<Expend> show_ExpendItem_time(Map<String, Object> map)throws Exception;

	/**
	 * 	无条件统计收入金额之和
	 * @param user_id 用户id
	 * @return
	 * @throws Exception
	 */
	public double expend_money_count(int user_id)throws Exception;
	/**
	 * 	统计某段时间内的收入金额之和
	 * @param map 条件
	 * @return
	 * @throws Exception
	 */
	public double expend_money_count_time(Map<String, Object> map)throws Exception;
	
	/**
	 * 	记录一条收入账单
	 * @param income
	 * @return
	 * @throws Exception
	 */
	public int expend_insert(Expend expend) throws Exception;
	/**
	 * 	更新一条支出账单
	 * @param in_id
	 * @return
	 * @throws Exception
	 */
	public int expend_update(Expend expend)throws Exception;
	/**
	 * 	根据账单id删除账单
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int expend_delete(Map<String,Object> map)throws Exception;
	/**
	 * 	根据支出账单id查询账单
	 * @param id 支出账单id
	 * @return
	 * @throws Exception
	 */
	public Expend getExpendById(String id) throws Exception;
	/**
	 * 	统计用户各种消费的金额之和
	 * 	条件:全部时间
	 * @param ex_type
	 * @param user_id
	 * @return
	 * @throws Exception
	 */
	public double expend_type_money_count(int ex_type,int user_id) throws Exception;
	/**
	 * 	统计用户各种消费的金额之和
	 * 	条件:某一时间段
	 * @param ex_type
	 * @param user_id
	 * @return
	 * @throws Exception
	 */
	public double expend_type_money_count_time(int ex_type,int user_id,Date start_time,Date end_time) throws Exception;
}
