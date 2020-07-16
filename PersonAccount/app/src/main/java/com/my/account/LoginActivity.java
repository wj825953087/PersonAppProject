package com.my.account;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.my.account.Service.ConnectionService;

import org.codehaus.jackson.map.ObjectMapper;


public class LoginActivity extends Activity implements View.OnClickListener {
    //消息码
    private  final int MSG_LOGIN_SUCCESS=1;//登录成功
    private  final int MSG_LOGIN_FAIL=0;//登录失败

    final String REMEMBER_PWD_PREF = "rememberPwd";
    final String ACCOUNT_PREF = "account";
    final String PASSWORD_PREF = "password";

    private ConnectionService service=null;
    private EditText userNameEd,passwordEd;//用户名和密码
    private Button login_btn,regist_btn;//登录和注册按钮
    private CheckBox rememberPassword_cbox;//记住密码

    private SharedPreferences sp;//在键值对中存储私有原始数据
    private SharedPreferences.Editor editor;
    private String username,password;//用户名和密码
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_LOGIN_FAIL://登录失败
                    Toast.makeText(LoginActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                    break;
                case MSG_LOGIN_SUCCESS://登录成功
                    Bundle bundle= (Bundle) msg.obj;
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    //判断是否记住用户名,密码
                    if (rememberPassword_cbox.isChecked()){
                        //记住用户名\密码
                        editor =sp.edit();
                        editor.putBoolean(REMEMBER_PWD_PREF,true);
                        editor.putString(ACCOUNT_PREF,username);
                        editor.putString(PASSWORD_PREF,password);
                        editor.commit();
                    }

                    intent.putExtras(bundle);
                    startActivity(intent);//启动
                    break;
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载布局
        setContentView(R.layout.login);
        //初始化
        init();
        sp= PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRemember=sp.getBoolean(REMEMBER_PWD_PREF,false);
        rember_password(isRemember);//记住密码

        /*绑定监听*/
        login_btn.setOnClickListener(this); //登录按钮
        regist_btn.setOnClickListener(this);//注册按钮
    }
    /**
     * 初始化
     */
    public void init(){
        userNameEd=findViewById(R.id.username);//输入用户名
        passwordEd=findViewById(R.id.password);//输入密码
        rememberPassword_cbox=findViewById(R.id.remeber_password); //记住密码
        login_btn=findViewById(R.id.login);//登录
        regist_btn=findViewById(R.id.register);//注册
    }

    /**
     *  记住密码
     * @param isRemember
     */
    public void rember_password(boolean isRemember){
        if(isRemember){//设置账号与密码
            userNameEd.setText(sp.getString(ACCOUNT_PREF,""));
            passwordEd.setText(sp.getString(PASSWORD_PREF,""));
            rememberPassword_cbox.setChecked(true);

        }
    }
    /**
     * 登录操作
     * @param username
     * @param password
     * @return
     * @throws Exception
     */
    private   String LoginOperationHttp(String username,String password) throws Exception{
        String oper="UserServlet?method=isLogin&&username="+username+"&&password="+password;
        String login_result=null;//登录成功,返回信息
        service=new ConnectionService();//初始化
        login_result=service.HttpGET(oper);
        if (login_result!=null)return login_result;

        return "";
    }
    //按钮监听
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register:
                //注册
                Intent intent=new Intent(LoginActivity.this,RegisteredActivity.class);
                startActivity(intent);
                break;
            case R.id.login:
                //登录
                username=userNameEd.getText().toString().trim();
                password=passwordEd.getText().toString().trim();
                if(TextUtils.isEmpty(username)||TextUtils.isEmpty(password)){
                    //账号密码为空
                    Toast.makeText(LoginActivity.this,"输入值为空!",Toast.LENGTH_SHORT).show();
                }else{//开启线程
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                //连接服务器
                                String result=LoginOperationHttp(username,password);
                                /*使用jackson解析*/
                                ObjectMapper mapper=new ObjectMapper();

                                //解析JSON字符串,根据is_login 判断是否登录成功 ,1表示登录成功,0表示登录失败
                                JSONObject object= JSON.parseObject(result);//转成JSON对象

                                int is_login=Integer.parseInt(object.getString("is_login")); //获取is_login字段值

                                if(is_login==1){
                                    //登录成功
                                    //获取用户信息
                                    JSONObject user_object=object.getJSONObject("user");
                                    String user_json=user_object.toJSONString();
                                    //通过Bundle保存数据
                                    Bundle bundle=new Bundle();
                                    bundle.putString("user_result",result);//用户信息
                                    bundle.putString("user",user_json);
                                    //消息
                                    Message message=new Message();
                                    message.what=MSG_LOGIN_SUCCESS;
                                    message.obj=bundle;
                                    //发送消息
                                    handler.sendMessage(message);


                                }else{//密码有误,登录失败
                                    //发消息给主线程进行UI操作
                                    Message message=new Message();//消息
                                    message.what=MSG_LOGIN_FAIL;
                                    handler.sendMessage(message);//发送
                                }
                            }catch (Exception e){
                                System.out.println(e.toString());
                            }

                        }
                    }).start();
                }
                break;
            default:
                break;
        }
    }


}

