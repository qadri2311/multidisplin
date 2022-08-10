package com.example.multidisplin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONException;
import org.json.JSONObject;

public class Seting_changePass extends AppCompatActivity {

    TextView currentUsername, error;
    EditText currentPass,newPass,comfPass;
    String passwordcheck;

    Bundle bundle;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seting_change_pass);

        bundle = getIntent().getExtras();
        final int acountId = bundle.getInt("id");

        Toolbar mtollbar = findViewById(R.id.toolbarp);
        setSupportActionBar(mtollbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        mtollbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Seting_changePass.this, Setting.class);
                intent.putExtra("id", bundle.getInt("id"));
                startActivity(intent);
            }
        });

        currentUsername = findViewById(R.id.currentUsernameP);
        error = findViewById(R.id.setting_error);
        currentPass = findViewById(R.id.currentPass);
        newPass = findViewById(R.id.new_pass);
        comfPass = findViewById(R.id.retype);

        //get user
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                //Starting Write and Read data with URL
                //Creating array for parameters
                String[] field = new String[1];
                field[0] = "id";

                //Creating array for data
                String[] data = new String[1];
                data[0] = String.valueOf(acountId);

                PutData putData = new PutData("http://192.168.0.125/Multidisplin/getUser.php", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        try {
                            JSONObject obj = new JSONObject(putData.getResult());
                            currentUsername.setText(obj.getString("username") );
                            passwordcheck = obj.getString("password");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                //End Write and Read data with URL
            }
        });

    }

    public void navigation(View view) {
        bundle = getIntent().getExtras();
        final int acountId = bundle.getInt("id");

        if (view.getId() == R.id.setting_passSave){
            if (newPass.getText().toString().matches( comfPass.getText().toString()) && !newPass.getText().toString().isEmpty()){
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //Starting Write and Read data with URL
                        //Creating array for parameters
                        String[] field = new String[3];
                        field[0] = "oldpassword";
                        field[1] = "newpassword";
                        field[2] = "id";

                        //Creating array for data
                        String[] data = new String[3];
                        data[0] = currentPass.getText().toString();
                        data[1] = comfPass.getText().toString();
                        data[2] = String.valueOf(acountId);

                        PutData putData = new PutData("http://192.168.0.125/Multidisplin/updatePassword.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();
                                if (result.equals("success")){
                                    Intent intent = new Intent(getApplicationContext(), menu.class);
                                    intent.putExtra("id", bundle.getInt("id"));
                                    startActivity(intent);
                                    finish();
                                }else {
                                    error.setText(result);
                                    error.setVisibility(View.VISIBLE);
                                }

                            }
                        }
                        //End Write and Read data with URL
                    }
                });
            }
        }



    }
}