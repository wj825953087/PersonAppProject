package com.my.account.utils;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FastJsonUtils<T>{
    private Map<String, Object> map;
    public FastJsonUtils() {
        this.map=new HashMap<>();
    }
    //JSON字符串转成Map
    public Map<String, Object> toMap(String jsonStr) {
        map=(Map<String, Object>) JSON.parse(jsonStr);
        return map;
    }
    //JSON字符串转成List
    public <T> ArrayList<T> toList(String jsonStr, Class<T> clazz){
        ArrayList<T> list= (ArrayList<T>) JSON.parseArray(jsonStr, clazz);
        return  list;
    }
}
