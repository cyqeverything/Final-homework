package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity{
    EditText money,type,kind,date,beizhu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();

    }
    private void falseEdit(EditText eidtext)
    {
        eidtext.setEnabled(false);//去掉点击时编辑框下面横线:
        eidtext.setFocusable(false);//不可编辑
        eidtext.setFocusableInTouchMode(false);//不可编辑
    }

    private void initView() {
        Intent intent = getIntent();
        money = findViewById(R.id.change_money);
        type = findViewById(R.id.change_type);
        kind = findViewById(R.id.change_kind);
        date = findViewById(R.id.change_riqi);
        beizhu = findViewById(R.id.change_beizhu);
        money.setText(intent.getStringExtra("money"));
        type.setText(intent.getStringExtra("type"));
        date.setText(intent.getStringExtra("time"));
        beizhu.setText(intent.getStringExtra("beizhu"));
        kind.setText(intent.getStringExtra("kind"));
        falseEdit(money);
        falseEdit(type);
        falseEdit(kind);
        falseEdit(date);
        falseEdit(beizhu);
    }

    public void onClick(View view)
    {
        switch (view.getId()) {
            case R.id.change_back:
                finish();
                break;

        }
    }

}