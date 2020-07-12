package com.example.khuisf.entitys.course;

import android.os.Parcel;
import android.os.Parcelable;


public class Course implements Parcelable {
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setCharac(String charac) {
        this.charac = charac;
    }

    private String title;
    private String day;
    private String time;
    private String charac;

    public Course(int id, String title, String day, String time, String charac) {
        this.id = id;
        this.title = title;
        this.day = day;
        this.charac = charac;
        this.time = time;
    }

    public Course(String title, String day, String time, String charac) {
        this.title = title;
        this.day = day;
        this.charac = charac;
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

    public int getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.day);
        dest.writeString(this.time);
        dest.writeString(this.charac);
    }

    protected Course(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.day = in.readString();
        this.time = in.readString();
        this.charac = in.readString();
    }

    public static final Parcelable.Creator<Course> CREATOR = new Parcelable.Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel source) {
            return new Course(source);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };
}
