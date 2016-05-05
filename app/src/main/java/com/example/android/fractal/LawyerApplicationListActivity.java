package com.example.android.fractal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LawyerApplicationListActivity extends AppCompatActivity {
    private List<LawyerApplicationData> listSuperHeroes;
    //Creating Views
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_application_list);
        recyclerView = (RecyclerView) findViewById(R.id.notification_recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        listSuperHeroes = new ArrayList<>();
        getData();
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
                Intent in = new Intent(LawyerApplicationListActivity.this, LoginActivity.class);
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


    //This method will get data from the web api
    private void getData() {
        //Showing a progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Loading Data", "Please wait...", false, false);
        //Creating a json array request
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(LawyerResults.DATA_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Dismissing progress dialog
                        loading.dismiss();
                        //calling method to parse json array
                        parseData(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        //Getting editor
                        SharedPreferences.Editor editor = preferences.edit();
                        //Puting the value false for loggedin
                        editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, false);
                        //Putting blank value to email
                        editor.putString(Config.EMAIL_SHARED_PREF, "");
                        //Saving the sharedpreferences
                        editor.commit();
                        Intent in = new Intent(LawyerApplicationListActivity.this, LoginActivity.class);
                        startActivity(in);
                    }
                });
        //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //Adding request to the queue
        requestQueue.add(jsonArrayRequest);
    }

    //This method will parse json data
    private void parseData(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            LawyerApplicationData superHero = new LawyerApplicationData();
            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
                superHero.setVisaid(json.getString(LawyerResults.TAG_VISA_ID));
                superHero.setName(json.getString(LawyerResults.TAG_NAME));
                superHero.setEmployeeid(json.getString(LawyerResults.TAG_EMPID));
                superHero.setCountry(json.getString(LawyerResults.TAG_COUNTRY));
                superHero.setVisatype(json.getString(LawyerResults.TAG_VISA_TYPE));
                superHero.setStatus(json.getString(LawyerResults.TAG_STATUS));
                superHero.setPassportNumber(json.getString(LawyerResults.TAG_PASSPORT_NUMBER));
                superHero.setPlaceIssue(json.getString(LawyerResults.TAG_PLACE_ISSUE));
                superHero.setPassportExpieryDate(json.getString(LawyerResults.TAG_PASSPORT_EXPIERY));
                superHero.setVisaStartDate(json.getString(LawyerResults.TAG_START_DATE));
                superHero.setVisaEndDate(json.getString(LawyerResults.TAG_END_DATE));
                superHero.setProjectnumber(json.getString(LawyerResults.TAG_PROJECT_NUMBER));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            listSuperHeroes.add(superHero);
        }
        //Finally initializing our adapter
        adapter = new LawyerCardAdapter(listSuperHeroes, this);
        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);

    }
}
