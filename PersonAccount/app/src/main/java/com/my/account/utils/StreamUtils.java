package com.my.account.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StreamUtils {
    /**
     * 输入流转成字符串
     * @param is
     * @return
     */
    public static String parseString(InputStream is){
        String line="";

        String response="";
        //每次读取一行，若非空则添加到StringBuilder
        try{
            //连接后，创建一个输入流来读取response
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is,"utf-8"));
            StringBuilder builder=new StringBuilder();
            while ((line=bufferedReader.readLine())!=null){
                builder.append(line);
            }
            //读取所有数据后，赋值给response
            response=builder.toString().trim();
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return response;
    }
}
