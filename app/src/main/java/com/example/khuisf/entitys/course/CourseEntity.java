package com.example.khuisf.entitys.course;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_courses")
public class CourseEntity implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String characteristic;
    private String date;
    private String day;//in week
    private String Time;
    private String place;
    private String master;
    private String testDay;
    private int unitAc;
    private int unitTh;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(String characteristic) {
        this.characteristic = characteristic;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public String getTestDay() {
        return testDay;
    }

    public void setTestDay(String testDay) {
        this.testDay = testDay;
    }

    public int getUnitAc() {
        return unitAc;
    }

    public void setUnitAc(int unitAc) {
        this.unitAc = unitAc;
    }

    public int getUnitTh() {
        return unitTh;
    }

    public void setUnitTh(int unitTh) {
        this.unitTh = unitTh;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.characteristic);
        dest.writeString(this.date);
        dest.writeString(this.day);
        dest.writeString(this.Time);
        dest.writeString(this.place);
        dest.writeString(this.master);
        dest.writeString(this.testDay);
        dest.writeInt(this.unitAc);
        dest.writeInt(this.unitTh);
    }

    public CourseEntity() {
    }

    protected CourseEntity(Parcel in) {
        this.id = in.readInt();
        this.characteristic = in.readString();
        this.date = in.readString();
        this.day = in.readString();
        this.Time = in.readString();
        this.place = in.readString();
        this.master = in.readString();
        this.testDay = in.readString();
        this.unitAc = in.readInt();
        this.unitTh = in.readInt();
    }

    public static final Parcelable.Creator<CourseEntity> CREATOR = new Parcelable.Creator<CourseEntity>() {
        @Override
        public CourseEntity createFromParcel(Parcel source) {
            return new CourseEntity(source);
        }

        @Override
        public CourseEntity[] newArray(int size) {
            return new CourseEntity[size];
        }
    };
}
