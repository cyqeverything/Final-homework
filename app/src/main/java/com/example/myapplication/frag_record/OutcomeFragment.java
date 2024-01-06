package com.example.myapplication.frag_record;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.data_backage.DBManager;
import com.example.myapplication.data_backage.TypeBean;

import java.util.List;

public class OutcomeFragment extends BaseEditFragment {

    public void loadDataToGV(){
        super.loadDataToGV();
        List<TypeBean> outlist =  DBManager.getTypelist(0);
        typelist.addAll(outlist);
        adapter.notifyDataSetChanged();
        typeTv.setText("其它");
        typeIv.setImageResource(R.mipmap.ic_qita);
    }

    @Override
    public void saveAccountToDB() {
        accountBean.setKind(0);
        DBManager.insertItemToAccounttb(accountBean);

    }

}
