package com.example.myapplication.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.example.myapplication.R;

public class SelectTimeDialog extends Dialog implements View.OnClickListener {
    DatePicker datePicker;
    Button ensurebtn,cancelbtn;
    public interface OnEnsureListener{
        public void onEnsure(String time,int year,int month,int day);
    }
    OnEnsureListener onEnsureListener;

    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

    public SelectTimeDialog(@NonNull Context context){
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_calendar);
        datePicker = findViewById(R.id.dialog_calendar);
        ensurebtn = findViewById(R.id.dialog_time_btn_ensure);
        cancelbtn = findViewById(R.id.dialog_time_btn_cancel);
        ensurebtn.setOnClickListener(this);
        cancelbtn.setOnClickListener(this);
        hideDatePickerHeader();

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.dialog_time_btn_cancel:
                cancel();
                break;
            case R.id.dialog_time_btn_ensure:
                int year = datePicker.getYear();
                int month = datePicker.getMonth()+1;
                int day = datePicker.getDayOfMonth();
                String monthstr = String.valueOf(month);
                if(month<10)
                {
                    monthstr = "0"+month;
                }
                String daystr = String.valueOf(day);
                if(day<10)
                {
                    daystr = "0"+day;
                }

                String timeFormat = year + "年" + monthstr + "月" +daystr +"日";
                if(onEnsureListener!=null){
                    onEnsureListener.onEnsure(timeFormat,year,month,day);
                }
                cancel();
                break;
        }
    }


   public void hideDatePickerHeader() {
        ViewGroup rootView = (ViewGroup) datePicker.getChildAt(0);
        if(rootView==null){
            return;
        }
        View header =  rootView.getChildAt(0);
        if(header == null){
            return;
        }

      int headerid =  getContext().getResources().getIdentifier("date_picker_header","id","android");
        if(headerid == header.getId())
        {
            header.setVisibility(View.GONE);
            ViewGroup.LayoutParams layoutParamsroot = rootView.getLayoutParams();
            layoutParamsroot.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            rootView.setLayoutParams(layoutParamsroot);

            ViewGroup animator = (ViewGroup) rootView.getChildAt(1);
            ViewGroup.LayoutParams layoutParamsAnimator = animator.getLayoutParams();
            layoutParamsAnimator.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            animator.setLayoutParams(layoutParamsAnimator);

            View child = animator.getChildAt(0);
            ViewGroup.LayoutParams layoutParamsChild = child.getLayoutParams();
            layoutParamsChild.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            child.setLayoutParams(layoutParamsChild);
            return;
        }
    }
}
