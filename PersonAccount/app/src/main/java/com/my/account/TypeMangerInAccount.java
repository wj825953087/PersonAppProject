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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.my.account.Service.ConnectionService;
import com.my.account.adapter.MangerTypeAdapter;
import com.my.account.entity.MangerTypeEntity;
import com.my.account.entity.income.IncomeType;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.util.ArrayList;
import java.util.List;

public class TypeMangerInAccount extends Activity implements View.OnClickListener {

    //消息码
    private final int MSG_UPDATE_UI=0;//加载数据,更新UI
    private ImageView refresh_imgv;//刷新按钮
    private ImageView go_back_imgv;//返回键
    private ListView listView;
    private LinearLayout add_type;//添加

    private Context context;//上下文
    private MangerTypeAdapter mangerTypeAdapter=null;//自定义适配器
    private List<MangerTypeEntity> entities=null;//适配器内容
    private MangerTypeEntity entity=null;//
    private int count=0;//已有收入类型的数目
    private ConnectionService connectionService=null;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_UPDATE_UI:
                    //加载数据更新UI
                    //--------------------------------------------------------------------------
                        try {
                            JSONObject ob= (JSONObject) msg.obj;
                            JSONArray array=ob.getJSONArray("income_type_list");
                            String  str=JSONObject.toJSONString(array);
                            ObjectMapper mapper=new ObjectMapper();
                            List<IncomeType> incomeTypes=(List<IncomeType>) mapper.readValue(str,
                                    new TypeReference<List<IncomeType>>() {});

                            count=incomeTypes.size();
                            //初始化数据源并加载数据源
                            entities=new ArrayList<MangerTypeEntity>();
                            for(int i=0;i<incomeTypes.size();i++){
                                entity=new MangerTypeEntity();

                                entity.setId(incomeTypes.get(i).getId());//id
                                entity.setPermissions(incomeTypes.get(i).getPermissions());//权限
                                entity.setIs_default(incomeTypes.get(i).getIs_default());//是否是系统默认类别
                                entity.setImage_id(incomeTypes.get(i).getIncome_pngId());//图片id
                                entity.setType_name(incomeTypes.get(i).getIncome_name());//收入类别名字
                                entities.add(entity);
                            }
                        }catch (Exception e){
                            System.out.println(e.toString());
                        }
                    //--------------------------------------------------------------------------

                    //初始化适配器,并往适配器加载数据源
                    mangerTypeAdapter=new MangerTypeAdapter(context,entities);
                    //给ListView控件绑定适配器
                    listView.setAdapter(mangerTypeAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            MangerTypeEntity e= (MangerTypeEntity) mangerTypeAdapter.getItem(position);
                            Intent intent=new Intent(TypeMangerInAccount.this,TypeIncomeItem.class);

                            Bundle bundle=new Bundle();
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
        setContentView(R.layout.manger_income_type_categories);
        init();//初始化
        //------------------------------------------------------------------------------------------
        //开启线程加载数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                //加载数据
                try {
                    JSONObject ob=query_income_type();
                    //消息
                    Message message=new Message();
                    message.what=MSG_UPDATE_UI;
                    message.obj=ob;
                    //发送消息,更新UI
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

//    @Override
//    protected void onResume() {
//        super.onResume();
//        onCreate(null);
//    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    //初始化控件
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
    /**
     *  显示收入类别
     * @return
     * @throws Exception
     */
    public JSONObject query_income_type() throws Exception{
        String op="MangerTypeServlet?method=ShowInAccountType";
        String json=null;
        //初始化
        connectionService=new ConnectionService();
        json=connectionService.HttpGET(op);
        if(json!=null){
            return JSON.parseObject(json);
        }
        return null;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.refresh://刷新
                refresh();
                break;
            case R.id.go_back://返回
                TypeMangerInAccount.this.finish();
                break;
            case R.id.add://添加
                int type_id=count;
                Intent intent=new Intent(TypeMangerInAccount.this,AddTypeIncome.class);

                Bundle bundle=new Bundle();
                bundle.putInt("type_id",type_id);
                intent.putExtras(bundle);

                startActivity(intent);
                break;
        }
    }
}
