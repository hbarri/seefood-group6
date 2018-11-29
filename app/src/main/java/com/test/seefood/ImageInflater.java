package com.test.seefood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ImageInflater extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_inflater);

        List<Image> images = GalleryView.getImages();
        Intent intent = getIntent();
        Image image = images.get(intent.getIntExtra("position", 0));

        ImageView imageView = findViewById(R.id.imageView);
        TextView isFoodText = findViewById(R.id.isFood);
        TextView confidence = findViewById(R.id.confidenceLevel);
        TextView date = findViewById(R.id.date);
        View progress = findViewById(R.id.progress);

        //setup the action bar
        setupActionBar(image.getName());

        imageView.setImageBitmap(image.getImage());
        isFoodText.setText(image.getIsFood());
        confidence.setText(image.getConfidenceLevel());
    }

    public void backBtn() {
        Intent intent = new Intent(getBaseContext(), GalleryView.class);
        startActivity(intent);
    }

    public void setupActionBar(String name) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_HOME_AS_UP);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        TextView tv = getSupportActionBar().getCustomView().findViewById(R.id.action_bar_title);
        tv.setText(name);
    }

    @Override
    public boolean onSupportNavigateUp(){
        backBtn();
        return true;
    }
}
