package com.example.khuisf.entitys;

public class Attendance {
    String date;
    String status;

    public Attendance(String date, String status) {
        this.date = date;
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }
}
