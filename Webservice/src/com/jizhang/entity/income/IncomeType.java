package com.jizhang.entity.income;
/**
 * ������������
 * @author administrator
 *
 */
public class IncomeType {
	private int id;//�������id
	//private int user_id;//�û�id,0���������û�
	private String income_name;//���������
	//0����ϵͳĬ����� ,1��ϵͳĬ�����
	private int is_default;//�Ƿ���ϵͳĬ�ϵ����	
	//0����û���ϼ������������:�����ϼ����id
	private int super_id;//�ϼ�id
	//1ϵͳĬ������û�ֻ��ɾ��Ȩ��   ,2:�Զ��������û����������޸�
	private int permissions;//Ȩ��
	private int income_pngId;//ͼƬid
	public int getId() {
		return id;
	}
	public String getIncome_name() {
		return income_name;
	}
	public int getIs_default() {
		return is_default;
	}
	public int getSuper_id() {
		return super_id;
	}
	public int getPermissions() {
		return permissions;
	}
	public int getIncome_pngId() {
		return income_pngId;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setIncome_name(String income_name) {
		this.income_name = income_name;
	}
	public void setIs_default(int is_default) {
		this.is_default = is_default;
	}
	public void setSuper_id(int super_id) {
		this.super_id = super_id;
	}
	public void setPermissions(int permissions) {
		this.permissions = permissions;
	}
	public void setIncome_pngId(int income_pngId) {
		this.income_pngId = income_pngId;
	}
	@Override
	public String toString() {
		return "IncomeType [id=" + id + ", income_name=" + income_name + ", is_default=" + is_default + ", super_id="
				+ super_id + ", permissions=" + permissions + ", income_pngId=" + income_pngId + "]";
	}

	
	
	
	




}
