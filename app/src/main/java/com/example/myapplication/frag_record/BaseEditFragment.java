package com.example.myapplication.frag_record;


import android.app.Activity;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.data_backage.AccountBean;
import com.example.myapplication.data_backage.AccountBeancopy;
import com.example.myapplication.data_backage.DBManager;
import com.example.myapplication.data_backage.TypeBean;
import com.example.myapplication.utils.BeiZhuDialog;
import com.example.myapplication.utils.KeyBoardUtils;
import com.example.myapplication.utils.SelectTimeDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;


public abstract class BaseEditFragment extends Fragment implements View.OnClickListener {

    static AccountBean accountBean;
    KeyboardView keyboardView;
    EditText moneyEt;
    ImageView typeIv;
    TextView typeTv,beizhuTv,timeTv,riqiTv;
    GridView typeGv;
    List<TypeBean>typelist;
    TypeBaseAdapter adapter;
    //将需要插入到记账本中的数据保存成对象的形式

    public static int getid()
    {return accountBean.getId();}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountBean =new AccountBean();
        accountBean.setTypename("其它");
        accountBean.setsImageId(R.mipmap.ic_qita);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_outcome,container,false);
        initView(view);//初始化界面
        setInitTime(); //初始化时间
        loadDataToGV();//给gridview填充typetb中的数据
        setGVListener();//设置监听
        return view;
    }

    //获取当前时间，显示在timeTv上
    private void setInitTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年mm月dd日");
        String time = sdf.format(date);
        timeTv.setText(time);
        accountBean.setTime(time);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        accountBean.setYear(year);
        accountBean.setMonth(month);
        accountBean.setDay(day);
    }
    private void setGVListener() {
        typeGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.selectpos=position;
                adapter.notifyDataSetChanged(); //提示绘制发生变化
                TypeBean typeBean = typelist.get(position);
                String typename = typeBean.getTypename();
                typeTv.setText(typename);
                accountBean.setTypename(typename);
                int imageId = typeBean.getImageId();
                typeIv.setImageResource(imageId);
                accountBean.setsImageId(imageId);
            }
        });
    }
    public void loadDataToGV() {
        typelist =new ArrayList<>();
        adapter = new TypeBaseAdapter(getContext(), typelist);
        typeGv.setAdapter(adapter); }

    public void initView(View view){
        keyboardView = view.findViewById(R.id.frag_record_keyboard);
        moneyEt = view.findViewById(R.id.frag_record_et_money);
        typeIv = view.findViewById(R.id.frag_record_iv);
        typeGv = view.findViewById(R.id.frag_record_gv);
       beizhuTv= view.findViewById(R.id.frag_record_tv_beizhu);
        timeTv = view.findViewById(R.id.frag_record_tv_time);
        typeTv = view.findViewById(R.id.frag_record_tv_type);
        beizhuTv.setOnClickListener(this);
        timeTv.setOnClickListener(this);
        moneyEt.setOnClickListener(this);
        //显示自定义软件盘
      KeyBoardUtils boardUtils = new KeyBoardUtils(keyboardView,moneyEt);
      boardUtils.showKeyboard();
        //设置接口，监听按钮是否被点击
      boardUtils.setOnEnsureListener(new KeyBoardUtils.OnEnsureListener() {
            @Override
            public void onEnsure() {
                //获取输入的金额
                String moneystr = moneyEt.getText().toString();
               if (TextUtils.isEmpty(moneystr)||moneystr.equals("0")) {
                   getActivity().finish();
                   return;
               }
               float money = Float.parseFloat(moneystr);
               accountBean.setMoney(money);
                //获取记录的信息，保存在数据库当中
                saveAccountToDB();
                //同时返回上一级页面
                getActivity().finish();
            }
       });
    }

    //子类重写此方法
    public abstract void saveAccountToDB();

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case  R.id.frag_record_tv_time:
                showTimeDialog();
                break;
            case  R.id.frag_record_tv_beizhu:
                showBzDiaglog();
                break;
        }
    }

    protected void showTimeDialog(){
        SelectTimeDialog dialog = new SelectTimeDialog(getContext());
        dialog.show();
        dialog.setOnEnsureListener(new SelectTimeDialog.OnEnsureListener() {
            @Override
            public void onEnsure(String time, int year, int month, int day) {
                timeTv.setText(time);
                accountBean.setTime(time);
                accountBean.setYear(year);
                accountBean.setMonth(month);
                accountBean.setDay(day);
            }
        });
    }

    //弹出备注框
    public  void showBzDiaglog() {
        final BeiZhuDialog beiZhuDialog = new BeiZhuDialog(getContext());
        beiZhuDialog.show();
        beiZhuDialog.setdialogsize();
        beiZhuDialog.setOnEnsureListener(new BeiZhuDialog.OnEnsureListener() {
            @Override
            public void onEnsure() {
                String ms = beiZhuDialog.getEditText();
                if(!TextUtils.isEmpty(ms)){
                    beizhuTv.setText(ms);
                    accountBean.setBeizhu(ms);
                }
                beiZhuDialog.cancel();
            }
        });
    }

}