package com.example.multidisplin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Setting extends AppCompatActivity {

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        bundle = getIntent().getExtras();

        Toolbar mtoolbar = findViewById(R.id.setting_toolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mtoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Setting.this, menu.class);
                intent.putExtra("id",bundle.getInt("id"));
                startActivity(intent);
                finish();
            }
        });

        TextView textView = findViewById(R.id.texttessss);
        textView.setText(String.valueOf(bundle.getInt("id")));
    }

    public void navigation(View view) {
        if (view.getId() == R.id.setting_usernameV2){
            Intent intent = new Intent(this, Seting_changeUsername.class);
            intent.putExtra("id", bundle.getInt("id"));
            startActivity(intent);
            finish();
        }
        else if (view.getId() == R.id.setting_passV2){
            Intent intent = new Intent(this, Seting_changePass.class);
            intent.putExtra("id", bundle.getInt("id"));
            startActivity(intent);
            finish();
        }
        else if (view.getId() == R.id.setting_faq){
            Intent intent = new Intent(this, FAQ.class);
            intent.putExtra("id", bundle.getInt("id"));
            startActivity(intent);
            finish();
        }
        else if (view.getId() == R.id.seeting_logOut){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

}