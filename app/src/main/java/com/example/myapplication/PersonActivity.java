package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.data_backage.DBManager;

public class PersonActivity extends AppCompatActivity implements View.OnClickListener {

    TextView t1,t2,t3,t4,t5,t6;
    ImageView i1,i2;
    RelativeLayout r1,r2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerenzhongxin);
        initview();
    }

    private void initview() {
        r1 = findViewById(R.id.geren_top);
        r2=  findViewById(R.id.geren_mid);
        t1 = findViewById(R.id.geren_geren);
        t2=  findViewById(R.id.geren_single);
        t3= findViewById(R.id.geren_jilu);
        t4 = findViewById(R.id.geren_jizhang);
        t5 = findViewById(R.id.geren_yusuan);
        t6 = findViewById(R.id.geren_clear);
        i1= findViewById(R.id.geren_back);
        i2 = findViewById(R.id.geren_touxiang);

        t3.setOnClickListener(this);
        t4.setOnClickListener(this);
        t5.setOnClickListener(this);
        t6.setOnClickListener(this);
    }

    public void onClick(View v){
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.geren_jilu:
                intent = new Intent(PersonActivity.this,MonthChartActivity.class);
                startActivity(intent);
                break;
            case  R.id.geren_jizhang:
                intent = new Intent(PersonActivity.this,RecordActivity.class);
                startActivity(intent);
                break;
            case  R.id.geren_yusuan:
                intent = new Intent(PersonActivity.this,MainActivity.class);
                startActivity(intent);
                break;
            case  R.id.geren_clear:
                showDeleteDialog();
                break;
            case  R.id.geren_back:
                finish();
                break;

        }
    }


    private void showDeleteDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("删除提示")
                .setMessage("您确定要删除所有记录吗?\t注意: 删除后无法恢复，请慎重选择!")
                .setPositiveButton("取消",null)
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBManager.deleteAllAccount();
                        Toast.makeText(PersonActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                    }
                });
        builder.create().show();
    }
}