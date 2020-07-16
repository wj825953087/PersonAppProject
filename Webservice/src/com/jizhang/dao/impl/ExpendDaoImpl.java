package com.jizhang.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import com.jizhang.dao.ExpendDao;
import com.jizhang.entity.expend.Expend;
import com.jizhang.util.ReadConfig;

public class ExpendDaoImpl implements ExpendDao {
	private ReadConfig readconfig;//��ʼ������
	private String config_xml;//�����ļ�
	private double expend_money_sum=0;//֧�����֮��
	private Expend expend=null;//֧��ʵ����
	private List<Expend> list=null;
	
	public ExpendDaoImpl(String config_xml) {
		super();
		this.config_xml=config_xml;
		this.readconfig=new ReadConfig();
	}
	//��ʾ����ʱ���֧����Ϣ
	@Override
	public List<Expend> show_ExpendItem(int user_id) throws Exception {
		// TODO Auto-generated method stub
		SqlSession session=readconfig.loadConfigFile(config_xml);//��������
		try {
			list=session.selectList("com.jizhang.entity.expend.show_ExpendItem", user_id);
			session.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}
		return list;
	}
	//��ʾĳ��ʱ���ڵ�֧����Ϣ
	@Override
	public List<Expend> show_ExpendItem_time(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		SqlSession session=readconfig.loadConfigFile(config_xml);//��������
		try {
			list=session.selectList("com.jizhang.entity.expend.show_ExpendItem_time", map);
			session.close();	
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}
		return list;
	}
	//ͳ������ʱ���֧�����֮��
	@Override
	public double expend_money_count(int user_id) throws Exception {
		// TODO Auto-generated method stub
		//��������
		SqlSession session=readconfig.loadConfigFile(config_xml);
		try {
			expend_money_sum=session.selectOne("com.jizhang.entity.expend.expend_money_count", user_id);
			session.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}
		return expend_money_sum;
	}
	//ͳ��ĳ��ʱ��֧�����֮��
	@Override
	public double expend_money_count_time(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		//��������
		SqlSession session=readconfig.loadConfigFile(config_xml);
		try {
			expend_money_sum=session.selectOne("com.jizhang.entity.expend.expend_money_count_time", map);
			session.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}
		return expend_money_sum;
	}
	@Override
	public int expend_insert(Expend expend) throws Exception {
		int row=0;
		try {
			SqlSession sqlSession=readconfig.loadConfigFile(config_xml);
			row=sqlSession.insert("com.jizhang.entity.expend.expend_insert", expend);
			sqlSession.commit();//�ύ����
			sqlSession.close();//�ر���Դ
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}
		return row;
	}
	@Override
	public int expend_update(Expend expend) throws Exception {
		// TODO Auto-generated method stub
		int row=0;
		try {
			SqlSession sqlSession=readconfig.loadConfigFile(config_xml);
			row=sqlSession.update("com.jizhang.entity.expend.expend_update", expend);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}
		return row;
	}
	@Override
	public Expend getExpendById(String id) throws Exception {
		// TODO Auto-generated method stub
		try {
			SqlSession session=readconfig.loadConfigFile(config_xml);
			expend=session.selectOne("com.jizhang.entity.expend.getExpendById", id);
			session.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
			
		}
		return expend;
	}

	@Override
	public int expend_delete(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		int row=0;
		SqlSession session=null;
		try {
			session=readconfig.loadConfigFile(config_xml);
			row=session.delete("com.jizhang.entity.expend.expend_delete", map);
			session.commit();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}finally {
			session.close();
		}
		return row;
	}
	
	@Override
	public double expend_type_money_count(int ex_type, int user_id) throws Exception {
		// TODO Auto-generated method stub
		double sum=0;
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("user_id", user_id);
		map.put("ex_type", ex_type);
		try {
			SqlSession session=readconfig.loadConfigFile(config_xml);
			sum=session.selectOne("com.jizhang.entity.expend.expend_type_money_count", map);
			session.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}
		return sum;
	}
	@Override
	public double expend_type_money_count_time(int ex_type, int user_id,Date start_time,Date end_time) throws Exception {
		// TODO Auto-generated method stub
		double sum=0;
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("user_id", user_id);
		map.put("ex_type", ex_type);
		map.put("start_time", start_time);
		map.put("end_time", end_time);
		
		try {
			SqlSession session=readconfig.loadConfigFile(config_xml);
			sum=session.selectOne("com.jizhang.entity.expend.expend_type_money_count_time", map);
			session.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}
		return sum;
	}

}
