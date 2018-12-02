package com.test.seefood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ViewImage extends AppCompatActivity {

    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewimage);

        final List<Image> images = GalleryView.getImages();
        final Intent intent = getIntent();

        position = intent.getIntExtra("position", 0);

        Image image = images.get(position);

        setupActionBar(image.getName());

        ImageView iv = findViewById(R.id.image);

        iv.setImageBitmap(image.getImage());
    }
    public void backBtn() {
        Intent intent = new Intent(getBaseContext(), ImageInflater.class);
        intent.putExtra("position", position);
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
