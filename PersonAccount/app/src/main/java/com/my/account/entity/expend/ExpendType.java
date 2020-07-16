package com.my.account.entity.expend;

/**
 * 支出类别管理类
 * @author administrator
 *
 */
public class ExpendType {
    private int id;//支出类别id
    private String expend_name;//支出类别名
    //0不是系统默认类别 ,1是系统默认类别
    private int is_default;//是否是系统默认的类别
    //0代表没有上级类别，其他数字:代表上级类别id
    private int super_id;//上级id
    //1系统默认类别用户只有删除权限   ,2:自定义的类别用户可以自由修改
    private int permissions;//权限
    private int expend_pngId;//图片id
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
