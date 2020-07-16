package com.my.account.entity.user;

public class UserAccount {
    private int id;//账户id
    private int user_id;//用户id
    private String account_name;//账户名
    private String account_card_number;//卡号
    private double account_money;//账户金额
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
