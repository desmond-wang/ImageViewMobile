package com.example.d268wang.fotagd268wang.View;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.d268wang.fotagd268wang.Model.ListImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DownloadJsonTask extends AsyncTask<Void, Void, JSONObject> {
    private static final String TAG = "DownloadJsonTask";
    private String url;
    private ListImage listImage;
    private Context context;

    DownloadJsonTask(String url, ListImage listImage, Context context) {
        this.url = url;
        this.listImage = listImage;
        this.context = context;
    }

    @Override
    protected JSONObject doInBackground(Void... strings) {
        try {
            InputStream inputStream = new java.net.URL(url).openStream();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                    builder.append('\n');
                }
                return new JSONObject(builder.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        if (jsonObject == null) {
            Toast.makeText(context,"Failed to connect google search", Toast.LENGTH_LONG).show();
            return;
        }
        try {
            JSONArray items = jsonObject.getJSONArray("items");
            for (int i = 0; i < items.length(); ++i) {
                JSONObject item = items.getJSONObject(i);
                String link = item.getString("link");
                listImage.addImageFromUser(link);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
