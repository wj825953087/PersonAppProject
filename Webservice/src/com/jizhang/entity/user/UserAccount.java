package com.jizhang.entity.user;
/**
 * �û��˻���
 * @author administrator
 *
 */
public class UserAccount {
	private int id;//�˻�id
	private int user_id;//�û�id
	private String account_name;//�˻���
	private String account_card_number;//����
	private double account_money;//�˻����
	public int getId() {
		return id;
	}
	public int getUser_id() {
		return user_id;
	}
	public String getAccount_name() {
		return account_name;
	}
	public String getAccount_card_number() {
		return account_card_number;
	}
	public double getAccount_money() {
		return account_money;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public void setAccount_name(String account_name) {
		this.account_name = account_name;
	}
	public void setAccount_card_number(String account_card_number) {
		this.account_card_number = account_card_number;
	}
	public void setAccount_money(double account_money) {
		this.account_money = account_money;
	}
	@Override
	public String toString() {
		return "UserAccount [id=" + id + ", user_id=" + user_id + ", account_name=" + account_name
				+ ", account_card_number=" + account_card_number + ", account_money=" + account_money + "]";
	}
	
	


}
