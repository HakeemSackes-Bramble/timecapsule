package com.timecapsule.app.locationpick.controller;

import android.content.Intent;
import android.net.Uri;

/**
 * Created by catwong on 3/17/17.
 */

public interface MediaListener {

    void goToCamera(Intent intent);

    void goToVideo(Intent intent);

    void goToAudio();

    void setLatLongValues(double locationLatitude, double locationLongitude);

    void setAddress(String address);

    void uploadAudio(Uri downloadUrl);
}
