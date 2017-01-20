package com.restaurantsapp.model;

/**
 * Created by indianic on 30/03/16.
 */
public class GalleryModel {

    private String imagePath;
    private boolean isSelected;


    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
