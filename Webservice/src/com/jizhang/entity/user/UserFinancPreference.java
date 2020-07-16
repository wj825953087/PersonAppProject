package com.jizhang.entity.user;
/**
 * 用户理财偏好表
 * @author administrator
 *
 */
public class UserFinancPreference {
	private int fp_id;//Id
	private int user_id;//用户id
	private int fp_family_structure;//家庭结构
	/**
	 * 0单身
	 * 1夫妻均有收入，有子女
	 * 2夫妻均有收入，无子女
	 * 3只有一人有收入，有子女
	 * 4只有一人有收入，无子女
	 */


	private int fp_annual_investment;//年投资额
	/**
	 * 0 :5万以下
	 * 1 :5万到10万
	 * 2 :10万到30万
	 * 3 :30万以上
	 */

	private int fp_earnings;//预期收益
	




	private String fp_investment_name;//投资类别
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
