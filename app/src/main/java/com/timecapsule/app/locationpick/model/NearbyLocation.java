package com.timecapsule.app.locationpick.model;

/**
 * Created by catwong on 3/15/17.
 */

public class NearbyLocation {

    private String name;
    private String address;
    private String latlong;

    public NearbyLocation() {
        this.name = name;
        this.address = address;
        this.latlong = latlong;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatlong() {
        return latlong;
    }

    public void setLatlong(String latlong) {
        this.latlong = latlong;
    }
}
