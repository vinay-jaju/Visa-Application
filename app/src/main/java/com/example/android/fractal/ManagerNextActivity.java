package com.example.android.fractal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ManagerNextActivity extends AppCompatActivity {
    String project_code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_next);
        Intent y = getIntent();
        project_code = y.getStringExtra("projectcode");
        Intent x= new Intent(ManagerNextActivity.this,ManagerApplicationListActivity.class);
        x.putExtra("projectcode",project_code);
        startActivity(x);
    }
}
