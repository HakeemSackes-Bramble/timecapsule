package com.timecapsule.app.googleplaces;//package com.timecapsule.app.googleplaces;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import static com.facebook.FacebookSdk.getApplicationContext;

;

/**
 * Created by hakeemsackes-bramble on 3/10/17.
 */

public class LocationObject implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {


    private static final String TAG = LocationObject.class.getSimpleName();
    PlacePicker.IntentBuilder builder;
    private GoogleApiClient mGoogleApiClient;
    private static final int REQUEST_LOCATION = 201;
    private FusedLocationProviderApi mCurrentLocation;
    private Context context;
    double mLatitude;
    double mLongitude;
    private boolean isLocationSet;
    private LatLng lonlat;

    public double getmLatitude() {

        return mLatitude;
    }

    public double getmLongitude() {
        return mLongitude;
    }

    public LocationObject(Context context) {
        this.context = context;
        builder = new PlacePicker.IntentBuilder();
        this.mGoogleApiClient = new GoogleApiClient
                .Builder(getApplicationContext())
                .addConnectionCallbacks(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
        mCurrentLocation = LocationServices.FusedLocationApi;
        if (mGoogleApiClient.isConnected()) {
            Log.d(TAG, "Was connected");
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            }
            mCurrentLocation.getLastLocation(mGoogleApiClient);
        } else {
            Log.d(TAG, "Wasn't connected");
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("ERROR")
                .setMessage("Something is wrong")
                .setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public boolean isLocationSet() {
        return isLocationSet;
    }

    public void setmLatitude(double mLatitude) {
        this.mLatitude = mLatitude;
    }

    public void setmLongitude(double mLongitude) {
        this.mLongitude = mLongitude;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mCurrentLocation = LocationServices.FusedLocationApi;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        setmLatitude(mCurrentLocation.getLastLocation(mGoogleApiClient).getLatitude());
        setmLongitude(mCurrentLocation.getLastLocation(mGoogleApiClient).getLongitude());
        isLocationSet = true;
        Log.d(TAG, "onConnected: " + mLatitude + " " + mLongitude);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
