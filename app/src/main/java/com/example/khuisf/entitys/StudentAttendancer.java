package com.example.khuisf.entitys;

public class StudentAttendancer {
    String name;
    String code;

    //1=hozoor //0=gheybar // 2=null
    int status;


    public StudentAttendancer(String name, String code ,int status) {
        this.name = name;
        this.code = code;
        this.status=status;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
