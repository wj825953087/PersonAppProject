package com.jizhang.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;

import com.jizhang.dao.IncomeDao;
import com.jizhang.entity.income.Income;

import com.jizhang.util.ReadConfig;

public class IncomeDaoImpl implements IncomeDao{
	private ReadConfig readconfig;//初始化配置
	private String config_xml;//配置文件
	private double income_money_sum=0;//收入金额之和
	private Income income=null;//实体类
	private List<Income> list=null;
	public IncomeDaoImpl(String config_xml) { 
		this.config_xml = config_xml;
		this.readconfig = new ReadConfig();
		

	}


	//无条件统计用户收入金额
	@Override
	public double income_money_count(int user_id) throws Exception {
		// TODO Auto-generated method stub
		//加载配置
		SqlSession session=readconfig.loadConfigFile(config_xml);
		try {
			income_money_sum=session.selectOne("com.jizhang.entity.income.income_money_count",user_id);
			session.close();//关闭资源
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}
		return income_money_sum;
	}
	//根据时间条件统计用户收入金额
	@Override
	public double income_money_count_time(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		//加载配置
		SqlSession session=readconfig.loadConfigFile(config_xml);
		try {
			
			
			income_money_sum=session.selectOne("com.jizhang.entity.income.income_money_count_time",map);
			session.close();//关闭资源
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}
		return income_money_sum;
	}


	@Override
	public List<Income> show_IncomeItem(int user_id) throws Exception {
		// TODO Auto-generated method stub
		SqlSession session=readconfig.loadConfigFile(config_xml);
		try {
			list=session.selectList("com.jizhang.entity.income.show_IncomeItem", user_id);
			session.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}
		return list;
	}


	@Override
	public List<Income> show_IncomeItem_time(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		SqlSession session=readconfig.loadConfigFile(config_xml);
		try {
			list=session.selectList("com.jizhang.entity.income.show_IncomeItem_time", map);
			session.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}
		return list;
	}

	
	@Override
	public int income_insert(Income income) throws Exception {
		// TODO Auto-generated method stub
		int row=0;
		try {
			SqlSession sqlSession=readconfig.loadConfigFile(config_xml);

			row=sqlSession.insert("com.jizhang.entity.income.income_insert", income);
		
			sqlSession.commit();//提交事务
			sqlSession.close();//关闭资源
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}
		return row;
	}


	@Override
	public int income_update(Income income) throws Exception {
		// TODO Auto-generated method stub
		int row=0;
		try {
			SqlSession sqlSession=readconfig.loadConfigFile(config_xml);
			row=sqlSession.update("com.jizhang.entity.income.income_update", income);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}
		return row;
	}


	@Override
	public Income getIncomeById(String id) throws Exception {
		// TODO Auto-generated method stub
		try {
			SqlSession  session=readconfig.loadConfigFile(config_xml);
			income=session.selectOne("com.jizhang.entity.income.getIncomeById",id);
			session.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}
		return income;
	}




	@Override
	public int income_delete(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		int row=0;
		SqlSession session=readconfig.loadConfigFile(config_xml);
		try {
			row=session.delete("com.jizhang.entity.income.income_delete",map);
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
	public double income_type_money_count(int in_type, int user_id) throws Exception {
		// TODO Auto-generated method stub
		double sum=0;
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("user_id", user_id);
		map.put("in_type", in_type);
		SqlSession session=readconfig.loadConfigFile(config_xml);
		try {
			
			sum=session.selectOne("com.jizhang.entity.income.income_type_money_count",map);
			
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}finally {
			session.close();
		}
		return sum;
	}


	@Override
	public double income_type_money_count_time(int in_type, int user_id, Date start_time, Date end_time)
			throws Exception {
		// TODO Auto-generated method stub
		double sum=0;
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("user_id", user_id);
		map.put("in_type", in_type);
		map.put("start_time", start_time);
		map.put("end_time", end_time);
		SqlSession session=readconfig.loadConfigFile(config_xml);
		try {
			
			sum=session.selectOne("com.jizhang.entity.income.income_type_money_count_time",map);
			
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}finally {
			session.close();
		}
		return sum;
	}


}
