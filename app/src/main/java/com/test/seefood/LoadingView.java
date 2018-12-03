package com.test.seefood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class LoadingView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_view);


        initialize();
    }

    public void initialize() {
        // run api to get image database
        API_O api = new API_O("open");
        api.execute();

        // run api to initialize AI in find_food.py
        API_O api3 = new API_O("initialize");
        api3.execute();

        // run api to parse imagesToConfirm from database and store in gallery
        API_G api2 = new API_G(GalleryView.getImages(), getBaseContext());
        api2.execute();
    }
}
