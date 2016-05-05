package com.example.android.fractal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LawyerImagesDisplay extends AppCompatActivity implements View.OnClickListener {
    ImageView imageView1, imageView2, imageView3, imageView4;
    String str1, str2, str3, str4;
    URL url1, url2, url3, url4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_images_display);
        imageView1 = (ImageView) findViewById(R.id.first_Image);
        imageView2 = (ImageView) findViewById(R.id.second_Image);
        imageView3 = (ImageView) findViewById(R.id.thid_Image);
        imageView4 = (ImageView) findViewById(R.id.fourth_Image);
        Intent i = new Intent(getIntent());
        str1 = i.getStringExtra("img1");
        str2 = i.getStringExtra("img2");
        str3 = i.getStringExtra("img3");
        str4 = i.getStringExtra("img4");
        ImageLoadTask i1 = new ImageLoadTask(str1,imageView1);
        i1.execute();
        ImageLoadTask i2 = new ImageLoadTask(str2,imageView2);
        i2.execute();
        ImageLoadTask i3 = new ImageLoadTask(str3,imageView3);
        i3.execute();
        ImageLoadTask i4 = new ImageLoadTask(str4,imageView4);
        i4.execute();
        imageView1.setOnClickListener(this);
        imageView2.setOnClickListener(this);
        imageView3.setOnClickListener(this);
        imageView4.setOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onClick(View v) {
        if(v == imageView1){
            Intent x = new Intent(this,FourthActivity.class);
            x.putExtra("image",str1);
            startActivity(x);
        }
        if(v == imageView2){
            Intent x = new Intent(this,FourthActivity.class);
            x.putExtra("image",str2);
            startActivity(x);
        }
        if(v == imageView3){
            Intent x = new Intent(this,FourthActivity.class);
            x.putExtra("image",str3);
            startActivity(x);
        }
        if(v == imageView4){
            Intent x = new Intent(this,FourthActivity.class);
            x.putExtra("image",str4);
            startActivity(x);
        }
    }


    public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {
        final ProgressDialog loading = ProgressDialog.show(LawyerImagesDisplay.this, "Downloading...", "Please wait...", false, false);
        private String url;
        private ImageView imageView;

        public ImageLoadTask(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {

            try {
                URL urlConnection = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlConnection
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            imageView.setImageBitmap(result);
            loading.dismiss();
        }

    }
}
