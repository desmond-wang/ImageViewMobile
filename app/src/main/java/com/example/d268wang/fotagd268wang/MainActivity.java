package com.example.d268wang.fotagd268wang;

import android.content.res.Configuration;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.d268wang.fotagd268wang.Model.ListImage;
import com.example.d268wang.fotagd268wang.Model.Observer;
import com.example.d268wang.fotagd268wang.View.ImageCollectionView;
import com.example.d268wang.fotagd268wang.View.MyImageView;
import com.example.d268wang.fotagd268wang.View.ToolBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Observer {

    private ListImage listImage;
    private GridView gridView;
    private ToolBar toolBar;
    private List<MyImageView> myImageViews;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            gridView.setNumColumns(1);
        } else {
            gridView.setNumColumns(-1);
            // TODO add program name
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null){
            this.listImage = (ListImage) savedInstanceState.getSerializable("ListImage");
            this.listImage.context = this;
        } else {
            this.listImage = new ListImage(this);
        }
        this.listImage.addObserver(this);

        myImageViews = new ArrayList<>();

        setContentView(R.layout.activity_main);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        this.gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(new ImageCollectionView(this,this.listImage));
        final MainActivity context = this;
        gridView.setOnItemClickListener((parent, view, position, id) -> Toast.makeText(context,"Clicked" + id, Toast.LENGTH_SHORT).show());
        this.toolBar = new ToolBar(this,this.listImage);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.clear();
        outState.putSerializable("ListImage", listImage);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void update(Object observable) {
        ((ImageCollectionView)this.gridView.getAdapter()).update(listImage);
        ((ImageCollectionView) this.gridView.getAdapter()).notifyDataSetChanged();
    }
}
