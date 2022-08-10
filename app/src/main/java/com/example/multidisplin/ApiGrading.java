package com.example.multidisplin;

import com.google.gson.annotations.SerializedName;

public class ApiGrading {

    @SerializedName("data")
    private String grade;

    public String getGrade() {
        return grade;
    }
}
