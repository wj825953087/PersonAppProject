package com.my.account.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.my.account.R;
import com.my.account.entity.MangerTypeEntity;
import com.my.account.item.MangerTypeItem;

import java.util.List;

public class MangerTypeAdapter extends BaseAdapter  {


    private List<MangerTypeEntity> list=null;
    private MangerTypeEntity typeEntity=null;
    private MangerTypeItem item=null;
    private Context context;//上下文
    private LayoutInflater inflater=null;

    public MangerTypeAdapter(Context context, List<MangerTypeEntity> list ) {
        this.context = context;
        this.list = list;
        this.inflater=LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        typeEntity=list.get(position);
        if(convertView==null){
            convertView=inflater.inflate(R.layout.manger_type_item,null);
            item=new MangerTypeItem();
            //初始化
            item.typeId_textview=convertView.findViewById(R.id.typeId);//类别id
            item.permissions_textview=convertView.findViewById(R.id.permissions);//权限
            item.isdefault_textview=convertView.findViewById(R.id.isdefault);//是否是系统默认类别
            //---------
            item.typePng_imageview=convertView.findViewById(R.id.typePng);//图片
            item.typeName_textview=convertView.findViewById(R.id.typeName);//类名名字

            //设置显示
            convertView.setTag(item);
        }else {
            //否则显示
            item= (MangerTypeItem) convertView.getTag();
        }
        //设置参数
        item.typeId_textview.setText(String.valueOf(list.get(position).getId()));//设置类别id
        item.permissions_textview.setText(String.valueOf(list.get(position).getPermissions()));//设置权限
        item.isdefault_textview.setText(String.valueOf(list.get(position).getIs_default()));//是否是系统默认类别
        //-------------
        item.typePng_imageview.setImageResource(list.get(position).getImage_id());//设置图片id
        item.typeName_textview.setText(list.get(position).getType_name());//设置类别名字


        return convertView;
    }

}
