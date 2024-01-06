package com.example.myapplication.frag_chart;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.data_backage.DBManager;


public class OutcomeChartFragment extends BaseFragmentChart {

    int kind = 0;
    @Override
    public void onResume() {
        super.onResume();
        loadData(year,month,kind);
        pieChart.setCenterText("总支出\n" + DBManager.getSummonth(year, month, kind));
        setData(kind);
    }

    @Override
    public void setDate(int year, int month) {
        super.setDate(year, month);
        loadData(year,month,kind);
    }

}