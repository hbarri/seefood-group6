package com.test.seefood;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ImageInflater extends AppCompatActivity {

    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_inflater);

        final List<Image> images = GalleryView.getImages();
        final Intent intent = getIntent();

        position = intent.getIntExtra("position", 0);

        Image image = images.get(position);

        ImageView imageView = findViewById(R.id.imageView);
        TextView isFoodText = findViewById(R.id.isFood);
        TextView confidence = findViewById(R.id.confidenceLevel);
        TextView date = findViewById(R.id.date);
        TextView percentage = findViewById(R.id.foodPercentage);
        final View progress = findViewById(R.id.progress);
        View divider = findViewById(R.id.divider);
        ImageButton next = findViewById(R.id.next);
        ImageButton previous = findViewById(R.id.previous);
        ImageView imageDisplayed = findViewById(R.id.imageView);

        //setup the action bar
        setupActionBar(image.getName());

        //get phones display width to calculate percentage
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;

        if (image.getConfidenceLevel() == "25%") {
            isFoodText.setText("Uh Oh! No Food :(");
        } else if (image.getConfidenceLevel() == "25 - 75") {
            isFoodText.setText("We may SeeFood?");
        } else {
            isFoodText.setText("Yes! We SeeFood!");
        }

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.setBackgroundColor(Color.parseColor("#ffffff"));
            }
        });

        progress.setBackground(setGradientColor(100 * calculatePercentage(image.getConfidenceLevel())));

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        System.out.println(formatter.format(image.getDateCreated()));

        date.setText(formatter.format(image.getDateCreated()));
        progress.getLayoutParams().width = (int)(width * calculatePercentage(image.getConfidenceLevel()));
        divider.getLayoutParams().width = (int)(width * calculatePercentage(image.getConfidenceLevel()));
        imageView.setImageBitmap(image.getImage());
        confidence.setText(image.getConfidenceLevel());
        percentage.setText(Integer.toString((int)(100 * calculatePercentage(image.getConfidenceLevel()))) + "%");

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position + 1 != images.size()) {
                    switchImage(intent.getIntExtra("position", 0) + 1);
                }
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position - 1 != -1)
                switchImage(intent.getIntExtra("position", 0) - 1);
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

    public void switchImage(int position) {
        Intent intent = new Intent(getBaseContext(), ImageInflater.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    public void backBtn() {
        Intent intent = new Intent(getBaseContext(), GalleryView.class);
        startActivity(intent);
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
