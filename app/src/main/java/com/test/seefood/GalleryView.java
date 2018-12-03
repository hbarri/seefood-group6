package com.test.seefood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GalleryView extends AppCompatActivity {
    private static List<Image> images = new ArrayList<>();
    private static GridView gridView;
    private static ImageAdapter imageAdapter;
    ImageView edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_view);

        //Format and setup the action bar
        setupActionBar(true);

        // reset list of imagesToConfirm selected
        ConfirmImages.imagesToConfirm = new ArrayList<>();

        // finds gridView to populate with list of images sent from the cloud
        gridView = findViewById(R.id.gridView);
        imageAdapter = new ImageAdapter(this, images, false);
        gridView.setAdapter(imageAdapter);

        // imageAdapter.setLayout(true);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                if (imageAdapter.getLayout() == true) {
                    removeImage(position, imageAdapter, gridView);
                } else {
                    imageClick(position, images);
                }
            }
        });
    }

    public void removeImage(int position, ImageAdapter adapter, GridView gridView) {

        int id = images.get(position).getId();

        images.remove(position);
        adapter.notifyDataSetChanged();
        gridView.setAdapter(adapter);

        // run api to update image database
        API_O api2 = new API_O("images/" + id, "DELETE");
        api2.execute();

        // run api to update image database
        API_O api = new API_O("close", "GET");
        api.execute();
    }

    public static void updateGrid() {
        imageAdapter.notifyDataSetChanged();
        gridView.setAdapter(imageAdapter);
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

    public void imageClick(int position, List images) {
        Intent intent = new Intent(getBaseContext(), ImageInflater.class);
        intent.putExtra("position", position);
        intent.putExtra("images", images.size());
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

    public void setupActionBar(boolean toggle) {

        if (toggle == true) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_HOME_AS_UP);
            getSupportActionBar().setCustomView(R.layout.galleryview_actionbar);
            TextView tv = getSupportActionBar().getCustomView().findViewById(R.id.action_bar_title);
            edit = findViewById(R.id.edit);
            tv.setText("Gallery");
            tv.setPadding(0, 0, 175, 0);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageAdapter.setLayout(true);
                    imageAdapter.notifyDataSetChanged();
                    gridView.setAdapter(imageAdapter);
                    setupActionBar(false);
                }
            });
        } else {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.galleryview_edit_actionbar);
            Button done = getSupportActionBar().getCustomView().findViewById(R.id.done);
            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageAdapter.setLayout(false);
                    imageAdapter.notifyDataSetChanged();
                    gridView.setAdapter(imageAdapter);
                    setupActionBar(true);
                }
            });
        }
    }
}
