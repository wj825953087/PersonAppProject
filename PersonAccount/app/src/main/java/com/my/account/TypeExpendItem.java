package com.my.account;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.my.account.Service.ConnectionService;

import java.lang.reflect.Field;

public class TypeExpendItem extends Activity implements View.OnClickListener {
    //消息码
    private final int MSG_SAVE_UI=0;
    private final int MSG_DEL_UI=1;

    private ImageView go_back_imgv;//返回
    private ImageView type_png_imgv;//收入类型图标
    private EditText type_name_edx;//收入类型名称
    private Button save_btn,del_btn;//保存,删除
    private ConnectionService connectionService=null;

    private int type_id,permissions,is_default,pngId;//类型id,权限,是否系统默认类,图片id
    private String type_name;//类型名字

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_SAVE_UI:
                    //保存
                    showDialog("保存成功");
                    break;
                case MSG_DEL_UI:
                    //删除
                    showDialog("删除成功");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载布局
        setContentView(R.layout.manger_expend_type_item);
        init();//初始化

        //获取Bundle
        Bundle bundle=getIntent().getExtras();
        type_id=bundle.getInt("type_id");
        permissions=bundle.getInt("permissions");
        is_default=bundle.getInt("is_default");
        pngId=bundle.getInt("pngId");
        type_name=bundle.getString("type_name");
        //UI更新
        type_png_imgv.setImageResource(pngId);//图片
        type_name_edx.setText(type_name);//名称
        if(is_default==1){
            //是系统默认类型,设置EditText不可编辑
            type_name_edx.setFocusableInTouchMode(false);
            save_btn.setVisibility(View.GONE);
            LinearLayout.LayoutParams lp= (LinearLayout.LayoutParams) del_btn.getLayoutParams();
            lp.leftMargin=450;
            del_btn.setLayoutParams(lp);
        }
        //按钮监听
        go_back_imgv.setOnClickListener(this);//返回
        save_btn.setOnClickListener(this);//保存
        del_btn.setOnClickListener(this);//删除
    }

    /**
     * 初始化
     */
    private void init() {
        go_back_imgv=findViewById(R.id.go_back);
        type_png_imgv=findViewById(R.id.typePng);
        type_name_edx=findViewById(R.id.typeName);
        save_btn=findViewById(R.id.save);
        del_btn=findViewById(R.id.del);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.go_back://返回按钮
                TypeExpendItem.this.finish();
                break;
            case R.id.save://保存按钮
                //开启线程
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            int pngId=getImageIdByName(type_name);
                            type_name=type_name_edx.getText().toString().trim();
                            int save_success=save_expend_type(type_id,pngId,is_default,type_name);
                            if(save_success==1){//保存
                                Message message=new Message();//消息
                                message.what=MSG_SAVE_UI;
                                //发消息
                                handler.sendMessage(message);

                            }
                        }catch (Exception e){
                            System.out.println(e.toString());
                        }
                    }
                }).start();
                break;
            case R.id.del://删除按钮
                DelDialog("确认删除?");
                break;
        }
    }
    /**
     * 保存
     * @param type_id 支出类型id
     * @param pngId   图片id
     * @param is_default 是否是系统默认类别
     * @return
     * @throws Exception
     */
    public int save_expend_type(int type_id,int pngId,int is_default,String name) throws Exception{
        String op="MangerTypeServlet?method=update_expend_type"
                +"&&type_id="+type_id
                +"&&pngId="+pngId
                +"&&is_default="+is_default
                +"&&expend_name="+name;
        String json=null;
        try {
            //初始化
            connectionService=new ConnectionService();
            json=connectionService.HttpGET(op);
            JSONObject ob=JSON.parseObject(json);
            return Integer.parseInt(ob.getString("update_success"));
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return 0;
    }
    /**
     *  删除一条支出类型
     * @param type_id 类型id
     * @param permissions 权限
     * @return
     * @throws Exception
     */
    public int del_expend_type(int type_id,int permissions) throws Exception{
        String op="MangerTypeServlet?method=del_expend_type"
                + "&&type_id="+type_id
                +"&&permissions="+permissions;
        String json=null;
        try {
            //初始化
            connectionService=new ConnectionService();
            json=connectionService.HttpGET(op);
            JSONObject ob= JSON.parseObject(json);
            return Integer.parseInt(ob.getString("del_success"));

        }catch (Exception e){
            System.out.println(e.toString());
        }

        return 0;
    }
    /**
     * 获取图片id
     * @param name
     * @param
     */
    private int getImageIdByName(String name) throws Exception  {
        int id=0;
        Field field=null;
        switch (name){
            case "早餐":
                field=R.drawable.class.getField("expend_breakfast");
                id=Integer.parseInt(field.get(null).toString());
                break;
            case "午餐":
                field=R.drawable.class.getField("expend_lunch");
                id=Integer.parseInt(field.get(null).toString());
                break;
            case "晚餐":
                field=R.drawable.class.getField("expend_supper");
                id=Integer.parseInt(field.get(null).toString());
                break;
            case "饮料水果":

                field=R.drawable.class.getField("expend_fruit_drink");
                id=Integer.parseInt(field.get(null).toString());
                break;
            case "买菜原料":
                field=R.drawable.class.getField("expend_buy_vegetables");
                id=Integer.parseInt(field.get(null).toString());
                break;
            case "打车":
                field=R.drawable.class.getField("expend_lift");
                id=Integer.parseInt(field.get(null).toString());
                break;
            case "公交":
                field=R.drawable.class.getField("expend_bus");
                id=Integer.parseInt(field.get(null).toString());
                break;
            case "加油":
                field=R.drawable.class.getField("expend_oil");
                id=Integer.parseInt(field.get(null).toString());
                break;
        }
        return id;
    }

    /*-----------------------------------消息框---------------------------------------------------*/
    //消息提示框
    private void showDialog(String contain) {
        new AlertDialog.Builder(this)
                .setMessage(contain)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TypeExpendItem.this.finish();

                    }
                })
                .show();
    }

    /**
     * 删除消息提示框
     * @param contain
     */
    private void DelDialog(String contain) {
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
                                    int del_success=del_expend_type(type_id,permissions);
                                    if(del_success==1){
                                        Message message=new Message();//消息
                                        message.what=MSG_DEL_UI;
                                        //发消息
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
}
