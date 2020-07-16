package com.jizhang.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jizhang.entity.expend.Expend;
import com.jizhang.entity.income.Income;

public interface ExpendDao {
	/**
	 * 	��������ʾ�û����е�֧�������Ϣ
	 * @param user_id �û�id 
	 * @return
	 * @throws Exception
	 */
	public List<Expend> show_ExpendItem(int user_id)throws Exception;
	/**
	 * 	��ʾ�û�ĳ��ʱ���ڵ������б�
	 * @param map ����
	 * @return
	 * @throws Exception
	 */
	public List<Expend> show_ExpendItem_time(Map<String, Object> map)throws Exception;

	/**
	 * 	������ͳ��������֮��
	 * @param user_id �û�id
	 * @return
	 * @throws Exception
	 */
	public double expend_money_count(int user_id)throws Exception;
	/**
	 * 	ͳ��ĳ��ʱ���ڵ�������֮��
	 * @param map ����
	 * @return
	 * @throws Exception
	 */
	public double expend_money_count_time(Map<String, Object> map)throws Exception;
	
	/**
	 * 	��¼һ�������˵�
	 * @param income
	 * @return
	 * @throws Exception
	 */
	public int expend_insert(Expend expend) throws Exception;
	/**
	 * 	����һ��֧���˵�
	 * @param in_id
	 * @return
	 * @throws Exception
	 */
	public int expend_update(Expend expend)throws Exception;
	/**
	 * 	�����˵�idɾ���˵�
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int expend_delete(Map<String,Object> map)throws Exception;
	/**
	 * 	����֧���˵�id��ѯ�˵�
	 * @param id ֧���˵�id
	 * @return
	 * @throws Exception
	 */
	public Expend getExpendById(String id) throws Exception;
	/**
	 * 	ͳ���û��������ѵĽ��֮��
	 * 	����:ȫ��ʱ��
	 * @param ex_type
	 * @param user_id
	 * @return
	 * @throws Exception
	 */
	public double expend_type_money_count(int ex_type,int user_id) throws Exception;
	/**
	 * 	ͳ���û��������ѵĽ��֮��
	 * 	����:ĳһʱ���
	 * @param ex_type
	 * @param user_id
	 * @return
	 * @throws Exception
	 */
	public double expend_type_money_count_time(int ex_type,int user_id,Date start_time,Date end_time) throws Exception;
}
