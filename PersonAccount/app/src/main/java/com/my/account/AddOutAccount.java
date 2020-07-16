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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.my.account.Service.ConnectionService;
import com.my.account.adapter.SpinnerAdapter;
import com.my.account.entity.SpinnerEntity;
import com.my.account.entity.user.User;
import com.my.account.entity.user.UserAccount;
import com.my.account.view.MoneyEditText;

import org.codehaus.jackson.map.ObjectMapper;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * 新增支出页面
 */
public class AddOutAccount extends Activity implements View.OnClickListener {
    //消息码
    private final int MSG_ADD_UI=0;//添加成功
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_ADD_UI:
                    showDialog();
                    break;
            }
        }
    };
    private SpinnerAdapter adapter;//下拉菜单适配器
    private Calendar cal;
    private ConnectionService service=null;
    private int year,month,day,hour,minute;//年-月-日\ 小时:分钟
    private String select_accountName=null;//选中账户名
    private int select_accountId=0;//选中账户id

    private LinearLayout back_btn;//返回按钮
    private ImageView sava_imgv;//保存

    private ImageView expendTypeImg;//显示支出类别图标
    private TextView show_expend_type;//显示选择收入类别
    private Button breakfast_btn;//早餐按钮
    private Button lunch_btn;//午餐按钮
    private Button supper_btn;//晚餐按钮
    private Button  fruit_drink_btn;//饮料水果按钮
    private Button buy_vegetables_btn;//买菜
    private Button lift_btn;//打车
    private Button bus_btn;//公交
    private Button oil_btn;//加油

    private Button save_btn;//保存按钮
    private Button choose_date_btn;//选择日期
    private Spinner account_sp;//选择账户
    private EditText remark_edt;//填写备注
    private MoneyEditText money_edt;//填写金额
    private TextView chooseDate_tv;//
    private ArrayList<UserAccount> accountList=null;//用户账户信息
    private ObjectMapper mapper=null;

    private int user_id;//用户id
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载布局
        setContentView(R.layout.add_out_account);
        //初始化控件
        init();
        //获取当前时间
        getDate();

        Bundle bundle=getIntent().getExtras();
        //用户id
        user_id=bundle.getInt("user_id");
        //获取用户信息
        String user_json= (String) bundle.get("user");
        User user1= JSON.parseObject(user_json,User.class);
        accountList= (ArrayList<UserAccount>) user1.getAccountList();

        //设置Spinner 适配器
        ArrayList<SpinnerEntity> spinnerEntities=new ArrayList<SpinnerEntity>();
        for(int i=0;i<accountList.size();i++){
            SpinnerEntity entity=new SpinnerEntity();
            entity.setAccount_name(accountList.get(i).getAccount_name());
            entity.setId(accountList.get(i).getId());
            spinnerEntities.add(entity);
        }
        adapter=new SpinnerAdapter(spinnerEntities,this);
        account_sp.setAdapter(adapter);
        //获取Spinner选中值
        account_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerEntity entity= (SpinnerEntity) adapter.getItem(position);
                select_accountId= entity.getId();
                select_accountName= entity.getAccount_name();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //点击事件
        save_btn.setOnClickListener(this);//保存按钮
        sava_imgv.setOnClickListener(this);

        back_btn.setOnClickListener(this);//返回按钮
        breakfast_btn.setOnClickListener(this);//早餐
        lunch_btn.setOnClickListener(this);//午餐
        supper_btn.setOnClickListener(this);//晚餐
        fruit_drink_btn.setOnClickListener(this);//饮料水果

        buy_vegetables_btn.setOnClickListener(this);//买菜
        lift_btn.setOnClickListener(this);//打车
        bus_btn.setOnClickListener(this);//公交
        oil_btn.setOnClickListener(this);//加油
        choose_date_btn.setOnClickListener(this);//选择日期
    }

    /**
     * 消息提示框
     */
    private void showDialog(){
        new AlertDialog.Builder(this)
                .setMessage("添加成功")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AddOutAccount.this.finish();
                    }
                })
                .show();
    }

    /**
     * 获取当前时间
     */
    private void getDate() {
        cal=Calendar.getInstance();
        year=cal.get(Calendar.YEAR);//获取年
        month=cal.get(Calendar.MONTH);//获取月
        day=cal.get(Calendar.DAY_OF_MONTH);//获取日
        hour=cal.get(Calendar.HOUR);//获取小时
        minute=cal.get(Calendar.MINUTE);//获取分钟
    }

    /**
     * 初始化
     */
    public void init() {
        back_btn=findViewById(R.id.go_back);//返回
        sava_imgv=findViewById(R.id.tick_save);//保存

        expendTypeImg=findViewById(R.id.expend_type_img);//类别图标
        show_expend_type=findViewById(R.id.choose_expend_type_txv);//显示类别名
        money_edt=findViewById(R.id.input_money);//输入金额
        //选择支出类别按钮
        breakfast_btn=findViewById(R.id.expend_type_btn1);//早餐
        lunch_btn=findViewById(R.id.expend_type_btn2);//午餐
        supper_btn=findViewById(R.id.expend_type_btn3);//晚餐
        fruit_drink_btn=findViewById(R.id.expend_type_btn4);//饮料水果
        buy_vegetables_btn=findViewById(R.id.expend_type_btn5);//买菜
        lift_btn=findViewById(R.id.expend_type_btn6);//打车
        bus_btn=findViewById(R.id.expend_type_btn7);//公交
        oil_btn=findViewById(R.id.expend_type_btn8);//加油

        System.out.println("-");
        remark_edt=findViewById(R.id.input_remark);//填写备注
        System.out.println("-");

        //操作按钮
        save_btn=findViewById(R.id.sava);//保存
        choose_date_btn=findViewById(R.id.choose_date);//选择日期
        account_sp=findViewById(R.id.acount_choose);//选择账户
        chooseDate_tv=findViewById(R.id.show);
    }

    /**
     * 作用:添加一条支出账单
     * @param user_id
     * @return
     * @throws Exception
     */
    public int add_expend_oper(int user_id) throws Exception{
        String str=show_expend_type.getText().toString().trim();
        int money=Integer.parseInt(money_edt.getText().toString().trim());//输入金额
        int ex_type=typeParseInt(str);//支出类型
        int ex_account_type=select_accountId;//账户类型
        String time=chooseDate_tv.getText().toString().trim();

        String add_expend="ExpendServlet?" +
                "method=insert_expend" +
                "&&user_id=" +user_id+
                "&&ex_money="+money+
                "&&ex_type="+ex_type+
                "&&ex_account_type="+ex_account_type+
                "&&ex_time="+time;

        String result=null;//服务器发来的json串
        //初始化
        service=new ConnectionService();
        result=service.HttpGET(add_expend);

        JSONObject object= JSON.parseObject(result);
        int add_success=Integer.parseInt(object.getString("insert_sucess"));

        return add_success ;
    }

    /**
     * 转换
     * @param str
     * @return
     */
    private int typeParseInt(String str) {
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
                DatePickerDialog dialog=new DatePickerDialog(AddOutAccount.this, 0,listener,year,month,day);//后边三个参数为显示dialog时默认的日期，月份从0开始，0-11对应1-12个月
                dialog.show();
                break;
            case R.id.tick_save://保存
            case R.id.sava:
                //开启线程
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            int add_success=add_expend_oper(user_id);
                            if(add_success==1) {
                                //消息
                                Message message=new Message();
                                message.what=MSG_ADD_UI;
                                //发送消息
                                handler.sendMessage(message);
                            }




                        }catch (Exception e){
                            System.out.println(e.toString());
                        }

                    }
                }).start();
                break;
            case R.id.go_back://返回上一页面
                AddOutAccount.this.finish();
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

    /**
     * 获取图片id
     * @param imageName
     * @return
     */
    public int getResourceId(String imageName){
        Context ctx=getBaseContext();
        int resId = getResources().getIdentifier(imageName, "drawable" , ctx.getPackageName());
        return resId;
    }
}
