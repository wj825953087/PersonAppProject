package com.my.account.utils;

import java.math.BigDecimal;

public class Precision {
    /**
     * 精度减法
     * @param num1
     * @param num2
     * @return
     */
    public static double sub(double num1,double num2){
        BigDecimal A=new BigDecimal(Double.toString(num1));
        BigDecimal B=new BigDecimal(Double.toString(num2));
        return A.subtract(B).doubleValue();
    }

    /**
     * 精度加法
     * @param num1
     * @param num2
     * @return
     */
    public static double add(double num1,double num2){
        BigDecimal A=new BigDecimal(Double.toString(num1));
        BigDecimal B=new BigDecimal(Double.toString(num2));
        return A.add(B).doubleValue();
    }

    /**
     * 精度乘法
     * @param num1
     * @param num2
     * @return
     */
    public static double mul(double num1,double num2){
        BigDecimal A=new BigDecimal(Double.toString(num1));
        BigDecimal B=new BigDecimal(Double.toString(num2));
        return A.multiply(B).doubleValue();
    }

}
