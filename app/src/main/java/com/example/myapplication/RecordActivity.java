package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.adapter.RecordPagerAdapter;
import com.example.myapplication.frag_record.IncomeFragment;
import com.example.myapplication.frag_record.BaseEditFragment;
import com.example.myapplication.frag_record.OutcomeFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class RecordActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        //1.查找控件
        tabLayout = findViewById(R.id.record_tabs);
        viewPager = findViewById(R.id.record_vp);
        //2.设置ViewPager加载页面
        initPager();
    }

    private void initPager() {
        List<Fragment>fragmentList = new ArrayList<>();//初始化ViewPager页面的集合
        //创建收入和支出页面，放置在Fragment当中
        OutcomeFragment outFrag = new OutcomeFragment();//支出
        IncomeFragment inFrag = new IncomeFragment();//收入
        fragmentList.add(outFrag);
        fragmentList.add(inFrag);
        RecordPagerAdapter pagerAdapter = new RecordPagerAdapter(getSupportFragmentManager(), fragmentList);//创建适配器
        viewPager.setAdapter(pagerAdapter);//设置适配器
        tabLayout.setupWithViewPager(viewPager);//将TabLayout和ViwePager进行关联
        }

    /* 点击事件*/
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.record_iv_back:
                finish();
                break;
        }
    }
}
