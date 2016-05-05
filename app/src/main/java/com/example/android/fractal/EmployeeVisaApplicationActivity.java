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

public class EmployeeVisaApplicationActivity extends AppCompatActivity implements View.OnClickListener {
    EditText mName, mEmployeId, mProject, mVisatype, mCountry,mPassNo,mPlace,PassExpiry,VisaStart,VisaEnd;
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_visa_application);
        mName = (EditText) findViewById(R.id.employeeName);
        mEmployeId = (EditText) findViewById(R.id.employeeUserName);
        mProject = (EditText) findViewById(R.id.projectText);
        mVisatype = (EditText) findViewById(R.id.visaText);
        mCountry = (EditText) findViewById(R.id.countryText);
        mPassNo = (EditText) findViewById(R.id.passportNo);
        mPlace = (EditText) findViewById(R.id.placeIssue);
        PassExpiry = (EditText) findViewById(R.id.passExpiry);
        VisaStart = (EditText) findViewById(R.id.datestartVisa);
        VisaEnd = (EditText) findViewById(R.id.dateendVisa);
        submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submitButton:
                String EmpName = mName.getText().toString();
                String EmpId = mEmployeId.getText().toString();
                String ProjectName = mProject.getText().toString();
                String Empvisatype = mVisatype.getText().toString();
                String EmpCountry = mCountry.getText().toString();
                String PassportNo = mPassNo.getText().toString();
                String PlaceIssue = mPlace.getText().toString();
                String passExpiry = PassExpiry.getText().toString();
                String visaStart = VisaStart.getText().toString();
                String visaEnd = VisaEnd.getText().toString();
                final ProgressDialog loading = ProgressDialog.show(EmployeeVisaApplicationActivity.this, "Submitting Application...", "Please wait...", false, false);
                final Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                Intent intent = new Intent(EmployeeVisaApplicationActivity.this,FetchEmployeeStatusActivity.class);
                                startActivity(intent);
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(EmployeeVisaApplicationActivity.this);
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
                EmployeeVisaApplicationRequest visaApplicationRequest = new EmployeeVisaApplicationRequest(EmpName, EmpId,EmpCountry, Empvisatype,PassportNo,PlaceIssue,passExpiry,visaStart,visaEnd,ProjectName, responseListener);
                RequestQueue queue = Volley.newRequestQueue(EmployeeVisaApplicationActivity.this);
                queue.add(visaApplicationRequest);
                break;
        }
    }
}
