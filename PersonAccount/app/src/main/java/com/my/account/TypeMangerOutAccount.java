package com.my.account;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.my.account.Service.ConnectionService;
import com.my.account.adapter.MangerTypeAdapter;
import com.my.account.entity.MangerTypeEntity;
import com.my.account.entity.expend.ExpendType;

import java.util.ArrayList;
import java.util.List;


public class TypeMangerOutAccount extends Activity implements View.OnClickListener {
    //消息码
    private final int MSG_UPDATE_UI=0;
    private ImageView refresh_imgv;//刷新按钮
    private ImageView go_back_imgv;//返回
    private ListView listView;
    private LinearLayout add_type;//添加

    private Context context;
    private MangerTypeAdapter adapter=null;//ListView 适配器
    private List<MangerTypeEntity> entities=null;//适配器内容
    private MangerTypeEntity entity=null;//一条ListView
    private int count=0;//已有支出类型的数目
    private ConnectionService connectionService=null;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_UPDATE_UI:
                    //更新UI
                    try {
                        JSONObject ob= (JSONObject) msg.obj;
                        JSONArray array=ob.getJSONArray("expend_type_list");
                        String str=JSONObject.toJSONString(array);
                        List<ExpendType> expendTypes= (List<ExpendType>) JSONObject.parseArray(str,ExpendType.class);

                        count=expendTypes.size();
                        //初始化数据源并加载数据源
                        entities=new ArrayList<MangerTypeEntity>();
                        for(int i=0;i<expendTypes.size();i++){
                            entity=new MangerTypeEntity();

                            entity.setId(expendTypes.get(i).getId());//id
                            entity.setPermissions(expendTypes.get(i).getPermissions());//权限
                            entity.setIs_default(expendTypes.get(i).getIs_default());//是否是系统默认类别
                            entity.setImage_id(expendTypes.get(i).getExpend_pngId());//图片id
                            entity.setType_name(expendTypes.get(i).getExpend_name());//收入类别名字
                            entities.add(entity);
                        }

                    }catch (Exception e){
                        System.out.println(e.toString());
                    }
                    //------------------------------------------------------------------------------
                    //初始化适配器,并给适配器加载数据源
                    adapter=new MangerTypeAdapter(context,entities);
                    //绑定
                    listView.setAdapter(adapter);
                    //ListView点击事件
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            MangerTypeEntity e= (MangerTypeEntity) adapter.getItem(position);
                            Intent intent=new Intent(TypeMangerOutAccount.this,TypeExpendItem.class);
                            Bundle bundle=new Bundle();
                                //传值
                                bundle.putInt("type_id",e.getId());
                                bundle.putInt("permissions",e.getPermissions());
                                bundle.putInt("is_default",e.getIs_default());
                                bundle.putInt("pngId",e.getImage_id());
                                bundle.putString("type_name",e.getType_name());
                                intent.putExtras(bundle);
                                startActivity(intent);

                        }
                    });

                    break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载布局
        setContentView(R.layout.manger_expend_type_categories);
        init();//初始化
        //------------------------------------------------------------------------------------------
        //开启线程,加载数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    JSONObject ob=query_expend_type();
                    //创建消息
                    Message message=new Message();
                    message.what=MSG_UPDATE_UI;
                    message.obj=ob;//存数据
                    //发送消息,更新主线程UI
                    handler.sendMessage(message);
                }catch (Exception e){
                    System.out.println(e.toString());
                }
            }
        }).start();

        //绑定监听
        go_back_imgv.setOnClickListener(this);//返回
        refresh_imgv.setOnClickListener(this);//刷新
        add_type.setOnClickListener(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        TypeMangerOutAccount.this.finish();
    }
    //    @Override
//    protected void onResume() {
//        super.onResume();
//        onCreate(null);
//    }

    /**
     * 显示支出类别
     * @return
     * @throws Exception
     */
    public JSONObject query_expend_type()throws Exception {
        String op="MangerTypeServlet?method=ShowOutAccountType";
        String json=null;
        connectionService=new ConnectionService();//初始化
        json=connectionService.HttpGET(op);
        if(json!=null){
            return JSON.parseObject(json);
        }
        return null;
    }

    /**
     * 控件初始化
     */
    private void init() {
        context=this;
        refresh_imgv=findViewById(R.id.refresh);//刷新
        go_back_imgv=findViewById(R.id.go_back);//返回键
        listView=findViewById(R.id.listview);
        add_type=findViewById(R.id.add);//添加
    }
    /**
     * 手动刷新
     */
    private void refresh(){
        onCreate(null);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.refresh://刷新
                refresh();
                break;
            case R.id.go_back://返回
                TypeMangerOutAccount.this.finish();
                break;
            case R.id.add:
                int type_id=count;
                Intent intent=new Intent(TypeMangerOutAccount.this,AddTypeExpend.class);
                Bundle bundle=new Bundle();
                //传值
                bundle.putInt("type_id",type_id);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }

    }
}
