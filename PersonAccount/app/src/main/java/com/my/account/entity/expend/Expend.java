package com.my.account.entity.expend;
/**
 * 支出账单实体类
 * @author administrator
 *
 */

import com.my.account.entity.user.UserAccount;

import java.util.Date;

public class Expend {

	private String ex_id;//支出账单id
	private int user_id;//用户id
	private double ex_money;//支出账单金额
	//0:早餐  1:午餐 2:晚餐 3:饮料水果 4:买菜原料 5:打车 6:公交 7:加油
	private int ex_type;//支出类别
	//0:现金 1:信用卡 2:储蓄卡 3:网络账户 4:投资账户 5:虚拟账户
	private int ex_account_type;//账户类别
	private String ex_remark;//备注
	private Date ex_time;//记录支出账单时间
	private ExpendType expendType;
	private UserAccount account;

	public String getEx_id() {
		return ex_id;
	}

	public void setEx_id(String ex_id) {
		this.ex_id = ex_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public double getEx_money() {
		return ex_money;
	}

	public void setEx_money(double ex_money) {
		this.ex_money = ex_money;
	}

	public int getEx_type() {
		return ex_type;
	}

	public void setEx_type(int ex_type) {
		this.ex_type = ex_type;
	}

	public int getEx_account_type() {
		return ex_account_type;
	}

	public void setEx_account_type(int ex_account_type) {
		this.ex_account_type = ex_account_type;
	}

	public String getEx_remark() {
		return ex_remark;
	}

	public void setEx_remark(String ex_remark) {
		this.ex_remark = ex_remark;
	}

	public Date getEx_time() {
		return ex_time;
	}

	public void setEx_time(Date ex_time) {
		this.ex_time = ex_time;
	}

	public ExpendType getExpendType() {
		return expendType;
	}

	public UserAccount getAccount() {
		return account;
	}

	public void setAccount(UserAccount account) {
		this.account = account;
	}

	public void setExpendType(ExpendType expendType) {
		this.expendType = expendType;
	}

	@Override
	public String toString() {
		return "Expend{" +
				"ex_id='" + ex_id + '\'' +
				", user_id=" + user_id +
				", ex_money=" + ex_money +
				", ex_type=" + ex_type +
				", ex_account_type=" + ex_account_type +
				", ex_remark='" + ex_remark + '\'' +
				", ex_time=" + ex_time +
				", expendType=" + expendType +
				", account=" + account +
				'}';
	}

}
