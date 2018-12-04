package com.test.seefood;

import android.graphics.Bitmap;
import java.util.Date;

public class Image {
    private int id;
    private Date dateCreated;
    private Bitmap image;
    private String name;
    private boolean isFood;
    private boolean isFavorite;
    private String confidenceLevel;

    /**
     * creates image given bitmap
     * @param bitmap
     * @return
     */
    public Image createImage(Bitmap bitmap) {
        //update input received from AI later
        dateCreated = new Date();
        image = bitmap;

        return this;
    }

    /**
     * returns if it is favorited
     * @return
     */
    public boolean getisFavorite() {
        return isFavorite;
    }

    /**
     * returns image bitmap
     * @return
     */
    public Bitmap getImage() {
        return image;
    }

    /**
     * returns name
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * returns confidence level
     * @return
     */
    public String getConfidenceLevel() {
        return confidenceLevel;
    }

    /**
     * returns date
     * @return
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * returns id
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * returns if it is food
     * @return
     */
    public String getIsFood() {
        if (isFood) return "I SEE FOOD";
        else return "NO FOOD";
    }

    /**
     * sets if it is a favorite
     * @param favorite
     */
    public void setIsFavorite(boolean favorite) {
        this.isFavorite = favorite;
    }

    /**
     * sets image bitmap
     * @param image
     */
    public void setImage(Bitmap image) {
        this.image = image;
    }

    /**
     * sets image id
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * sets if it is food
     * @param isFood
     */
    public void setIsFood(boolean isFood) {
        this.isFood = isFood;
    }

    /**
     * sets name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * sets confidence level
     * @param confidenceLevel
     */
    public void setConfidenceLevel(String confidenceLevel) {
        this.confidenceLevel = confidenceLevel;
    }
}
