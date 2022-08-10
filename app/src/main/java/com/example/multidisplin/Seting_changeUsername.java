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

public class Seting_changeUsername extends AppCompatActivity {

    TextView currentUsername, textView;
    EditText newUsernamel;
    Bundle bundle;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seting_change_username);

        bundle = getIntent().getExtras();
        final int acountId = bundle.getInt("id");

        Toolbar mtoolbar = findViewById(R.id.toolbarU);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        mtoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Seting_changeUsername.this, Setting.class);
                intent.putExtra("id", bundle.getInt("id"));
                startActivity(intent);
            }
        });

        textView = findViewById(R.id.textView4);
        textView.setText(String.valueOf(bundle.getInt("id")));
        textView.setVisibility(View.VISIBLE);

        currentUsername = findViewById(R.id.currentUsername);
        newUsernamel = findViewById(R.id.new_Username);

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
        if (view.getId() == R.id.setting_usernameSave){
//            if (databaseHelper.Change_userName(bundle.getInt("id"), newUsernamel.getText().toString())){
//                Intent intent = new Intent(this, menu.class);
//                intent.putExtra("id", bundle.getInt("id"));
//                startActivity(intent);
//            }
            bundle = getIntent().getExtras();
            final int acountId = bundle.getInt("id");

            Handler handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    //Starting Write and Read data with URL
                    //Creating array for parameters
                    String[] field = new String[2];
                    field[0] = "username";
                    field[1] = "id";

                    //Creating array for data
                    String[] data = new String[2];
                    data[0] = newUsernamel.getText().toString();
                    data[1] = String.valueOf(acountId);

                    PutData putData = new PutData("http://192.168.0.125/Multidisplin/updateUsername.php", "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            String result = putData.getResult();
                            if (result.equals("success")){
                                Intent intent = new Intent(getApplicationContext(), menu.class);
                                intent.putExtra("id", bundle.getInt("id"));
                                startActivity(intent);
                                finish();
                            }else {
                                textView.setText(result);
                                textView.setVisibility(View.VISIBLE);
                            }

                        }
                    }
                    //End Write and Read data with URL
                }
            });

        }
    }
}