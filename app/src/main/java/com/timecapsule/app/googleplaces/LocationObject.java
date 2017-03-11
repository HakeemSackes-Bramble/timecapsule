//package com.timecapsule.app.googleplaces;
//
//import android.Manifest;
//import android.app.Activity;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.pm.PackageManager;
//import android.location.Location;
//import android.support.annotation.NonNull;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.app.FragmentActivity;
//import android.support.v7.app.AlertDialog;
//
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.location.LocationListener;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.location.places.Places;
//import com.google.android.gms.location.places.ui.PlacePicker;
//
//;
//
///**
// * Created by hakeemsackes-bramble on 3/10/17.
// */
//
//public class LocationObject implements GoogleApiClient.OnConnectionFailedListener {
//
//    PlacePicker.IntentBuilder builder;
//    private GoogleApiClient mGoogleApiClient;
//    private static final int REQUEST_LOCATION = 201;
//    private Location mCurrentLocation;
//    private Context context;
//
//    public LocationObject(Context context,GoogleApiClient googleApiClient) {
//        this.context = context;
//        builder = new PlacePicker.IntentBuilder();
//        this.mGoogleApiClient = googleApiClient;
////
//// new GoogleApiClient
////                .Builder(context)
////                .addApi(Places.GEO_DATA_API)
////                .addApi(Places.PLACE_DETECTION_API)
////                .enableAutoManage( context, this)
////                .build();
//    }
//
//    public void startLocationUpdates() {
//        LocationRequest mLocationRequest = new LocationRequest();
//        //mLocationRequest.setInterval(Constants.LOCATION_UPDATE_INTERVAL);
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        LocationListener mLocationListener = new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
//                mCurrentLocation = location;
//            }
//        };
//
//        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
//                    != PackageManager.PERMISSION_GRANTED) {
//                // Check Permissions Now
//                ActivityCompat.requestPermissions
//                        (
//                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                        REQUEST_LOCATION);
//            }
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, mLocationListener);
//    }
//
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//
//        builder.setTitle("ERROR")
//                .setMessage("Something is wrong")
//        .setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//
//        AlertDialog dialog = builder.create();
//        dialog.show();
//    }
//}
