package com.example.android.fractal;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistrationActivity extends AppCompatActivity {
    private EditText mName, mUserName, mEmail, mPhone, mPassword;
    private Button regitser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mName = (EditText) findViewById(R.id.EmployeeName);
        mUserName = (EditText) findViewById(R.id.EmployeeUserName);
        mEmail = (EditText) findViewById(R.id.EmployeeEmail);
        mPhone = (EditText) findViewById(R.id.EmployeePhone);
        mPassword = (EditText) findViewById(R.id.EmployeePassword);
        regitser = (Button) findViewById(R.id.regButton);
        regitser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = mName.getText().toString();
                final String username = mUserName.getText().toString();
                final String email = mEmail.getText().toString();
                final String phone = mPhone.getText().toString();
                final String password = mPassword.getText().toString();
                final ProgressDialog loading = ProgressDialog.show(RegistrationActivity.this, "Registering...", "Please wait...", false, false);

                final Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                                startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);
                                builder.setMessage("REGISTRATION FAILED")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                RegistrationRequest registerRequest = new RegistrationRequest(name, username, email, phone, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegistrationActivity.this);
                queue.add(registerRequest);
            }
        });
    }
}
