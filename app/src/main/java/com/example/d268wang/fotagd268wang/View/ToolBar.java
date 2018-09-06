package com.example.d268wang.fotagd268wang.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.d268wang.fotagd268wang.Model.ListImage;
import com.example.d268wang.fotagd268wang.Model.Observer;
import com.example.d268wang.fotagd268wang.R;

import java.util.ArrayList;
import java.util.List;


public class ToolBar implements Observer {
    private ListImage listImage;
    private Context context;
    private View view;
    private List<ImageButton> imageButtons;
    private Drawable emptyStar;
    private Drawable filledStar;
    private boolean imageLoaded;
    private static final String api_key = "AIzaSyCpgB9I93SOg_58kTairfW7_QcrhVZ7QXg";
    private static final String search_engine = "010766345319347882220%3A5kyu8pew9t0";
    private static final String searchAPI = "https://www.googleapis.com/customsearch/v1?cx=" +
            search_engine + "&num=10&searchType=image&key=" +
            api_key + "&q=";

    public ToolBar(final Context context, final ListImage listImage) {
        this.listImage = listImage;
        this.context = context;
        this.listImage.addObserver(this);

        this.emptyStar = ContextCompat.getDrawable(context, R.drawable.star);
        this.filledStar = ContextCompat.getDrawable(context, R.drawable.markstar);

        this.view = ((Activity) context).findViewById(R.id.toolbar);
        this.imageButtons = new ArrayList<>();
        this.imageButtons.add((ImageButton) this.view.findViewById(R.id.star1));
        this.imageButtons.add((ImageButton) this.view.findViewById(R.id.star2));
        this.imageButtons.add((ImageButton) this.view.findViewById(R.id.star3));
        this.imageButtons.add((ImageButton) this.view.findViewById(R.id.star4));
        this.imageButtons.add((ImageButton) this.view.findViewById(R.id.star5));

        for (int i = 0; i < 5; i++) {
            this.imageButtons.get(i).setOnClickListener(new Stars(listImage, i));
            this.imageButtons.get(i).setTag(i);
        }

        ImageButton deleteButton = (ImageButton) this.view.findViewById(R.id.delete);
        ImageButton loadButton = (ImageButton) this.view.findViewById(R.id.add);
        ImageButton searchButton = (ImageButton) this.view.findViewById(R.id.search);

        deleteButton.setOnClickListener(v -> {
            listImage.setRate(0);
            listImage.clearAll();
            imageLoaded = false;
            Toast.makeText(context, "Cleared Image", Toast.LENGTH_SHORT).show();
        });

        loadButton.setOnClickListener(v -> {
            if (imageLoaded) {
                Toast.makeText(context, "Image Already Loaded", Toast.LENGTH_SHORT).show();
            } else {
                listImage.loadDefaultImage();
                listImage.setRate(0);
                listImage.notifyObservers();
                Toast.makeText(context, "Loaded Image", Toast.LENGTH_SHORT).show();
                imageLoaded = true;
            }
        });

        searchButton.setOnClickListener(v -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(context);
            alert.setTitle("Enter URL or keywords to load image");
            final EditText input = new EditText(context);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            alert.setView(input);

            alert.setPositiveButton("URL", (dialog, which) -> {
                String url = input.getText().toString();
                listImage.addImageFromUser(url);
            });

            alert.setNegativeButton("Google", (dialog, which) -> {
                String keyword = input.getText().toString();
                String searchRequest = searchAPI + keyword;
                new DownloadJsonTask(searchRequest,listImage, context).execute();
            });

            // TODO google

            alert.setNeutralButton("Cancel", (dialog, which) -> dialog.cancel());

            alert.show();


        });

        update(listImage);
    }


    @Override
    public void update(Object observable) {
        for (int i = 0; i < this.listImage.getRate(); ++i) {
            this.imageButtons.get(i).setImageDrawable(filledStar);
        }
        for (int i = this.listImage.getRate(); i < 5; ++i) {
            this.imageButtons.get(i).setImageDrawable(emptyStar);
        }

    }
}
