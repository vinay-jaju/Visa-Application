package com.example.android.fractal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.events.DecoEvent;

public class MainActivity extends AppCompatActivity {
    private TextView mDisplay,textApply, textApproved, textAdminApproved, textDocumentsUploaded, textLawyerApproved,percentStatus;
    private Button applyButton, notifyButton;
    private String name;
    private CardView cd,cd2,cd3,cd4;
     int y,x;
    String status, employee_name, employee_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textApply=(TextView)findViewById(R.id.appliedForVisa);
        textApproved=(TextView)findViewById(R.id.managerApproved);
        textAdminApproved=(TextView)findViewById(R.id.AdministratorApproved);
        textDocumentsUploaded=(TextView)findViewById(R.id.DocumentsUploaded);
        textLawyerApproved=(TextView)findViewById(R.id.LawyerApproved);
        percentStatus= (TextView) findViewById(R.id.percentStatus);
        Intent i = getIntent();
        employee_name = i.getStringExtra("employee_name");
        status = i.getStringExtra("status");
        employee_code = i.getStringExtra("employee_code");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_notifications);
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String email = sharedPreferences.getString(Config.EMAIL_SHARED_PREF, "Not Available");
        //employee_code = email;
        ab.setDisplayHomeAsUpEnabled(true);
        cd = (CardView) findViewById(R.id.ImageUploadCardView);
        cd2 = (CardView) findViewById(R.id.ManagerCardView);
        cd3 = (CardView) findViewById(R.id.AdminApprovedCardView);
        cd4 = (CardView) findViewById(R.id.LawyerApprovedCardView);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(employee_name);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, EmployeeVisaApplicationActivity.class));
            }
        });
        //Toast.makeText(this,status + y + "\t",Toast.LENGTH_SHORT).show();
         y = Integer.parseInt(status);
        x = y;
        if(y > 10){
            x = 0;
        }
        loadBackdrop(x);
        switch(y){
            case 0:
                break;
            case 1:
              //  status_value = "Application Submitted";
                textApply.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_24dp,0,0,0);
                break;
            case 2:
               // status_value = "Manager Approved";
                textApply.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_24dp,0,0,0);
                textApproved.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_24dp,0,0,0);
                break;
            case 3:
                textApply.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_24dp,0,0,0);
                textApproved.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_24dp,0,0,0);
                textAdminApproved.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_24dp,0,0,0);
              //  status_value = "Adminstrator Approved";
                break;
            case 4:
                textApply.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_24dp,0,0,0);
                textApproved.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_24dp,0,0,0);
                textAdminApproved.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_24dp,0,0,0);
                textDocumentsUploaded.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_24dp,0,0,0);
               // status_value = "Images Uploaded";
                break;
            case 5:
                textApply.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_24dp,0,0,0);
                textApproved.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_24dp,0,0,0);
                textAdminApproved.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_24dp,0,0,0);
                textDocumentsUploaded.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_24dp,0,0,0);
                textLawyerApproved.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_24dp,0,0,0);
              //  status_value = "Lawyer Approved";
                break;
            case 20:
                textApply.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_24dp,0,0,0);
                textApproved.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_clear_24dp, 0, 0, 0);
                textApproved.setText("Manager Approved\nClick Here to check comments.");
                cd2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent y =new Intent(MainActivity.this,ManagerCommentsActivity.class);
                        y.putExtra("employee_code",employee_code);
                        startActivity(y);
                    }
                });
                break;
            case 30:
                textApply.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_24dp,0,0,0);
                textApproved.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_24dp,0,0,0);
                textAdminApproved.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_clear_24dp,0,0,0);
                textAdminApproved.setText("Admin Approved\nClick Here to check comments.");
                cd3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent y =new Intent(MainActivity.this,AdminCommentsActivity.class);
                        y.putExtra("employee_code",employee_code);
                        startActivity(y);
                    }
                });
                break;
            case 50:
                textApply.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_24dp,0,0,0);
                textApproved.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_24dp,0,0,0);
                textAdminApproved.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_24dp,0,0,0);
                textDocumentsUploaded.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_24dp,0,0,0);
                textLawyerApproved.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_clear_24dp,0,0,0);
                textLawyerApproved.setText("Lawyer Approved\n" +
                        "Click Here to check comments.");
                cd4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent y =new Intent(MainActivity.this,LawyerCommentsActivity.class);
                        y.putExtra("employee_code",employee_code);
                        startActivity(y);
                    }
                });
                break;
        }
        if(y == 3)
        {
            cd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent x = new Intent(MainActivity.this,EmployeeImageUpload.class);
                    x.putExtra("employee_code", employee_code);
                    startActivity(x);
                    textDocumentsUploaded.setText("Upload Documents \nClick here to upload.");
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(MainActivity.this, ManagerAuthenticationActivity.class));
                break;
            case R.id.action_logout:
                SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                //Getting editor
                SharedPreferences.Editor editor = preferences.edit();
                //Puting the value false for loggedin
                editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, false);
                //Putting blank value to email
                editor.putString(Config.EMAIL_SHARED_PREF, "");
                //Saving the sharedpreferences
                editor.commit();
                Intent in = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(in);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    private void loadBackdrop(int y) {
        //final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        // Create background track
        DecoView arcView = (DecoView)findViewById(R.id.dynamicArcView);
        percentStatus= (TextView) findViewById(R.id.percentStatus);
        arcView.addSeries(new SeriesItem.Builder(Color.argb(255, 218, 218, 218))
                .setRange(0, 100, 100)
                .setInitialVisibility(true)
                .setLineWidth(32f)
                .build());

//Create data series track Dall de
        SeriesItem seriesItem1 = new SeriesItem.Builder(Color.argb(255, 0, 68, 76))
                .setRange(0, 100, 0)
                .setLineWidth(32f)
                .build();

        int series1Index = arcView.addSeries(seriesItem1);
        arcView.addEvent(new DecoEvent.Builder(DecoEvent.EventType.EVENT_SHOW, true)
                .setDelay(500)
                .setDuration(1000)
                .build());
        int percent =  y *20;
        percentStatus.setText(percent+"%");
        arcView.addEvent(new DecoEvent.Builder(percent).setIndex(series1Index).setDelay(1000).build());
    }
}
