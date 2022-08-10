package com.example.multidisplin;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ApiDataDetail {

    @SerializedName("mistakes")
    private ArrayList<String> mistake;

    @SerializedName("corrections")
    private ArrayList<String> correction;

    @SerializedName("start_posititons")
    private ArrayList<Integer> start_posititons;

    @SerializedName("end_position")
    private ArrayList<Integer> end_position;

    public ArrayList<String> getMistake() {
        return mistake;
    }

    public ArrayList<String> getCorrection() {
        return correction;
    }

    public ArrayList<Integer> getStart_posititons() {
        return start_posititons;
    }

    public ArrayList<Integer> getEnd_position() {
        return end_position;
    }
}
