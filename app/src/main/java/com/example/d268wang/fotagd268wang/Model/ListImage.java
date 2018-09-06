package com.example.d268wang.fotagd268wang.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.example.d268wang.fotagd268wang.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class ListImage implements Rateable, Serializable {
    /**
     * The observers that are watching this model for changes.
     */
    private static final long serialVersionUID = 1339350;
    public transient Context context;
    private transient List<Observer> observers;
    private transient List<Image> imageList;
    private Map<Integer, Integer> defalutImageMap;
    private Map<String, Integer> userAddedImageMap;
    private Layout layout;
    private int rate;

    /**
     * Create a new model.
     */
    public ListImage(Context context) {
        rate = 0;
        this.observers = new ArrayList<>();
        this.imageList = new ArrayList<>();
        this.defalutImageMap = new HashMap<>();
        this.userAddedImageMap = new HashMap<>();
        this.context = context;
    }

    /**
     * Add an observer to be notified when this model changes.
     */
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    public void init() {
        if (observers == null) {
            this.observers = new ArrayList<>();
            this.imageList = new ArrayList<>();
        }
    }


    /**
     * Remove an observer from this model.
     */
    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }

    /**
     * Notify all observers that the model has changed.
     */
    public void notifyObservers() {
        for (Observer observer : this.observers) {
            observer.update(this);
        }
    }

    public boolean setRate(int filter) {
        if (this.rate == filter) {
            this.rate = 0;
            return false;
        } else {
            this.rate = filter;
            return true;
        }

    }

    @Override
    public int getRate() {
        return rate;
    }

    public void addImageList(int imagePath, int rate, boolean isRecovery) {
        Image newImage;
        newImage = new Image(imagePath, context, this, rate);
        imageList.add(newImage);
    }

    public void addImageFromUser(String val) {
            new DownloadImage(this, val,context).execute();
    }

    public void loadDefaultImage() {

        defalutImageMap.put(R.drawable.cat01, 0);
        defalutImageMap.put(R.drawable.cat02, 0);
        defalutImageMap.put(R.drawable.cat03, 0);
        defalutImageMap.put(R.drawable.cat04, 0);
        defalutImageMap.put(R.drawable.cat05, 0);
        defalutImageMap.put(R.drawable.dog01, 0);
        defalutImageMap.put(R.drawable.dog02, 0);
        defalutImageMap.put(R.drawable.dog03, 0);
        defalutImageMap.put(R.drawable.dog04, 0);
        defalutImageMap.put(R.drawable.dog05, 0);
        Set<Integer> keySet = defalutImageMap.keySet();
        for (Integer i : keySet) {
            addImageList(i, 0, false);
        }
    }

    public void clearAll() {
        this.imageList = new ArrayList<>();
        notifyObservers();
    }

    public List<Image> getImageList() {
        return imageList;
    }

    public Layout getLayout() {
        return layout;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
        notifyObservers();
    }


    void updateImageRateList(Image image) {
        int imagePath = image.getPath();
        defalutImageMap.replace(imagePath, image.getRate());
        userAddedImageMap.replace(image.getUserInputValue(), image.getRate());

    }

    public Map<String, Integer> getUserAddedImageMap() {
        return userAddedImageMap;
    }
}
