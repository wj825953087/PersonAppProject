package com.my.account;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.my.account.Service.ConnectionService;
import com.my.account.adapter.BillDetailsAdapter;
import com.my.account.entity.BillDetailsEntity;
import com.my.account.entity.expend.Expend;
import com.my.account.entity.income.Income;
import com.my.account.entity.user.UserAccount;
import com.my.account.utils.Precision;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

/**
 * 账单明细
 */
public class BillDetails extends Activity implements View.OnClickListener {
    private BillDetailsAdapter adapter=null;
    private List<BillDetailsEntity> entities=null;
    private BillDetailsEntity entity=null;
    private ConnectionService income_service=null;
    private ConnectionService expend_service=null;
    private Context txContext;

    private Spinner date_sp;//选择时间下拉框

    private ListView listView;//显示收入和支出信息
    private ImageView GoBackKey;//返回键
    private TextView ChooseTimeKey;//选择时间按钮
//    private ImageView SearchKey;//搜索键
//    private ImageView MoreKey;//更多键
    private TextView IncomeText;//某段时间收入总和
    private TextView ExpendText;//某段时间支出总和
    private TextView SurplusText;//某段时间结余
    private ImageView DetailedAnalysis;//明细分析
    //消息码
    private final int MSG_UPDATE_UI=0;//全部时间:更新UI
    private final int MSG_UPDATE_TIME_UI=1;//时间条件更新

    private Calendar cal;
    private int year,month,day,hour,minute;//年/月/日/ 小时 : 分钟
    private double incomeMoneySum,expendMoneySum,surplusMoney;
    private String StarTime,EndTime;//开始时间,结束时间
    private int user_id;//用户id

    private Handler handler=new Handler(){
       @Override
       public void handleMessage(@NonNull Message msg) {
           super.handleMessage(msg);
           switch (msg.what){
               case MSG_UPDATE_TIME_UI: //时间条件更新
                   //获取数据
                   try {
                       ObjectMapper mapper=new ObjectMapper();
                       Map<String,Object> map2= (Map<String, Object>) msg.obj;

                       String incomeStr2= (String) map2.get("incomeStr");
                       String expendStr2= (String) map2.get("expendStr");
                       JSONObject ob1= JSON.parseObject(incomeStr2);
                       JSONObject ob2= JSON.parseObject(expendStr2);
                       incomeMoneySum=ob1.getDouble("income_money_sum");//收入金额之和
                       expendMoneySum=ob2.getDouble("expend_money_sum");//支出金额之和
                       surplusMoney= Precision.sub(incomeMoneySum,expendMoneySum);
                       /*--------------------------显示某段时间内的收入支出结余之和-------------------------------*/
                       IncomeText.setText(String.valueOf(incomeMoneySum));
                       ExpendText.setText(String.valueOf(expendMoneySum));
                       SurplusText.setText(String.valueOf (surplusMoney));
                       /*---------------------------显示某段时间内的支出和消费列---------------------------------*/
                       /*用户收入全部信息*/
                       JSONArray array_income=ob1.getJSONArray("income_list");
                       /*用户支出全部信息*/
                       JSONArray array_expend=ob2.getJSONArray("expend_list");
                       //1.JSONArray转成String 类型
                       String inAccount= JSONObject.toJSONString(array_income);
                       String outAccount= JSONObject.toJSONString(array_expend);
                       //2.String类型再转List
                       List<Income>  incomes= (List<Income>) mapper.readValue(inAccount, new TypeReference<List<Income>>(){});
                       List<Expend>  expends= (List<Expend>) mapper.readValue(outAccount, new TypeReference<List<Expend>>(){});
                       entities=new ArrayList<BillDetailsEntity>();
                       SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
                       //收入
                       for (int i=0;i<incomes.size();i++){
                           entity=new BillDetailsEntity();
                           entity.setId(incomes.get(i).getIn_id());
                           entity.setBill_type("收入");
                           entity.setImage_id(incomes.get(i).getIncomeType().getIncome_pngId());
                           entity.setTypename(String.valueOf(incomes.get(i).getIncomeType().getIncome_name()));
                           entity.setMoney(incomes.get(i).getIn_money());
                           entity.setTime(sf.format(incomes.get(i).getIn_time()));
                           entities.add(entity);
                       }
                       //支出
                       for(int i=0;i<expends.size();i++){
                           entity=new BillDetailsEntity();
                           entity.setId(expends.get(i).getEx_id());
                           entity.setBill_type("支出");
                           entity.setImage_id(expends.get(i).getExpendType().getExpend_pngId());
                           entity.setTypename(String.valueOf(expends.get(i).getExpendType().getExpend_name()));
                           entity.setMoney(expends.get(i).getEx_money());
                           entity.setTime(sf.format(expends.get(i).getEx_time()));
                           entities.add(entity);
                       }

                       //适配器初始化
                       adapter=new BillDetailsAdapter(txContext,entities);
                       listView.setAdapter(adapter);
                       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                           @Override
                           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                               BillDetailsEntity e=(BillDetailsEntity)adapter.getItem(position);
                               switch (e.getBill_type()){
                                   case "收入":
                                       Intent intent=new Intent(BillDetails.this, UpdateInAccount.class);
                                       //传个账单id给更新页面
                                       String in_id=e.getId();
                                       double money=e.getMoney();

                                       //将id存进 Bundle 对象中
                                       Bundle bundle=getIntent().getExtras();
                                       bundle.putString("in_id",in_id);
                                       intent.putExtras(bundle);

                                       startActivity(intent);

                                       Toast.makeText(BillDetails.this, position + "号位置的条目被点击", Toast.LENGTH_SHORT).show();
                                       break;
                                   case "支出":
                                       Intent intent1=new Intent(BillDetails.this,UpdateOutAccount.class);
                                       //传个账单id给更新页面
                                       String ex_id=e.getId();
                                       double money2=e.getMoney();
                                       //将id存进 Bundle 对象中
                                       Bundle bundle2=getIntent().getExtras();
                                       bundle2.putString("ex_id",ex_id);
                                       intent1.putExtras(bundle2);

                                       startActivity(intent1);
                                       Toast.makeText(BillDetails.this, position + "号位置的条目被点击", Toast.LENGTH_SHORT).show();
                                       break;
                               }

                           }
                       });
                   }catch (Exception e){
                       System.out.println(e.toString());
                   }

                   break;
               case MSG_UPDATE_UI://显示全部时间段的信息
                   try {
                   ObjectMapper mapper=new ObjectMapper();
                    /*-------------------------------------------------------------------------------------*/
                   Map<String,Object> map= (Map<String, Object>) msg.obj;
                   JSONObject object_income= (JSONObject) map.get("object_income");
                   JSONObject object_expend=(JSONObject)map.get("object_expend");

                  incomeMoneySum= object_income.getDouble("income_money_sum");//收入金额之和
                  expendMoneySum=object_expend.getDouble("expend_money_sum");//支出金额之和
                  surplusMoney= Precision.sub(incomeMoneySum,expendMoneySum);
                  /*显示全部时间内的收入支出结余之和*/
                  IncomeText.setText(String.valueOf(incomeMoneySum));
                  ExpendText.setText(String.valueOf(expendMoneySum));
                  SurplusText.setText(String.valueOf (surplusMoney));
                  /*-------------------------------------------------------------------------------------*/

                  /*---------------------------显示全部时间内的支出和消费列---------------------------------*/
                  /*用户收入全部信息*/
                   JSONArray array_income=object_income.getJSONArray("income_list");
                  /*用户支出全部信息*/
                   JSONArray array_expend=object_expend.getJSONArray("expend_list");
                   //1.JSONArray转成String 类型
                   String incomeStr= JSONObject.toJSONString(array_income);
                   String expendStr= JSONObject.toJSONString(array_expend);

                   //2.String类型再转List
                   List<Income>  incomes= (List<Income>) mapper.readValue(incomeStr, new TypeReference<List<Income>>(){});
                   List<Expend> expends   =(List<Expend>) mapper.readValue(expendStr, new TypeReference<List<Expend>>(){});

                   entities=new ArrayList<BillDetailsEntity>();
                   SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
                   //收入
                   for (int i=0;i<incomes.size();i++){
                        entity=new BillDetailsEntity();
                        entity.setId(incomes.get(i).getIn_id());
                        entity.setBill_type("收入");
                        entity.setImage_id(incomes.get(i).getIncomeType().getIncome_pngId());
                        entity.setTypename(String.valueOf(incomes.get(i).getIncomeType().getIncome_name()));
                        entity.setMoney(incomes.get(i).getIn_money());
                        entity.setTime(sf.format(incomes.get(i).getIn_time()));
                        entities.add(entity);
                   }
                    //支出
                   for(int i=0;i<expends.size();i++){
                       entity=new BillDetailsEntity();
                       entity.setId(expends.get(i).getEx_id());
                       entity.setBill_type("支出");
                       entity.setImage_id(expends.get(i).getExpendType().getExpend_pngId());
                       entity.setTypename(String.valueOf(expends.get(i).getExpendType().getExpend_name()));
                       entity.setMoney(expends.get(i).getEx_money());
                       entity.setTime(sf.format(expends.get(i).getEx_time()));
                       entities.add(entity);
                   }
                   //适配器初始化
                   adapter=new BillDetailsAdapter(txContext,entities);
                   listView.setAdapter(adapter);
                   listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                       @Override
                       public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                           BillDetailsEntity e=(BillDetailsEntity)adapter.getItem(position);
                           switch (e.getBill_type()){
                               case "收入":
                                   Intent intent=new Intent(BillDetails.this, UpdateInAccount.class);
                                   //传个账单id给更新页面
                                   String in_id=e.getId();
                                   double money=e.getMoney();

                                   //将id存进 Bundle 对象中
                                   Bundle bundle=getIntent().getExtras();
                                   bundle.putString("in_id",in_id);
                                   intent.putExtras(bundle);

                                   startActivity(intent);

                                   Toast.makeText(BillDetails.this, position + "号位置的条目被点击", Toast.LENGTH_SHORT).show();
                                   break;
                               case "支出":
                                   Intent intent1=new Intent(BillDetails.this,UpdateOutAccount.class);
                                   //传个账单id给更新页面
                                   String ex_id=e.getId();
                                   double money2=e.getMoney();
                                   //将id存进 Bundle 对象中
                                   Bundle bundle2=getIntent().getExtras();
                                   bundle2.putString("ex_id",ex_id);
                                   intent1.putExtras(bundle2);

                                   startActivity(intent1);
                                   Toast.makeText(BillDetails.this, position + "号位置的条目被点击", Toast.LENGTH_SHORT).show();
                                   break;
                           }

                       }
                   });
                   /*-------------------------------------------------------------------------------------*/
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
        setContentView(R.layout.bill_detail);

        //初始化
        init();
        //获取当前时间
        getDate();
        //------------------------------------------------------------------------------------------------------------
        //接收Bundle
        Bundle bundle=getIntent().getExtras();
        try{
            StarTime=bundle.getString("star_time");
            EndTime=bundle.getString("end_time");
            if(StarTime!=null){
                ChooseTimeKey.setText(StarTime);
            }
            user_id=bundle.getInt("user_id");


        }catch (Exception e){
            System.out.println(e.toString());
        }

        /*开启线程*/
        new Thread(new Runnable() {
            @Override
            public void run() {
                //加载数据
                try {
                    Map<String,Object> map=new HashMap<String,Object>();
                    //连接服务器
                    if(ChooseTimeKey.getText().toString().trim().equals("全部时间")){

                        JSONObject object_income=query_income(user_id);
                        JSONObject object_expend=query_expend(user_id);

                        map.put("object_income",object_income);
                        map.put("object_expend",object_expend);

                        //消息
                        Message message=new Message();
                        message.what=MSG_UPDATE_UI;
                        message.obj=map;
                        //发送消息
                        handler.sendMessage(message);
                    }else {

                        String income_str=query_income_condition(user_id,StarTime,EndTime);
                        String expend_str=query_expend_condition(user_id,StarTime,EndTime);

                        map.put("incomeStr",income_str);
                        map.put("expendStr",expend_str);
                        //消息
                        Message message=new Message();
                        message.what=MSG_UPDATE_TIME_UI;
                        message.obj=map;
                        //发送消息
                        handler.sendMessage(message);

                    }


                }catch (Exception e){
                    System.out.println(e.toString());
                }
            }
        }).start();

    //点击事件
    GoBackKey.setOnClickListener(this);//返回按钮点击事件
    ChooseTimeKey.setOnClickListener(this);//选择时间点击事件
//    SearchKey.setOnClickListener(this);//搜索点击事件
    DetailedAnalysis.setOnClickListener(this);//明细分析点击事件


    }

    @Override
    protected void onResume() {
        super.onResume();
        onCreate(null);
    }

    /*
     * 初始化
     */
    public void init(){

        txContext=this;
        listView=findViewById(R.id.listview);
        GoBackKey=findViewById(R.id.go_back);//返回
        ChooseTimeKey=findViewById(R.id.choose_time);//选择时间
//        SearchKey=findViewById(R.id.search);//搜索
//        MoreKey=findViewById(R.id.more);//更多

        IncomeText=findViewById(R.id.income_money_sum_time);//收入
        ExpendText=findViewById(R.id.expend_money_sum_time);//支出
        SurplusText=findViewById(R.id.surplus_money_sum_time);//结余
        DetailedAnalysis=findViewById(R.id.detailed_analysis);//明细分析

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



    /**
     * 某个时间范围内用户的收入金额之和,用户收入信息
     * @param user_id
     * @param starTime
     * @param endTime
     * @return
     * @throws Exception
     */
    public String query_income_condition(int user_id,String starTime,String endTime) throws  Exception {

            String op = "IncomeServlet?method=query_income_time"
                    + "&&user_id=" + user_id
                    + "&&start_time=" + starTime + "-1"
                    + "&&end_time=" + endTime + "-1";
            String jsonStr = null;
            //初始化
            income_service = new ConnectionService();
            jsonStr = income_service.HttpGET(op);
           return jsonStr;
    }

    /**
     * 某个时间范围内用户的支出金额之和,用户支出信息
     * @param user_id
     * @param starTime
     * @param endTime
     * @return
     * @throws Exception
     */
    public String query_expend_condition(int user_id,String starTime,String endTime) throws Exception{
        String op="ExpendServlet?method=query_expend_time"
                +"&&user_id="+user_id
                +"&&start_time="+starTime+"-1"
                +"&&end_time="+endTime+"-1";
        String jsonStr=null;
        //初始化
        expend_service=new ConnectionService();
        jsonStr=expend_service.HttpGET(op);
        return jsonStr;
    }
    /**
     * 全部时间内,查询用户收入金额之和,用户收入信息
     * @param user_id
     * @return
     * @throws Exception
     */
    public JSONObject query_income(int user_id) throws Exception {
        String oper_income="IncomeServlet?method=query_income&&user_id="+user_id;
        String result_income=null;//从服务器获取的JSON字符串
        //初始化
        income_service=new ConnectionService();
        result_income=income_service.HttpGET(oper_income);

        if (result_income!=null)
            return JSON.parseObject(result_income);
        return null;
    }
    /**
     * 全部时间内,查询用户支出金额之和,用户支出信息
     * @param user_id
     * @return
     * @throws Exception
     */
    public JSONObject query_expend(int user_id) throws Exception {
        String oper_expend="ExpendServlet?method=query_expend&&user_id="+user_id;
        String result_expend=null;//从服务器获取的JSON字符串
        expend_service=new ConnectionService();
        result_expend=expend_service.HttpGET(oper_expend);
        if(result_expend!=null)
            return JSON.parseObject(result_expend);
        return null;
    }

    /**
     * 遍历方法查找DatePicker里的子控件：年、月、日
     * @param group
     * @return
     */
    private DatePicker findDatePicker(ViewGroup group) {
        if (group != null) {
            for (int i = 0, j = group.getChildCount(); i < j; i++) {
                View child = group.getChildAt(i);
                if (child instanceof DatePicker) {
                    return (DatePicker) child;
                } else if (child instanceof ViewGroup) {
                    DatePicker result = findDatePicker((ViewGroup) child);
                    if (result != null)
                        return result;
                }
            }
        }
        return null;
    }


    @Override
    public void onClick(View v) {
        Bundle bundle=getIntent().getExtras();
        switch (v.getId()){
            case R.id.choose_time://选择时间
                Calendar calendar=Calendar.getInstance();
                DatePickerDialog dialog=new DatePickerDialog(this, DatePickerDialog.THEME_HOLO_DARK, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        ChooseTimeKey.setText(year+"-"+(++month));
                        Intent intent=new Intent(BillDetails.this,BillDetails.class);
                        Bundle bundle=getIntent().getExtras();
                        bundle.putString("star_time",ChooseTimeKey.getText().toString().trim());
                        bundle.putString("end_time",year+"-"+(month+1));
                        intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtras(bundle);

                        startActivity(intent);
                    }
                },calendar.get(Calendar.YEAR)
                        ,calendar.get(Calendar.MONTH)
                        ,calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
                //
                DatePicker dp = findDatePicker((ViewGroup) dialog.getWindow().getDecorView());
                if (dp != null) {
                    ((ViewGroup)((ViewGroup) dp.getChildAt(0)).getChildAt(0)).getChildAt(2).setVisibility(View.GONE);
                }


                break;
            case R.id.detailed_analysis://明细分析
                Intent intent=new Intent(BillDetails.this,DetailedAnalysis.class);

                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.go_back://返回键
                BillDetails.this.finish();
                break;
        }
    }


}

