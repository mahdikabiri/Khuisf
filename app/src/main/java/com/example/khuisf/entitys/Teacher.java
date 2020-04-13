package com.example.khuisf.entitys;

public class Teacher {
    private String teacherName,courseName,teacherCode;

    public Teacher(String teacherName, String courseName, String teacherCode) {
        this.teacherName = teacherName;
        this.courseName = courseName;
        this.teacherCode = teacherCode;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getTeacherCode() {
        return teacherCode;
    }

    public String getCourseName() {
        return courseName;
    }
}
