package com.test.seefood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class GalleryView extends AppCompatActivity {
    private static List<Image> images = new ArrayList<>();
    private static GridView gridView;
    private static ImageAdapter imageAdapter;
    ImageView edit;
    ImageView showFav;
    private boolean favorites = false;

    /**
     * onCreate method called on acitivty start up
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_view);

        // format and setup the action bar
        setupActionBar(true);

        // reset list of imagesToConfirm selected
        ConfirmImages.imagesToConfirm = new ArrayList<>();

        // finds gridView to populate with list of images
        gridView = findViewById(R.id.gridView);
        imageAdapter = new ImageAdapter(this, images, false, true);
        gridView.setAdapter(imageAdapter);

        // listener to either remove image from gallery or inflate image
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                if (imageAdapter.getLayout() == true) {
                    // removes image from gallery
                    removeImage(position);
                } else {
                    // inflates image
                    imageClick(position, images);
                }
            }
        });
    }

    /**
     * removes image from gallery
     * @param position
     */
    public void removeImage(int position) {
        // recieves image id
        int id = images.get(position).getId();

        // removes image from list
        images.remove(position);
        // updates grid
        updateGrid();

        // run api to delete image from database
        API_O api2 = new API_O("images/" + id, "DELETE");
        api2.execute();

        // run api to update image database
        API_O api = new API_O("close", "GET");
        api.execute();
    }

    /**
     * updates gridview
     */
    public static void updateGrid() {
        imageAdapter.notifyDataSetChanged();
        gridView.setAdapter(imageAdapter);
    }

    /**
     * updates view based on favorites picked
     */
    public void updateView() {
        // if favorites option is not chosen, all images will be displayed
        if (!favorites) {
            imageAdapter.setImages(images);
        } else {
            // else, only images favorited will be displayed
            List<Image> tmpImages = new ArrayList<Image>();
            for (int i = 0; i < images.size(); i++) {
                if (images.get(i).getisFavorite() == true) {
                    tmpImages.add(images.get(i));
                }
            }
            // sets adapter with favorite images
            imageAdapter.setImages(tmpImages);
        }
        // updates grid
        updateGrid();
    }

    /**
     * retrieves gridview
     * @return
     */
    public static GridView getGridView() {
        return gridView;
    }

    /**
     * retrieves list of images
     * @return
     */
    public static List<Image> getImages() {
        return images;
    }

    /**
     * image inflator
     * @param position
     * @param images
     */
    public void imageClick(int position, List images) {
        // inflates image clicked on by switching activities
        Intent intent = new Intent(getBaseContext(), ImageInflater.class);
        intent.putExtra("position", position);
        intent.putExtra("images", images.size());
        startActivity(intent);
    }

    /**
     * back button
     */
    public void backBtn() {
        Intent intent = new Intent(getBaseContext(), SeeFood.class);
        startActivity(intent);
    }

    /**
     * bar to set up back button
     * @return
     */
    @Override
    public boolean onSupportNavigateUp(){
        backBtn();
        return true;
    }

    /**
     * sets up action bar
     * @param toggle
     */
    public void setupActionBar(boolean toggle) {

        if (toggle == true) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_HOME_AS_UP);
            getSupportActionBar().setCustomView(R.layout.galleryview_actionbar);
            TextView tv = getSupportActionBar().getCustomView().findViewById(R.id.action_bar_title);
            edit = findViewById(R.id.edit);
            showFav = findViewById(R.id.favorite);
            tv.setText("Gallery");
            tv.setPadding(0, 0, 175, 0);

            showFav.setImageResource(R.drawable.like_disabled);

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageAdapter.setLayout(true);
                    imageAdapter.notifyDataSetChanged();
                    gridView.setAdapter(imageAdapter);
                    setupActionBar(false);
                }
            });
            showFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (favorites) {
                        showFav.setImageResource(R.drawable.like_disabled);
                        favorites = false;
                        updateView();
                    } else {
                        showFav.setImageResource(R.drawable.like_enabled);
                        favorites = true;
                        updateView();
                    }
                }
            });

        } else {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.galleryview_edit_actionbar);
            Button done = getSupportActionBar().getCustomView().findViewById(R.id.done);
            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageAdapter.setLayout(false);
                    imageAdapter.notifyDataSetChanged();
                    gridView.setAdapter(imageAdapter);
                    setupActionBar(true);
                }
            });
        }
    }
}
