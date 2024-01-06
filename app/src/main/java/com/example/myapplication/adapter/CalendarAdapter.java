package com.example.myapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

//历史账单界面，点击日历表，弹出对话框，当中的GridView对应的适配器
public class CalendarAdapter extends BaseAdapter {
    Context context;
    List<String> mDatas;
    public int year;
    public int selpos=-1;

    public void setYear(int year) {
        this.year = year;
        mDatas.clear();
        loaddata(year);
        notifyDataSetChanged();
    }

    public CalendarAdapter(Context context, int year) {
        this.context = context;
        this.year = year;
        mDatas = new ArrayList<>();
        loaddata(year);
    }

    private void loaddata(int year)
    {
        for (int i=1;i<13;i++)
        {
            String data = year+"/"+i;
            mDatas.add(data);
        }
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
        convertView = LayoutInflater.from(context).inflate(R.layout.item_dialog_gv,parent,false);
        TextView view = convertView.findViewById(R.id.item_dialog_gv_tv);
        view.setText(mDatas.get(position));
        view.setBackgroundResource(R.color.grey_f3f3f3);
        view.setTextColor(Color.BLACK);
        if (position==selpos) {
            view.setBackgroundResource(R.color.blue01);
            view.setTextColor(Color.WHITE);
        }
        return convertView;
    }
}
