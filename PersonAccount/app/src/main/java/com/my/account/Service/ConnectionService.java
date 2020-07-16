package com.my.account.Service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.my.account.utils.StreamUtils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectionService implements Service {
    //服务器ip地址
    private   String ip="111.229.180.123";
    private   String port_number="8080";
    private   String  head= "http://";//头
    private   String  project_name="/Webservice/";//后台项目名
    private HttpURLConnection conn=null;//
    private InputStream inputStream=null;//

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort_number() {
        return port_number;
    }

    public void setPort_number(String port_number) {
        this.port_number = port_number;
    }

    @Override
    public String HttpGET(String oper_path) throws Exception {
        String path=head+ip+":"+port_number+project_name+oper_path;
        try {
            init(path,"GET");//初始化
            if(conn.getResponseCode()==200){
                inputStream=conn.getInputStream();
                return StreamUtils.parseString(inputStream);
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return null;
    }

    @Override
    public String HttpPOST(String oper_path) throws Exception {
        return null;
    }

    /**
     *
     * @param path 请求地址
     * @param request_method 请求方式
     * @throws Exception
     */
    private void init(String path,String request_method) throws Exception{
        conn= (HttpURLConnection) new URL(path).openConnection();
        //设置请求方式
        conn.setRequestMethod(request_method);//设置获取信息方式
        conn.setConnectTimeout(5000);//设置超时时间
        conn.setReadTimeout(5000);
        //设置运行输入\输出
        conn.setDoOutput(true);
        conn.setDoInput(true);
        //post不能缓存,需手动设置false
        // conn.setUseCaches(false);
        //设置请求头
        conn.setRequestProperty("Charset","UTF-8");//设置接受数据编码格式
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");//设置参数类型是json格式
    }

    /**
     * 加载图片
     * @param url
     * @return
     */
    public Bitmap getImageBitmap(String url){
        try{
            URL url2 = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) url2.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == 200){
                InputStream inputStream = conn.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }

        return null;
    }
}
