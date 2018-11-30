package com.test.seefood;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class SeeFood extends AppCompatActivity implements helpDialog.BottomSheetListener {
    private final int imageCapture = 1, imageGallery = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen_view);

        //setup the action bar
        setupActionBar();

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

        Button gallery = findViewById(R.id.galleryBtn);
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gallery();
            }
        });
    }

    public void gallery() {
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
            ConfirmImages.getImagesToConfirm().add(new Image().createImage(bitmap));
        } else if (requestCode == imageGallery) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                ConfirmImages.getImagesToConfirm().add(new Image().createImage(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Intent intent = new Intent(getBaseContext(), ConfirmImages.class);
        startActivity(intent);
    }

    public void setupActionBar() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.start_screen_actionbar);

        TextView tv = getSupportActionBar().getCustomView().findViewById(R.id.action_bar_title);
        ImageView iv = getSupportActionBar().getCustomView().findViewById(R.id.help);

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

    @Override
    public void onButtonClicked(String text) {

    }
}
