package com.example.d268wang.fotagd268wang.View;

import android.view.View;

import com.example.d268wang.fotagd268wang.Model.ListImage;
import com.example.d268wang.fotagd268wang.Model.Rateable;

public class Stars implements View.OnClickListener {
    Rateable rateable;
    int position;

    public Stars(Rateable rateable, int position) {
        this.rateable = rateable;
        this.position = position;

    }

    @Override
    public void onClick(View v) {
        rateable.setRate(position + 1);
        rateable.notifyObservers();
    }

    public int getRate() {
        return rateable.getRate();
    }
}
