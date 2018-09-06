package com.example.d268wang.fotagd268wang.Model;

public interface Rateable {
    boolean setRate(int rate);
    int getRate();
    void notifyObservers();
}
