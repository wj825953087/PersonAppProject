package com.my.account.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.my.account.R;
import com.my.account.entity.SpinnerEntity;
import com.my.account.item.SpinnerItem;

import java.util.ArrayList;


public class SpinnerAdapter extends BaseAdapter {


    private ArrayList<SpinnerEntity> list;
    private Context context;//上下文
    private LayoutInflater inflater;

    public SpinnerAdapter(ArrayList<SpinnerEntity> list, Context context) {
        super();
        this.list=list;
        this.context = context;
        this.inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
       // if (accountName!=null)return accountName.length;
        if(list!=null)return list.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
      //  return accountName[position];
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //String AccountName=accountName[position];//获取
        SpinnerEntity entity=list.get(position);//
        SpinnerItem item=null;//布局内容
        if (convertView==null){
            convertView=inflater.inflate(R.layout.spinner_1,null);//加载自定义布局
            item=new SpinnerItem();//初始化
            //item元素与布局控件绑定id
            item.accountTextView=convertView.findViewById(R.id.contain);
            item.idTextView=convertView.findViewById(R.id.account_id);

            //设置显示
            convertView.setTag(item);
        }else{//布局已加载
            item= (SpinnerItem) convertView.getTag();
        }
        //数据源跟控件设置
       // item.accountTextView.setText(accountName[position]);
        item.accountTextView.setText(list.get(position).getAccount_name());
        item.idTextView.setText(String.valueOf(list.get(position).getId()));
        return convertView;
    }
}
