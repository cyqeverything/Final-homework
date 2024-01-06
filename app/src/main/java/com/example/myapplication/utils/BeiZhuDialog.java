package com.example.myapplication.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;

import java.util.List;
import java.util.logging.LogRecord;

public class BeiZhuDialog extends Dialog implements View.OnClickListener {
    EditText et;
    Button cancelBtn, ensureBtn;
    OnEnsureListener onEnsureListener;

    public BeiZhuDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_beizhu); //设置对话框显示布局
        et = findViewById(R.id.dialog_beizhu_et);
        cancelBtn = findViewById(R.id.dialog_beizhu_btn_cancel);
        ensureBtn = findViewById(R.id.dialog_beizhu_btn_ensure);
        cancelBtn.setOnClickListener(this);
        ensureBtn.setOnClickListener(this);
    }

    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

    public interface OnEnsureListener {
        public void onEnsure();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_beizhu_btn_cancel:
                cancel();
                break;
            case R.id.dialog_beizhu_btn_ensure:
                if (onEnsureListener!=null)
                {
                    onEnsureListener.onEnsure();
                }
                break;
        }
    }

    //获取输入数据的方法
    public String getEditText() {
        return et.getText().toString().trim();
    }

    //设置dialog的尺寸与屏幕保持一致
    public void setdialogsize() {
        Window window = getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        Display d = window.getWindowManager().getDefaultDisplay();
        wlp.width = (int) (d.getWidth());
        wlp.gravity = Gravity.BOTTOM;
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setAttributes(wlp);
        handler.sendEmptyMessageDelayed(1,100);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    };
}


