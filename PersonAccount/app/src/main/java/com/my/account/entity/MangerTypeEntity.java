package com.my.account.entity;

public class MangerTypeEntity {
    private int id;//类别id
    private int is_default;//是否是默认类别
    private int  permissions;//权限
    private int image_id;//图片id
    private String type_name;//类别名字

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public int getPermissions() {
        return permissions;
    }

    public void setPermissions(int permissions) {
        this.permissions = permissions;
    }

    public int getIs_default() {
        return is_default;
    }

    public void setIs_default(int is_default) {
        this.is_default = is_default;
    }

    @Override
    public String toString() {
        return "MangerTypeEntity{" +
                "id=" + id +
                ", is_default=" + is_default +
                ", permissions=" + permissions +
                ", image_id=" + image_id +
                ", type_name='" + type_name + '\'' +
                '}';
    }
}
