package com.example.myapplication.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.myapplication.AboutActivity;
import com.example.myapplication.PersonActivity;
import com.example.myapplication.HistoryActivity;
import com.example.myapplication.MonthChartActivity;
import com.example.myapplication.R;
import com.example.myapplication.SettingActivity;

public class MoreDialog extends Dialog implements View.OnClickListener{
    Button about,setting,history,info,geren;
    ImageView back;
    public MoreDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_more);
        about = findViewById(R.id.dialog_btn_about);
        setting = findViewById(R.id.dialog_btn_shezhi);
        history = findViewById(R.id.dialog_btn_history);
        info = findViewById(R.id.dialog_btn_account);
        //back = findViewById(R.id.dialog_more_back);
        geren = findViewById(R.id.dialog_btn_geren);
        geren.setOnClickListener(this);
        about.setOnClickListener(this);
        setting.setOnClickListener(this);
        history.setOnClickListener(this);
        info.setOnClickListener(this);
        back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId())
        {
            case R.id.dialog_btn_about:
                intent.setClass(getContext(), AboutActivity.class);
                getContext().startActivity(intent);
                break;
            case R.id.dialog_btn_account:
                intent.setClass(getContext(), MonthChartActivity.class);
                getContext().startActivity(intent);
                break;
            case R.id.dialog_btn_history:
                intent.setClass(getContext(), HistoryActivity.class);
                getContext().startActivity(intent);
                break;
            case R.id.dialog_btn_shezhi:
                intent.setClass(getContext(), SettingActivity.class);
                getContext().startActivity(intent);
                break;
            case R.id.dialog_btn_geren:
                intent.setClass(getContext(), PersonActivity.class);
                getContext().startActivity(intent);
                break;
           // case R.id.dialog_more_back:
               // break;
        }
        cancel();
    }

    //设置dialog的尺寸与屏幕保持一致
    public void setdialogsize() {
        Window window = getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        Display d = window.getWindowManager().getDefaultDisplay();
        wlp.width = (int) (d.getWidth()*4/5);
        wlp.gravity = Gravity.LEFT;
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setAttributes(wlp);
    }
}
