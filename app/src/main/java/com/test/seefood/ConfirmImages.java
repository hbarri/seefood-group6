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
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ConfirmImages extends AppCompatActivity {
    // sets variables to retrieve image
    private final int imageCapture = 1, imageGallery = 2;
    // list of images chosen to get tested
    public static List<Image> imagesToConfirm = new ArrayList<>();

    /**
     * onCreate method called on acitivty start up
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_images);

        //setup action bar
        setupActionBar();

        // creates gridview of images selected
        final GridView gridView = findViewById(R.id.gridView);
        final ImageAdapter imageAdapter = new ImageAdapter(this, imagesToConfirm, true, false);
        // attaches adapter
        gridView.setAdapter(imageAdapter);

        // sets on click listener to remove image if clicked on
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                removeImage(position, imageAdapter, gridView);
            }
        });

        // opens camera to capture image if clicked on
        ImageButton takeImage = findViewById(R.id.captureImageBtn);
        takeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeImage();
            }
        });

        // opens local phone gallery to upload image if clicked on
        ImageButton uploadImage = findViewById(R.id.uploadImageBtn);
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        // tests image by sending up to API if clicked on
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

    /**
     * switch activity back to previous view
     */
    public void backBtn() {
        Intent intent = new Intent(getBaseContext(), SeeFood.class);
        startActivity(intent);
    }

    /**
     * retrieves images to test list
     * @return
     */
    public static List<Image> getImagesToConfirm() {
        return imagesToConfirm;
    }

    /**
     * removes image from list of images to test
     * @param position
     * @param adapter
     * @param gridView
     */
    public void removeImage(int position, ImageAdapter adapter, GridView gridView) {
        imagesToConfirm.remove(position);

        // refresh gridView
        adapter.notifyDataSetChanged();
        gridView.setAdapter(adapter);
    }

    /**
     * tests images selected
     * @throws IOException
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public void testImages() throws IOException, InterruptedException, ExecutionException {
        // send images through API_Post to receive and update response
        API_P api = new API_P(imagesToConfirm, GalleryView.getImages());
        api.execute();

        // update database
        API_O api2 = new API_O("close", "GET");
        api2.execute();

        // redirect to gallery
        Intent intent = new Intent(getBaseContext(), GalleryView.class);
        startActivity(intent);
    }

    /**
     * receive image from phone gallery
     */
    public void uploadImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, imageGallery);
    }

    /**
     * receive image from phone camera
     */
    public void takeImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, imageCapture);
    }

    /**
     * compute result of intent
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // if image came from camera, it will be converted to bitmap and added to the list
        if (requestCode == imageCapture) {
            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            imagesToConfirm.add(new Image().createImage(bitmap));
        // else, it will get the uri and add to list
        } else if (requestCode == imageGallery) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imagesToConfirm.add(new Image().createImage(bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // switches to gallery view
        Intent intent = new Intent(getBaseContext(), ConfirmImages.class);
        startActivity(intent);
    }

    /**
     * bar to hold back button
     * @return
     */
    @Override
    public boolean onSupportNavigateUp(){
        backBtn();
        return true;
    }

    /**
     * action bar set up
     */
    public void setupActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_HOME_AS_UP);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        TextView tv = getSupportActionBar().getCustomView().findViewById(R.id.action_bar_title);
        tv.setText("Confirm Images");
    }
}
