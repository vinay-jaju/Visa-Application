package com.example.android.fractal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LawyerNextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_next);
        Intent x= new Intent(LawyerNextActivity.this,LawyerApplicationListActivity.class);
        startActivity(x);
    }
}
