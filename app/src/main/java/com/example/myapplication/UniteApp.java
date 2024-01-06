package com.example.myapplication;

import static com.example.myapplication.data_backage.DBManager.initdb;

import android.app.Application;

import com.example.myapplication.data_backage.DBManager;

//表示全局应用的类
public class UniteApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化数据库
        DBManager.initdb(getApplicationContext());
    }
}
