package com.jizhang.dao;

import java.util.List;

import com.jizhang.entity.expend.ExpendType;
import com.jizhang.entity.income.IncomeType;

public interface MangerTypeDao {
	/**
	 * 	显示收入类别
	 * @return
	 * @throws Exception
	 */
	public List<IncomeType> showIncome() throws Exception;
	/**
	 * 	显示支出类别
	 * @return
	 * @throws Exception
	 */
	public List<ExpendType> showExpend() throws Exception;
	
	/**
	 * 	添加收入类型
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public int  insertIncome(IncomeType type)throws Exception;
	/**
	 * 	添加支出类型
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public int  insertExpend(ExpendType type)throws Exception;
	/**
	 * 	收入类型更新
	 * 	
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public int updateIncome(IncomeType type)throws Exception;
	/**
	 * 支出类型更新
	 * @param type
	 * @return
	 * @throws Exception
	 */
	
	public int updateExpend(ExpendType type)throws Exception;
	

	/**
	 * 删除一条收入类型(系统默认的收入类型不给删除)
	 * @param type_id
	 * @return
	 * @throws Exception
	 */
	public int delIncome(int type_id) throws Exception;
	
	/**
	 * 删除一条支出类型(系统默认的支出类型不给删除)
	 * @param type_id
	 * @return
	 * @throws Exception
	 */
	public int delExpend(int type_id)throws Exception;
}
