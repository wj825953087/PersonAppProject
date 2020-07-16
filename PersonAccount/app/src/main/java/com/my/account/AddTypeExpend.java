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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.my.account.Service.ConnectionService;

public class AddTypeExpend extends Activity implements View.OnClickListener {
    //消息码
    private final int MSG_ADD_UI=0;
    private ImageView go_back_imgv;//返回
    private EditText type_name_edt;//名称
    private Button save_btn;//保存按钮

    private int type_id,is_default=0,permissions=2;//类型id,是否是系统默认类型,权限
    private String type_name;//输入的名称
    private ConnectionService service=null;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case  MSG_ADD_UI:
                    showDialog("添加成功");
                    break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载布局
        setContentView(R.layout.add_type_expend);
        init();
        Bundle bundle=getIntent().getExtras();
        type_id=bundle.getInt("type_id");
        //绑定监听事件
        go_back_imgv.setOnClickListener(this);
        save_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.go_back://返回
                AddTypeExpend.this.finish();

                break;
            case R.id.sava://新增
                //开启线程
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            type_name=type_name_edt.getText().toString().trim();
                            if(type_name!=null){//输入的类型名称不为空,进行新增操作
                                int add_success=insert_expend_type(type_id,type_name);
                                //消息
                                Message message=new Message();
                                message.what=MSG_ADD_UI;
                                //发消息
                                handler.sendMessage(message);
                            }else{
                                //否则,不进行新增操作
                                Toast.makeText(AddTypeExpend.this,"输入的名称不能为空",Toast.LENGTH_SHORT);
                            }
                        }catch (Exception e){
                            System.out.println(e.toString());
                        }



                    }
                }).start();
                break;
        }
    }

    /**
     * 作用: 新增一条支出类型
     * @param type_id
     * @param type_name
     * @return
     */
    public int insert_expend_type(int type_id, String type_name) throws Exception{
        String op="MangerTypeServlet?method=insert_expend_type"
                +  "&&type_id="+type_id
                +  "&&expend_name="+type_name
                +  "&&is_default=0"
                +  "&&super_id=0"
                +  "&&permissions=2";
        String json=null;
        //初始化
        service=new ConnectionService();
        json=service.HttpGET(op);
        JSONObject ob= JSON.parseObject(json);
        return Integer.parseInt(ob.getString("add_success"));
    }
    /**
     * 初始化
     */
    private void init() {
        go_back_imgv=findViewById(R.id.go_back);
        type_name_edt=findViewById(R.id.input_name);
        save_btn=findViewById(R.id.sava);
    }

    //----------------------消息框------------------------------------------------------------------
    //提示框
    private void showDialog(String contain){
        new AlertDialog.Builder(this)
                .setMessage(contain)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AddTypeExpend.this.finish();

                    }
                })
                .show();
    }
}
