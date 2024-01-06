package com.example.myapplication.data_backage;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;

public class self_data extends SQLiteOpenHelper {
    public self_data(@Nullable Context context) {
        super(context,"myapplication.data_backage", null, 1);
    }

// 创建数据库的方法，只有项目第一次运行时才会被调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建表示类型的表
        String sql = "create table typetb(id integer primary key autoincrement,typename varchar(10)" +
                ",imageId integer,simageId integer,kind integer)";
        db.execSQL(sql);
        insertType(db);
        //创建记账表
        sql = "create table accounttb(id integer primary key autoincrement,typename varchar(10),imageId integer,beizhu varchar(80)," +
                "money float,time varchar(60),year intger,month integer,day integer,kind integer,riqi varchar(80))";
        db.execSQL(sql);
    }

    private void insertType(SQLiteDatabase db) {
        //向typetb表中插入元素
        String sql = "insert into typetb (typename,imageId,simageId,kind) values(?,?,?,?)";
        db.execSQL(sql,new Object[]{"其他", R.mipmap.ic_qita,R.mipmap.ic_qita_fs,0});
        db.execSQL(sql,new Object[]{"吃喝饮食", R.mipmap.ic_canyin,R.mipmap.ic_canyin_fs,0});
        db.execSQL(sql,new Object[]{"交通出行", R.mipmap.ic_jiaotong,R.mipmap.ic_jiaotong_fs,0});
        db.execSQL(sql,new Object[]{"通讯费用", R.mipmap.ic_tongxun,R.mipmap.ic_tongxun_fs,0});
        db.execSQL(sql,new Object[]{"欠债还款", R.mipmap.ic_jiekuan,R.mipmap.ic_jiekuan_fs,0});
        db.execSQL(sql,new Object[]{"人情礼物", R.mipmap.ic_renqing,R.mipmap.ic_renqing_fs,0});
        db.execSQL(sql,new Object[]{"房租水电", R.mipmap.ic_fangzushuidian,R.mipmap.ic_fangzushuidian_fs,0});
        db.execSQL(sql,new Object[]{"医疗费用", R.mipmap.ic_yiliao,R.mipmap.ic_yiliao_fs,0});
        db.execSQL(sql,new Object[]{"社交红包", R.mipmap.ic_shejiaohongbao,R.mipmap.ic_shejiaohongbao_fs,0});
        db.execSQL(sql,new Object[]{"逛街购物", R.mipmap.ic_gouwu,R.mipmap.ic_gouwu_fs,0});
        db.execSQL(sql,new Object[]{"运动健身", R.mipmap.ic_jianshen,R.mipmap.ic_jianshen_fs,0});
        db.execSQL(sql,new Object[]{"工资存储", R.mipmap.ic_gongzi,R.mipmap.ic_gongzi_fs,0});
        db.execSQL(sql,new Object[]{"日常用品", R.mipmap.ic_riyong,R.mipmap.ic_riyong_fs,0});
        db.execSQL(sql,new Object[]{"聚会聚餐", R.mipmap.ic_juhui,R.mipmap.ic_juhui_fs,0});
        db.execSQL(sql,new Object[]{"基金理财", R.mipmap.ic_jijin,R.mipmap.ic_jijin_fs,0});

        db.execSQL(sql,new Object[]{"其他", R.mipmap.ic_qita,R.mipmap.ic_qita_fs,1});
        db.execSQL(sql,new Object[]{"薪资", R.mipmap.in_xinzi,R.mipmap.in_xinzi_fs,1});
        db.execSQL(sql,new Object[]{"奖金", R.mipmap.in_jiangjin,R.mipmap.in_jiangjin_fs,1});
        db.execSQL(sql,new Object[]{"借入", R.mipmap.in_jieru,R.mipmap.in_jieru_fs,1});
        db.execSQL(sql,new Object[]{"收债", R.mipmap.in_shouzhai,R.mipmap.in_shouzhai_fs,1});
        db.execSQL(sql,new Object[]{"利息收入", R.mipmap.in_lixifuji,R.mipmap.in_lixifuji_fs,1});
        db.execSQL(sql,new Object[]{"投资回报", R.mipmap.in_touzi,R.mipmap.in_touzi_fs,1});
        db.execSQL(sql,new Object[]{"二手交易", R.mipmap.in_ershoushebei,R.mipmap.in_ershoushebei_fs,1});
    }

    //数据库版本发生更新时发生改变
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
