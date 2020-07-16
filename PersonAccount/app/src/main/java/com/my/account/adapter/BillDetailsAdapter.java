package com.my.account.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.my.account.R;
import com.my.account.entity.BillDetailsEntity;
import com.my.account.item.BillDetailsItem;

import java.util.List;

public class BillDetailsAdapter extends BaseAdapter {

    private List<BillDetailsEntity> entityList;
    private  Context context;
    private LayoutInflater inflater;
    //构造函数

    public BillDetailsAdapter(Context context, List<BillDetailsEntity> entityList) {
        super();
        this.context=context;
        this.entityList=entityList;
        this.inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if(entityList!=null)return entityList.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return entityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BillDetailsEntity entity=entityList.get(position);
        BillDetailsItem item=null;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.bill_detail_item,null);
            item=new BillDetailsItem();
            //初始化
            item.bill_id_text_view=convertView.findViewById(R.id.id);
            item.bill_type_text_view=convertView.findViewById(R.id.bill_type);

            item.Type_image_view=convertView.findViewById(R.id.type_img);
            item.Type_name_text_view=convertView.findViewById(R.id.type_name);
            item.time_text_view=convertView.findViewById(R.id.time_tv);
            item.money_text_view=convertView.findViewById(R.id.money);
            //设置显示
            convertView.setTag(item);
        }else{
            item= (BillDetailsItem) convertView.getTag();
        }
        item.bill_id_text_view.setText(entityList.get(position).getId());//账单id
        item.bill_type_text_view.setText(entityList.get(position).getBill_type());//账单类型

        item.Type_image_view.setImageResource(entityList.get(position).getImage_id());//设置类别图像
        item.Type_name_text_view.setText(entityList.get(position).getTypename());//设置类别名字
        item.time_text_view.setText(entityList.get(position).getTime());//设置时间
        item.money_text_view.setText(String.valueOf(entityList.get(position).getMoney()));//设置金额
        return convertView;
    }
}