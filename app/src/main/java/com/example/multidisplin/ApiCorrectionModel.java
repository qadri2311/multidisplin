package com.example.multidisplin;

import com.google.gson.annotations.SerializedName;

public class ApiCorrectionModel {

    @SerializedName("data")
    private ApiDataModel data;

    public ApiDataModel getData() {
        return data;
    }
}
