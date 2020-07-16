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
import com.my.account.entity.income.Income;
import com.my.account.entity.user.UserAccount;
import com.my.account.view.MoneyEditText;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UpdateInAccount extends Activity implements View.OnClickListener {
    //消息码
    private final int MSG_UPDATE_UI=0;//更新ui
    private final int MSG_SAVE=1;//保存
    private final int MSG_SAVE_FAIL=2;//保存失败
    private final int MSG_DELETE=3;//删除
    private Income income=null;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_UPDATE_UI:
                    try {
                        JSONObject jsonObject= (JSONObject) msg.obj;
                        String str=jsonObject.toJSONString();
                        JSONObject s= (JSONObject) jsonObject.get("income");
                        //------------------------------------------------
                        income= JSON.parseObject(s.toJSONString(),Income.class);

                        Double in_money=s.getDouble("in_money");
                        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        Date in_time=s.getDate("in_time");


                        //图标
                        incomeTypeImg.setImageResource(income.getIncomeType().getIncome_pngId());
                        //显示收入类别
                        show_income_type.setText(income.getIncomeType().getIncome_name());
                        //设置按钮颜色
                        setButtonColor(income.getIncomeType().getIncome_name());
                        //显示金额
                        money_edt.setText(String.valueOf(in_money));
                        //显示账户类型名
                        defaultAccount(income.getAccount().getAccount_name(),accountList);
                        //显示时间
                        chooseDate_tv.setText(String.valueOf(format.format(in_time)));

                    }catch (Exception e){
                        System.out.println(e.toString());
                    }

                    break;
                case MSG_SAVE://保存成功UI更新
                    try {
                        showDialog("保存成功");

                    }catch (Exception e){

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
    private Context context;
    private SpinnerAdapter spinnerAdapter;//下拉菜单适配器
    private Calendar cal;
    private ConnectionService service=null;
    private int year,month,day,hour,minute;
    private String select_accountName=null;
    private int select_accountId=0;//选中账户id


    private ImageView back_btn;//返回按钮
    private ImageView sava_imgv;//保存
    private ImageView incomeTypeImg;//显示收入类别图标
    private TextView show_income_type;//显示选择收入类别

    private Button pay_salary_btn;//工资薪水按钮
    private Button interest_btn;//利息按钮
    private Button part_time_btn;//兼职外快按钮
    private Button business_btn;//营业收入按钮
    private Button red_envelope_btn;//红包按钮
    private Button sales_btn;//销售款按钮
    private Button reimburse_btn;//退款按钮
    private Button dispatch_btn;//报销款按钮

    private Button save_btn;//保存按钮
    private Button del_btn;//删除按钮
    private Button choose_date_btn;//选择日期

    private Spinner account_sp;//选择账户

    private EditText remark_edt;//填写备注
    private MoneyEditText money_edt;//填写金额
    private TextView chooseDate_tv;//
    private ArrayList<UserAccount> accountList=null;//用户账户信息
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载布局
        setContentView(R.layout.update_income);
        init();//初始化
        getDate();
        /************************获取上个页面Bundle对象**********************************************/
        Bundle bundle=getIntent().getExtras();
        //获取账单id
        final String in_id= (String) bundle.get("in_id");
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
                    JSONObject incomeObject=showByBillId(user_id,in_id);
                    //通知主线程更新UI
                    Message message=new Message();
                    message.obj=incomeObject;
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


        save_btn.setOnClickListener(this);//保存按钮
        sava_imgv.setOnClickListener(this);//保存

        del_btn.setOnClickListener(this);//删除按钮
        back_btn.setOnClickListener(this);//返回按钮
        pay_salary_btn.setOnClickListener(this);//工资薪水按钮
        interest_btn.setOnClickListener(this);//利息按钮v
        part_time_btn.setOnClickListener(this);//兼职外快按钮
        business_btn.setOnClickListener(this);//营业收入按钮
        red_envelope_btn.setOnClickListener(this);//红包按钮
        sales_btn.setOnClickListener(this);//销售款按钮
        reimburse_btn.setOnClickListener(this);//退款按钮
        dispatch_btn.setOnClickListener(this);//报销款按钮
        choose_date_btn.setOnClickListener(this);//选择日期
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
                        UpdateInAccount.this.finish();
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
                                    int del_success= del_income(income.getIn_id(),income.getUser_id());
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

    /**
     * 默认选中
     * @param accountName
     * @param list
     */
    private void defaultAccount(String accountName,ArrayList<UserAccount> list){
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
        String op="IncomeServlet?method=del_income"
                +"&&in_id="+bill_id
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
        String op="IncomeServlet?method=update_income"
                +"&&in_id="+bill_id
                +"&&user_id="+user_id
                +"&&in_money="+money
                +"&&in_type="+in_type
                +"&&in_account_type="+in_account_type
                +"&&in_time="+time;
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
        String oper="IncomeServlet?method=view_income"
                + "&&user_id="+user_id
                + "&&in_id="+bill_id;

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
     * 初始化参数
     */
    public void init(){
        context=this;
        back_btn=findViewById(R.id.go_back);//返回按钮
        sava_imgv=findViewById(R.id.tick_save);

        incomeTypeImg=findViewById(R.id.income_type_img);
        show_income_type=findViewById(R.id.choose_income_type_txv);//显示选择收入类别
        pay_salary_btn=findViewById(R.id.income_type_btn1);//工资薪水按钮
        interest_btn=findViewById(R.id.income_type_btn2);//利息按钮
        part_time_btn=findViewById(R.id.income_type_btn3);//兼职外快按钮
        business_btn=findViewById(R.id.income_type_btn4);//营业收入按钮
        red_envelope_btn=findViewById(R.id.income_type_btn5);//红包按钮
        sales_btn=findViewById(R.id.income_type_btn6);//销售款按钮
        reimburse_btn=findViewById(R.id.income_type_btn7);//退款按钮
        dispatch_btn=findViewById(R.id.income_type_btn8);//报销款按钮
        save_btn=findViewById(R.id.sava);//保存按钮
        del_btn=findViewById(R.id.del);//删除按钮
        choose_date_btn=findViewById(R.id.choose_date);//选择日期

        account_sp=(Spinner) findViewById(R.id.acount_choose);//选择账户
      //  account_txtView=findViewById(R.id.account_name);//账户名

        remark_edt=findViewById(R.id.input_remark);//填写备注
        money_edt=findViewById(R.id.input_money);//填写金额
        chooseDate_tv=findViewById(R.id.show);
    }

    /**
     * 设置选中按钮颜色
     * @param name
     */
    private void setButtonColor(String name){
        switch (name){
            case "工资薪水":
                pay_salary_btn.setBackgroundResource(R.drawable.button_red);//工资薪水
                break;
            case "利息":
                interest_btn.setBackgroundResource(R.drawable.button_red);//利息
                break;
            case "兼职外快":
                part_time_btn.setBackgroundResource(R.drawable.button_red);//兼职外快
                break;
            case "营业收入":
                business_btn.setBackgroundResource(R.drawable.button_red);//营业收入
                break;
            case "红包":
                red_envelope_btn.setBackgroundResource(R.drawable.button_red);//红包
                break;
            case "销售额":
                sales_btn.setBackgroundResource(R.drawable.button_red);//销售额
                break;
            case "退款返款":
                reimburse_btn.setBackgroundResource(R.drawable.button_red);//退款返款
                break;
            case "报销款":
                dispatch_btn.setBackgroundResource(R.drawable.button_red);//报销额
                break;
        }
    }
    /**
     * 作用:收入类别名字转换成收入类别id
     * @param income_name
     * @return
     */
    private int TypeParseInt(String income_name){
        int in_type=0;
        switch (income_name){

            case "工资薪水":
                in_type=0;
                break;
            case "利息":
                in_type=1;
                break;
            case "兼职外快":
                in_type=2;
                break;
            case "营业收入":
                in_type=3;
                break;
            case "红包":
                in_type=4;
                break;
            case "销售额":
                in_type=5;
                break;
            case "退款返款":
                in_type=6;
                break;
            case "报销款":
                in_type=7;
                break;
        }
        return in_type;
    }

    /**
     * 获取当前日期
     */
    private void getDate(){
        cal=Calendar.getInstance();
        year=cal.get(Calendar.YEAR);//获取年
        month=cal.get(Calendar.MONTH);//获取月
        day=cal.get(Calendar.DAY_OF_MONTH);//获取日
        hour=cal.get(Calendar.HOUR);//获取小时
        minute=cal.get(Calendar.MINUTE);//获取分钟
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
            DatePickerDialog dialog=new DatePickerDialog(UpdateInAccount.this, 0,listener,year,month,day);//后边三个参数为显示dialog时默认的日期，月份从0开始，0-11对应1-12个月
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
                            int in_type=TypeParseInt(show_income_type.getText().toString().trim());


                            String time_str=chooseDate_tv.getText().toString().trim();

                            int save_success= sava_income(income.getIn_id(),income.getUser_id(),money,in_type,select_accountId,time_str);
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
                UpdateInAccount.this.finish();
                break;
            case R.id.income_type_btn1:
                show_income_type.setText("工资薪水");
                incomeTypeImg.setImageDrawable(getResources().getDrawable(R.drawable.income_pay_salary));

                pay_salary_btn.setBackgroundResource(R.drawable.button_red);//工资薪水
                interest_btn.setBackgroundResource(R.drawable.button_white);//利息
                part_time_btn.setBackgroundResource(R.drawable.button_white);//兼职外快
                business_btn.setBackgroundResource(R.drawable.button_white);//营业收入
                red_envelope_btn.setBackgroundResource(R.drawable.button_white);//红包
                sales_btn.setBackgroundResource(R.drawable.button_white);//销售额
                reimburse_btn.setBackgroundResource(R.drawable.button_white);//退款返款
                dispatch_btn.setBackgroundResource(R.drawable.button_white);//报销额

                break;
            case R.id.income_type_btn2:
                show_income_type.setText("利息");
                incomeTypeImg.setImageDrawable(getResources().getDrawable(R.drawable.income_interest));

                pay_salary_btn.setBackgroundResource(R.drawable.button_white);//工资薪水
                interest_btn.setBackgroundResource(R.drawable.button_red);//利息
                part_time_btn.setBackgroundResource(R.drawable.button_white);//兼职外快
                business_btn.setBackgroundResource(R.drawable.button_white);//营业收入
                red_envelope_btn.setBackgroundResource(R.drawable.button_white);//红包
                sales_btn.setBackgroundResource(R.drawable.button_white);//销售额
                reimburse_btn.setBackgroundResource(R.drawable.button_white);//退款返款
                dispatch_btn.setBackgroundResource(R.drawable.button_white);//报销额
                break;
            case R.id.income_type_btn3:
                show_income_type.setText("兼职外快");
                incomeTypeImg.setImageDrawable(getResources().getDrawable(R.drawable.income_part_time));
                pay_salary_btn.setBackgroundResource(R.drawable.button_white);//工资薪水
                interest_btn.setBackgroundResource(R.drawable.button_white);//利息
                part_time_btn.setBackgroundResource(R.drawable.button_red);//兼职外快
                business_btn.setBackgroundResource(R.drawable.button_white);//营业收入
                red_envelope_btn.setBackgroundResource(R.drawable.button_white);//红包
                sales_btn.setBackgroundResource(R.drawable.button_white);//销售额
                reimburse_btn.setBackgroundResource(R.drawable.button_white);//退款返款
                dispatch_btn.setBackgroundResource(R.drawable.button_white);//报销额

                break;
            case R.id.income_type_btn4:
                show_income_type.setText("营业收入");
                incomeTypeImg.setImageDrawable(getResources().getDrawable(R.drawable.income_business));
                pay_salary_btn.setBackgroundResource(R.drawable.button_white);//工资薪水
                interest_btn.setBackgroundResource(R.drawable.button_white);//利息
                part_time_btn.setBackgroundResource(R.drawable.button_white);//兼职外快
                business_btn.setBackgroundResource(R.drawable.button_red);//营业收入
                red_envelope_btn.setBackgroundResource(R.drawable.button_white);//红包
                sales_btn.setBackgroundResource(R.drawable.button_white);//销售额
                reimburse_btn.setBackgroundResource(R.drawable.button_white);//退款返款
                dispatch_btn.setBackgroundResource(R.drawable.button_white);//报销额
                break;
            case R.id.income_type_btn5:
                show_income_type.setText("红包");
                incomeTypeImg.setImageDrawable(getResources().getDrawable(R.drawable.income_red_envelope));
                pay_salary_btn.setBackgroundResource(R.drawable.button_white);//工资薪水
                interest_btn.setBackgroundResource(R.drawable.button_white);//利息
                part_time_btn.setBackgroundResource(R.drawable.button_white);//兼职外快
                business_btn.setBackgroundResource(R.drawable.button_white);//营业收入
                red_envelope_btn.setBackgroundResource(R.drawable.button_red);//红包
                sales_btn.setBackgroundResource(R.drawable.button_white);//销售额
                reimburse_btn.setBackgroundResource(R.drawable.button_white);//退款返款
                dispatch_btn.setBackgroundResource(R.drawable.button_white);//报销额
                break;
            case R.id.income_type_btn6:
                show_income_type.setText("销售额");
                incomeTypeImg.setImageDrawable(getResources().getDrawable(R.drawable.income_sales));
                pay_salary_btn.setBackgroundResource(R.drawable.button_white);//工资薪水
                interest_btn.setBackgroundResource(R.drawable.button_white);//利息
                part_time_btn.setBackgroundResource(R.drawable.button_white);//兼职外快
                business_btn.setBackgroundResource(R.drawable.button_white);//营业收入
                red_envelope_btn.setBackgroundResource(R.drawable.button_white);//红包
                sales_btn.setBackgroundResource(R.drawable.button_red);//销售额
                reimburse_btn.setBackgroundResource(R.drawable.button_white);//退款返款
                dispatch_btn.setBackgroundResource(R.drawable.button_white);//报销额
                break;
            case R.id.income_type_btn7:
                show_income_type.setText("退款返款");
                incomeTypeImg.setImageDrawable(getResources().getDrawable(R.drawable.income_reimburse));
                pay_salary_btn.setBackgroundResource(R.drawable.button_white);//工资薪水
                interest_btn.setBackgroundResource(R.drawable.button_white);//利息
                part_time_btn.setBackgroundResource(R.drawable.button_white);//兼职外快
                business_btn.setBackgroundResource(R.drawable.button_white);//营业收入
                red_envelope_btn.setBackgroundResource(R.drawable.button_white);//红包
                sales_btn.setBackgroundResource(R.drawable.button_white);//销售额
                reimburse_btn.setBackgroundResource(R.drawable.button_red);//退款返款
                dispatch_btn.setBackgroundResource(R.drawable.button_white);//报销额
                break;
            case R.id.income_type_btn8:
                show_income_type.setText("报销款");
                incomeTypeImg.setImageDrawable(getResources().getDrawable(R.drawable.income_dispatch));
                pay_salary_btn.setBackgroundResource(R.drawable.button_white);//工资薪水
                interest_btn.setBackgroundResource(R.drawable.button_white);//利息
                part_time_btn.setBackgroundResource(R.drawable.button_white);//兼职外快
                business_btn.setBackgroundResource(R.drawable.button_white);//营业收入
                red_envelope_btn.setBackgroundResource(R.drawable.button_white);//红包
                sales_btn.setBackgroundResource(R.drawable.button_white);//销售额
                reimburse_btn.setBackgroundResource(R.drawable.button_white);//退款返款
                dispatch_btn.setBackgroundResource(R.drawable.button_red);//报销额
                break;
        }
    }
}
