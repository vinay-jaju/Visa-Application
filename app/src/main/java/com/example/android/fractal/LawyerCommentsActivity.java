package com.example.android.fractal;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LawyerCommentsActivity extends AppCompatActivity {
    String employee_code,comment;
    TextView comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_comments);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent i = getIntent();
        employee_code = i.getStringExtra("employee_code");
        comments = (TextView) findViewById(R.id.ViewComment);
        final ProgressDialog loading = ProgressDialog.show(this, "Fetching Status...", "Please wait...", false, false);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    loading.dismiss();
                    if(success){
                        comment = jsonObject.getString("comment");
                        comments.setText(comment);
                    }
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(LawyerCommentsActivity.this);
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
        LawyerCommentRequest employeeStatusRequest = new LawyerCommentRequest(employee_code,responseListener);
        RequestQueue queue = Volley.newRequestQueue(LawyerCommentsActivity.this);
        queue.add(employeeStatusRequest);
    }

    @Override
    public void onBackPressed() {
    }
}
