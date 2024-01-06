package com.example.myapplication.frag_chart;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ChartItemAdapter;
import com.example.myapplication.data_backage.ChartItemBean;
import com.example.myapplication.data_backage.DBManager;

import java.util.ArrayList;
import java.util.List;


public class IncomeChartFragment extends BaseFragmentChart {
    int kind = 1;
    @Override
    public void onResume() {
        super.onResume();
        loadData(year,month,kind);
        pieChart.setCenterText("总收入\n" + DBManager.getSummonth(year, month, kind));
        setData(kind);
    }

    @Override
    public void setDate(int year, int month) {
        super.setDate(year, month);
        loadData(year,month,kind);
    }

}