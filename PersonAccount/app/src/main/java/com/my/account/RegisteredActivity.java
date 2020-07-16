package com.my.account;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.my.account.Service.ConnectionService;

import java.util.Objects;

public class RegisteredActivity extends Activity implements View.OnClickListener {
    //消息码
    private final int MSG_REGISTERE_SUCCESS_UI=0;//注册成功
    private final int MSG_REGISTERE_FAILURE_UI=1;//用户名验证

    private LinearLayout ll_verify1,ll_verify2;
    private LinearLayout ll_verify_password,ll_verify_phone;

    private ImageView go_back_imgv;//返回
    private TextView hint_username,hint_password ,hint_confirm_password,hint_phone;
    private EditText input_username;//输入用户名
    private EditText input_password;//输入密码
    private EditText input_confirmpassword;//输入确认密码
    private EditText input_phone;//输入手机号
    private Button register_btn;//注册按钮
    private String username,password,confirm_password;//用户名,密码,确认密码
    private String phone;//手机号

    private int is_repeat=0;//用户名是否重复,0代表不重复, 1代表重复
    private int count =0;//验证次数,7次验证成功,才可以进行注册
    private int regster_success=0;//是否注册成功
    private ConnectionService connectionService=null;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_REGISTERE_SUCCESS_UI://注册
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                insert_Account_information(username);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    showDialog("注册成功");
                    break;
                case MSG_REGISTERE_FAILURE_UI://用户名验证
                    //Toast.makeText(RegisteredActivity.this,"用户名重复",Toast.LENGTH_LONG).show();

                    ll_verify1.setVisibility(View.VISIBLE);//
                    hint_username.setText("用户名已存在");
                    break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载布局
        setContentView(R.layout.registered);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉系统默认标题]
        init();//初始化
        //绑定监听
        register_btn.setOnClickListener(this);
        go_back_imgv.setOnClickListener(this);
    }

    /**
     * 初始化
     */
    private void init() {

        go_back_imgv=findViewById(R.id.go_back);
        ll_verify1=findViewById(R.id.verify1);
        ll_verify2=findViewById(R.id.verify2);
        ll_verify_password=findViewById(R.id.verify_password);
        ll_verify_phone=findViewById(R.id.verify_phone);

        hint_username=findViewById(R.id.hint_username);
        hint_password=findViewById(R.id.hint_password);
        hint_confirm_password=findViewById(R.id.hint_confirm_password);
        hint_phone=findViewById(R.id.hint_phone);

        input_username=findViewById(R.id.input_username);//输入用户名
        input_password=findViewById(R.id.input_password);//输入密码
        input_confirmpassword=findViewById(R.id.input_confirm_password);//输入确认密码
        input_phone=findViewById(R.id.input_phone);//输入手机号
        register_btn=findViewById(R.id.registered);//注册
    }



    /**
     * 作用:查询输入的用户名是否重复
     *
     * @param username
     * @return 0代表不重复, 1代表重复
     * @throws Exception
     */
    public int repeat_username(String username) throws Exception{
        String op="UserServlet?method=repeatUsername" +
                "&&username="+username;
        String json=null;
        connectionService=new ConnectionService();//初始化
        json=connectionService.HttpGET(op);
        if(json!=null){
            JSONObject object= JSON.parseObject(json);
            return Integer.parseInt(object.getString("is_repeat"));
        }
        return 0;
    }
    /**
     *  作用:用户注册
     * @param username
     * @param password
     * @param phone
     * @return 0代表注册失败, 1代表注册成功
     * @throws Exception
     */
    public int regist(String username,String password,String phone)throws Exception{
        String op="UserServlet?method=Regist" +
                "&&username="+username+
                "&&password="+password+
                "&&phone="+phone;

        String json=null;
        connectionService=new ConnectionService();//初始化
        json=connectionService.HttpGET(op);
        if(json!=null){
            JSONObject object= JSON.parseObject(json);
            return Integer.parseInt(object.getString("is_regist"));
        }
        return 0;
    }

    /**
     * 注册成功后,更新该用户的账户默认信息
     * @param username
     * @return
     * @throws Exception
     */
    public int insert_Account_information(String username) throws Exception{//
        String op="UserServlet?method=add_account_information" +
                "&&account_name=现金"+
                "&&username="+username+
                "&&account_money=0"+
                "&&account_card_number=0000";
        String json=null;
        connectionService=new ConnectionService();
        json=connectionService.HttpGET(op);
        if(json!=null){
            JSONObject ob=JSON.parseObject(json);
            return Integer.parseInt(ob.getString("insert_success"));
        }
        return 0;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.go_back://返回
                RegisteredActivity.this.finish();
                break;
            case R.id.registered://注册
             //获取输入信息
                username=input_username.getText().toString().trim();
                password=input_password.getText().toString().trim();
                confirm_password=input_confirmpassword.getText().toString().trim();
                phone=input_phone.getText().toString().trim();
            //-----------------------------注册相关验证----------------------------------------------

                //1.输入不能为空验证
                if(username.equals("")){
                    ll_verify1.setVisibility(View.VISIBLE);//显示
                    hint_username.setText("用户名不能为空");
                }else{
                    ll_verify1.setVisibility(View.GONE);//隐藏
                    count++;

                //2.用户名验证, 开启线程
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                is_repeat=repeat_username(username);
                                if(is_repeat==1){
                                    Message message=new Message();
                                    message.what=MSG_REGISTERE_FAILURE_UI;
                                    handler.sendMessage(message);
                                };
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    if(is_repeat==0){
                        ll_verify1.setVisibility(View.GONE);//隐藏
                        count++;
                    }
                }

                //3.输入密码不能为空验证
                if(password.equals("")){
                    ll_verify_password.setVisibility(View.VISIBLE);//显示
                    hint_password.setText("密码不能为空");
                }else {
                    ll_verify_password.setVisibility(View.GONE);//隐藏
                    count++;
                }

                //4.确认密码不能为空验证
                if(confirm_password.equals("")){
                    ll_verify2.setVisibility(View.VISIBLE);//显示
                    hint_confirm_password.setText("确认密码不能为空");
                }else {
                    ll_verify2.setVisibility(View.GONE);//隐藏
                    count++;
                }

                //5.输入的手机号不能为空
                if(phone.equals("")){
                    ll_verify_phone.setVisibility(View.VISIBLE);//显示
                    hint_phone.setText("手机号不能为空");
                }else{
                    ll_verify_phone.setVisibility(View.GONE);//隐藏
                    count++;
                    //7.验证手机号是否为11位
                    if(phone.length()!=11){
                        ll_verify_phone.setVisibility(View.VISIBLE);//显示
                        hint_phone.setText("手机号要11位数");
                    }else {
                        ll_verify_phone.setVisibility(View.GONE);//隐藏
                        count++;
                    }
                }

                //6.密码和确认密码都不为空,则进行密码与确认密码验证是否一致
                if(!password.equals("")&&
                   !confirm_password.equals("")&&
                   !password.equals(confirm_password)){

                    ll_verify2.setVisibility(View.VISIBLE);//显示
                    hint_confirm_password.setText("两次输入的密码不一致");
                }else {
                    ll_verify2.setVisibility(View.GONE);//显示
                    count++;
                }
            //--------------------------------------------------------------------------------------
            //------------------------------------验证成功-------------------------------------------
                   if(count==7){//验证成功,可以进行注册
                       //开启线程
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    regster_success=regist(username,password,phone);
                                    if (regster_success==1){
                                        //注册成功
                                        Message message=new Message();
                                        message.what=MSG_REGISTERE_SUCCESS_UI;
                                        handler.sendMessage(message);
                                    }
                                }catch (Exception e){
                                    System.out.println(e.toString());
                                }
                            }
                        }).start();
                   }


                break;
        }
    }


    //消息提示框
    private void showDialog(String contain) {
        new AlertDialog.Builder(this)
                .setMessage(contain)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RegisteredActivity.this.finish();

                    }
                })
                .show();
    }
}
