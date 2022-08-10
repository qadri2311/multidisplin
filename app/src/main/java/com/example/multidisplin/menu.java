package com.example.multidisplin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.vishnusivadas.advanced_httpurlconnection.FetchData;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class menu extends AppCompatActivity {

//    ListView listView;
    TextView username;

//    DatabaseHelper databaseHelper;
    List<ModelAnswer> modelList = new ArrayList<>();
//    ArrayList arrayList;
//    ArrayAdapter arrayAdapter;

    SQLiteDatabase mdata;
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutmanager;
    historyAdapter historyAdapter;

    SearchView searchView;


    int acountId;
    String status;

    Bundle bundle;

    Boolean frame = false;
    FloatingActionButton add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        username = findViewById(R.id.tv_menu_username);
        add = findViewById(R.id.menu_addText);
        recyclerView = findViewById(R.id.recyclerview);



        bundle = getIntent().getExtras();
        acountId = bundle.getInt("id");
        username.setText(String.valueOf(acountId));

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
                            username.setText("Hi " + obj.getString("username") + "!!!");
                            status = obj.getString("status");

                            if (status.equals("FALSE")){
                                add.setVisibility(View.INVISIBLE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                //End Write and Read data with URL
            }
        });


//        arrayList = databaseHelper.getName();
//        arrayAdapter = new ArrayAdapter(menu.this, android.R.layout.simple_list_item_1, arrayList);
//        listView.setAdapter(arrayAdapter);

//        answerList
        handler.post(new Runnable() {
            @Override
            public void run() {
                FetchData fetchData = new FetchData("http://192.168.0.125/Multidisplin/getAnswer.php");
                if (fetchData.startFetch()) {
                    if (fetchData.onComplete()) {
//                        String result = fetchData.getResult();
//                        tes.setText(result);
                        int thisid;
                        String thisnim, thisname, thistitle, thisanswer;
                        try {
                            JSONArray obj = new JSONArray(fetchData.getResult());
                            int n = obj.length();
                            for (int i = 0 ; i < n ; i++){
                                JSONObject data = obj.getJSONObject(i);
                                thisid = data.getInt("id");
                                thisnim = data.getString("nim");
                                thisname = data.getString("name");
                                thistitle = data.getString("title");
                                thisanswer = data.getString("answer");

                                ModelAnswer modelAnswer = new ModelAnswer(thisid, thisnim, thisname, thistitle,thisanswer);
                                modelList.add(modelAnswer);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        });

// currently use adapter
//        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(menu.this));
                historyAdapter = new historyAdapter(menu.this, modelList);
                recyclerView.setAdapter(historyAdapter);

                historyAdapter.setOnItemClickListener(new historyAdapter.onitemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Intent intent = new Intent(menu.this, Result.class);
                        intent.putExtra("answerId", position + 1);
                        intent.putExtra("id", acountId);
                        startActivity(intent);
//                username.setText(String.valueOf(position) + ", " + acountId);
                    }
                });
            }
        }, 1000);

//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(menu.this));
//        historyAdapter = new historyAdapter(menu.this, modelList);
//        recyclerView.setAdapter(historyAdapter);

//        historyAdapter.setOnItemClickListener(new historyAdapter.onitemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                Intent intent = new Intent(menu.this, Result.class);
//                intent.putExtra("answerId", position + 1);
//                intent.putExtra("id", acountId);
//                startActivity(intent);
////                username.setText(String.valueOf(position) + ", " + acountId);
//            }
//        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menu.this, grammarCheck.class);
                intent.putExtra("id", acountId);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_setting, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_settingV2){
            Intent intent = new Intent(this, Setting.class);
            intent.putExtra("id", acountId );
            startActivity(intent);
        }
        return true;
    }

}