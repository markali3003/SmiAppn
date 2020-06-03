package com.example.smiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {
    Globalv globalv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        globalv = (Globalv) getApplicationContext();
    }

    public void S1(View view) {
        globalv.setSemestre("S1");
        startActivity(new Intent(HomeActivity.this, ModelActivity.class));

    }

    public void S2(View view) {
        globalv.setSemestre("S2");
        startActivity(new Intent(HomeActivity.this, ModelActivity.class));

    }

    public void S3(View view) {
        globalv.setSemestre("S3");
        startActivity(new Intent(HomeActivity.this, ModelActivity.class));
    }

    public void S4(View view) {
        globalv.setSemestre("S4");
        startActivity(new Intent(HomeActivity.this, ModelActivity.class));
    }

    public void S5(View view) {
        globalv.setSemestre("S5");
        startActivity(new Intent(HomeActivity.this, ModelActivity.class));
    }

    public void S6(View view) {
        globalv.setSemestre("Sesstre  6");
        startActivity(new Intent(HomeActivity.this, ModelActivity.class));
    }


}
