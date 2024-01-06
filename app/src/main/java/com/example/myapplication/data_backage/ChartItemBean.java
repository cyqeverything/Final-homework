package com.example.myapplication.data_backage;

public class ChartItemBean {
    int sImageId;
    String type;
    float ratio;   //所占比例
    float totalMoney;  //此项的总钱数

    public ChartItemBean() {
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getsImageId() {
        return sImageId;
    }

    public String getType() {
        return type;
    }

    public float getRatio() {
        return ratio;
    }

    public float getTotalMoney() {
        return totalMoney;
    }

    public ChartItemBean(int sImageId, String type, float ratio, float totalMoney) {
        this.sImageId = sImageId;
        this.type = type;
        this.ratio = ratio;
        this.totalMoney = totalMoney;
    }
}
