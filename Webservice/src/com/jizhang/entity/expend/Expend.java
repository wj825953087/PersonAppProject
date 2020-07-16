package com.jizhang.entity.expend;
/**
 * ֧���˵�ʵ����
 * @author administrator
 *
 */

import java.util.Date;

import com.jizhang.entity.user.UserAccount;

public class Expend {
	private String ex_id;//֧���˵�id
	private int user_id;//�û�id
	private double ex_money;//֧���˵����
	//0:���  1:��� 2:��� 3:����ˮ�� 4:���ԭ�� 5:�� 6:���� 7:����
	private int ex_type;//֧�����
	//0:�ֽ� 1:���ÿ� 2:��� 3:�����˻� 4:Ͷ���˻� 5:�����˻�
	private int ex_account_type;//�˻����
	private String ex_remark;//��ע
	private Date ex_time;//��¼֧���˵�ʱ��
	private ExpendType expendType;
	private UserAccount account;
	public String getEx_id() {
		return ex_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public double getEx_money() {
		return ex_money;
	}
	public int getEx_type() {
		return ex_type;
	}
	public int getEx_account_type() {
		return ex_account_type;
	}
	public String getEx_remark() {
		return ex_remark;
	}
	public Date getEx_time() {
		return ex_time;
	}
	public void setEx_id(String ex_id) {
		this.ex_id = ex_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public void setEx_money(double ex_money) {
		this.ex_money = ex_money;
	}
	public void setEx_type(int ex_type) {
		this.ex_type = ex_type;
	}
	public void setEx_account_type(int ex_account_type) {
		this.ex_account_type = ex_account_type;
	}
	public void setEx_remark(String ex_remark) {
		this.ex_remark = ex_remark;
	}
	public void setEx_time(Date ex_time) {
		this.ex_time = ex_time;
	}
	

	public ExpendType getExpendType() {
		return expendType;
	}

	public void setExpendType(ExpendType expendType) {
		this.expendType = expendType;
	}
	
	public UserAccount getAccount() {
		return account;
	}
	public void setAccount(UserAccount account) {
		this.account = account;
	}
	@Override
	public String toString() {
		return "Expend [ex_id=" + ex_id + ", user_id=" + user_id + ", ex_money=" + ex_money + ", ex_type=" + ex_type
				+ ", ex_account_type=" + ex_account_type + ", ex_remark=" + ex_remark + ", ex_time=" + ex_time
				+ ", expendType=" + expendType + ", account=" + account + "]";
	}
	
	
}
