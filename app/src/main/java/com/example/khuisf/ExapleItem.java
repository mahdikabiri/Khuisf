package com.example.khuisf;

public class ExapleItem {
    private String title;
    private String day;
    private String time;

    public ExapleItem(String title, String day, String time) {
        this.title = title;
        this.day = day;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }
}
