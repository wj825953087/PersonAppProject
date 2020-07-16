package com.jizhang.dao;

import java.util.List;

import com.jizhang.entity.expend.ExpendType;
import com.jizhang.entity.income.IncomeType;

public interface MangerTypeDao {
	/**
	 * 	��ʾ�������
	 * @return
	 * @throws Exception
	 */
	public List<IncomeType> showIncome() throws Exception;
	/**
	 * 	��ʾ֧�����
	 * @return
	 * @throws Exception
	 */
	public List<ExpendType> showExpend() throws Exception;
	
	/**
	 * 	�����������
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public int  insertIncome(IncomeType type)throws Exception;
	/**
	 * 	���֧������
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public int  insertExpend(ExpendType type)throws Exception;
	/**
	 * 	�������͸���
	 * 	
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public int updateIncome(IncomeType type)throws Exception;
	/**
	 * ֧�����͸���
	 * @param type
	 * @return
	 * @throws Exception
	 */
	
	public int updateExpend(ExpendType type)throws Exception;
	

	/**
	 * ɾ��һ����������(ϵͳĬ�ϵ��������Ͳ���ɾ��)
	 * @param type_id
	 * @return
	 * @throws Exception
	 */
	public int delIncome(int type_id) throws Exception;
	
	/**
	 * ɾ��һ��֧������(ϵͳĬ�ϵ�֧�����Ͳ���ɾ��)
	 * @param type_id
	 * @return
	 * @throws Exception
	 */
	public int delExpend(int type_id)throws Exception;
}
