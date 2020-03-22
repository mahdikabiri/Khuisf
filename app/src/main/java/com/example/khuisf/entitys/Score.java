package com.example.khuisf.entitys;

public class Score {
    String courseName,unitTeori,unitAmali,score;

    public Score(String courseName, String unitTeori, String unitAmali, String score) {
        this.courseName = courseName;
        this.unitTeori = unitTeori;
        this.unitAmali = unitAmali;
        this.score = score;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getUnitTeori() {
        return unitTeori;
    }

    public String getUnitAmali() {
        return unitAmali;
    }

    public String getScore() {
        return score;
    }
}
