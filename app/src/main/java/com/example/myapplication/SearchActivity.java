package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.adapter.AccountAdapter;
import com.example.myapplication.data_backage.AccountBean;
import com.example.myapplication.data_backage.DBManager;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    ListView searchlv;
    EditText searchet;
    TextView emptytv;
    List<AccountBean> mDatas;
    AccountAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        mDatas = new ArrayList<>();
        adapter = new AccountAdapter(this,mDatas);
        searchlv.setAdapter(adapter);
        searchlv.setEmptyView(emptytv);
    }

    private void initView() {
        searchet = findViewById(R.id.search_iv_edit);
        searchlv = findViewById(R.id.search_lv);
        emptytv= findViewById(R.id.search_tv_empty);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_iv_back:
                finish();
                break;
            case R.id.search_iv_sh:
               String MSG =  searchet.getText().toString().trim();
                if (TextUtils.isEmpty(MSG)) {
                    Toast.makeText(this,"输入内容不可为空",Toast.LENGTH_SHORT).show();
                    return; }
                List<AccountBean>list = DBManager.getAccountListByReamrkFromAccounttb(MSG);
                mDatas.addAll(list);
                adapter.notifyDataSetChanged();
                break;
        }
    }
}