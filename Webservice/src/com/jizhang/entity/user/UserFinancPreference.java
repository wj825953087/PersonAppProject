package com.jizhang.entity.user;
/**
 * �û����ƫ�ñ�
 * @author administrator
 *
 */
public class UserFinancPreference {
	private int fp_id;//Id
	private int user_id;//�û�id
	private int fp_family_structure;//��ͥ�ṹ
	/**
	 * 0����
	 * 1���޾������룬����Ů
	 * 2���޾������룬����Ů
	 * 3ֻ��һ�������룬����Ů
	 * 4ֻ��һ�������룬����Ů
	 */


	private int fp_annual_investment;//��Ͷ�ʶ�
	/**
	 * 0 :5������
	 * 1 :5��10��
	 * 2 :10��30��
	 * 3 :30������
	 */

	private int fp_earnings;//Ԥ������
	




	private String fp_investment_name;//Ͷ�����
	/**
	 * 0:
	 * 1:
	 * 2:
	 * 3:
	 * 4:
	 */





	public int getFp_id() {
		return fp_id;
	}





	public int getUser_id() {
		return user_id;
	}





	public int getFp_family_structure() {
		return fp_family_structure;
	}





	public int getFp_annual_investment() {
		return fp_annual_investment;
	}





	public int getFp_earnings() {
		return fp_earnings;
	}





	public String getFp_investment_name() {
		return fp_investment_name;
	}





	public void setFp_id(int fp_id) {
		this.fp_id = fp_id;
	}





	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}





	public void setFp_family_structure(int fp_family_structure) {
		this.fp_family_structure = fp_family_structure;
	}





	public void setFp_annual_investment(int fp_annual_investment) {
		this.fp_annual_investment = fp_annual_investment;
	}





	public void setFp_earnings(int fp_earnings) {
		this.fp_earnings = fp_earnings;
	}





	public void setFp_investment_name(String fp_investment_name) {
		this.fp_investment_name = fp_investment_name;
	}





	@Override
	public String toString() {
		return "UserFinancPreference [fp_id=" + fp_id + ", user_id=" + user_id + ", fp_family_structure="
				+ fp_family_structure + ", fp_annual_investment=" + fp_annual_investment + ", fp_earnings="
				+ fp_earnings + ", fp_investment_name=" + fp_investment_name + "]";
	}
	




}
