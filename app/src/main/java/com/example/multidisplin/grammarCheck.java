package com.example.multidisplin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.vishnusivadas.advanced_httpurlconnection.FetchData;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class grammarCheck extends AppCompatActivity {

    EditText nim, name, title, answer;
    Button changeInput;
    CircularProgressButton submit;
    TextView error, header;

    Bundle bundle;

    DatabaseHelper databaseHelper;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    ModelAnswer modelAnswer;
    ModelGrade modelGrade;

    int accountId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grammar_check);

        Toolbar mtoolbar = findViewById(R.id.tollbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("Essai Input");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mtoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(grammarCheck.this, menu.class);
                intent.putExtra("id", accountId);
                startActivity(intent);
            }
        });


        bundle = getIntent().getExtras();
        accountId = bundle.getInt("id");


        nim = findViewById(R.id.GC_NIM);
        name = findViewById(R.id.GC_Name);
        title = findViewById(R.id.GC_Title);
        answer = findViewById(R.id.GC_Answer);
        error = findViewById(R.id.GC_error);
        submit = findViewById(R.id.GC_Submit);
        header = findViewById(R.id.GC_header);
        changeInput = findViewById(R.id.GC_back);


        submit.setEnabled(true);
        error.setVisibility(View.INVISIBLE);
//        error.setText(String.valueOf(accountId));

        nim.setText("");
        name.setText("");
        title.setText("");
        answer.setText("");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://essaygrading.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        databaseHelper = new DatabaseHelper(getApplicationContext());
    }

    public void navigation(View view) {
        if (view.getId() == R.id.GC_back){
            if (changeInput.getText().equals("Input Answer")){
                nim.setVisibility(View.INVISIBLE);
                name.setVisibility(View.INVISIBLE);
                title.setVisibility(View.INVISIBLE);

                answer.setVisibility(View.VISIBLE);
                header.setText("Essay Answer");
                changeInput.setText("Student Profil");
            } else {
                nim.setVisibility(View.VISIBLE);
                name.setVisibility(View.VISIBLE);
                title.setVisibility(View.VISIBLE);

                answer.setVisibility(View.INVISIBLE);
                header.setText("Student Profil");
                changeInput.setText("Input Answer");
//                submit.doneLoadingAnimation(Color.parseColor("#D2CB7C"), BitmapFactory.decodeResource(getResources(), R.drawable.profile_pic));
            }

        }
        else if(view.getId() == R.id.GC_Submit){
            String thisNim = "";

            try {
                thisNim = nim.getText().toString();
            }catch (Exception e){
                error.setText(e.getMessage());
                error.setVisibility(View.VISIBLE);
            }

            String thisName = name.getText().toString();
            String thisTitle = title.getText().toString();
            String thisAnswer = answer.getText().toString();

            insertIntoDB(thisNim,thisName,thisTitle,thisAnswer, accountId);
            submit.setEnabled(false);


//            if(databaseHelper.addAnswer(thisRealNim, thisName, thisTitle, thisAnswer)){
//                nim.setText("");
//                name.setText("");
//                title.setText("");
//                answer.setText("");
//            }

        }
    }

    private void insertIntoDB(final String thisNim, final String thisName, final String thisTitle, final String thisAnswer, int accountId_t) {
//        if (validationCheck(thisNim,thisName,thisTitle,thisAnswer) == true){
//            disableEditText();
//            submit.startAnimation();
//
//        try {
//                modelAnswer = new ModelAnswer(thisNim,thisName,thisTitle,thisAnswer);
//                Toast.makeText(grammarCheck.this, modelAnswer.toString(), Toast.LENGTH_SHORT).show();
//
//            }catch (Exception e){
//                Toast.makeText(grammarCheck.this, "cannot add data", Toast.LENGTH_SHORT).show();
//            }
//
//            if (databaseHelper.addAnswer(modelAnswer)){
//
//                essaiGrade(thisAnswer);
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            Intent intent = new Intent(grammarCheck.this, GrammarResult.class);
//                            intent.putExtra("id", accountId);
//                            startActivity(intent);
//                        }
//                    },20000);
//            }

            //mysql
            Handler handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    //Starting Write and Read data with URL
                    //Creating array for parameters
                    String[] field = new String[4];
                    field[0] = "nim";
                    field[1] = "name";
                    field[2] = "title";
                    field[3] = "answer";
                    //Creating array for data
                    String[] data = new String[4];
                    data[0] = thisNim;
                    data[1] = thisName;
                    data[2] = thisTitle;
                    data[3] = thisAnswer;
                    PutData putData = new PutData("http://192.168.0.125/Multidisplin/insertAnswer.php", "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            String result = putData.getResult();
                            if (result.equals("success")){

                                //opsi heroku app mati
                                Intent intent = new Intent(grammarCheck.this, GrammarResult.class);
                                intent.putExtra("id", accountId);
                                startActivity(intent);
//                                essaiGrade(thisAnswer, accountId);

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


    private void disableEditText() {
        nim.setFocusable(false);
        name.setFocusable(false);
        title.setFocusable(false);
        answer.setFocusable(false);
    }

    private void essaiGrade(final String answer, final int accountId) {

        Call<ApiGrading> call = jsonPlaceHolderApi.getGrade(answer);

////        asyntask
        call.enqueue(new Callback<ApiGrading>() {
            @Override
            public void onResponse(Call<ApiGrading> call, Response<ApiGrading> response) {
                if (!response.isSuccessful()){
                    error.setText("code: " + response.code());
                    error.setVisibility(View.VISIBLE);
                    return;
                }

                ApiGrading model = response.body();
                String content = model.getGrade();
                content = content.replace( " ", "");
                content = content.replace("result1", "");
                content = content.replace("result2", "");
                content = content.replace(",", " ");
                content = content.replace(":", "");
                content = content.replace("\"","");
                content = content.replace("[","");
                content = content.replace("]","");
                content = content.replace("{","");
                content = content.replace("}","");

                final String finalContent = content;

                //getAnswerId
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        FetchData fetchData = new FetchData("http://192.168.0.125/Multidisplin/getleatetestAnswerSubmited.php");
                        if (fetchData.startFetch()) {
                            if (fetchData.onComplete()) {
//                                String result = fetchData.getResult();
                                int answerId;
                                try {
                                    JSONArray obj = new JSONArray(fetchData.getResult());
                                    int n = obj.length();
                                    for (int i = 0 ; i < n ; i++){
                                        JSONObject data = obj.getJSONObject(i);
                                        answerId = data.getInt("id");

                                        //sampai disini insertgredrV
                                        insertGrade(answerId, finalContent, answer, accountId);

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    }
                });

            }

            @Override
            public void onFailure(Call<ApiGrading> call, Throwable t) {
                error.setText("Problem when call API grading");
                error.setVisibility(View.VISIBLE);
            }
        });
    }

    private void insertGrade(final int answerId, final String finalContent, final String answer, final int accountId) {
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                //Starting Write and Read data with URL
                //Creating array for parameters
                String[] field = new String[2];
                field[0] = "answerId";
                field[1] = "grade";

                //Creating array for data
                String[] data = new String[2];
                data[0] = String.valueOf(answerId);
                data[1] = finalContent;

                PutData putData = new PutData("http://192.168.0.125/Multidisplin/insertGradeV1(grade).php", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();
                        if (result.equals("insert grade success")){
//                            error.setText(result);
//                            error.setVisibility(View.VISIBLE);
                            essaiCorrection(answer, answerId, accountId);
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


    private void essaiCorrection(final String answer, final int answerId, final int accountId) {
        Call<ApiCorrectionModel> call = jsonPlaceHolderApi.getCorrection(answer);

        //asyntask
        call.enqueue(new Callback<ApiCorrectionModel>() {
            @Override
            public void onResponse(Call<ApiCorrectionModel> call, Response<ApiCorrectionModel> response) {
                if (!response.isSuccessful()) {
                    error.setText("grammar chek code: " + response.code());
                    error.setVisibility(View.VISIBLE);
                    return;
                }

                final ApiCorrectionModel model = response.body();

                if (model.getData().getError() == 0) {

//                    //>insert to my sql
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[5];
                            field[0] = "answerId";
                            field[1] = "mistake";
                            field[2] = "correction";
                            field[3] = "start_position";
                            field[4] = "end_position";

                            //Creating array for data
                            String[] data = new String[5];
                            data[0] = String.valueOf(answerId);
                            data[1] = "null";
                            data[2] = "null";
                            data[3] = String.valueOf(0);
                            data[4] = String.valueOf(0);

                            PutData putData = new PutData("http://192.168.0.125/Multidisplin/insertGradeV2(correction).php", "POST", field, data);

                            if (putData.startPut()) {

                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if (result.equals("insert correction success")) {
                                        Intent intent = new Intent(grammarCheck.this, GrammarResult.class);
                                        intent.putExtra("id", accountId);
                                        startActivity(intent);
                                    } else {
                                        error.setText(result);
                                        error.setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                } else {
                    String mistake, correction;
                    int start_p, end_p;
                    for (int j = 0; j < model.getData().getError(); j++) {
                        mistake = model.getData().getDetail().getMistake().get(j);
                        correction = model.getData().getDetail().getCorrection().get(j);
                        start_p = model.getData().getDetail().getStart_posititons().get(j);
                        end_p = model.getData().getDetail().getEnd_position().get(j);


                        //insert to db
                        final String finalMistake = mistake;
                        final String finalCorrection = correction;
                        final int finalStart_p = start_p;
                        final int finalEnd_p = end_p;
                        Handler handler = new Handler();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                //Starting Write and Read data with URL
                                //Creating array for parameters
                                String[] field = new String[5];
                                field[0] = "answerId";
                                field[1] = "mistake";
                                field[2] = "correction";
                                field[3] = "start_position";
                                field[4] = "end_position";

                                //Creating array for data
                                String[] data = new String[5];
                                data[0] = String.valueOf(answerId);
                                data[1] = finalMistake;
                                data[2] = finalCorrection;
                                data[3] = String.valueOf(finalStart_p);
                                data[4] = String.valueOf(finalEnd_p);

                                PutData putData = new PutData("http://192.168.0.125/Multidisplin/insertGradeV2(correction).php", "POST", field, data);

                                if (putData.startPut()) {

                                    if (putData.onComplete()) {
                                        String result = putData.getResult();
                                        if (result.equals("insert correction success")) {
                                            Intent intent = new Intent(grammarCheck.this, GrammarResult.class);
                                            intent.putExtra("id", accountId);
                                            startActivity(intent);
                                            finish();
                                        } else {
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

            @Override
            public void onFailure(Call<ApiCorrectionModel> call, Throwable t) {
                error.setText("Problem when call API correction :" + t);
                error.setVisibility(View.VISIBLE);
            }
        });
    }



    public boolean validationCheck(String txt1, String txt2, String txt3, String txt4){
        if (txt1.isEmpty()){
            error.setText("insert nim");
            error.setVisibility(View.VISIBLE);
            return false;
        }else if (txt2.isEmpty()){
            error.setText("insert name");
            error.setVisibility(View.VISIBLE);
            return false;
        }else if (txt3.isEmpty()){
            error.setText("insert title");
            error.setVisibility(View.VISIBLE);
            return false;
        }else if (txt4.isEmpty()){
            error.setText("insert answer");
            error.setVisibility(View.VISIBLE);
            return false;
        }else {
            return true;
        }
    }
}


