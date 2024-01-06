package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.adapter.AccountAdapter;
import com.example.myapplication.data_backage.AccountBean;
import com.example.myapplication.data_backage.AccountBeancopy;
import com.example.myapplication.data_backage.DBManager;
import com.example.myapplication.utils.BudgetDialog;
import com.example.myapplication.utils.MoreDialog;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ListView todayLv; //展示今日收支情况
    Button returnbtn;
    ImageButton morebtn, editbtn;
    //数据源
    List<AccountBean> mDatas;
    int year, month, day;
    AccountAdapter adapter;
    View headerView;
    //头布局相关控件
    TextView topout, topin, topbudget, topcon;
    SharedPreferences preferences;

    private NavigationView navView;//导航视图
    private static DrawerLayout drawerLayout;//滑动菜单

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inittime();
        initview();
        preferences = getSharedPreferences("budget", Context.MODE_PRIVATE);
        //添加头布局
        addLVHeaderView();
        mDatas = new ArrayList<>();
        //设置适配器
        adapter = new AccountAdapter(this, mDatas);
        todayLv.setAdapter(adapter);
        edit();
    }


    public void edit(){
        drawerLayout = findViewById(R.id.drawer_layout);
        findViewById(R.id.main_btn_more).setOnClickListener(v -> {
            drawerLayout.openDrawer(GravityCompat.START); });//打开滑动菜单  左侧出现
        navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(item -> {//导航菜单点击
            Intent intent = new Intent();
            switch (item.getItemId()) {
                case R.id.nav_history:
                    intent.setClass(this, HistoryActivity.class);
                    startActivity(intent);break;
                case R.id.nav_tongji:
                    intent.setClass(this, MonthChartActivity.class);
                    startActivity(intent);break;
                case R.id.nav_about:
                    intent.setClass(this, AboutActivity.class);
                    startActivity(intent);break;
                case R.id.nav_setting:
                    intent.setClass(this, SettingActivity.class);
                    startActivity(intent);break;
                case R.id.nav_geren:
                    intent.setClass(this, PersonActivity.class);
                    startActivity(intent);break;
                case R.id.nav_shouye: break; }
            drawerLayout.closeDrawer(GravityCompat.START);//关闭滑动菜单
            return true;
        }); }


    //初始化自带的view方法
    private void initview() {
        todayLv = findViewById(R.id.main_lv);
        editbtn = findViewById(R.id.main_btn_edit);
        morebtn = findViewById(R.id.main_btn_more);
        returnbtn = findViewById(R.id.main_btn_return);
        topout = findViewById(R.id.item_mainlv_top_tv_out);
        topin = findViewById(R.id.item_mainlv_top_tv_in);
        topbudget = findViewById(R.id.item_mainlv_top_tv_budget);
        topcon = findViewById(R.id.item_mainlv_top_tv_day);
        editbtn.setOnClickListener(this);
        morebtn.setOnClickListener(this);
        returnbtn.setOnClickListener(this);
        setLVLongClickListener();
    }

    /** 给ListView添加头布局的方法*/
    private void addLVHeaderView() {
        //将布局转换成View对象
        headerView = getLayoutInflater().inflate(R.layout.item_mainlv_top, null);
        todayLv.addHeaderView(headerView);
        //查找头布局可用控件
        topout = headerView.findViewById(R.id.item_mainlv_top_tv_out);
        topin = headerView.findViewById(R.id.item_mainlv_top_tv_in);
        topbudget = headerView.findViewById(R.id.item_mainlv_top_tv_budget);
        topcon = headerView.findViewById(R.id.item_mainlv_top_tv_day);

        topbudget.setOnClickListener(this);
        headerView.setOnClickListener(this);

    }

    //长按事件
    private void setLVLongClickListener() {
        todayLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
               if (position ==0) {
                    AccountBean accountBean = mDatas.get(position);
                    ShowItemDialog(accountBean);
                    return false;
                }
                else
                {
                    int pos = position-1;
                    AccountBean accountBean = mDatas.get(pos);
                    //弹出提示信息
                    ShowItemDialog(accountBean);
                    return false;
                }
            }
        });
    }
    private void ShowItemDialog(AccountBean accountBean) {
        int kind = accountBean.getKind();
        final String[] choices = new String[] { "修改记录", "删除记录","查看细明"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems( choices, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int arg1) {
                String choice = choices[arg1];
                if(choice=="删除记录")
                {
               int click_id = accountBean.getId();
                //删除文件
                DBManager.DeleteItemById(click_id);
                mDatas.remove(accountBean);  //实施刷新
                adapter.notifyDataSetChanged();//更新数据
                setTopTvshow(); //改变头布局显示的内容
              }
                if(choice=="修改记录")
                {
                    AccountBeancopy accountBeancopy = new AccountBeancopy();
                    Intent IT = new Intent(MainActivity.this, RecordActivitycopy.class);
                    startActivity(IT);
                    int click_id = accountBean.getId();
                    if(accountBeancopy!=null&&accountBeancopy.getBeizhu()!=null&&accountBeancopy.getMoney()!=0.0f) {
                        DBManager.exchange(click_id, accountBeancopy);
                        adapter.notifyDataSetChanged();//更新数据
                        setTopTvshow(); //改变头布局显示的内容
                    }
                }
                if(choice=="查看细明")
                {
                    Intent it = new Intent(MainActivity.this, DetailActivity.class);
                    it.putExtra("type",accountBean.getTypename());
                    it.putExtra("money",String.valueOf(accountBean.getMoney()));
                    it.putExtra("time",accountBean.getTime());
                    if (accountBean.getBeizhu()==null) { it.putExtra("beizhu","未填写备注"); }
                    else { it.putExtra("beizhu", accountBean.getBeizhu()); }
                    if(kind==1)
                    { it.putExtra("kind","收入"); }
                    else{ it.putExtra("kind","支出"); }
                    startActivity(it);
                }
            }
        });
        builder.create().show();//显示对话框
    }

    private void inittime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    //当获取焦点时调用
    @Override
    protected void onResume() {

        super.onResume();
        loadDBData();
        setTopTvshow();

    }
    //设置头布局文本内容
    private void setTopTvshow() {
        //获取今日收入,支出
        float income_day = DBManager.getSumday(year,month,day,1);
        float outcome_day = DBManager.getSumday(year,month,day,0);
        String infoOneDay = "今日支出:￥"+outcome_day+"       今日收入:￥"+income_day;
        topcon.setText(infoOneDay);

        //获取本月支出，收入
        float income_month = DBManager.getSummonth(year,month,1);
        float outcome_month = DBManager.getSummonth(year,month,0);
        topin.setText("￥ "+income_month);
        topout.setText("￥ "+outcome_month);
        //设置显示预算剩余
        float budgetmoney = preferences.getFloat("budgetmoney",0);
        if(budgetmoney==0)
        {
            topbudget.setText("￥ 0");
        }
        else{
            float shengyu = budgetmoney - outcome_month;
            topbudget.setText("￥ "+shengyu);
        }

    }

    private void loadDBData() {
        List<AccountBean> list = DBManager.getAccountListOneDayFromAccounttb(year, month, day);
        mDatas.clear();
        mDatas.addAll(list);
        adapter.notifyDataSetChanged();

    }


    private void setListViewPos(int pos) {
        if (android.os.Build.VERSION.SDK_INT >= 6) {
            todayLv.smoothScrollToPosition(pos);
        } else {
            todayLv.setSelection(pos);
        }
    }



    @Override
    public void onClick(View v) {
        Intent intent_ = new Intent();
        switch (v.getId()) {
            case R.id.main_btn_edit:
                Intent it1 = new Intent(this, RecordActivity.class);
                startActivity(it1);
                break;
            case R.id.main_iv_search:
                Intent it2 = new Intent(this, SearchActivity.class);
                startActivity(it2);
                break;
           // case R.id.main_btn_more:
             //   MoreDialog moreDialog = new MoreDialog(this);
              //  moreDialog.show();
              //  moreDialog.setdialogsize();
                //break;
            case R.id.item_mainlv_top_tv_budget:
                showBudgetDialog();
                break;
            case R.id.main_btn_return:
                setListViewPos(0);
                break;
        }
        if(v==headerView)
        {
            Intent intent = new Intent();
            intent.setClass(this, MonthChartActivity.class);
            startActivity(intent);
        }
    }

    private void showBudgetDialog() {
        BudgetDialog budgetDialog = new BudgetDialog(this);
        budgetDialog.show();
        budgetDialog.setdialogsize();
        budgetDialog.setOnEnsureListener(new BudgetDialog.OnEnsureListener() {
            @Override
            public void onEnsure(float money) {
                //存储预算金额
                SharedPreferences.Editor editor = preferences.edit();
                editor.putFloat("budgetmoney",money);
                editor.commit();

                //计算剩余金额
                float outmonth = DBManager.getSummonth(year,month,0);

                float shengyu = money-outmonth;
                topbudget.setText("￥ "+shengyu);
            }
        });
    }

}