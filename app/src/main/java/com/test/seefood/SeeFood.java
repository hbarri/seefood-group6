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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.IOException;

public class SeeFood extends AppCompatActivity {
    private final int imageCapture = 1, imageGallery = 2;

    /**
     * onCreate method called on activity start up
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen_view);

        // setup the action bar
        setupActionBar();

        // calls method to capture image
        Button takeImage = findViewById(R.id.captureImageBtn);
        takeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeImage();
            }
        });

        // calls method to upload image
        Button uploadImage = findViewById(R.id.uploadImageBtn);
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        // calls method to open gallery
        Button gallery = findViewById(R.id.galleryBtn);
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gallery();
            }
        });
    }

    /**
     * opens gallery class
     */
    public void gallery() {
        Intent intent = new Intent(getBaseContext(), GalleryView.class);
        startActivity(intent);
    }

    /**
     * opens gallery to upload image
     */
    public void uploadImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, imageGallery);
    }

    /**
     * opens camera to snap image
     */
    public void takeImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, imageCapture);
    }

    /**
     * captures result of intent
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // captures result based on if image was received from gallery or camera
        if (requestCode == imageCapture) {
            // converts to bitmap
            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            ConfirmImages.getImagesToConfirm().add(new Image().createImage(bitmap));
        } else if (requestCode == imageGallery) {
            Uri uri = data.getData();
            try {
                // converts to bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                ConfirmImages.getImagesToConfirm().add(new Image().createImage(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // switches view to confirm images
        Intent intent = new Intent(getBaseContext(), ConfirmImages.class);
        startActivity(intent);
    }

    /**
     * sets up action bar
     */
    public void setupActionBar() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.start_screen_actionbar);

        TextView tv = getSupportActionBar().getCustomView().findViewById(R.id.action_bar_title);
        ImageView iv = getSupportActionBar().getCustomView().findViewById(R.id.help);

        // opens up help dialog box
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpDialog bottomSheet = new helpDialog();
                bottomSheet.show(getSupportFragmentManager(), "exampleBottomSheet");
            }
        });

        tv.setText("SeeFood");
        tv.setPadding(30, 0, 0, 0);
    }
}
