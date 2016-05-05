package com.example.android.fractal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FourthActivity extends AppCompatActivity {
    TouchImageView img;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        img = (TouchImageView) findViewById(R.id.imgView);
        Intent y = getIntent();
        url = y.getStringExtra("image");
        ImageLoadTask i1 = new ImageLoadTask(url,img);
        i1.execute();
        img.setMaxZoom(4f);
    }

    public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {
        final ProgressDialog loading = ProgressDialog.show(FourthActivity.this, "Loading...", "Please wait...", false, false);
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
