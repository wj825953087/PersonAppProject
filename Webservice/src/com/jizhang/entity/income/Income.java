package com.jizhang.entity.income;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.jizhang.entity.user.UserAccount;

/**
 * 收入账单
 * @author administrator
 *
 */



public class Income {
	private String in_id;//收入账单id
	private int user_id;//用户id
	private double in_money;//收入金额
	private int in_type;//收入类别
	private int in_account_type;//账户类别
	private String in_remark ;//备注
	
	private Date in_time;//记录收入时间
	private IncomeType incomeType;
	private UserAccount account;//账户信息
	
	public UserAccount getAccount() {
		return account;
	}
	public void setAccount(UserAccount account) {
		this.account = account;
	}
	public String getIn_id() {
		return in_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public double getIn_money() {
		return in_money;
	}
	public int getIn_type() {
		return in_type;
	}
	public int getIn_account_type() {
		return in_account_type;
	}
	public String getIn_remark() {
		return in_remark;
	}
	public Date getIn_time() {
		return in_time;
	}
	public IncomeType getIncomeType() {
		return incomeType;
	}
	public void setIn_id(String in_id) {
		this.in_id = in_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public void setIn_money(double in_money) {
		this.in_money = in_money;
	}
	public void setIn_type(int in_type) {
		this.in_type = in_type;
	}
	public void setIn_account_type(int in_account_type) {
		this.in_account_type = in_account_type;
	}
	public void setIn_remark(String in_remark) {
		this.in_remark = in_remark;
	}
	public void setIn_time(Date in_time) {
		this.in_time = in_time;
	}
	public void setIncomeType(IncomeType incomeType) {
		this.incomeType = incomeType;
	}
	@Override
	public String toString() {
		return "Income [in_id=" + in_id + ", user_id=" + user_id + ", in_money=" + in_money + ", in_type=" + in_type
				+ ", in_account_type=" + in_account_type + ", in_remark=" + in_remark + ", in_time=" + in_time
				+ ", incomeType=" + incomeType + ", account=" + account + "]";
	}
	
	

	

}
