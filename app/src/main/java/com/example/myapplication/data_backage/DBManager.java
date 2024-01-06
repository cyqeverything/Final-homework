package com.example.myapplication.data_backage;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.utils.FloatUtils;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;


//负责管理数据库的类
//对表进行操作：增删改查
public class DBManager {
    private static SQLiteDatabase db;

    //初始化数据库对象
    public static void initdb(Context context){
        self_data helper = new self_data(context);
        db = helper.getWritableDatabase(); //得到数据库对象
    }

    //读取数据库当中的数据，写入内存集合里
    //kind:表示收入或者支出
    @SuppressLint("Range")
    public static List<TypeBean> getTypelist(int kind){
        List<TypeBean>list = new ArrayList<>();
        //读取typetb表中的数据
        String sql = "select * from typetb where kind = " +kind;
        Cursor cursor = db.rawQuery(sql,null);
        //循环读取图标，存储到对象当中
       while(cursor.moveToNext()){
           String typename = cursor.getString(cursor.getColumnIndex("typename"));
           int imageId = cursor.getInt(cursor.getColumnIndex("imageId"));
           int simageId = cursor.getInt(cursor.getColumnIndex("simageId"));
           int kind1 = cursor.getInt(cursor.getColumnIndex("kind"));
           int id = cursor.getInt(cursor.getColumnIndex("id"));
           TypeBean typeBean = new TypeBean(id,typename,imageId,simageId,kind);
           list.add(typeBean);
       }
        return list;
    }

    //像记账表当中插入一条元素
    public static void insertItemToAccounttb(AccountBean bean){
        ContentValues values =new ContentValues();
        values.put("typename",bean.getTypename());
        values.put("imageId",bean.getsImageId());
        values.put("beizhu",bean.getBeizhu());
        values.put("money",bean.getMoney());
        values.put("time",bean.getTime());
        values.put("year",bean.getYear());
        values.put("month",bean.getMonth());
        values.put("day",bean.getDay());
        values.put("kind",bean.getKind());
        db.insert("accounttb",null,values);
    }

    //获取一天的收入支出情况
    @SuppressLint("Range")
    public static List<AccountBean>getAccountListOneDayFromAccounttb(int year,int month,int day){
        List<AccountBean>list = new ArrayList<>();
        String sql = "select * from accounttb where year=? and month=? and day=? order by id desc";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", day + ""});
        //遍历符合要求的每一行数据
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String typename = cursor.getString(cursor.getColumnIndex("typename"));
            String beizhu = cursor.getString(cursor.getColumnIndex("beizhu"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            int sImageId = cursor.getInt(cursor.getColumnIndex("imageId"));
            int kind = cursor.getInt(cursor.getColumnIndex("kind"));
            float money = cursor.getFloat(cursor.getColumnIndex("money"));
            AccountBean accountBean = new AccountBean(id, typename, sImageId, beizhu, money, time, year, month, day, kind);
            list.add(accountBean);
        }
        return list;
    }
    //获取一月的收入支出情况
    @SuppressLint("Range")
    public static List<AccountBean>getAccountListOneMonthFromAccounttb(int year,int month){
        List<AccountBean>list = new ArrayList<>();
        String sql = "select * from accounttb where year=? and month=? order by id desc";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + ""});
        //遍历符合要求的每一行数据
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String typename = cursor.getString(cursor.getColumnIndex("typename"));
            String beizhu = cursor.getString(cursor.getColumnIndex("beizhu"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            int sImageId = cursor.getInt(cursor.getColumnIndex("imageId"));
            int kind = cursor.getInt(cursor.getColumnIndex("kind"));
            float money = cursor.getFloat(cursor.getColumnIndex("money"));
            int day = cursor.getInt(cursor.getColumnIndex("day"));
            AccountBean accountBean = new AccountBean(id, typename, sImageId, beizhu, money, time, year, month, day, kind);
            list.add(accountBean);
        }
        return list;
    }


    //获取某一天的支出收入的总金额 kind=0/1
    @SuppressLint("Range")
    public static float getSumday(int year,int month,int day,int kind){
        float sum=0.0f;
        String sql = "select sum(money) from accounttb where year=? and month=? and day=? and kind=?";
        Cursor cursor = db.rawQuery(sql,new String[]{year+"",month+"",day+"",kind+""});

        if(cursor.moveToFirst()){
            float money = cursor.getFloat(cursor.getColumnIndex("sum(money)"));
            sum=money;
        }
        return sum;

    }

    //获取某一月的支出收入的总金额 kind=0/1
    @SuppressLint("Range")
    public static float getSummonth(int year,int month,int kind){
        float sum=0.0f;
        String sql = "select sum(money) from accounttb where year=? and month=? and kind=?";
        Cursor cursor = db.rawQuery(sql,new String[]{year+"",month+"",kind+""});

        if(cursor.moveToFirst()){
            float money = cursor.getFloat(cursor.getColumnIndex("sum(money)"));
            sum=money;
        }
        return sum;
    }


    /** 统计某月份支出或者收入情况有多少条  收入-1   支出-0*/
    @SuppressLint("Range")
    public static int getCountItemOneMonth(int year,int month,int kind){
        int total = 0;
        String sql = "select count(money) from accounttb where year=? and month=? and kind=?";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", kind + ""});
        if (cursor.moveToFirst()) {
            int count = cursor.getInt(cursor.getColumnIndex("count(money)"));
            total = count;
        }
        return total;
    }

    //根据id删除出accounttb的数据
    public static void DeleteItemById(int id){
        String sql = "delete from accounttb where id ="+id;
        db.execSQL(sql);
    }


    public static void exchange(int id,AccountBeancopy accountBeancopy){

        db.execSQL("update accounttb set typename=?,imageId=?,beizhu=?,money=?,time=?,year=?," +
                "month=?,day=?,kind=? where id="+id,new Object[]{accountBeancopy.getTypename(),
                accountBeancopy.getsImageId(),accountBeancopy.getBeizhu(),accountBeancopy.getMoney()
                , accountBeancopy.getTime(),accountBeancopy.getYear(),accountBeancopy.getMonth(),
                accountBeancopy.getDay(),accountBeancopy.getKind()});
    }



    @SuppressLint("Range")
    public static List<AccountBean>getAccountListByReamrkFromAccounttb(String beizhu) {
        List<AccountBean> list = new ArrayList<>();
        String sql = "select * from accounttb where beizhu like '%" + beizhu + "%'";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String typename = cursor.getString(cursor.getColumnIndex("typename"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            int sImageId = cursor.getInt(cursor.getColumnIndex("imageId"));
            int kind = cursor.getInt(cursor.getColumnIndex("kind"));
            float money = cursor.getFloat(cursor.getColumnIndex("money"));
            int year = cursor.getInt(cursor.getColumnIndex("year"));
            int month = cursor.getInt(cursor.getColumnIndex("month"));
            int day = cursor.getInt(cursor.getColumnIndex("day"));
            AccountBean accountBean = new AccountBean(id, typename, sImageId, beizhu, money, time, year, month, day, kind);
            list.add(accountBean);
        }
        return  list;
    }

    @SuppressLint("Range")
    //查询记账表中的年份信息
    public static List<Integer> getYearList(){
        List<Integer>list = new ArrayList<>();
        String sql = "select distinct(year) from accounttb order by year asc";
        Cursor cursor = db.rawQuery(sql,null);
        while (cursor.moveToNext())
        {
            int year = cursor.getInt(cursor.getColumnIndex("year"));
            list.add(year);
        }
        return list;

    }

    //删除Account表格所有数据
    public static void deleteAllAccount(){
        String sql = "delete from accounttb";
        db.execSQL(sql);
    }

    /**
     * 查询指定年份和月份的收入或者支出每一种类型的总钱数
     * */
    @SuppressLint("Range")
    public static List<ChartItemBean>getChartListFromAccounttb(int year,int month,int kind){
        List<ChartItemBean>list = new ArrayList<>();
        float sumMoneyOneMonth = getSummonth(year, month, kind);  //求出支出或者收入总钱数
        String sql = "select typename,imageId,sum(money)as total from accounttb where year=? and month=? and kind=? group by typename " +
                "order by total desc";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", kind + ""});
        while (cursor.moveToNext()) {
            int sImageId = cursor.getInt(cursor.getColumnIndex("imageId"));
            String typename = cursor.getString(cursor.getColumnIndex("typename"));
            float total = cursor.getFloat(cursor.getColumnIndex("total"));
            //计算所占百分比  total /sumMonth
            float ratio = FloatUtils.div(total,sumMoneyOneMonth);
            ChartItemBean bean = new ChartItemBean(sImageId, typename, ratio, total);
            list.add(bean);
        }
        return list;
    }

    @SuppressLint("Range")
    public static List<PieEntry>getPiEntryFromAccounttb(int year, int month, int kind){
        List<PieEntry>list = new ArrayList<>();
        float sumMoneyOneMonth = getSummonth(year, month, kind);  //求出支出或者收入总钱数
        String sql = "select typename,sum(money)as total from accounttb where year=? and month=? and kind=? group by typename " +
                "order by total desc";
        Cursor cursor = db.rawQuery(sql, new String[]{year + "", month + "", kind + ""});
        while (cursor.moveToNext()) {
            String typename = cursor.getString(cursor.getColumnIndex("typename"));
            float total = cursor.getFloat(cursor.getColumnIndex("total"));
            //计算所占百分比  total /sumMonth
            float ratio = FloatUtils.div(total,sumMoneyOneMonth);
            PieEntry bean = new PieEntry(ratio,typename);
            list.add(bean);
        }
        return list;
    }

}
