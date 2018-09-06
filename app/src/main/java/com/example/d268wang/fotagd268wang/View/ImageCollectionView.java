package com.example.d268wang.fotagd268wang.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.d268wang.fotagd268wang.Model.Image;
import com.example.d268wang.fotagd268wang.Model.ListImage;
import com.example.d268wang.fotagd268wang.Model.Observer;
import com.example.d268wang.fotagd268wang.R;

import java.util.ArrayList;
import java.util.List;


public class ImageCollectionView extends BaseAdapter implements Observer {
    private ListImage listImage;
    private Context context;
    private List<Image> filteredImage;
    private List<MyImageView> myImageViews;

    public ImageCollectionView(Context context, ListImage listImage){
        this.context =context;
        this.listImage = listImage;
        myImageViews = new ArrayList<>();
        this.listImage.addObserver(this); //TODO
        update(this.listImage);
    }

    @Override
    public int getCount() {
        return this.filteredImage.size();
    }

    @Override
    public Object getItem(int position) {
        return this.filteredImage.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            View imageView = LayoutInflater.from(this.context).inflate(R.layout.thumbnail,parent,false);
            MyImageView iv = new MyImageView(this.context, this.filteredImage.get(position), imageView);
            iv.update(this.filteredImage.get(position));
            this.myImageViews.add(iv);

            return iv.getView();
        } else {
            MyImageView myIV = null;
            for (MyImageView iv : this.myImageViews){
                if (iv.getView() == convertView){
                    myIV = iv;
                }
            }
            myIV.setImage(filteredImage.get(position));
            myIV.update(this.filteredImage.get(position));
            return convertView;
        }
    }

    @Override
    public void update(Object observable) {
        filteredImage = new ArrayList<>();
        if(listImage.getRate() > 0){
            for(Image image : this.listImage.getImageList()){
                if(image.getRate() >= listImage.getRate()){
                    this.filteredImage.add(image);
                }
            }
        } else {
            this.filteredImage = this.listImage.getImageList();
        }
        notifyDataSetChanged();
    }


}
