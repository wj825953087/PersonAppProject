package com.my.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.my.account.entity.Picture;
import com.my.account.entity.user.User;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private GridView gridView;//
    String[] titles=new String[]{"新增支出", "新增收入", "账单明细",
            "支出类别管理", "收入类别管理", "退出" };
    int[] images=new int[]{R.drawable.add_expend_bill,R.drawable.add_income_bill, R.drawable.bill_details
            , R.drawable.expend_type_member,
            R.drawable.income_type_member, R.drawable.exit };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*加载布局*/
        setContentView(R.layout.main_layout);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉系统默认标题]
        //初始化
        init();
        //接收用户信息
        final Bundle bundle=getIntent().getExtras();

        //解析
       String user_json= (String) bundle.get("user");
       final User user= JSON.parseObject(user_json,User.class);


        pictureAdapter adapter=new pictureAdapter(titles,images,this);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=null;
                int user_id=user.getUser_id();
                switch (position){
                    case 0://新增支出
                        intent=new Intent(MainActivity.this,AddOutAccount.class);
                        //传参
                        bundle.putInt("user_id",user_id);
                        intent.putExtras(bundle);

                        startActivity(intent);//启动
                        break;
                    case 1://新增收入
                        intent=new Intent(MainActivity.this,AddInAccount.class);
                        //传参
                        bundle.putInt("user_id",user_id);
                        intent.putExtras(bundle);
                        startActivity(intent);//启动
                        break;

                    case 2://账单明细
                        intent=new Intent(MainActivity.this, BillDetails.class);

                        bundle.putInt("user_id",user_id);
                        intent.putExtras(bundle);
                        startActivity(intent);//启动
                        break;

                    case 3://支出类别管理
                        intent=new Intent(MainActivity.this, TypeMangerOutAccount.class);
                        bundle.putInt("user_id",user_id);
                        intent.putExtras(bundle);
                        startActivity(intent);//启动
                        break;
                    case 4://收入类别管理
                        intent=new Intent(MainActivity.this, TypeMangerInAccount.class);
                        bundle.putInt("user_id",user_id);
                        intent.putExtras(bundle);
                        startActivity(intent);//启动
                        break;
                    case 5://退出
                        MainActivity.this.finish();
                        break;

                }
            }
        });


    }

    public void init(){
        setTitle("个人财务管理系统");
        gridView=findViewById(R.id.grid);
    }

}

class  pictureAdapter extends BaseAdapter{
    private LayoutInflater inflater;
    private List<Picture> pictureList;
    //构造函数
    public pictureAdapter(String[] titles, int[] images, Context context){
        super();
        pictureList=new ArrayList<Picture>();//初始化
        inflater=LayoutInflater.from(context);//初始化LayoutInflater
        //遍历图像数组
        for (int i=0;i<images.length;i++){
            Picture picture=new Picture(titles[i],images[i] );
            //将picture对象添加到集合中
            pictureList.add(picture);
        }

    }

    @Override
    public int getCount() {
        //集合不为空，返回集合长度
        if (pictureList!=null)return pictureList.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return pictureList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;//
        if(convertView==null){
            convertView=inflater.inflate(R.layout.grid_item,null);//设置图像标识
            viewHolder=new ViewHolder();
            viewHolder.title=convertView.findViewById(R.id.ItemTitle);//
            viewHolder.imageView=convertView.findViewById(R.id.ItemImage);//
            convertView.setTag(viewHolder);//设置提示
        }else{
            viewHolder= (ViewHolder) convertView.getTag();//设置提示

        }
        viewHolder.title.setText(pictureList.get(position).getTitle());//设置图像标题
        viewHolder.imageView.setImageResource(pictureList.get(position).getImage_id());//设置图像
        return convertView;
    }

}

class  ViewHolder{
    public TextView title;//创建TextView对象
    public ImageView imageView;//创建ImageView 对象

}