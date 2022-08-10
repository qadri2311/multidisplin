package com.example.multidisplin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class FAQ extends AppCompatActivity {

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_a_q);

        bundle = getIntent().getExtras();

        Toolbar mtolbar = findViewById(R.id.faq_ttollbar);
        setSupportActionBar(mtolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        mtolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FAQ.this, Setting.class);
                intent.putExtra("id", bundle.getInt("id"));
                startActivity(intent);
            }
        });


    }
}