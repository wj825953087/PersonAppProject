package com.jizhang.entity.user;

import java.util.Date;
import java.util.List;

/**
 *  �û�ʵ��
 * @author Administrator
 *
 */
public class User {

	private int user_id;//�û�id
	private String	username;//�û���
	private String password;//�û�����
	private String phone;//�绰����
	private String user_alias;//�û�����
	private String gender;//�û��Ա�
	private String headImage;//�û�ͷ��
	private Date birthday;//�û�����
	private List<UserAccount> accounts;//�û��˺���Ϣ
	
	/*get AND set */
	public int getUser_id() {
		return user_id;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public String getPhone() {
		return phone;
	}
	public String getUser_alias() {
		return user_alias;
	}
	public String getGender() {
		return gender;
	}
	public String getHeadImage() {
		return headImage;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setUser_alias(String user_alias) {
		this.user_alias = user_alias;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	public List<UserAccount> getAccountList() {
		return accounts;
	}
	public void setAccountList(List<UserAccount> accounts) {
		this.accounts = accounts;
	}
	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", username=" + username + ", password=" + password + ", phone=" + phone
				+ ", user_alias=" + user_alias + ", gender=" + gender + ", headImage=" + headImage + ", birthday="
				+ birthday + ", accountList=" + accounts + "]";
	}
	
	
	

	
}
