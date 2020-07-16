package com.jizhang.entity.income;
/**
 * 收入类别管理类
 * @author administrator
 *
 */
public class IncomeType {
	private int id;//收入类别id
	//private int user_id;//用户id,0代表所有用户
	private String income_name;//收入类别名
	//0不是系统默认类别 ,1是系统默认类别
	private int is_default;//是否是系统默认的类别	
	//0代表没有上级类别，其他数字:代表上级类别id
	private int super_id;//上级id
	//1系统默认类别用户只有删除权限   ,2:自定义的类别用户可以自由修改
	private int permissions;//权限
	private int income_pngId;//图片id
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
