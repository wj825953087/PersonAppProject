package com.my.account.entity;

//主页面-图片加文字
public class Picture {
    private  String title;//图像标题
    private  int Image_id;//图像id

    public Picture() {
    }

    public Picture(String title, int image_id) {
        this.title = title;
        Image_id = image_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage_id() {
        return Image_id;
    }

    public void setImage_id(int image_id) {
        Image_id = image_id;
    }
}
