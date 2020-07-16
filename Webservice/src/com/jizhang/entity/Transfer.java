package com.jizhang.entity;
/*
 * 转账账单实体
 */

import java.util.Date;

public class Transfer {
	private int tf_id;//转账账单表id
	private int user_id;//用户id
	private double tf_money;//转账金额
	private int tf_type;//转账类别
	/**
	 * 1:账户互转 1:信用卡还款3:取钱 4:存钱
	 */
	private int tf_account_type;//账户类别
	/**
	 * 0:现金 1:信用卡 2:储蓄卡 3:网络账户 4:投资账户 5:虚拟账户
	 */
	private String tf_remark;//备注
	private Date tf_time;//记录转账时间
	public int getTf_id() {
		return tf_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public double getTf_money() {
		return tf_money;
	}
	public int getTf_type() {
		return tf_type;
	}
	public int getTf_account_type() {
		return tf_account_type;
	}
	public String getTf_remark() {
		return tf_remark;
	}
	public Date getTf_time() {
		return tf_time;
	}
	public void setTf_id(int tf_id) {
		this.tf_id = tf_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public void setTf_money(double tf_money) {
		this.tf_money = tf_money;
	}
	public void setTf_type(int tf_type) {
		this.tf_type = tf_type;
	}
	public void setTf_account_type(int tf_account_type) {
		this.tf_account_type = tf_account_type;
	}
	public void setTf_remark(String tf_remark) {
		this.tf_remark = tf_remark;
	}
	public void setTf_time(Date tf_time) {
		this.tf_time = tf_time;
	}
	@Override
	public String toString() {
		return "Transfer [tf_id=" + tf_id + ", user_id=" + user_id + ", tf_money=" + tf_money + ", tf_type=" + tf_type
				+ ", tf_account_type=" + tf_account_type + ", tf_remark=" + tf_remark + ", tf_time=" + tf_time + "]";
	}
	
	
}
