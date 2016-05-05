package com.example.android.fractal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.Hashtable;
import java.util.Map;

public class LawyerImageDeatils extends AppCompatActivity {
    private String QUERY_URL = "http://rubix.co.in/Fractal/FetchUserDataServer.php";
    String myJSON;
    private static final String TAG_RESULTS = "result";
    private static final String TAG_NUMBER = "number";
    private static final String TAG_IMG_1 = "img1";
    private static final String TAG_IMG_2 = "img2";
    private static final String TAG_IMG_3 = "img3";
    private static final String TAG_IMG_4 = "img4";
    JSONArray peoples = null;
    private String imagesJSON;
    private String number, username;
    private String photo1, photo2, photo3, photo4;
    private String st1, st2, st3, st4;
    URL u1,u2,u3,u4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_image_deatils);
        Intent x = getIntent();
        username = x.getStringExtra("employee_code");
        sendRequest();
    }

    @Override
    public void onBackPressed() {
    }

    private void sendRequest() {
        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, QUERY_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        //showList();
                        loading.dismiss();
                        imagesJSON = s;
                        try {
                            JSONObject jsonObject = new JSONObject(imagesJSON);
                            photo1 = jsonObject.getString("img1");
                            photo2 = jsonObject.getString("img2");
                            photo3 = jsonObject.getString("img3");
                            photo4 = jsonObject.getString("img4");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        st1 = "http://rubix.co.in/Fractal/" + photo1;
                        st2 = "http://rubix.co.in/Fractal/" + photo2;
                        st3 = "http://rubix.co.in/Fractal/" + photo3;
                        st4 = "http://rubix.co.in/Fractal/" + photo4;
                        Intent in = new Intent(LawyerImageDeatils.this,LawyerImagesDisplay.class);
                        in.putExtra("img1", st1);
                        in.putExtra("img2", st2);
                        in.putExtra("img3", st3);
                        in.putExtra("img4", st4);
                        startActivity(in);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(LawyerImageDeatils.this, "ERROR", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                // String image = getStringImage(bitmap);
                //Getting Image Name

                //Creating parameters
                Map<String, String> params = new Hashtable<String, String>();

                params.put(TAG_NUMBER, username);
                //returning parameters
                return params;
            }

        };
        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //Adding request to the queue
        requestQueue.add(stringRequest);

    }
}
