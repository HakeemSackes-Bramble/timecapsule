package com.timecapsule.app.locationpick;

import android.Manifest;
import android.content.Context;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.timecapsule.app.locationpick.model.NearbyLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by catwong on 3/15/17.
 */

public class LocationLoader extends Loader<List<NearbyLocation>> implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
        , ResultCallback<PlaceLikelihoodBuffer> {


    private static final String TAG = LocationLoader.class.getSimpleName();
    private GoogleApiClient mGoogleApiClient;
    private List<NearbyLocation> mNearbyLocations;
    private List<PlaceLikelihood> mLikelyPlaces;

    /**
     * Stores away the application context associated with context.
     * Since Loaders can be used across multiple activities it's dangerous to
     * store the context directly; always use {@link #getContext()} to retrieve
     * the Loader's Context, don't use the constructor argument directly.
     * The Context returned by {@link #getContext} is safe to use across
     * Activity instances.
     *
     * @param context used to retrieve the application context.
     */
    public LocationLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        if (mNearbyLocations != null) {
            deliverResult(mNearbyLocations);
        }
        if (mGoogleApiClient == null) {
            mGoogleApiClient =
                    new GoogleApiClient.Builder(getContext(), this, this)
                            .addApi(Places.PLACE_DETECTION_API)
                            .addApi(Places.GEO_DATA_API)
                            .addApi(LocationServices.API)
                            .build();
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onForceLoad() {
        if (mNearbyLocations != null) {
            deliverResult(mNearbyLocations);
        }
        if (!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void deliverResult(List<NearbyLocation> nearbyLocations) {
        mNearbyLocations = nearbyLocations;
        super.deliverResult(mNearbyLocations);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "onConnected() called");
        callPlaceDetectionApi();

    }

    private void callPlaceDetectionApi() {
        Log.d(TAG, "callPlaceDetectionApi() called");
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi
                .getCurrentPlace(mGoogleApiClient, null);
        result.setResultCallback(this);

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "onConnectionSuspended() called");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed() called with");
    }

    @Override
    public void onResult(@NonNull PlaceLikelihoodBuffer likelyPlaces) {
        Log.d(TAG, "onResult() called with: " + "likelyPlaces = [" + likelyPlaces.toString() + "]");
        List<NearbyLocation> nearbyPlaces = new ArrayList<>();
        for (PlaceLikelihood placeLikelihood : likelyPlaces) {

            NearbyLocation nearbyPlace = new NearbyLocation();
            Log.i(TAG, String.format("Place '%s' and '%s' with "+
                            "latlng: %s",
                    placeLikelihood.getPlace().getName(),
                    placeLikelihood.getPlace().getAddress(),
                    placeLikelihood.getPlace().getLatLng()));
            nearbyPlace.setName(placeLikelihood.getPlace().getName().toString());
            nearbyPlace.setAddress(placeLikelihood.getPlace().getAddress().toString());
            nearbyPlace.setLatlong(placeLikelihood.getPlace().getLatLng().toString());
            nearbyPlaces.add(nearbyPlace);
        }
        likelyPlaces.release();
        if (nearbyPlaces.size() > 0) {
            deliverResult(nearbyPlaces);
        }

    }
}
