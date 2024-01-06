package com.example.myapplication.frag_chart;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ChartItemAdapter;
import com.example.myapplication.data_backage.ChartItemBean;
import com.example.myapplication.data_backage.DBManager;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

abstract public class BaseFragmentChart extends Fragment {
    ListView chartLv;
    int kind;
     int year;
     int month;
    List<ChartItemBean> mDatas;   //数据源
    private ChartItemAdapter itemAdapter;
    PieChart pieChart;
    TextView chartTv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_income_chart, container, false);
        chartLv = view.findViewById(R.id.frag_chart_lv);
        //获取Activity传递的数据
        Bundle bundle = getArguments();
        year = bundle.getInt("year");
        month = bundle.getInt("month");
        //设置数据源
        mDatas = new ArrayList<>();
//        设置适配器
        itemAdapter = new ChartItemAdapter(getContext(), mDatas);
        chartLv.setAdapter(itemAdapter);
        //添加头布局
        addLVHeaderView(kind);
        setData(kind);
        return view;
    }

    protected void addLVHeaderView(int kind){
//        将布局转换成View对象
        View headerView = getLayoutInflater().inflate(R.layout.item_chartfrag_top,null);
//        将View添加到ListView的头布局上
        chartLv.addHeaderView(headerView);
//        查找头布局当中包含的控件
        pieChart = headerView.findViewById(R.id.item_chartfrag_chart);
        chartTv = headerView.findViewById(R.id.item_chartfrag_top_tv);
//        设定图显示描述
        //pieChart.setCenterText("总支出\n" + DBManager.getSummonth(year, month, kind));
        pieChart.setCenterTextSize(16f);
        pieChart.setCenterTextColor(Color.BLACK);
        pieChart.setHoleColor(Color.parseColor("#FFBB86FC"));
        pieChart.getDescription().setEnabled(false);
        pieChart.setBackgroundColor(Color.parseColor("#f3f3f3"));
        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        pieChart.setEntryLabelColor(Color.parseColor("#7D7D7D"));
        //设置pieChart图表文本字体颜色
        pieChart.setEntryLabelTextSize(10f);
//        设置图的内边距
        pieChart.setExtraOffsets(10, 10, 10, 10);

   }

    public void setData(int kind) {
        List<PieEntry> pieEntries = DBManager.getPiEntryFromAccounttb(year,month,kind);
        // 把准备好的数据统一进行格式设置
        PieDataSet pieDataSet = new PieDataSet(pieEntries,null);
        // 设置饼图各部分的颜色
        pieDataSet.setColors(Color.parseColor("#D81B60"), Color.parseColor("#66ffff")
        ,Color.parseColor("#FFF68F"),Color.parseColor("#FA8072"),Color.parseColor("#C6E2FF"),
                Color.parseColor("#6600ff"));
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

                pieDataSet.setValueFormatter(new IValueFormatter() {
                    @Override
                    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                        // 此处的value就是PieEntry（）中第一个参数的value
                        value = value*100;
                        BigDecimal b1 = new BigDecimal(value);
                        float v1 = b1.setScale(2, 4).floatValue();
                        return v1+"%";
                    }
                });

                // 设置饼图中数据显示的格式
                pieDataSet.setValueTextSize(15f);
                pieDataSet.setValueTextColor(Color.BLACK);
                pieDataSet.setSliceSpace(5f); // 设置扇区中的间隔

                // 设置饼图显示的线
                pieDataSet.setValueLineColor(Color.GRAY);
                pieDataSet.setValueLineWidth(1);
                pieDataSet.setValueLinePart1OffsetPercentage(70.f); // 第一条线离圆心的百分比
                pieDataSet.setValueLinePart1Length(0.6f); // 第一条线长度
                pieDataSet.setValueLinePart2Length(0.8f); // 第二条线长度
                pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE); // 设置值显示的位置


            }

            @Override
            public void onNothingSelected() {
                pieDataSet.setValueLineColor(Color.parseColor("#00000000"));
                pieDataSet.setValueTextColor(Color.parseColor("#00000000"));
            }
        });

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData); // 为饼图设置数据
    }


    @Override
    public void onResume() {
        super.onResume();
        loadData(year,month,1);
    }

    void loadData(int year, int month, int kind) {
        List<ChartItemBean> list = DBManager.getChartListFromAccounttb(year, month, kind);
        mDatas.clear();
        mDatas.addAll(list);
        itemAdapter.notifyDataSetChanged();
    }

    public void setDate(int year,int month)
    {
        this.year=year;
        this.month=month;
    }

}
