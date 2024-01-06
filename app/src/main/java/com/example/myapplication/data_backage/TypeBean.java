package com.example.myapplication.data_backage;

//表示收入或者支出
public class TypeBean {
    int id;
    String typename; //类型名
    int imageId; // 未被选中的图片
    int simageId; //被选中的图片
    int kind; //0表示支出，1表示收入
    public TypeBean(int id, String typename, int imageId, int simageId, int kind) {
        this.id = id;
        this.typename = typename;
        this.imageId = imageId;
        this.simageId = simageId;
        this.kind = kind; }
    public TypeBean() {}
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTypename() {
        return typename;
    }
    public int getImageId() {
        return imageId;
    }
    public int getSimageId() {
        return simageId;
    }
    public int getKind() {
        return kind;
    }
    public void setKind(int kind) {
        this.kind = kind;
    }}
