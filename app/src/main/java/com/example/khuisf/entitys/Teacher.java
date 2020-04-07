package com.example.khuisf.entitys;

public class Teacher {
    private String teacherName,courseName;

    public Teacher(String teacherName, String courseName) {
        this.teacherName = teacherName;
        this.courseName = courseName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getCourseName() {
        return courseName;
    }
}
