package com.test.seefood;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ViewImage extends AppCompatActivity {
    Image image;
    int position;

    /**
     * onCreate method called on activity start up
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewimage);

        // retrieves list of images from gallery
        final List<Image> images = GalleryView.getImages();
        final Intent intent = getIntent();

        // gets image from intent
        position = intent.getIntExtra("position", 0);
        image = images.get(position);

        // sets action bar
        setupActionBar(image.getName());

        // sets image
        ImageView iv = findViewById(R.id.image);
        iv.setImageBitmap(image.getImage());
    }

    /**
     * returns to previous activity
     */
    public void backBtn() {
        Intent intent = new Intent(getBaseContext(), ImageInflater.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    /**
     * sets up action bar
     * @param name
     */
    public void setupActionBar(String name) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_HOME_AS_UP);
        getSupportActionBar().setCustomView(R.layout.view_image_actionbar);
        TextView tv = getSupportActionBar().getCustomView().findViewById(R.id.action_bar_title);

        // icon to download image
        ImageView download = getSupportActionBar().getCustomView().findViewById(R.id.download);
        tv.setText(name);
        // saves image to local gallery upon click
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }

    /**
     * returns the unique directory for the phones DCIM folder
     */
    private File getDirectory() {
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        return new File(dir, "homework");
    }

    /** This save method creates a unique filename and specifies an exact path to the phones
     *  photo application. The specified path is then used in an output stream to write the
     *  created bitmap to the phones storage in .jpeg format.
     */
    public void save() {
        FileOutputStream os = null;
        File file = getDirectory();

        if (!file.exists() && !file.mkdirs()) {
            return;
        }

        // creates a unique name for the image using the time and date it was created
        String fileLocation = file.getAbsolutePath() + "/" + image.getName();
        File newFile = new File(fileLocation);

        try {
            os = new FileOutputStream(fileLocation);
            image.getImage().compress(Bitmap.CompressFormat.JPEG,100,os);
            Toast.makeText(this, "Art piece saved to gallery", Toast.LENGTH_SHORT).show();
            os.flush();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(newFile));
        sendBroadcast(intent);
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
}
