package com.test.seefood;

import android.content.Intent;
import android.os.Bundle;
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

        imageView.setImageBitmap(image.getImage());
        isFoodText.setText(image.getIsFood());
        confidence.setText(image.getConfidenceLevel());

        Button back = findViewById(R.id.backBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backBtn();
            }
        });
    }

    public void backBtn() {
        Intent intent = new Intent(getBaseContext(), GalleryView.class);
        startActivity(intent);
    }
}
