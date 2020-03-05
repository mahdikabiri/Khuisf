package com.example.khuisf;

public class Course {
    private String title;
    private String day;
    private String time;

    public Course(String title, String day, String time) {
        this.title = title;
        this.day = day;
        this.time = time;
    }

    public Course(String title) {
        this.title = title;
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
