package com.test.seefood;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class GalleryView extends AppCompatActivity {
    private static List<Image> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_view);

        // reset list of imagesToConfirm selected
        ConfirmImages.imagesToConfirm = new ArrayList<>();

        // finds gridView to populate with list of images sent from the cloud
        GridView gridView = findViewById(R.id.gridView);
        final ImageAdapter imageAdapter = new ImageAdapter(this, images, false);
        gridView.setAdapter(imageAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                imageClick(position);
            }
        });

        Button back = findViewById(R.id.backBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backBtn();
            }
        });
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
}
