package com.test.seefood;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ImageInflater extends AppCompatActivity implements editNameDialog.BottomSheetListener {

    int position;
    Image image;
    ImageView imageView;
    TextView isFoodText;
    TextView date;
    TextView percentage;
    View progress;
    View divider;
    ImageButton next;
    ImageButton previous;
    ImageView imageDisplayed;
    ImageView favorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_inflater);

        final List<Image> images = GalleryView.getImages();
        final Intent intent = getIntent();

        position = intent.getIntExtra("position", 0);

        imageView = findViewById(R.id.imageView);
        isFoodText = findViewById(R.id.isFood);
        date = findViewById(R.id.date);
        percentage = findViewById(R.id.foodPercentage);
        progress = findViewById(R.id.progress);
        divider = findViewById(R.id.divider);
        next = findViewById(R.id.next);
        previous = findViewById(R.id.previous);
        imageDisplayed = findViewById(R.id.imageView);
        favorite = findViewById(R.id.favorite);

        updateView(position, images);
    }

    public void updateView(int positiontmp, List<Image> imagestmp) {

        final int position = positiontmp;
        final List<Image> images = imagestmp;

        image = images.get(position);

        //setup the action bar
        setupActionBar(image.getName());

        //check the angle buttons
        checkNextPrevious(position, images);

        //get phones display width to calculate percentage
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;

        progress.setBackground(setGradientColor(100 * calculatePercentage(image.getConfidenceLevel())));

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

        isFoodText.setText(image.getIsFood());
        date.setText(formatter.format(image.getDateCreated()));
        progress.getLayoutParams().width = (int)(width * calculatePercentage(image.getConfidenceLevel()));
        divider.getLayoutParams().width = (int)(width * calculatePercentage(image.getConfidenceLevel()));
        imageView.setImageBitmap(image.getImage());
        percentage.setText(Integer.toString((int)(100 * calculatePercentage(image.getConfidenceLevel()))) + "%");

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (image.getisFavorite()) {
                    favorite.setImageResource(R.drawable.like_disabled);
                    image.setIsFavorite(false);
                } else {
                    favorite.setImageResource(R.drawable.like_enabled);
                    image.setIsFavorite(true);
                }
            }
        });

        if (image.getisFavorite() == true) {
            favorite.setImageResource(R.drawable.like_enabled);
        } else {
            favorite.setImageResource(R.drawable.like_disabled);
        }

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position + 1 != images.size()) {
                    updateView(position + 1, images);
                }
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position - 1 != -1)
                    updateView(position - 1, images);
            }
        });

        imageDisplayed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ViewImage.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
    }

    public void checkNextPrevious(int position, List<Image> images) {
        if (position == 0) {
            previous.setVisibility(View.GONE);
        } else {
            previous.setVisibility(View.VISIBLE);
        }
        if (position == images.size() - 1) {
            next.setVisibility(View.GONE);
        } else {
            next.setVisibility(View.VISIBLE);
        }
    }

    public void backBtn() {
        Intent intent = new Intent(getBaseContext(), GalleryView.class);
        startActivity(intent);
    }

    public String getName() {
        return image.getName();
    }

    public double calculatePercentage(String s) {
        double returnVal;
        String[] nums;
        ArrayList<String> conf = new ArrayList<String>();
        double conf1;
        double conf2;

        s = s.replaceAll("\\[", "").replaceAll("\\]", "");
        s = s.trim();
        nums = s.split(" ");

        for (int i = 0; i < nums.length; i++) {
            if (nums[i].isEmpty()) {
                System.out.println("yo");
            } else {
                conf.add(nums[i]);
            }
        }

        conf1 = Double.parseDouble(conf.get(0));
        conf2 = Double.parseDouble(conf.get(1));

        returnVal = (1/(1 + Math.exp(-1 * Math.abs(conf1 - conf2))));
        System.out.println(returnVal);

        return returnVal;
    }

    public GradientDrawable setGradientColor(double percentage) {
        int[] colors = new int[2];
        colors[0] = Color.parseColor("#282933");
        colors[1] = Color.parseColor(getColorPercentage((int)percentage));


        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM, colors);
        return gd;
    }

    public String getColorPercentage(int percentage)
    {
        int g = ((255 * percentage) / 100);
        int r = (255 * (100 - percentage)) / 100;
        int b = 0;

        String hex = String.format("#%02x%02x%02x", r, g, b);

        return hex;
    }

    public void setupActionBar(String name) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_HOME_AS_UP);
        getSupportActionBar().setCustomView(R.layout.imageinflator_actionbar);
        TextView tv = getSupportActionBar().getCustomView().findViewById(R.id.action_bar_title);
        ImageView edit = getSupportActionBar().getCustomView().findViewById(R.id.edit);
        tv.setText(name);
        tv.setPadding(0, 0, 150, 0);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editNameDialog bottomSheet = new editNameDialog();
                bottomSheet.show(getSupportFragmentManager(), "exampleBottomSheet");
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        backBtn();
        return true;
    }

    @Override
    public void onButtonClicked(String text) {
        image.setName(text);
        setupActionBar(text);

        // run api to update database with new name
        API_O api2 = new API_O("images/" + image.getId(), "PUT");
        api2.execute();
    }
}
