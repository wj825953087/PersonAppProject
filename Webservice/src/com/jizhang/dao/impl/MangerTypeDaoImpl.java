package com.jizhang.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.jizhang.dao.MangerTypeDao;
import com.jizhang.entity.expend.ExpendType;
import com.jizhang.entity.income.IncomeType;
import com.jizhang.util.ReadConfig;

/**
 * 	������ӿ�ʵ��
 * @author administrator
 *
 */
public class MangerTypeDaoImpl implements MangerTypeDao{
	private ReadConfig readconfig;
	private String config_xml;//�����ļ�
	private IncomeType incomeType=null;
	private ExpendType expendType=null;
	private List<IncomeType> incomeTypes=null;//������𼯺�
	private List<ExpendType> expendTypes=null;//֧����𼯺�
	
	public MangerTypeDaoImpl(String config_xml) {

		this.readconfig =new ReadConfig();
		this.config_xml = config_xml;
	}
	//��ѯ
	@Override
	public List<IncomeType> showIncome() throws Exception {
		// TODO Auto-generated method stub
		SqlSession session=readconfig.loadConfigFile(config_xml);
		try {
			incomeTypes=session.selectList("com.jizhang.entity.income.IncomeType.showIncome");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}
		session.close();
		return incomeTypes;
	}

	//��ѯ
	@Override
	public List<ExpendType> showExpend() throws Exception {
		// TODO Auto-generated method stub
		SqlSession session=readconfig.loadConfigFile(config_xml);
		try {
			expendTypes=session.selectList("com.jizhang.entity.expend.ExpendType.showExpend");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}
		session.close();
		return expendTypes;

	}
	//����
	@Override
	public int updateIncome(IncomeType type) throws Exception {
		// TODO Auto-generated method stub
		int row=0;
		SqlSession session=readconfig.loadConfigFile(config_xml);
		try {
			row=session.update("com.jizhang.entity.income.IncomeType.updateIncome", type);
			session.commit();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}finally {
			session.close();
		}
		return row;
	}
	//����
	@Override
	public int updateExpend(ExpendType type) throws Exception {
		// TODO Auto-generated method stub
		int row=0;
		SqlSession session=readconfig.loadConfigFile(config_xml);
		try {
			row=session.update("com.jizhang.entity.expend.ExpendType.updateExpend", type);
			session.commit();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}finally {
			session.close();
		}
		return row;
	}
	//��������ɾ��
	@Override
	public int delIncome(int type_id) throws Exception {
		// TODO Auto-generated method stub
		int row=0;
		SqlSession session=readconfig.loadConfigFile(config_xml);
		try {
			row=session.delete("com.jizhang.entity.income.IncomeType.delIncome", type_id);
			session.commit();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}finally {
			session.close();
		}
		return row;
	}
	//֧������ɾ��
	@Override
	public int delExpend(int type_id) throws Exception {
		// TODO Auto-generated method stub
		int row=0;
		SqlSession session=readconfig.loadConfigFile(config_xml);
		try {
			row=session.delete("com.jizhang.entity.expend.ExpendType.delExpend", type_id);
			session.commit();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}finally {
			session.close();
		}
		return row;
	}
	//������������
	@Override
	public int insertIncome(IncomeType type) throws Exception {
		// TODO Auto-generated method stub
		int row=0;
		SqlSession session=readconfig.loadConfigFile(config_xml);
		try {
			row=session.insert("com.jizhang.entity.income.IncomeType.insertIncome", type);
			session.commit();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}finally {
			session.close();
		}
		return row;
	}
	//����֧������
	@Override
	public int insertExpend(ExpendType type) throws Exception {
		// TODO Auto-generated method stub
		int row=0;
		SqlSession session=readconfig.loadConfigFile(config_xml);
		try {
			row=session.insert("com.jizhang.entity.expend.ExpendType.insertExpend", type);
			session.commit();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}finally {
			session.close();
		}
		return row;
	}

	

}
