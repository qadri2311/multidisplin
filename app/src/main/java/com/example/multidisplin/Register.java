package com.example.multidisplin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Register extends AppCompatActivity {

    TextView check;
    EditText username, email, pass;
    CheckBox student, teacher;
    boolean status;
    String tempStatus;
    String link = "9caeb884c993f8";

    ModelUser modelUser;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        check = findViewById(R.id.tv_register_check);
        username = findViewById(R.id.et_regUsername);
        email = findViewById(R.id.et_regEmail);
        pass = findViewById(R.id.et_regPass);
        student = findViewById(R.id.ckx_student);
        teacher = findViewById(R.id.ckx_teacher);

        check.setVisibility(View.INVISIBLE);

        username.setText("");
        email.setText("");
        pass.setText("");

        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (student.isChecked()){
                    teacher.setChecked(false);
                    status = false;
                    tempStatus = "FALSE";
//                    check.setText(tempStatus);
//                    check.setVisibility(View.VISIBLE);
                }
            }
        });

        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (teacher.isChecked()){
                    student.setChecked(false);
                    status = true;
                    tempStatus = "TRUE";
//                    check.setText(tempStatus);
//                    check.setVisibility(View.VISIBLE);
                }
            }
        });



        databaseHelper = new DatabaseHelper(getApplicationContext());
    }

    public void navigation(View view) {
        if (view.getId() == R.id.btn_regCencel){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.btn_regRegister){
            final String thisUsername = username.getText().toString();
            final String thisEmail = email.getText().toString();
            final String thisPass = pass.getText().toString();
            final String thisStatus = tempStatus;



            //sqlite
//            if (databaseHelper.checkUser(thisUsername) >= 1){
//                check.setText("username already teken");
//                check.setVisibility(View.VISIBLE);
//            }else {
//                try {
//                    modelUser = new ModelUser(thisUsername, thisEmail, thisPass, status);
//                    Toast.makeText(Register.this, modelUser.toString(), Toast.LENGTH_SHORT).show();
//                }catch (Exception e){
//                    Toast.makeText(Register.this, "cannot add data", Toast.LENGTH_SHORT).show();
//                }
//
//                if (databaseHelper.addUser(modelUser)){
////                        Intent intent = new Intent(Register.this, MainActivity.class);
////                        startActivity(intent);
////                        finish();
//                }
//
////
//            }

            //mysql
            Handler handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    //Starting Write and Read data with URL
                    //Creating array for parameters
                    String[] field = new String[4];
                    field[0] = "username";
                    field[1] = "password";
                    field[2] = "email";
                    field[3] = "status";
                    //Creating array for data
                    String[] data = new String[4];
                    data[0] = thisUsername;
                    data[1] = thisPass;
                    data[2] = thisEmail;
                    data[3] = thisStatus;
                    PutData putData = new PutData("http://192.168.0.125/Multidisplin/signup.php", "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            String result = putData.getResult();
                            if (result.equals("Sign Up Success")){
                                Intent intent = new Intent(Register.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }else {
                                check.setText(result);
                                check.setVisibility(View.VISIBLE);
                            }

                        }
                    }
                    //End Write and Read data with URL
                }
            });
        }
    }

}