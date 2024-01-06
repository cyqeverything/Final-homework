package com.example.myapplication.frag_record;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.data_backage.DBManager;
import com.example.myapplication.data_backage.TypeBean;

import java.util.List;


public class IncomeFragment extends BaseEditFragment {

    public void loadDataToGV(){
        super.loadDataToGV();
        List<TypeBean> inlist =  DBManager.getTypelist(1);
        typelist.addAll(inlist);
        adapter.notifyDataSetChanged();
        typeTv.setText("其它");
        typeIv.setImageResource(R.mipmap.ic_qita);
    }

    @Override
    public void saveAccountToDB() {
        accountBean.setKind(1);
        DBManager.insertItemToAccounttb(accountBean);
    }
}