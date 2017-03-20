package com.timecapsule.app.profilefragment.model;

import android.support.annotation.Nullable;

/**
 * Created by catwong on 3/7/17.
 */

public class Capsule {

    public String address;
    public String locationName;
    public double positionLat;
    public double positionLong;
    public String storageUrl;
    public String userId;
    public String date;

    public Capsule() {

    }

    public Capsule(String address, String storageUrl, String date) {
        this.address = address;
        this.storageUrl = storageUrl;
        this.date = date;
    }

    public Capsule(String userId, String storageUrl, double positionLat, double positionLong, @Nullable String date, String address) {
        this.userId = userId;
        this.storageUrl = storageUrl;
        this.positionLat = positionLat;
        this.positionLong = positionLong;
        this.date = date;
        this.address = address;
    }

    public Capsule(String userId, String storageUrl, double positionLat, double positionLong, @Nullable String date) {
        this.userId = userId;
        this.storageUrl = storageUrl;
        this.positionLat = positionLat;
        this.positionLong = positionLong;
        this.date = date;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
