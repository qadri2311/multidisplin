package com.example.multidisplin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.vishnusivadas.advanced_httpurlconnection.FetchData;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    EditText username;
    EditText pass;
    TextView check;
    Button login;
   

    JsonPlaceHolderApi jsonPlaceHolderApi;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.et_username);
        pass = findViewById(R.id.et_password);
        check = findViewById(R.id.tv_main_check);
        login = findViewById(R.id.btn_Login);

        check.setText("waiting api callback");
        check.setVisibility(View.VISIBLE);
        username.setFocusable(false);
        pass.setFocusable(false);
        login.setEnabled(false);

        // mancing post retrofit pertama
        //api_callbait();
        check.setVisibility(View.INVISIBLE);
        username.setFocusableInTouchMode(true);
        pass.setFocusableInTouchMode(true);
        login.setEnabled(true);
    }

    private void api_callbait() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://essaygrading.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<ApiGrading> call = jsonPlaceHolderApi.getGrade("answer");
        call.enqueue(new Callback<ApiGrading>() {
            @Override
            public void onResponse(Call<ApiGrading> call, Response<ApiGrading> response) {
                check.setText("callback success");

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after 5s = 5000ms
                        check.setVisibility(View.INVISIBLE);
                        username.setFocusableInTouchMode(true);
                        pass.setFocusableInTouchMode(true);
                        login.setEnabled(true);
                    }
                }, 1000);

            }

            @Override
            public void onFailure(Call<ApiGrading> call, Throwable t) {
                api_callbait();
            }
        });
    }

    public void navigation(View view) {
        if (view.getId() == R.id.btn_register){
            Intent intent = new Intent(MainActivity.this, Register.class);
            startActivity(intent);
        } else if (view.getId() == R.id.btn_Login){
            final String thisUsername = username.getText().toString();
            final String thisPass = pass.getText().toString();

            //sqlite
//            if (databaseHelper.checkUser(thisUsername, thisPass) >= 1){
////                Intent intent = new Intent(MainActivity.this, menu.class);
////                intent.putExtra("id", databaseHelper.checkUser(thisUsername,thisPass));
////                startActivity(intent);
////                finish();
//            } else {
//                check.setText("no acount match!!!");
//                check.setVisibility(View.VISIBLE);
//            }

            //get user id
            Handler handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    //Starting Write and Read data with URL
                    //Creating array for parameters
                    String[] field = new String[2];
                    field[0] = "username";
                    field[1] = "password";

                    //Creating array for data
                    String[] data = new String[2];
                    data[0] = thisUsername;
                    data[1] = thisPass;

                    //http://192.168.1.3
                    PutData putData = new PutData("http://192.168.0.125/Multidisplin/getUserId.php", "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            String result = putData.getResult();
                            if (result.equals("username or pass not match")){
                                check.setText(result);
                                check.setVisibility(View.VISIBLE);
                            }
                            else if(result.equals("error db connection")){
                                check.setVisibility(View.VISIBLE);
                                check.setText(result);
                            }
                            else if (result.equals("all field required")){
                                check.setVisibility(View.VISIBLE);
                                check.setText(result);
                            }
                            else {
//                                check.setVisibility(View.VISIBLE);
//                                check.setText(result);
                                Intent intent = new Intent(getApplicationContext(), menu.class);
                                intent.putExtra("id", Integer.valueOf(result));
                                startActivity(intent);
                                finish();
                            }

                        }
                    }
                    //End Write and Read data with URL
                }
            });

        }

    }
}

//mysql
////            Handler handler = new Handler();
//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//                    //Starting Write and Read data with URL
//                    //Creating array for parameters
//                    String[] field = new String[2];
//                    field[0] = "username";
//                    field[1] = "password";
//
//                    //Creating array for data
//                    String[] data = new String[2];
//                    data[0] = thisUsername;
//                    data[1] = thisPass;
//
//                    PutData putData = new PutData("http://192.168.1.3/Multidisplin/login.php", "POST", field, data);
//                    if (putData.startPut()) {
//                        if (putData.onComplete()) {
//                            String result = putData.getResult();
//                            if (result.equals("Login Success")){
//                                Intent intent = new Intent(MainActivity.this, menu.class);
////                                intent.putExtra("id", databaseHelper.checkUser(thisUsername,thisPass));
//                                startActivity(intent);
//                                finish();
//                            }else {
//                                check.setText(result);
//                                check.setVisibility(View.VISIBLE);
//                            }
//
//                        }
//                    }
//                    //End Write and Read data with URL
//                }
//            });