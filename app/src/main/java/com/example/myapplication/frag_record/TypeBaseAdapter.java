package com.example.myapplication.frag_record;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.data_backage.TypeBean;

import java.util.List;

public class TypeBaseAdapter extends BaseAdapter {

    Context context;
    List<TypeBean>mDatas;
    int selectpos=0;//默认寻中的位置

    public TypeBaseAdapter(Context context, List<TypeBean> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_recordfrag,parent,false);
        //查找布局当前控件
        ImageView iv = convertView.findViewById(R.id.item_recordfrag_iv);
        TextView tv = convertView.findViewById(R.id.item_recordfrag_tv);
        //获取指定位置的数据源
        TypeBean typeBean = mDatas.get(position);
        tv.setText(typeBean.getTypename());
        //判断当前是否为选中位置
        if(selectpos==position){
            iv.setImageResource(typeBean.getSimageId());
        }
        else {
            iv.setImageResource(typeBean.getImageId());
        }
        return convertView;
    }
}
