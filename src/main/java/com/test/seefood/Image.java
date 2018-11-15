package com.test.seefood;

import android.graphics.Bitmap;

import java.util.Date;

public class Image {
    private int id;
    private Date dateCreated;
    private Bitmap image;
    private String name;
    private boolean isFood;
    private String confidenceLevel;

    public Image createImage(Bitmap bitmap) {
        //update input received from AI later
        id = 1;
        dateCreated = new Date();
        image = bitmap;
        name = "image1";
        isFood = true;
        confidenceLevel = "[[ 3.7548828 -1.6814774]]";

        return this;
    }

    public void formatPic() {
        //format pic to AI standards before sending it to the AI
        //return image
    }

    public Bitmap getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getIsFood() {
        if (isFood) return "I SEE FOOD";
        else return "NO FOOD";
    }
}
