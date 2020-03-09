package com.example.khuisf;

public class Course {
    private String title;
    private String day;
    private String time;
    private String charac;

    public Course(String title, String day, String time,String charac) {
        this.title = title;
        this.day = day;
        this.charac=charac;
        this.time = time;
    }

    public Course(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getCharac() {
        return charac;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }
}
