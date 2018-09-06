package com.example.d268wang.fotagd268wang.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;

public class Image implements Rateable {
    private int path;
    private int rate;
    private String UserInputValue;
    //    private String name;
//    private String creationTime;
    private transient Bitmap bitmap;
    private transient Bitmap scaledImage;
    private transient Context context;
    private ListImage listImage;

    // load from resources
    public Image(int id, Context context, ListImage listImage, int rate) {
        this.context = context;
        this.listImage = listImage;
        this.rate = rate;
        this.path = id;
        bitmap = BitmapFactory.decodeResource(context.getResources(), id);
    }

    public Image(Bitmap bitmap, ListImage listImage) {
        this.UserInputValue = UserInputValue;
        this.context = context;
        this.rate = 0;
        this.listImage = listImage;
        this.bitmap = bitmap;

        notifyObservers();
    }


    public Bitmap getImage() {
        return bitmap;
    }

    public Bitmap getScaledImage() {
        return scaledImage;
    }


    public int getRate() {
        return rate;
    }

//    public String getCreationTime() {
//        return creationTime;
//    }

//    public String getName() {
//        return name;
//    }

    public boolean setRate(int rate) {
        // TODO uncomment
        if (this.rate == rate) {
            this.rate = 0;
            listImage.updateImageRateList(this);
            return false;
        } else {
            this.rate = rate;
            listImage.updateImageRateList(this);
            return true;
        }

    }

    public void setImage(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Integer getPath() {
        return path;
    }

    public void notifyObservers() {
        listImage.notifyObservers();
    }

    public void addObserver(Observer observer){
        listImage.addObserver(observer);
    }

    public String getUserInputValue() {
        return UserInputValue;
    }
}
