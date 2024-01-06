package com.example.myapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.data_backage.AccountBean;

import java.util.Calendar;
import java.util.List;

public class AccountAdapter extends BaseAdapter {
    Context context;
    List<AccountBean> mDatas;
    LayoutInflater inflater;
    int year,month,day;
    public AccountAdapter(Context context, List<AccountBean> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        inflater = LayoutInflater.from(context);
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }
    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView==null)
        {
            convertView = inflater.inflate(R.layout.item_mainlv,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        AccountBean bean = mDatas.get(position);
        int kind = bean.getKind();
        holder.typeiv.setImageResource(bean.getsImageId());
        holder.typetv.setText(bean.getTypename());
        holder.beizhutv.setText(bean.getBeizhu());
        if(kind==1)
        { holder.moneytv.setText("+￥ "+bean.getMoney());
        }
        else{
            holder.moneytv.setText("-￥ "+bean.getMoney());
           ;
        }
        holder.timetv.setText(bean.getTime());
        return convertView;
    }

    class ViewHolder{
        ImageView typeiv;
        TextView typetv,beizhutv,timetv,moneytv;
        public ViewHolder(View view){
            typeiv = view.findViewById(R.id.item_mainlv_iv);
            beizhutv = view.findViewById(R.id.item_mainlv_tv_beizhu);
            timetv = view.findViewById(R.id.item_mainlv_tv_time);
            typetv = view.findViewById(R.id.item_mainlv_tv_title);
            moneytv = view.findViewById(R.id.item_mainlv_tv_money);
        }
    }

}
