package com.my.account;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.my.account.Service.ConnectionService;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Random;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;


public class DetailedAnalysis extends Activity implements View.OnClickListener {

    private Context context;
    private ConnectionService service=null;
    private Calendar cal;
    //消息码
    private final int MSG_UPDATE_UI=0;//全部时间:更新UI
    private final int MSG_UPDATE_TIME_UI=1;//时间条件更新

    private  GraphicalView view=null;//收入图表
    private  GraphicalView view2=null;//支出图表

    private ImageView go_back_imgv,share_imgv;//返回\分享
    private TextView show_time_txt;//显示时间
    private LinearLayout expend_pie_chart,income_pie_chart;//支出饼状图,收入饼状图
    private TextView expend_txt,income_txt;//支出,收入


    private double PaySalaryMoney,InterestMoney,PartTimeMoney;//工资薪水,利息,兼职外快

    private double TakingMoney,RedEnvelopeMoney,SalesMoney;//营业收入,红包,销售额
    private double RefundMoney,ReimburseMoney;//退款返款,报销款

    private double BreakfastMoney,LunchMoney,DinnerMoney;//早餐中餐晚餐
    private double DrinkAndFruitMoney,BuyFoodMoney,TaxiMoney;//饮料水果, 买菜原料 ,打车
    private double BusMoney,OilMoney;//公交 ,加油
    private int noEmptyInAccount,noEmptyOutAccout;//非空个数
    private double incomeMoneySum,expendMoneySum;//收入金额之和,支出金额之和
    private  int year,month,day,hour,minute;//年月日小时分钟
    private String StarTime=null,EndTime=null;//开始时间,结束时间
    private int user_id;


    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case MSG_UPDATE_UI: //全部时间更新
                    //收入图表
                    view= ChartFactory.getPieChartView(context,getData(),getRenderer(noEmptyInAccount));
                    //支出图表
                    view2=ChartFactory.getPieChartView(context,getDate2(),getRenderer(noEmptyOutAccout));
                    expend_pie_chart.addView(view2);
                    income_pie_chart.addView(view);
                    break;
                case MSG_UPDATE_TIME_UI:
                    Toast.makeText(context,"时间:"+StarTime+"-"+EndTime,Toast.LENGTH_SHORT).show();
                    //时间条件更新
                    //收入图表
                    if(noEmptyInAccount==0){
                        income_txt.setVisibility(View.VISIBLE);
                    }else{
                        income_txt.setVisibility(View.GONE);
                        view= ChartFactory.getPieChartView(context,getData(),getRenderer(noEmptyInAccount));
                        income_pie_chart.addView(view);
                    }

                    //支出图表
                    if(noEmptyOutAccout==0){
                        expend_txt.setVisibility(View.VISIBLE);
                    }else {
                        expend_txt.setVisibility(View.GONE);
                        view2=ChartFactory.getPieChartView(context,getDate2(),getRenderer(noEmptyOutAccout));
                        expend_pie_chart.addView(view2);
                    }




                    break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载布局
        setContentView(R.layout.detailed_analysis);
        //初始化
        init();
        //获取当前时间
        getDate();
        //接收Bundle
        Bundle bundle=getIntent().getExtras();//获取数据
        try {
            user_id= (int) bundle.get("user_id");//用户id
            StarTime=bundle.getString("star_time");
            EndTime=bundle.getString("end_time");
            if(StarTime!=null)show_time_txt.setText(StarTime);
        }catch (Exception e){
            System.out.println(e.toString());
        }

        //-----------------------------------------------------------
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{

                    if (show_time_txt.getText().toString().trim().equals("全部时间")){
                        LoadData(false,user_id);//加载全部时间数据

                        //发消息
                        Message message=new Message();
                        message.what=MSG_UPDATE_UI;
                        handler.sendMessage(message);
                    }else{
                        LoadData(true,user_id);
                        //发消息
                        Message message=new Message();
                        message.what=MSG_UPDATE_TIME_UI;
                        handler.sendMessage(message);
                    }

                }catch (Exception e){
                    System.out.println(e.toString());
                }

            }
        }).start();
        //绑定监听
        go_back_imgv.setOnClickListener(this);//返回
        share_imgv.setOnClickListener(this);//分享
        show_time_txt.setOnClickListener(this);//选择时间
    }


    /**
     * 初始化
     */
    public void init(){
        context=this;
        expend_txt=findViewById(R.id.expendTxt);
        income_txt=findViewById(R.id.incomeTxt);

        go_back_imgv=findViewById(R.id.go_back);
        share_imgv=findViewById(R.id.share);
        show_time_txt=findViewById(R.id.time);
        expend_pie_chart=findViewById(R.id.expend_pie_chart);
        income_pie_chart=findViewById(R.id.income_pie_chart);

    }
    /**
     * 获取当前日期
     */
    private void getDate(){
        cal= Calendar.getInstance();
        year=cal.get(Calendar.YEAR);//获取年
        month=cal.get(Calendar.MONTH);//获取月
        day=cal.get(Calendar.DAY_OF_MONTH);//获取日
        hour=cal.get(Calendar.HOUR);//获取小时
        minute=cal.get(Calendar.MINUTE);//获取分钟
    }

//    /**
//     * 全部时间内,查询用户收入金额之和,用户收入信息
//     * @param user_id
//     * @return
//     * @throws Exception
//     */
//    public JSONObject query_income(int user_id)throws Exception{
//        String oper_income="IncomeServlet?method=query_income"
//                + "&&user_id="+user_id;
//        String result_income=null;//从服务器获取的JSON字符串
//        //初始化
//        service=new ConnectionService();
//        result_income=service.HttpGET(oper_income);
//
//        if (result_income!=null)
//            return JSON.parseObject(result_income);
//        return null;
//    }
//    /**
//     * 全部时间内,查询用户支出金额之和,用户支出信息
//     * @param user_id
//     * @return
//     * @throws Exception
//     */
//    public JSONObject query_expend(int user_id) throws Exception {
//        String oper_expend="ExpendServlet?method=query_expend"
//                + "&&user_id="+user_id;
//        String result_expend=null;//从服务器获取的JSON字符串
//        service=new ConnectionService();
//        result_expend=service.HttpGET(oper_expend);
//        if(result_expend!=null)
//            return JSON.parseObject(result_expend);
//        return null;
//    }

    /**
     * 统计支出各个类别金额和
     * 全部时间范围内
     * @param user_id
     * @return
     * @throws Exception
     */
    public JSONObject query_count_expend(int user_id)throws Exception{
        String op="ExpendServlet?method=query_expend_type"
                + "&&user_id="+user_id;
        String json=null;
        service=new ConnectionService();
        json=service.HttpGET(op);
        if(json!=null){
            return JSON.parseObject(json);
        }
        return null;
    }
    /**
     * 统计收入各个类别金额和
     * @param user_id
     * @return
     * @throws Exception
     */
    public JSONObject query_count_income(int user_id)throws Exception{
        String op="IncomeServlet?method=query_income_type"
                + "&&user_id="+user_id;
        String json=null;
        service=new ConnectionService();
        json=service.HttpGET(op);
        if(json!=null){
            return JSON.parseObject(json);
        }
        return null;
    }


    /**
     * 统计支出各个类别金额和
     * 某一时间范围内
     * @param user_id
     * @param start_time
     * @param end_time
     * @return
     * @throws Exception
     */

    public JSONObject query_count_expend_time(int user_id,String start_time,String end_time)throws Exception{
        String op="ExpendServlet?method=query_expend_type_time"
                + "&&user_id="+user_id
                + "&&start_time="+start_time+"-1"
                + "&&end_time="+end_time+"-1";
        String json=null;
        service=new ConnectionService();
        json=service.HttpGET(op);
        if(json!=null){
            return JSON.parseObject(json);
        }
        return null;
    }

    /**
     * 统计收入各个类别金额和
     * 某一时间范围内
     * @param user_id
     * @param start_time
     * @param end_time
     * @return
     * @throws Exception
     */
    public JSONObject query_count_income_time(int user_id,String start_time,String end_time)throws Exception{
        String op="IncomeServlet?method=query_income_type_time"
                + "&&user_id="+user_id
                + "&&start_time="+start_time+"-1"
                + "&&end_time="+end_time+"-1";
        String json=null;
        service=new ConnectionService();
        json=service.HttpGET(op);
        if(json!=null){
            return JSON.parseObject(json);
        }
        return null;
    }


    public void LoadData(boolean is_time, final int UserId){
        if(is_time==false){//全部时间范围内

            try {
                JSONObject out_account=query_count_expend(UserId);
                JSONObject in_account=query_count_income(UserId);
                //收入
                noEmptyInAccount=Integer.parseInt(in_account.getString("count"));//非空的类别个数
                System.out.println(noEmptyInAccount);
                incomeMoneySum=Double.parseDouble(in_account.getString("income_money_sum"));
                PaySalaryMoney= Double.parseDouble(in_account.getString("paySalary_money")) ;
                InterestMoney= Double.parseDouble(in_account.getString("interest_money"));
                PartTimeMoney= Double.parseDouble(in_account.getString("partTime_money"));
                TakingMoney= Double.parseDouble(in_account.getString("taking_money"));
                RedEnvelopeMoney=Double.parseDouble(in_account.getString("redEnvelope_money"));
                SalesMoney= Double.parseDouble(in_account.getString("sales_money"));
                RefundMoney= Double.parseDouble(in_account.getString("refund_money"));
                ReimburseMoney=Double.parseDouble(in_account.getString("reimburse_money"));
                //支出
                noEmptyOutAccout=Integer.parseInt(out_account.getString("count"));//非空的类别个数

                expendMoneySum=Double.parseDouble(out_account.getString("expend_money_sum"));
                BreakfastMoney=Double.parseDouble(out_account.getString("breakfast_money"));
                LunchMoney=Double.parseDouble(out_account.getString("lunch_money"));
                DinnerMoney=Double.parseDouble(out_account.getString("dinner_money"));
                DrinkAndFruitMoney=Double.parseDouble(out_account.getString("drinkAndFruit_money"));
                BuyFoodMoney=Double.parseDouble(out_account.getString("buyFood_money"));
                TaxiMoney=Double.parseDouble(out_account.getString("taxi_money"));
                BusMoney=Double.parseDouble(out_account.getString("bus_money"));
                OilMoney=Double.parseDouble(out_account.getString("oil_money"));
            }catch (Exception e){
                System.out.println(e.toString());
            }

        }else{
            try {
                JSONObject out_account=query_count_expend_time(UserId,StarTime,EndTime);
                JSONObject in_account=query_count_income_time(UserId,StarTime,EndTime);
                //收入
                noEmptyInAccount=Integer.parseInt(in_account.getString("count"));//非空的类别个数
                System.out.println(noEmptyInAccount);
                incomeMoneySum=Double.parseDouble(in_account.getString("income_money_sum"));
                PaySalaryMoney= Double.parseDouble(in_account.getString("paySalary_money")) ;
                InterestMoney= Double.parseDouble(in_account.getString("interest_money"));
                PartTimeMoney= Double.parseDouble(in_account.getString("partTime_money"));
                TakingMoney= Double.parseDouble(in_account.getString("taking_money"));
                RedEnvelopeMoney=Double.parseDouble(in_account.getString("redEnvelope_money"));
                SalesMoney= Double.parseDouble(in_account.getString("sales_money"));
                RefundMoney= Double.parseDouble(in_account.getString("refund_money"));
                ReimburseMoney=Double.parseDouble(in_account.getString("reimburse_money"));
                //支出
                noEmptyOutAccout=Integer.parseInt(out_account.getString("count"));//非空的类别个数

                expendMoneySum=Double.parseDouble(out_account.getString("expend_money_sum"));
                BreakfastMoney=Double.parseDouble(out_account.getString("breakfast_money"));
                LunchMoney=Double.parseDouble(out_account.getString("lunch_money"));
                DinnerMoney=Double.parseDouble(out_account.getString("dinner_money"));
                DrinkAndFruitMoney=Double.parseDouble(out_account.getString("drinkAndFruit_money"));
                BuyFoodMoney=Double.parseDouble(out_account.getString("buyFood_money"));
                TaxiMoney=Double.parseDouble(out_account.getString("taxi_money"));
                BusMoney=Double.parseDouble(out_account.getString("bus_money"));
                OilMoney=Double.parseDouble(out_account.getString("oil_money"));
            }catch (Exception e){
                System.out.println(e.toString());
            }

        }
    }

    /**
     * 支出数据源
     * @return
     */
    public CategorySeries getDate2(){
        CategorySeries cs=new CategorySeries("  支出饼状图");

        if (BreakfastMoney!=0)cs.add("早餐",BreakfastMoney/expendMoneySum);
        if(LunchMoney!=0)cs.add("中餐",LunchMoney/expendMoneySum);
        if(DinnerMoney!=0)cs.add("晚餐",DinnerMoney/expendMoneySum);
        if(DrinkAndFruitMoney!=0)cs.add("饮料水果",DrinkAndFruitMoney/expendMoneySum);
        if(BuyFoodMoney!=0)cs.add("买菜原料",BuyFoodMoney/expendMoneySum);
        if(TaxiMoney!=0)cs.add("打车",TaxiMoney/expendMoneySum);
        if (BusMoney!=0)cs.add("公交",BusMoney/expendMoneySum);
        if(OilMoney!=0)cs.add("加油",OilMoney/expendMoneySum);

        return cs;
    }
    /**
     * 收入数据源
     * @return
     */
    public CategorySeries getData(){
        CategorySeries cs=new CategorySeries("  第一个饼状图");
        if(PaySalaryMoney!=0)
            cs.add("工资薪水",PaySalaryMoney/incomeMoneySum);

        if(InterestMoney!=0)
            cs.add("利息",InterestMoney/incomeMoneySum);
        if(PartTimeMoney!=0)
            cs.add("兼职外快",PartTimeMoney/incomeMoneySum);
        if(TakingMoney!=0)
            cs.add("营业收入",TakingMoney/incomeMoneySum);
        if(RedEnvelopeMoney!=0)
            cs.add("红包",RedEnvelopeMoney/incomeMoneySum);
        if (SalesMoney!=0)
            cs.add("销售额",SalesMoney/incomeMoneySum);
        if(RefundMoney!=0)
            cs.add("退款返款",RefundMoney/incomeMoneySum);
        if(ReimburseMoney!=0)
            cs.add("报销款",ReimburseMoney/incomeMoneySum);
        return cs;
    }

    /**
     * 渲染器
     * @param count
     * @return
     */
    public DefaultRenderer getRenderer(int count){
        //创建渲染器,描绘
        DefaultRenderer renderer=new DefaultRenderer();

        for(int i=0;i<count;i++){
            SimpleSeriesRenderer simpleSeriesRenderer=new SimpleSeriesRenderer();
            Random random = new Random();
            simpleSeriesRenderer.setColor(randomColor());
            simpleSeriesRenderer.setChartValuesFormat(NumberFormat.getPercentInstance());
            renderer.addSeriesRenderer(simpleSeriesRenderer);
        }


        //饼图上标记文字的颜色
        renderer.setLabelsColor(Color.BLACK);
        //设置左下角标注文字的大小
        renderer.setLegendTextSize(25);
        //饼图上标记文字的字体大小
        renderer.setLabelsTextSize(25);
        //显示数据
        renderer.setDisplayValues(true);
        //设置显示标签
        renderer.setShowLabels(true);
        //显示底部说明标签
        renderer.setShowLegend(true);
        //设置背景色
        renderer.setBackgroundColor(Color.WHITE);
        //设置图例高度
        renderer.setLegendHeight(60);
        //设置是否允许拖动
        renderer.setPanEnabled(false);
        //消失锯齿
        renderer.setAntialiasing(true);
        renderer.setApplyBackgroundColor(true);//想要添加背景要先申请

        return  renderer;
    }
    /**
     * 生成随机颜色
     */
    private int randomColor() {
        Random random = new Random();
        int red = random.nextInt(255);
        int green = random.nextInt(255);
        int blue = random.nextInt(255);
        return Color.rgb(red, green, blue);
    }

    /**
     * 日期选择
     * @param context
     * @param ysId
     * @throws Exception
     */
    private void showDatePickerDialog(Context context,int ysId) throws Exception{
        Calendar calendar=Calendar.getInstance();
        DatePickerDialog dialog=new DatePickerDialog(context, ysId, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                show_time_txt.setText(year+"-"+(++month));
                Intent intent=new Intent(DetailedAnalysis.this,DetailedAnalysis.class);

                Bundle bundle=getIntent().getExtras();
                bundle.putString("star_time",show_time_txt.getText().toString().trim());
                bundle.putString("end_time",year+"-"+(month+1));
                intent.putExtras(bundle);
                intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        },calendar.get(Calendar.YEAR)
                ,calendar.get(Calendar.MONTH)
                ,calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
        DatePicker dp = findDatePicker((ViewGroup) dialog.getWindow().getDecorView());
        if (dp != null) {
            ((ViewGroup)((ViewGroup) dp.getChildAt(0)).getChildAt(0)).getChildAt(2).setVisibility(View.GONE);
        }
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
        switch (v.getId()){
            case R.id.go_back://返回
                DetailedAnalysis.this.onDestroy();
                break;
            case R.id.share://分享
                Toast.makeText(this,"正在开发",Toast.LENGTH_SHORT).show();
                break;
            case R.id.time://时间选择
                try {
                    showDatePickerDialog(this,DatePickerDialog.THEME_HOLO_DARK);
                }catch (Exception e){
                    System.out.println(e.toString());
                }


                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();

    }
}
