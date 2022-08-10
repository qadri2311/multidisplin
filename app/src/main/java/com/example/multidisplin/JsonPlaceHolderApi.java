package com.example.multidisplin;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface JsonPlaceHolderApi {

    @FormUrlEncoded
    @POST("Controller/")
    Call<ApiCorrectionModel> getCorrection(
            @Field("text") String txt
    );

    @FormUrlEncoded
    @POST("controllerScore/")
    Call<ApiGrading> getGrade(
            @Field("text") String txt
    );
}
