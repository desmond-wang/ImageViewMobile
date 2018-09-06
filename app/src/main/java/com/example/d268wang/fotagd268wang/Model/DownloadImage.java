package com.example.d268wang.fotagd268wang.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

public class DownloadImage extends AsyncTask<Void, Void, Bitmap> {


    private ListImage imageList;
    private String url;
    private Context context;

    public DownloadImage(ListImage imageList, String url, Context context) {

        this.imageList = imageList;

        this.url = url;
        this.context = context;
    }

    @Override
    protected Bitmap doInBackground(Void... strings) {
        Bitmap bitmap = null;
        try {
            InputStream inputStream = new java.net.URL(url).openStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (MalformedURLException e) {
            Log.e("MalformedURL", e.getMessage());
        } catch (IOException e) {
            Log.e("IOException", e.getMessage());
        }
        return bitmap;
    }

    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap == null){
            Toast.makeText(context,"Failed to load" + url, Toast.LENGTH_LONG).show();
        }
            Image newImage = new Image(bitmap, imageList);
            imageList.getImageList().add(newImage);
            imageList.getUserAddedImageMap().put(url, 0);
            imageList.notifyObservers();
    }
}
