package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.adapter.AccountAdapter;
import com.example.myapplication.data_backage.AccountBean;
import com.example.myapplication.data_backage.AccountBeancopy;
import com.example.myapplication.data_backage.DBManager;
import com.example.myapplication.utils.CalendarDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    ListView history;
    TextView timetv;

    List<AccountBean> Datas;
    AccountAdapter adapter;
    int year,month;
    int dialogselpos=-1;
    int dialogselmonth=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        history = findViewById(R.id.history_listview);
        timetv = findViewById(R.id.history_time);
        Datas = new ArrayList<>();

        adapter = new AccountAdapter(this,Datas);
        history.setAdapter(adapter);
        inittime();
        timetv.setText(year+"年"+month+"月");
        loadData(year,month);
        setLVClickListener();

    }

    //设置listview的长按事件
    private void setLVClickListener() {
        history.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AccountBean accountbean =Datas.get(position);
                deleteitem(accountbean);
                return false;
            }
        });

    }
    private void deleteitem(AccountBean accountBean) {
        int kind = accountBean.getKind();
        final String[] choices = new String[] { "修改记录", "删除记录","查看细明"};
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems( choices, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int arg1) {
                String choice = choices[arg1];
                if(choice=="删除记录")
                {
                    int click_id = accountBean.getId();
                    //删除文件
                    DBManager.DeleteItemById(click_id);
                    Datas.remove(accountBean);  //实施刷新
                    adapter.notifyDataSetChanged();//更新数据
                }
                if(choice=="修改记录")
                {
                    AccountBeancopy accountBeancopy = new AccountBeancopy();
                    Intent IT = new Intent(HistoryActivity.this, RecordActivitycopy.class);
                    startActivity(IT);
                    int click_id = accountBean.getId();
                    if(accountBeancopy!=null&&accountBeancopy.getBeizhu()!=null&&accountBeancopy.getMoney()!=0.0f) {
                        DBManager.exchange(click_id, accountBeancopy);
                        adapter.notifyDataSetChanged();//更新数据
                    }

                }
                if(choice=="查看细明")
                {

                    Intent it = new Intent(HistoryActivity.this, DetailActivity.class);
                    it.putExtra("type",accountBean.getTypename());
                    it.putExtra("money",String.valueOf(accountBean.getMoney()));
                    it.putExtra("time",accountBean.getTime());
                    if (accountBean.getBeizhu()==null) {
                        it.putExtra("beizhu","未填写备注");
                    }
                    else {
                        it.putExtra("beizhu", accountBean.getBeizhu());
                    }
                    if(kind==1)
                    {
                        it.putExtra("kind","收入");
                    }
                    else{
                        it.putExtra("kind","支出");
                    }
                    startActivity(it);
                }
            }
        });

        builder.create().show();//显示对话框

    }

    private void loadData(int year,int month) {
        List<AccountBean> list = DBManager.getAccountListOneMonthFromAccounttb(year,month);
        Datas.clear();
        Datas.addAll(list);
        adapter.notifyDataSetChanged();
    }

    private void inittime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.history_back:
                finish();;
                break;
            case R.id.history_calendar:
                CalendarDialog dialog =new CalendarDialog(this,dialogselpos,dialogselmonth);
                dialog.show();
                dialog.setdialogsize();
                dialog.setOnRefreshListener(new CalendarDialog.OnRefreshListener() {
                    @Override
                    public void onRefresh(int selpos, int year, int month) {
                        timetv.setText(year+"年"+month+"月");
                        loadData(year,month);
                        dialogselpos = selpos;
                        dialogselmonth =month; }
                });break;
            case R.id.history_info:
                break;
        }
    }


}
