package com.jizhang.entity.expend;
/**
 * ֧����������
 * @author administrator
 *
 */
public class ExpendType {
	private int id;//֧�����id
	private String expend_name;//֧�������
	//0����ϵͳĬ����� ,1��ϵͳĬ�����
	private int is_default;//�Ƿ���ϵͳĬ�ϵ����
	//0����û���ϼ������������:�����ϼ����id
	private int super_id;//�ϼ�id
	//1ϵͳĬ������û�ֻ��ɾ��Ȩ��   ,2:�Զ��������û����������޸�
	private int permissions;//Ȩ��
	private int expend_pngId;//ͼƬid
	public int getId() {
		return id;
	}
	public String getExpend_name() {
		return expend_name;
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
	public int getExpend_pngId() {
		return expend_pngId;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setExpend_name(String expend_name) {
		this.expend_name = expend_name;
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
	public void setExpend_pngId(int expend_pngId) {
		this.expend_pngId = expend_pngId;
	}
	@Override
	public String toString() {
		return "ExpendType [id=" + id + ", expend_name=" + expend_name + ", is_default=" + is_default + ", super_id="
				+ super_id + ", permissions=" + permissions + ", expend_pngId=" + expend_pngId + "]";
	}
	
}
