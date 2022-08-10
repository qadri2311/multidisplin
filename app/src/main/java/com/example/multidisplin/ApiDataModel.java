package com.example.multidisplin;

import com.google.gson.annotations.SerializedName;

public class ApiDataModel {
    @SerializedName("num_err")
    private int error;

    @SerializedName("data")
    private ApiDataDetail detail;

    public int getError() {
        return error;
    }

    public ApiDataDetail getDetail() {
        return detail;
    }
}
