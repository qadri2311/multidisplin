package com.example.multidisplin;

import java.util.ArrayList;

public class ModelGrade {
    int id;
    int answerId;
    String grade;
    String mistake;
    String correction;
    int start_posititons;
    int end_position;

    public ModelGrade(int answerId, String grade) {
        this.answerId = answerId;
        this.grade = grade;
    }


    public ModelGrade(int answerId, String mistake, String correction, int start_posititons, int end_position) {
        this.answerId = answerId;
        this.mistake = mistake;
        this.correction = correction;
        this.start_posititons = start_posititons;
        this.end_position = end_position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getMistake() {
        return mistake;
    }

    public void setMistake(String mistake) {
        this.mistake = mistake;
    }

    public String getCorrection() {
        return correction;
    }

    public void setCorrection(String correction) {
        this.correction = correction;
    }

    public int getStart_posititons() {
        return start_posititons;
    }

    public void setStart_posititons(int start_posititons) {
        this.start_posititons = start_posititons;
    }

    public int getEnd_position() {
        return end_position;
    }

    public void setEnd_position(int end_position) {
        this.end_position = end_position;
    }
}
