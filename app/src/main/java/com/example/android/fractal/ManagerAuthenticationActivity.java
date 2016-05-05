package com.example.android.fractal;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ManagerAuthenticationActivity extends AppCompatActivity implements View.OnClickListener {
    Button authenticationButton;
    TextView passwordView;
    String managerpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_authentication);
        passwordView = (TextView) findViewById(R.id.password);
        authenticationButton = (Button) findViewById(R.id.authenticate);
        authenticationButton.setOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View v) {
        String confirmpassword = "fractal";
        managerpassword = passwordView.getText().toString();
        if(managerpassword.equals(confirmpassword)){
            Intent i = new Intent(ManagerAuthenticationActivity.this,ManagerApplicationListActivity.class);
            startActivity(i);
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(ManagerAuthenticationActivity.this);
            builder.setMessage("LOGIN FAILED")
                    .setNegativeButton("Retry", null)
                    .create()
                    .show();
        }
    }
}
