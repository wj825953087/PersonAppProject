package com.my.account.utils;

public class Calculate_Day {
    /**
     * 判断是否是闰年
     * @param year
     * @return
     */
    public static boolean judge(int year){
        boolean yearleap=(year%400==0)||(year%4==0)&&(year%100!=0);//
        return yearleap;
    }
    public static int calculate(int year,int month){
        boolean yearleap=judge(year);
        int day;
        if(yearleap&&month==2)
            day=29;
        else if (!yearleap&&month==2)
            day=28;
        else if (month==4||month==6||month==9||month==11)
            day=30;
        else day=31;
        return day;
    }
    public static int time1(int month){
        return month+1;
    }
}
