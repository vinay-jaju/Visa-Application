package com.example.android.fractal;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class EmployeeImageUpload extends AppCompatActivity implements View.OnClickListener {
    String employee_code;
    private Button buttonChoose;
    private Button buttonUpload;
    private ImageView imageView, image2, image3, image4;
    private Bitmap bitmap;
    private int PICK_IMAGE_REQUEST = 1;
    // public int x = 1;

    private String IMAGE1 = "img1";
    private String IMAGE2 = "img2";
    private String IMAGE3 = "img3";
    private String IMAGE4 = "img4";
    String imageEncoded;
    List<String> imagesEncodedList;
    public ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
    public Bitmap b1, b2, b3, b4;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_image_upload);
        Intent y = getIntent();
        employee_code = y.getStringExtra("employee_code");
        buttonChoose = (Button) findViewById(R.id.buttonChoose);
        buttonUpload = (Button) findViewById(R.id.buttonUpload);
        imageView = (ImageView) findViewById(R.id.imageView);
        image2 = (ImageView) findViewById(R.id.imageView2);
        image3 = (ImageView) findViewById(R.id.imageView3);
        image4 = (ImageView) findViewById(R.id.imageView4);
        buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        imagesEncodedList = new ArrayList<String>();
        //Uri filePath = data.getData();
        ClipData mClipData = data.getClipData();

        for (int i = 0; i < mClipData.getItemCount(); i++) {
            ClipData.Item item = mClipData.getItemAt(i);
            Uri uri = item.getUri();
            mArrayUri.add(uri);
            // Get the cursor
            Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
            // Move to first row
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            imageEncoded = cursor.getString(columnIndex);
            imagesEncodedList.add(imageEncoded);
            cursor.close();
        }

        try {
            //Getting the Bitmap from Gallery
            Uri ur1 = mArrayUri.get(0);
            b1 = MediaStore.Images.Media.getBitmap(getContentResolver(), ur1);
            Uri ur2 = mArrayUri.get(1);
            b2 = MediaStore.Images.Media.getBitmap(getContentResolver(), ur2);
            Uri ur3 = mArrayUri.get(2);
            b3 = MediaStore.Images.Media.getBitmap(getContentResolver(), ur3);
            Uri ur4 = mArrayUri.get(3);
            b4 = MediaStore.Images.Media.getBitmap(getContentResolver(), ur4);
            for (int i = 0; i < 4; i++) {
                Uri u1 = mArrayUri.get(i);
                //String str = u1.toString();
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), u1);
                //Bitmap b = Bitmap.createScaledBitmap(bitmap, 160, 160, false);
                switch (i) {
                    case 0:
                        imageView.setImageBitmap(bitmap);
                        //x = 2;
                        break;
                    case 1:
                        image2.setImageBitmap(bitmap);
                        // x = 3;
                        break;
                    case 2:
                        image3.setImageBitmap(bitmap);
                        // x = 4;
                        break;
                    case 3:
                        image4.setImageBitmap(bitmap);
                        break;
                }
            }
        } catch (Exception e) {
        }
    }

    private void uploadImage() {
        String img1 = getStringImage(b1);
        String img2 = getStringImage(b2);
        String img3 = getStringImage(b3);
        String img4 = getStringImage(b4);
        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);
        final Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                //Showing toast message of the response
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        Toast.makeText(EmployeeImageUpload.this,  "Uploaded SuccessFully ", Toast.LENGTH_LONG).show();
                        imageView.setVisibility(View.INVISIBLE);
                        image2.setVisibility(View.INVISIBLE);
                        image3.setVisibility(View.INVISIBLE);
                        image4.setVisibility(View.INVISIBLE);
                        loading.dismiss();
                        Intent i = new Intent(EmployeeImageUpload.this,FetchEmployeeStatusActivity.class);
                        startActivity(i);
                    } else {
                        loading.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(EmployeeImageUpload.this);
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
        EmployeeImageUploadRequest employeeImageUploadRequest = new EmployeeImageUploadRequest(img1,img2,img3,img4,employee_code,responseListener);
        RequestQueue queue = Volley.newRequestQueue(EmployeeImageUpload.this);
        queue.add(employeeImageUploadRequest);
    }

    @Override
    public void onClick(View v) {
        if (v == buttonChoose) {
            showFileChooser();
        }
        if (v == buttonUpload) {
            uploadImage();
        }
    }

}
