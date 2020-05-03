package com.example.khuisf.entitys;

public class Student {
    String name,code,pic;
    int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Student(String name, String code, String pic,int state) {
        this.name = name;
        this.code = code;
        this.pic=pic;
        this.state=state;
    }

    public Student(String name, String code, String pic) {
        this.name = name;
        this.code = code;
        this.pic=pic;
    }

    public Student(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
