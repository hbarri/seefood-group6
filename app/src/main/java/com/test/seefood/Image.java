package com.test.seefood;

import android.graphics.Bitmap;
import java.util.Date;
import java.util.Random;

public class Image {
    private int id;
    private Date dateCreated;
    private Bitmap image;
    private String name;
    private boolean isFood;
    private boolean isFavorite;
    private String confidenceLevel;

    public Image createImage(Bitmap bitmap) {
        //update input received from AI later
        dateCreated = new Date();
        image = bitmap;

        return this;
    }

    public void formatPic() {
        //format pic to AI standards before sending it to the AI
        //return image
    }

    public boolean getisFavorite() {
        return isFavorite;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getConfidenceLevel() {
        return confidenceLevel;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public int getId() {
        return id;
    }

    public String getIsFood() {
        if (isFood) return "I SEE FOOD";
        else return "NO FOOD";
    }

    public void setIsFavorite(boolean favorite) {
        this.isFavorite = favorite;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIsFood(boolean isFood) {
        this.isFood = isFood;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setConfidenceLevel(String confidenceLevel) {
        this.confidenceLevel = confidenceLevel;
    }
}
