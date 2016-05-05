package com.example.android.fractal;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class FetchEmployeeStatusActivity extends AppCompatActivity {
    String employee_code, status, employee_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_employee_status);
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String email = sharedPreferences.getString(Config.EMAIL_SHARED_PREF, "Not Available");
        employee_code = email;
        final ProgressDialog loading = ProgressDialog.show(this, "Fetching Status...", "Please wait...", false, false);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    loading.dismiss();
                    if(success){
                        status = jsonObject.getString("status");
                        employee_name = jsonObject.getString("employee_name");
                        Intent intent = new Intent(FetchEmployeeStatusActivity.this, MainActivity.class);
                        intent.putExtra("status",status);
                        intent.putExtra("employee_name", employee_name);
                        intent.putExtra("employee_code", employee_code);
                        //intent.putExtra("status",status);
                        startActivity(intent);
                    }
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(FetchEmployeeStatusActivity.this);
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
        EmployeeStatusRequest employeeStatusRequest = new EmployeeStatusRequest(employee_code,responseListener);
        RequestQueue queue = Volley.newRequestQueue(FetchEmployeeStatusActivity.this);
        queue.add(employeeStatusRequest);


    }
}
