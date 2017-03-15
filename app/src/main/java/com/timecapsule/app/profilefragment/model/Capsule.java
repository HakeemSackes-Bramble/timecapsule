package com.timecapsule.app.profilefragment.model;

/**
 * Created by catwong on 3/7/17.
 */

public class Capsule {

    public double positionLat;
    public double positionLong;

    public double getPositionLat() {
        return positionLat;
    }

    public void setPositionLat(double positionLat) {
        this.positionLat = positionLat;
    }

    public double getPositionLong() {
        return positionLong;
    }

    public void setPositionLong(double positionLong) {
        this.positionLong = positionLong;
    }

    public String getStorageUrl() {
        return storageUrl;
    }

    public void setStorageUrl(String storageUrl) {
        this.storageUrl = storageUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String storageUrl;
    public String userId;

    public Capsule(){}

    public Capsule(String userId, String storageUrl, double positionLat, double positionLong){
        this.userId = userId;
        this.storageUrl = storageUrl;
        this.positionLat = positionLat;
        this.positionLong = positionLong;
    }




}
