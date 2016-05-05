package com.example.android.fractal;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mEditTextName, mEditTextPassword;
    private Button mLoginButton;
    private TextView mRegisterHere;
    private boolean loggedIn = false;
    private RadioGroup mRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEditTextName = (EditText) findViewById(R.id.email);
        mEditTextPassword = (EditText) findViewById(R.id.password);
        mLoginButton = (Button) findViewById(R.id.loginButton);
        mLoginButton.setOnClickListener(this);
        mRegisterHere = (TextView) findViewById(R.id.registerHereText);
        mRegisterHere.setOnClickListener(this);
        mRadioGroup = (RadioGroup) findViewById(R.id.ManagerRadio);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //In onresume fetching value from sharedpreference
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        //Fetching the boolean value form sharedpreferences
        loggedIn = sharedPreferences.getBoolean(Config.LOGGEDIN_SHARED_PREF, false);
        String choice = sharedPreferences.getString(Config.RADIO_BUTTON_CHOICE,"Hello");

        //If we will get true
        if (loggedIn) {
            //We will start the Profile Activity
            if(choice.equals("Employee")) {
                Intent intent = new Intent(LoginActivity.this, FetchEmployeeStatusActivity.class);
                startActivity(intent);
            }
            else if(choice.equals("Administrator")){
                Intent intent = new Intent(LoginActivity.this, NextActivity.class);
                startActivity(intent);
            }
            else if(choice.equals("Lawyer")) {
                Intent intent = new Intent(LoginActivity.this, LawyerNextActivity.class);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registerHereText:
                Intent in = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(in);
                break;
            case R.id.loginButton:
                int selectedItem = mRadioGroup.getCheckedRadioButtonId();
                final ProgressDialog loading = ProgressDialog.show(this, "Logging In...", "Please wait...", false, false);
                final String username = mEditTextName.getText().toString();
                final String password = mEditTextPassword.getText().toString();
                switch (selectedItem) {
                    case R.id.radioButton:
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    boolean success = jsonObject.getBoolean("success");
                                    loading.dismiss();
                                    if (success) {
                                        String name = jsonObject.getString("employee_name");
                                        String username1 = jsonObject.getString("employee_code");
                                        SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                                        //Creating editor to store values to shared preferences
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        //Adding values to editor
                                        editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                                        editor.putString(Config.EMAIL_SHARED_PREF, username1);
                                        editor.putString(Config.KEY_EMAIL, name);
                                        editor.putString(Config.RADIO_BUTTON_CHOICE,"Employee");
                                        //Saving values to editor
                                        editor.commit();
                                        Intent intent = new Intent(LoginActivity.this, FetchEmployeeStatusActivity.class);
                                        intent.putExtra("username",username);
                                        //intent.putExtra("status",status);
                                        startActivity(intent);
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                        builder.setMessage("LOGIN FAILED")
                                                .setNegativeButton("Retry", null)
                                                .create()
                                                .show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        };
                        LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                        queue.add(loginRequest);
                        break;
                    case R.id.radioButton3:
                        try {

                        }catch (Exception e){
                        }
                        if(username.equals("admin") && password.equals("admin")) {
                            SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                            //Creating editor to store values to shared preferences
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            //Adding values to editor
                            String admin = "administrator";
                            editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                            editor.putString(Config.KEY_EMAIL, admin);
                            editor.putString(Config.EMAIL_SHARED_PREF, admin);
                            editor.putString(Config.RADIO_BUTTON_CHOICE, "Administrator");
                            Intent intent = new Intent(LoginActivity.this, NextActivity.class);
                            startActivity(intent);
                        }
                        else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setMessage("LOGIN FAILED")
                                    .setNegativeButton("Retry", null)
                                    .create()
                                    .show();
                        }
                        break;

                    case R.id.radioButton4:
                        if(username.equals("lawyer") && password.equals("lawyer")) {
                            SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            String lawyer = "Lawyer";
                            editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                            editor.putString(Config.EMAIL_SHARED_PREF, lawyer);
                            editor.putString(Config.KEY_EMAIL, lawyer);
                            editor.putString(Config.RADIO_BUTTON_CHOICE,"Lawyer");
                            Intent intent = new Intent(LoginActivity.this, LawyerNextActivity.class);
                            startActivity(intent);
                        }
                        else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setMessage("LOGIN FAILED")
                                    .setNegativeButton("Retry", null)
                                    .create()
                                    .show();
                        }
                        break;
                }
                break;
        }
    }
}
