package com.test.seefood;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GalleryView extends AppCompatActivity {
    private static List<Image> images = new ArrayList<>();
    private static GridView gridView;
    private static ImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_view);

        //Format and setup the action bar
        setupActionBar();

        // reset list of imagesToConfirm selected
        ConfirmImages.imagesToConfirm = new ArrayList<>();

        // finds gridView to populate with list of images sent from the cloud
        gridView = findViewById(R.id.gridView);
        imageAdapter = new ImageAdapter(this, images, false);
        gridView.setAdapter(imageAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                imageClick(position);
            }
        });
    }

    public static ImageAdapter getImageAdapter() {
        return imageAdapter;
    }

    public static GridView getGridView() {
        return gridView;
    }

    public static List<Image> getImages() {
        return images;
    }

    public void imageClick(int position) {
        Intent intent = new Intent(getBaseContext(), ImageInflater.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    public void backBtn() {
        Intent intent = new Intent(getBaseContext(), SeeFood.class);
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp(){
        backBtn();
        return true;
    }

    public void setupActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_HOME_AS_UP);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        TextView tv = getSupportActionBar().getCustomView().findViewById(R.id.action_bar_title);
        tv.setText("Gallery");
    }
}
