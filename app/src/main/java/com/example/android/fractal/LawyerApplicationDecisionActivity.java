package com.example.android.fractal;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LawyerApplicationDecisionActivity extends AppCompatActivity implements View.OnClickListener {
    TextView applicationdetails;
    private EditText comments;
    private String status1 = "5", status2 = "50";
    private Button approved, rejected, viewImages;
    private String name, visaid, employee_code, country, visatype, status, passport_number, passportissue, passportexpiery, visastart, visaend, project_number;
    private String comment_text = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_application_decision);
        applicationdetails = (TextView) findViewById(R.id.application_details_text_view);
        approved = (Button) findViewById(R.id.application_approved);
        rejected = (Button) findViewById(R.id.application_disapproved);
        comments = (EditText) findViewById(R.id.comment);
        viewImages = (Button) findViewById(R.id.ShowImages);
        Intent x = getIntent();
        visaid = x.getStringExtra("visa_id");
        name = x.getStringExtra("employee_name");
        employee_code = x.getStringExtra("employee_code");
        country = x.getStringExtra("country");
        visatype = x.getStringExtra("visatype");
        status = x.getStringExtra("status");
        passport_number = x.getStringExtra("passport_number");
        passportissue = x.getStringExtra("place_issue");
        passportexpiery = x.getStringExtra("passport_expiry_date");
        visastart = x.getStringExtra("date_required_from");
        visaend = x.getStringExtra("date_required_to");
        project_number = x.getStringExtra("projectcode");
        applicationdetails.setText(
                "Visa ID: " + visaid + "\n" +
                        "Employee Name: " + name + "\n" +
                        "Employee Code: " + employee_code + "\n" +
                        "Country: " + country + "\n" +
                        "Visa Type: " + visatype + "\n" +
                        "Passport Number: " + passport_number + "\n" +
                        "Passport Issue Place: " + passportissue + "\n" +
                        "Passport Expiery Date: " + passportexpiery + "\n" +
                        "Visa Start Date: " + visastart + "\n" +
                        "Visa End Date: " + visaend + "\n" +
                        "Project Code: " + project_number + "\n" + "\n");
        approved.setOnClickListener(this);
        rejected.setOnClickListener(this);
        viewImages.setOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View v) {
        final ProgressDialog loading = ProgressDialog.show(LawyerApplicationDecisionActivity.this, "Updatig Application Status...", "Please wait...", false, false);
        switch (v.getId()) {
            case R.id.ShowImages:
                Intent i = new Intent(LawyerApplicationDecisionActivity.this,LawyerImageDeatils.class);
                i.putExtra("employee_code",employee_code);
                startActivity(i);
                break;
            case R.id.application_approved:
                comment_text = comments.getText().toString();
                final Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                Intent intent = new Intent(LawyerApplicationDecisionActivity.this, NextActivity.class);
                                startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LawyerApplicationDecisionActivity.this);
                                builder.setMessage("Update Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                LawyerApprovalRequest approvalReuqest = new LawyerApprovalRequest(employee_code, status1,comment_text, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LawyerApplicationDecisionActivity.this);
                queue.add(approvalReuqest);
                break;
            case R.id.application_disapproved:
                comment_text = comments.getText().toString();
                final Response.Listener<String> responseListener1 = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                Intent intent = new Intent(LawyerApplicationDecisionActivity.this, LawyerNextActivity.class);
                                startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LawyerApplicationDecisionActivity.this);
                                builder.setMessage("Update Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                LawyerApprovalRequest approvalReuqest1 = new LawyerApprovalRequest(employee_code, status2,comment_text, responseListener1);
                RequestQueue queue1 = Volley.newRequestQueue(LawyerApplicationDecisionActivity.this);
                queue1.add(approvalReuqest1);
                break;
        }

    }

}
