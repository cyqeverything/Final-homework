package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.myapplication.data_backage.DBManager;

public class ExchangeActivity extends AppCompatActivity {
    EditText money,type,kind,date,beizhu;
    String money1,type1,kind1,date1,beizhu1;
    public ExchangeActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange);
        InitView();
        updatedata();
    }

    private void falseEdit(EditText eidtext)
    {
        eidtext.setEnabled(false);//去掉点击时编辑框下面横线:
        eidtext.setFocusable(false);//不可编辑
        eidtext.setFocusableInTouchMode(false);//不可编辑
    }


    private void InitView() {
        Intent intent = getIntent();
        money = findViewById(R.id.exchange_money);
        type = findViewById(R.id.exchange_type);
        kind = findViewById(R.id.exchange_kind);
        date = findViewById(R.id.exchange_riqi);
        beizhu = findViewById(R.id.exchange_beizhu);
        money.setText(intent.getStringExtra("money"));
        type.setText(intent.getStringExtra("type"));
        date.setText(intent.getStringExtra("time"));
        beizhu.setText(intent.getStringExtra("beizhu"));
        kind.setText(intent.getStringExtra("kind"));
        falseEdit(type);
        falseEdit(kind);



    }

    public void updatedata()
    {
        money1 = String.valueOf(money.getText());
         type1 = String.valueOf(type.getText());
         kind1 = String.valueOf(kind.getText());
         date1 = String.valueOf(date.getText());
         beizhu1 = String.valueOf(beizhu.getText());
         Intent it2 = new Intent(ExchangeActivity.this,DBManager.class);
         it2.putExtra("money",money1);
         it2.putExtra("type",type1);
         it2.putExtra("kind",kind1);
         it2.putExtra("date",date1);
         it2.putExtra("beizhu",beizhu1);
        System.out.println(money1);
        System.out.println(type1);
        System.out.println(kind1);
        System.out.println(date1);
        System.out.println(beizhu1);


    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.exchange_back:
                InitView();
                finish();
                break;
            case R.id.exchange_save:
                finish();
                break;
        }
    }
}