package com.test.seefood;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ConfirmImages extends AppCompatActivity {
    private final int imageCapture = 1, imageGallery = 2;
    public static List<Image> imagesToConfirm = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_images);

        //setup action bar
        setupActionBar();

        final GridView gridView = findViewById(R.id.gridView);
        final ImageAdapter imageAdapter = new ImageAdapter(this, imagesToConfirm, true);
        gridView.setAdapter(imageAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                removeImage(position, imageAdapter, gridView);
            }
        });

        ImageButton takeImage = findViewById(R.id.captureImageBtn);
        takeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeImage();
            }
        });

        ImageButton uploadImage = findViewById(R.id.uploadImageBtn);
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        ImageButton testImages = findViewById(R.id.testImagesBtn);
        testImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    testImages();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void backBtn() {
        Intent intent = new Intent(getBaseContext(), SeeFood.class);
        startActivity(intent);
    }

    public static List<Image> getImagesToConfirm() {
        return imagesToConfirm;
    }

    public void removeImage(int position, ImageAdapter adapter, GridView gridView) {
        imagesToConfirm.remove(position);
        adapter.notifyDataSetChanged();
        gridView.setAdapter(adapter);
    }

    public void testImages() throws IOException, InterruptedException, ExecutionException {
        // send images through API_Post to receive and update response
        API_P api = new API_P(imagesToConfirm, GalleryView.getImages(), GalleryView.getGridView(), GalleryView.getImageAdapter());
        api.execute();

        // update database
        API_C api2 = new API_C();
        api2.execute();

        // redirect to gallery
        Intent intent = new Intent(getBaseContext(), GalleryView.class);
        startActivity(intent);
    }

    public void uploadImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, imageGallery);
    }

    public void takeImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, imageCapture);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == imageCapture) {
            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            imagesToConfirm.add(new Image().createImage(bitmap));
        } else if (requestCode == imageGallery) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imagesToConfirm.add(new Image().createImage(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Intent intent = new Intent(getBaseContext(), ConfirmImages.class);
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
        tv.setText("Confirm Images");
    }
}
