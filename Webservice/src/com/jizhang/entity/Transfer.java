package com.jizhang.entity;
/*
 * ת���˵�ʵ��
 */

import java.util.Date;

public class Transfer {
	private int tf_id;//ת���˵���id
	private int user_id;//�û�id
	private double tf_money;//ת�˽��
	private int tf_type;//ת�����
	/**
	 * 1:�˻���ת 1:���ÿ�����3:ȡǮ 4:��Ǯ
	 */
	private int tf_account_type;//�˻����
	/**
	 * 0:�ֽ� 1:���ÿ� 2:��� 3:�����˻� 4:Ͷ���˻� 5:�����˻�
	 */
	private String tf_remark;//��ע
	private Date tf_time;//��¼ת��ʱ��
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
