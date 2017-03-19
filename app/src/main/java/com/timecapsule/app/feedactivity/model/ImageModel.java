package com.timecapsule.app.feedactivity.model;

/**
 * Created by catwong on 3/18/17.
 */

public class ImageModel {

    int imageId;
    String locationId;
    String aboutText;

    public ImageModel() {
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getAboutText() {
        return aboutText;
    }

    public void setAboutText(String aboutText) {
        this.aboutText = aboutText;
    }

}
