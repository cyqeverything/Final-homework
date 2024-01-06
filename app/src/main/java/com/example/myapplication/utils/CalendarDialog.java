package com.example.myapplication.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.myapplication.HistoryActivity;
import com.example.myapplication.R;
import com.example.myapplication.adapter.CalendarAdapter;
import com.example.myapplication.data_backage.DBManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarDialog extends Dialog implements View.OnClickListener{
    ImageView error;
    GridView gv;
    LinearLayout layout;

    List<TextView> headerlist;
    List<Integer> yearlist;

    int selectpos=-1;//正在被点击的年份的位置
    private CalendarAdapter adapter;
    int selectMonth=-1;

    public interface OnRefreshListener{
        public void onRefresh(int selpos,int year,int month);
    }

    OnRefreshListener onRefreshListener;

    public void setOnRefreshListener(OnRefreshListener onRefreshListener){
        this.onRefreshListener=onRefreshListener;
    }


    public CalendarDialog(@NonNull Context context,int selectpos,int selectMonth) {
        super(context);
        this.selectpos=selectpos;
        this.selectMonth=selectMonth;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_time);
        gv = findViewById(R.id.dialog_gridview);
        error = findViewById(R.id.dialog_calendar_back);
        layout = findViewById(R.id.dialog_choose_calendar);
        error.setOnClickListener(this);
        addViewLayout();
        initgridview();
        //设置girdview当中每一个item的点击事件
        setgvListener();
    }

    private void setgvListener() {
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.selpos=position;
                adapter.notifyDataSetInvalidated();
                int month=position+1;
                int year = adapter.year;
                //获取被选中的年份、月份
                onRefreshListener.onRefresh(selectpos,year,month);
                cancel();
            }
        });
    }

    private void initgridview() {
        int selyaer = yearlist.get(selectpos);
        adapter = new CalendarAdapter(getContext(), selyaer);
        if (selectMonth==-1) {
           int month =  Calendar.getInstance().get(Calendar.MONTH);
           adapter.selpos=month;
        }
        else
        {
            adapter.selpos=selectMonth-1;
        }
        gv.setAdapter(adapter);

    }

    private void addViewLayout() {
        headerlist = new ArrayList<>();
        yearlist = DBManager.getYearList();
        if(yearlist.size()==0)
        {
            int year = Calendar.getInstance().get(Calendar.YEAR);
            yearlist.add(year);
        }
        for(int i =0; i<yearlist.size();i++)
        {
            int year = yearlist.get(i);
            View v = getLayoutInflater().inflate(R.layout.item_dialog_head,null);
            layout.addView(v);
            TextView textView = v.findViewById(R.id.item_dialog_header_tv);
            textView.setText(year+"");
            headerlist.add(textView);
        }
        if(selectpos==-1)
        {
            selectpos=headerlist.size()-1;
        }
        changetv(selectpos);
        setheadlistClickListener();
    }

    //给横向的每一个Textview设置一个点击事件
    private void setheadlistClickListener() {
        for(int i=0;i<headerlist.size();i++)
        {
            TextView view = headerlist.get(i);
            int pos=i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changetv(pos);
                    selectpos=pos;
                    //获取被选中的年份
                    int year = yearlist.get(selectpos);
                    adapter.setYear(year);
                }
            });
        }
    }

    //传入被选中的位置
    private void changetv(int selectpos) {
        for(int i=0;i<headerlist.size();i++)
        {
            TextView textView = headerlist.get(i);
            textView.setBackgroundResource(R.drawable.search_btn_bg);
            textView.setTextColor(Color.BLACK);

        }

        TextView selview = headerlist.get(selectpos);
        selview.setBackgroundResource(R.drawable.draw_btn_bg_);
        selview.setTextColor(Color.WHITE);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_calendar_back:
                cancel();
                break;
                
        }
    }

    //设置dialog的尺寸与屏幕保持一致
    public void setdialogsize() {
        Window window = getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        Display d = window.getWindowManager().getDefaultDisplay();
        wlp.width = (int) (d.getWidth());
        wlp.gravity = Gravity.TOP;
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setAttributes(wlp);
    }
}
