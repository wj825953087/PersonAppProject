package com.my.account;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.my.account.Service.ConnectionService;
import com.my.account.adapter.SpinnerAdapter;
import com.my.account.entity.SpinnerEntity;
import com.my.account.entity.expend.Expend;
import com.my.account.entity.user.UserAccount;
import com.my.account.view.MoneyEditText;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UpdateOutAccount extends Activity implements View.OnClickListener {
    private Context context;
    private SpinnerAdapter spinnerAdapter;//下拉菜单适配器
    private Calendar cal;
    private ConnectionService service=null;
    private Expend expend=null;
    private ArrayList<UserAccount> accountList=null;//用户账户信息
    /*------------------------------------------------------------*/
    private int year,month,day,hour,minute;//年月日 小时 分钟
    private String select_accountName=null;//选择账户名
    private int select_accountId=0;//选中账户id
    /*------------------------------------------------------------*/
    private ImageView back_btn;//返回按钮
    private ImageView sava_imgv;//保存
    /*------------------------------------------------------------*/
    private ImageView expendTypeImg;//显示支出类别图标
    private TextView show_expend_type;//显示选择支出类别
    private MoneyEditText money_edt;//输入金额
    /*------------------------------------------------------------*/
    private Button breakfast_btn;//早餐按钮
    private Button lunch_btn;//午餐按钮
    private Button supper_btn;//晚餐按钮
    private Button  fruit_drink_btn;//饮料水果按钮
    private Button buy_vegetables_btn;//买菜
    private Button lift_btn;//打车
    private Button bus_btn;//公交
    private Button oil_btn;//加油
    /*------------------------------------------------------------*/
    private Spinner account_sp;//选择账户
    private EditText remark_edt;//输入备注
    /*------------------------------------------------------------*/
    private Button choose_date_btn;//选择日期
    private TextView chooseDate_tv;//日期显示
    /*------------------------------------------------------------*/
    private Button del_btn;//删除按钮
    private Button save_btn;//保存按钮
    /*------------------------------------------------------------*/
    //消息码
    private final int MSG_UPDATE_UI=0;//更新ui
    private final int MSG_SAVE=1;//保存
    private final int MSG_SAVE_FAIL=2;//保存失败
    private final int MSG_DELETE=3;//删除
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_UPDATE_UI:
                    try {
                        JSONObject jsonObject= (JSONObject) msg.obj;
                        String str=jsonObject.toJSONString();
                        JSONObject s= (JSONObject) jsonObject.get("expend");
                        //------------------------------------------------
                        expend= JSON.parseObject(s.toJSONString(), Expend.class);
                        Double ex_money=s.getDouble("ex_money");
                        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        Date ex_time=s.getDate("ex_time");
                        //图标
                        expendTypeImg.setImageResource(expend.getExpendType().getExpend_pngId());
                        //显示支出类别
                        show_expend_type.setText(expend.getExpendType().getExpend_name());
                        //设置按钮颜色
                        setButtonColor(expend.getExpendType().getExpend_name());
                        //显示支出金额
                        money_edt.setText(String.valueOf(ex_money));
                        //显示账户类型名
                        defaultAccount(expend.getAccount().getAccount_name(),accountList);
                        //显示时间
                        chooseDate_tv.setText(String.valueOf(format.format(ex_time)));

                    }catch (Exception e){
                        System.out.println(e.toString());
                    }

                    break;
                case MSG_SAVE://保存成功UI更新
                    try {
                        showDialog("保存成功");

                    }catch (Exception e){
                        System.out.println(e.toString());
                    }
                    break;
                case MSG_DELETE://删除成功UI更新
                    try {
                        showDialog("删除成功");
                    }catch (Exception e){
                        System.out.println(e.toString());
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载布局
        setContentView(R.layout.update_expend);
        init();//初始化
        getDate();
        /************************获取上个页面Bundle对象**********************************************/
        Bundle bundle=getIntent().getExtras();
        //获取账单id
        final String ex_id= (String) bundle.get("ex_id");
        //获取用户id
        final int user_id= (int) bundle.get("user_id");
        //获取用户信息
        String user_information= (String) bundle.get("user_result");
        JSONObject user_object= JSON.parseObject(user_information);
        String user=user_object.getString("user");
        //获取用户账户信息
        JSONObject ob= JSON.parseObject(user);
        JSONArray account_list=ob.getJSONArray("accountList");
        String accounts= JSONObject.toJSONString(account_list);
        //给服务端通信
        //开启线程
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    JSONObject expendObject=showByBillId(user_id,ex_id);
                    //通知主线程更新UI
                    Message message=new Message();
                    message.obj=expendObject;
                    message.what=MSG_UPDATE_UI;
                    handler.sendMessage(message);
                }catch (Exception e){
                    System.out.println(e.toString());
                }

            }
        }).start();
        /******************************************************************************************/
        //string - List
        ObjectMapper mapper=new ObjectMapper();
        try{
            //jackson
            accountList=(ArrayList<UserAccount>) mapper.readValue(accounts,new TypeReference<List<UserAccount>>(){});

        }catch (Exception e){
            System.out.println(e.toString());
        }
        //设置Spinner 适配器
        ArrayList<SpinnerEntity> spinnerEntities=new ArrayList<SpinnerEntity>();
        for(int i=0;i<accountList.size();i++){
            SpinnerEntity entity=new SpinnerEntity();
            entity.setAccount_name(accountList.get(i).getAccount_name());
            entity.setId(accountList.get(i).getId());
            spinnerEntities.add(entity);
        }
        spinnerAdapter=new SpinnerAdapter(spinnerEntities,this);
        account_sp.setAdapter(spinnerAdapter);


        //获取Spinner选中值
        account_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                SpinnerEntity entity= (SpinnerEntity) spinnerAdapter.getItem(position);
                select_accountId= entity.getId();
                select_accountName= entity.getAccount_name();

                //  account_txtView.setText(select_accountName);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //注册监听
        back_btn.setOnClickListener(this);//返回
        sava_imgv.setOnClickListener(this);//保存

        breakfast_btn.setOnClickListener(this);//早餐按钮
        lunch_btn.setOnClickListener(this);//午餐按钮
        supper_btn.setOnClickListener(this);//晚餐按钮
        fruit_drink_btn.setOnClickListener(this);//饮料水果按钮
        buy_vegetables_btn.setOnClickListener(this);//买菜
        lift_btn.setOnClickListener(this);//打车
        bus_btn.setOnClickListener(this);//公交
        oil_btn.setOnClickListener(this);//加油

        choose_date_btn.setOnClickListener(this);//选择日期
        del_btn.setOnClickListener(this);//删除
        save_btn.setOnClickListener(this);//保存按钮

    }

    /**
     * 初始化参数
     */
    public void init(){
        back_btn=findViewById(R.id.go_back);//返回
        sava_imgv=findViewById(R.id.tick_save);//保存
        /*-------------------------------------------*/
        expendTypeImg=findViewById(R.id.expend_type_img);//类别图标
        show_expend_type=findViewById(R.id.choose_expend_type_txv);//显示类别名
        money_edt=findViewById(R.id.input_money);//输入金额
        /*-------------------------------------------*/
        //选择支出类别按钮
        breakfast_btn=findViewById(R.id.expend_type_btn1);//早餐
        lunch_btn=findViewById(R.id.expend_type_btn2);//午餐
        supper_btn=findViewById(R.id.expend_type_btn3);//晚餐
        fruit_drink_btn=findViewById(R.id.expend_type_btn4);//饮料水果
        buy_vegetables_btn=findViewById(R.id.expend_type_btn5);//买菜
        lift_btn=findViewById(R.id.expend_type_btn6);//打车
        bus_btn=findViewById(R.id.expend_type_btn7);//公交
        oil_btn=findViewById(R.id.expend_type_btn8);//加油
        /*-------------------------------------------*/
        account_sp=findViewById(R.id.acount_choose);//选择账户
        remark_edt=findViewById(R.id.input_remark);//输入备注
        /*-------------------------------------------*/
        choose_date_btn=findViewById(R.id.choose_date);//选择日期
        chooseDate_tv=findViewById(R.id.show);//日期显示
        /*-------------------------------------------*/
        del_btn=findViewById(R.id.del);//删除
        save_btn=findViewById(R.id.sava);//保存
    }
    /**
     * 消息提示框
     */
    private void showDialog(String contain) {
        new AlertDialog.Builder(this)
                .setMessage(contain)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UpdateOutAccount.this.finish();
                    }
                })
                .show();
    }

    /**
     * 消息框:是否消息框
     * @param contain
     */
    private void showDialog2(String contain) {
        new AlertDialog.Builder(this)
                .setMessage(contain)
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //开启线程
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    int del_success= del_income(expend.getEx_id(),expend.getUser_id());
                                    if(del_success==1){
                                        Message message=new Message();
                                        message.what=MSG_DELETE;
                                        handler.sendMessage(message);
                                    }
                                }catch (Exception e){
                                    System.out.println(e.toString());
                                }

                            }
                        }).start();
                    }
                })
                .setNegativeButton("否",null)
                .show();
    }
    private void defaultAccount(String accountName, ArrayList<UserAccount> list){
        for(int i=0;i<list.size();i++){
            if(list.get(i).getAccount_name().equals(accountName)){
                account_sp.setSelection(i);
                break;
            }
        }
    }
    /**
     * 删除一条账单
     * @param bill_id
     * @param user_id
     * @return
     * @throws Exception
     */
    public int del_income(String bill_id,int user_id)throws Exception{
        String op="ExpendServlet?method=del_expend"
                +"&&ex_id="+bill_id
                +"&&user_id="+user_id;
        String json=null;
        //初始化
        service=new ConnectionService();
        json=service.HttpGET(op);
        if(json!=null){
            JSONObject o= JSON.parseObject(json);
            return (int) o.get("del_success");
        }
        return 0;
    }
    /**
     * 更新账单
     * @param bill_id
     * @param user_id
     * @param money
     * @param in_type
     * @param in_account_type
     * @param time
     * @return
     */
    public int sava_income(String bill_id,int user_id,double money,int in_type,int in_account_type,String time) throws Exception{
        String op="ExpendServlet?method=update_expend"
                +"&&ex_id="+bill_id
                +"&&user_id="+user_id
                +"&&ex_money="+money
                +"&&ex_type="+in_type
                +"&&ex_account_type="+in_account_type
                +"&&ex_time="+time;
        String json=null;
        //初始化
        service=new ConnectionService();
        json=service.HttpGET(op);
        if(json!=null){
            JSONObject o= JSON.parseObject(json);
            return (int) o.get("update_sucess");
        }
        return 0;
    }

    /**
     * 根据账单id显示数据
     * @param user_id 用户id
     * @param bill_id 账单id
     * @return
     * @throws Exception
     */
    public JSONObject showByBillId(int user_id, String bill_id) throws Exception {
        String oper="ExpendServlet?method=view_expend"
                + "&&user_id="+user_id
                + "&&ex_id="+bill_id;

        String resultJson=null;//服务端发来的json串
        //初始化
        service=new ConnectionService();
        resultJson=service.HttpGET(oper);
        if(resultJson!=null){
            JSONObject object= JSON.parseObject(resultJson);
            return object;
        }
        return null;
    }
    /**
     * 获取当前时间
     */
    public void getDate() {
        cal=Calendar.getInstance();
        year=cal.get(Calendar.YEAR);//获取年
        month=cal.get(Calendar.MONTH);//获取月
        day=cal.get(Calendar.DAY_OF_MONTH);//获取日
        hour=cal.get(Calendar.HOUR);//获取小时
        minute=cal.get(Calendar.MINUTE);//获取分钟
    }
    /**
     * 作用:设置选中按钮颜色
     * @param name
     */
    private void setButtonColor(String name){
        switch (name){
            case "早餐":

                breakfast_btn.setBackgroundResource(R.drawable.button_red);//早餐

                break;
            case "午餐":
                lunch_btn.setBackgroundResource(R.drawable.button_red);//午餐

                break;
            case "晚餐":
                supper_btn.setBackgroundResource(R.drawable.button_red);//晚餐

                break;
            case "饮料水果":
                fruit_drink_btn.setBackgroundResource(R.drawable.button_red);//饮料水果

                break;
            case "买菜原料":
                buy_vegetables_btn.setBackgroundResource(R.drawable.button_red);//买菜

                break;
            case "打车":
                lift_btn.setBackgroundResource(R.drawable.button_red);//打车

                break;
            case "公交":
                bus_btn.setBackgroundResource(R.drawable.button_red);//公交

                break;
            case "加油":
                oil_btn.setBackgroundResource(R.drawable.button_red);//加油
                break;
        }
    }
    /**
     * 作用:支出类别名字转换成支出类别id
     * @param str
     * @return
     */
    private int TypeParseInt(String str) {
        int ex_type=0;
        switch (str){

            case "早餐":
                ex_type=0;
                break;
            case "午餐":
                ex_type=1;
                break;
            case "晚餐":
                ex_type=2;
                break;
            case "饮料水果":
                ex_type=3;
                break;
            case "买菜原料":
                ex_type=4;
                break;
            case "打车":
                ex_type=5;
                break;
            case "公交":
                ex_type=6;
                break;
            case "加油":
                ex_type=7;
                break;
        }
        return ex_type;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.choose_date://日期选择
                DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        chooseDate_tv.setText(year+"-"+(++month)+"-"+dayOfMonth+" "+hour+":"+minute);//选择日期
                    }
                };
                DatePickerDialog dialog=new DatePickerDialog(UpdateOutAccount.this, 0,listener,year,month,day);//后边三个参数为显示dialog时默认的日期，月份从0开始，0-11对应1-12个月
                dialog.show();
                break;
            case R.id.tick_save://保存操作
            case R.id.sava:
                //开启线程
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Bundle bundle=getIntent().getExtras();

                            double money=Double.parseDouble(money_edt.getText().toString().trim());
                            int ex_type=TypeParseInt(show_expend_type.getText().toString().trim());


                            String time_str=chooseDate_tv.getText().toString().trim();

                            int save_success= sava_income(expend.getEx_id(),expend.getUser_id(),money,ex_type,select_accountId,time_str);
                            if(save_success==1) {
                                //消息
                                Message message=new Message();
                                message.what=MSG_SAVE;
                                //发送消息
                                handler.sendMessage(message);
                            }
                        }catch (Exception e){
                            System.out.println(e.toString());
                        }

                    }
                }).start();
                break;
            case R.id.del://删除操作
                showDialog2("删除账单?");

                break;
            case R.id.go_back://返回上一页面
                UpdateOutAccount.this.finish();
                break;
            case R.id.expend_type_btn1:
                show_expend_type.setText("早餐");
                expendTypeImg.setImageDrawable(getResources().getDrawable(R.drawable.expend_breakfast));

                breakfast_btn.setBackgroundResource(R.drawable.button_red);//早餐
                lunch_btn.setBackgroundResource(R.drawable.button_white);//午餐
                supper_btn.setBackgroundResource(R.drawable.button_white);//晚餐
                fruit_drink_btn.setBackgroundResource(R.drawable.button_white);//饮料水果
                buy_vegetables_btn.setBackgroundResource(R.drawable.button_white);//买菜
                lift_btn.setBackgroundResource(R.drawable.button_white);//打车
                bus_btn.setBackgroundResource(R.drawable.button_white);//公交
                oil_btn.setBackgroundResource(R.drawable.button_white);//加油
                break;
            case R.id.expend_type_btn2:
                show_expend_type.setText("午餐");
                expendTypeImg.setImageDrawable(getResources().getDrawable(R.drawable.expend_lunch));

                breakfast_btn.setBackgroundResource(R.drawable.button_white);//早餐
                lunch_btn.setBackgroundResource(R.drawable.button_red);//午餐
                supper_btn.setBackgroundResource(R.drawable.button_white);//晚餐
                fruit_drink_btn.setBackgroundResource(R.drawable.button_white);//饮料水果
                buy_vegetables_btn.setBackgroundResource(R.drawable.button_white);//买菜
                lift_btn.setBackgroundResource(R.drawable.button_white);//打车
                bus_btn.setBackgroundResource(R.drawable.button_white);//公交
                oil_btn.setBackgroundResource(R.drawable.button_white);//加油
                break;
            case R.id.expend_type_btn3:
                show_expend_type.setText("晚餐");
                expendTypeImg.setImageDrawable(getResources().getDrawable(R.drawable.expend_supper));

                breakfast_btn.setBackgroundResource(R.drawable.button_white);//早餐
                lunch_btn.setBackgroundResource(R.drawable.button_white);//午餐
                supper_btn.setBackgroundResource(R.drawable.button_red);//晚餐
                fruit_drink_btn.setBackgroundResource(R.drawable.button_white);//饮料水果
                buy_vegetables_btn.setBackgroundResource(R.drawable.button_white);//买菜
                lift_btn.setBackgroundResource(R.drawable.button_white);//打车
                bus_btn.setBackgroundResource(R.drawable.button_white);//公交
                oil_btn.setBackgroundResource(R.drawable.button_white);//加油
                break;
            case R.id.expend_type_btn4:
                show_expend_type.setText("饮料水果");
                expendTypeImg.setImageDrawable(getResources().getDrawable(R.drawable.expend_fruit_drink));

                breakfast_btn.setBackgroundResource(R.drawable.button_white);//早餐
                lunch_btn.setBackgroundResource(R.drawable.button_white);//午餐
                supper_btn.setBackgroundResource(R.drawable.button_white);//晚餐
                fruit_drink_btn.setBackgroundResource(R.drawable.button_red);//饮料水果
                buy_vegetables_btn.setBackgroundResource(R.drawable.button_white);//买菜
                lift_btn.setBackgroundResource(R.drawable.button_white);//打车
                bus_btn.setBackgroundResource(R.drawable.button_white);//公交
                oil_btn.setBackgroundResource(R.drawable.button_white);//加油
                break;
            case R.id.expend_type_btn5:
                show_expend_type.setText("买菜原料");
                expendTypeImg.setImageDrawable(getResources().getDrawable(R.drawable.expend_buy_vegetables));

                breakfast_btn.setBackgroundResource(R.drawable.button_white);//早餐
                lunch_btn.setBackgroundResource(R.drawable.button_white);//午餐
                supper_btn.setBackgroundResource(R.drawable.button_white);//晚餐
                fruit_drink_btn.setBackgroundResource(R.drawable.button_white);//饮料水果
                buy_vegetables_btn.setBackgroundResource(R.drawable.button_red);//买菜
                lift_btn.setBackgroundResource(R.drawable.button_white);//打车
                bus_btn.setBackgroundResource(R.drawable.button_white);//公交
                oil_btn.setBackgroundResource(R.drawable.button_white);//加油
                break;
            case R.id.expend_type_btn6:
                show_expend_type.setText("打车");
                expendTypeImg.setImageDrawable(getResources().getDrawable(R.drawable.expend_lift));

                breakfast_btn.setBackgroundResource(R.drawable.button_white);//早餐
                lunch_btn.setBackgroundResource(R.drawable.button_white);//午餐
                supper_btn.setBackgroundResource(R.drawable.button_white);//晚餐
                fruit_drink_btn.setBackgroundResource(R.drawable.button_white);//饮料水果
                buy_vegetables_btn.setBackgroundResource(R.drawable.button_white);//买菜
                lift_btn.setBackgroundResource(R.drawable.button_red);//打车
                bus_btn.setBackgroundResource(R.drawable.button_white);//公交
                oil_btn.setBackgroundResource(R.drawable.button_white);//加油
                break;
            case R.id.expend_type_btn7:
                show_expend_type.setText("公交");
                expendTypeImg.setImageDrawable(getResources().getDrawable(R.drawable.expend_bus));

                breakfast_btn.setBackgroundResource(R.drawable.button_white);//早餐
                lunch_btn.setBackgroundResource(R.drawable.button_white);//午餐
                supper_btn.setBackgroundResource(R.drawable.button_white);//晚餐
                fruit_drink_btn.setBackgroundResource(R.drawable.button_white);//饮料水果
                buy_vegetables_btn.setBackgroundResource(R.drawable.button_white);//买菜
                lift_btn.setBackgroundResource(R.drawable.button_white);//打车
                bus_btn.setBackgroundResource(R.drawable.button_red);//公交
                oil_btn.setBackgroundResource(R.drawable.button_white);//加油
                break;
            case R.id.expend_type_btn8:
                show_expend_type.setText("加油");
                expendTypeImg.setImageDrawable(getResources().getDrawable(R.drawable.expend_oil));

                breakfast_btn.setBackgroundResource(R.drawable.button_white);//早餐
                lunch_btn.setBackgroundResource(R.drawable.button_white);//午餐
                supper_btn.setBackgroundResource(R.drawable.button_white);//晚餐
                fruit_drink_btn.setBackgroundResource(R.drawable.button_white);//饮料水果
                buy_vegetables_btn.setBackgroundResource(R.drawable.button_white);//买菜
                lift_btn.setBackgroundResource(R.drawable.button_white);//打车
                bus_btn.setBackgroundResource(R.drawable.button_white);//公交
                oil_btn.setBackgroundResource(R.drawable.button_red);//加油
                break;
        }
    }
}
