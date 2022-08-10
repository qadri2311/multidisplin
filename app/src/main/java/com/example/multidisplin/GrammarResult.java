package com.example.multidisplin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.vishnusivadas.advanced_httpurlconnection.FetchData;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GrammarResult extends AppCompatActivity {
//activity ini dubuat karena saat submit di grammar check error parse ke activity result
    TextView nim, name, answer, sugest, txt_grade;
    BarChart barChart;
    Bundle bundle;
    DatabaseHelper mDatabaseHelper;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grammar_result);

        Toolbar gcrToolbar = findViewById(R.id.gcr_toolbar);
        setSupportActionBar(gcrToolbar);
        getSupportActionBar().setTitle("Result");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        gcrToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GrammarResult.this, menu.class);
                intent.putExtra("id", userId);
                startActivity(intent);
            }
        });

        nim = findViewById(R.id.gcr_nim);
        name = findViewById(R.id.gcr_name);
        answer = findViewById(R.id.gcr_Answer);
        sugest = findViewById(R.id.gcr_sugestion);
        txt_grade = findViewById(R.id.gcr_grade);
        barChart = findViewById(R.id.gcr_barChart);

        bundle = getIntent().getExtras();
        userId = bundle.getInt("id");

        mDatabaseHelper = new DatabaseHelper(getApplicationContext());

//        ModelAnswer detail = mDatabaseHelper.gcr();

        //getAnswerId
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                FetchData fetchData = new FetchData("http://192.168.0.125/Multidisplin/getleatetestAnswerSubmited.php");
                if (fetchData.startFetch()) {
                    if (fetchData.onComplete()) {
                      String result = fetchData.getResult();
//                      nim.setText(result);
                        try {
                            JSONArray obj = new JSONArray(fetchData.getResult());
                            int n = obj.length();
                            for (int i = 0 ; i < n ; i++){
                                JSONObject data = obj.getJSONObject(i);

                                nim.setText("NIM : " + data.getInt("nim"));
                                name.setText("Name : " + data.getString("name"));
                                answer.setText(data.getString("answer"));

                                essaiGrade(data.getInt("id"));
                                essaiCorrection(data.getInt("id"));

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        });

    }

    private void essaiGrade(final int id) {
        String grade = mDatabaseHelper.getGrade(id);

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                //Starting Write and Read data with URL
                //Creating array for parameters
                String[] field = new String[1];
                field[0] = "answer_id";

                //Creating array for data
                String[] data = new String[1];
                data[0] = String.valueOf(id);

                PutData putData = new PutData("http://192.168.0.125/Multidisplin/getGradeV1(grade).php", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        try {
                            JSONArray obj = new JSONArray(putData.getResult());
                            int n = obj.length();
                            for (int i = 0 ; i < n ; i++){
                                JSONObject value = obj.getJSONObject(i);

                                char temp = value.getString("grade").charAt(0);
                                int focus_purpose = Character.getNumericValue(temp);
                                temp = value.getString("grade").charAt(2);
                                int idea_development = Character.getNumericValue(temp);

//                                txt_grade.setText("" + focus_purpose + " " + idea_development);

                                ArrayList<BarEntry> score = new ArrayList<>();
                                score.add(new BarEntry(1, focus_purpose));
                                score.add(new BarEntry(2, idea_development));

                                BarDataSet barDataSet = new BarDataSet(score, "focus and Purpose | Idea and Development");
                                barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                                barDataSet.setValueTextColor(Color.BLACK);
                                barDataSet.setValueTextSize(20f);


                                BarData barData = new BarData(barDataSet);

                                barChart.setFitBars(true);
                                barChart.setData(barData);
                                barChart.getDescription().setText("Score");
                                barChart.getDescription().setTextSize(16f );
                                barChart.animateY(2000);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                //End Write and Read data with URL
            }
        });

    }

    private void essaiCorrection(final int id) {
        final ArrayList<String> mistake = new ArrayList<>();
        final ArrayList<String> correction = new ArrayList<>();

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                //Starting Write and Read data with URL
                //Creating array for parameters
                String[] field = new String[1];
                field[0] = "answer_id";

                //Creating array for data
                String[] data = new String[1];
                data[0] = String.valueOf(id);

                PutData putData = new PutData("http://192.168.0.125/Multidisplin/getGradeV2(correction).php", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        try {
                            JSONArray obj = new JSONArray(putData.getResult());
                            int n = obj.length();
                            for (int i = 0 ; i < n ; i++){
                                JSONObject value = obj.getJSONObject(i);
                                mistake.add(value.getString("mistake"));
                                correction.add(value.getString("correction"));
                            }

                            if (mistake.get(0).equals("null")){
                                sugest.setText("No Error on Grammar");
                            }else {
                                String contet = "Error grammar: ";
                                for (int i = 0;i < mistake.size();i++){
                                    contet += mistake.get(i) + "(" + correction.get(i) + "), ";
                                }

                                sugest.append(contet);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                //End Write and Read data with URL
            }
        });
    }


}