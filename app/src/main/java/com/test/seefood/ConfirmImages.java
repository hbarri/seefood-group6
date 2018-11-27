package com.test.seefood;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

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

        GridView gridView = findViewById(R.id.gridView);
        final ImageAdapter imageAdapter = new ImageAdapter(this, imagesToConfirm, true);
        gridView.setAdapter(imageAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                removeImage(position);
            }
        });

        Button takeImage = findViewById(R.id.captureImageBtn);
        takeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeImage();
            }
        });

        Button uploadImage = findViewById(R.id.uploadImageBtn);
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        Button testImages = findViewById(R.id.testImagesBtn);
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

    public static List<Image> getImagesToConfirm() {
        return imagesToConfirm;
    }

    public void removeImage(int position) {
        imagesToConfirm.remove(position);

        Intent intent = new Intent(getBaseContext(), ConfirmImages.class);
        startActivity(intent);
    }

    public void testImages() throws IOException, InterruptedException, ExecutionException {
        // send images through API_Post to receive and update response
        API_P api = new API_P(imagesToConfirm, GalleryView.getImages());
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
}
