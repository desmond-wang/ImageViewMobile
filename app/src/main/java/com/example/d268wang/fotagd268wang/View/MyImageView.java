package com.example.d268wang.fotagd268wang.View;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import com.example.d268wang.fotagd268wang.Model.Image;
import com.example.d268wang.fotagd268wang.Model.Observer;
import com.example.d268wang.fotagd268wang.Model.Rateable;
import com.example.d268wang.fotagd268wang.R;

import java.util.ArrayList;
import java.util.List;

public class MyImageView extends LinearLayout implements Observer {
    private Image image;
    private View view;
    private RatingBar rateButton;
    private List<ImageView> buttons;
    private Drawable emptyStar;
    private Drawable filledStar;

    public MyImageView(Context c, Image i, View view){
        super(c);

        this.image = i;
        this.view = view;

        this.view.setOnClickListener(v -> {
            final Dialog dialog = new Dialog(MyImageView.this.getContext(), R.style.FullScreenDialog);
            dialog.setContentView(R.layout.full_screen);
            ImageView iv = dialog.findViewById(R.id.image_view);
            iv.setImageBitmap(image.getImage());
            iv.setOnClickListener(v1 -> dialog.dismiss());
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.setCancelable(true);
            dialog.show();

        });
        this.emptyStar = ContextCompat.getDrawable(c, R.drawable.star);
        this.filledStar = ContextCompat.getDrawable(c, R.drawable.markstar);
        this.buttons = new ArrayList<>();
        this.buttons.add((ImageButton) this.view.findViewById(R.id.thumbstar1));
        this.buttons.add((ImageButton) this.view.findViewById(R.id.thumbstar2));
        this.buttons.add((ImageButton) this.view.findViewById(R.id.thumbstar3));
        this.buttons.add((ImageButton) this.view.findViewById(R.id.thumbstar4));
        this.buttons.add((ImageButton) this.view.findViewById(R.id.thumbstar5));

        for(int j = 0; j< 5; j++){
            this.buttons.get(j).setOnClickListener(new Stars(image,j));
            this.buttons.get(j).setTag(j);
        }

        update(image);
    }

    public View getView() {
        return view;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public void update(Object observable) {
        ImageView imageView = view.findViewById(R.id.image_view);
        imageView.setImageBitmap(this.image.getImage());
        for (int i=0; i < this.image.getRate(); ++i){
            this.buttons.get(i).setOnClickListener(new Stars(image, i));
            this.buttons.get(i).setImageDrawable(filledStar);
        }
        for (int i = this.image.getRate(); i < 5; ++i){
            this.buttons.get(i).setOnClickListener(new Stars(image, i));
            this.buttons.get(i).setImageDrawable(emptyStar);
        }
    }
}
