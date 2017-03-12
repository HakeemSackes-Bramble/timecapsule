package com.timecapsule.app;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.timecapsule.app.geofence.Constants;
import com.timecapsule.app.googleplaces.LocationObject;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by catwong on 3/4/17.
 */

public class SearchFragment extends Fragment implements OnMapReadyCallback {

    private static final String TAG = SearchFragment.class.getSimpleName();
    private View mRoot;
    private GoogleMap mMap;
    private FusedLocationProviderApi location;
    private GoogleApiClient googleApiClient;
    private LocationObject locationObject;
    private MapFragment mapFragment;
    private Geofence geofence;
    private String MY_LOCATION_ID = "MY_LOCATION";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
//        googleApiClient = new GoogleApiClient
//                .Builder(getApplicationContext())
//                .addConnectionCallbacks(this)
//                .addApi(Places.GEO_DATA_API)
//                .addApi(Places.PLACE_DETECTION_API)
//                .addApi(LocationServices.API)
//                .build();
//        googleApiClient.connect();
        //fusedLocationProviderApi = LocationServices.FusedLocationApi;
//        if(googleApiClient.isConnected()){
//            Log.d(TAG, "Was connected");
//         //   fusedLocationProviderApi.getLastLocation(googleApiClient);
//        } else {
//            Log.d(TAG, "Wasn't connected");
//            googleApiClient.connect();
//        }

    }

    @Override
    public void onStop() {
        super.onStop();
//        googleApiClient.disconnect();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        locationObject = new LocationObject(getApplicationContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_search, parent, false);
        mapFragment = (MapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return mRoot;


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LatLng currentLocation2 = new LatLng(locationObject.getmLatitude(), locationObject.getmLongitude()); //40.7128° N, -74.0059° W
        Log.d(TAG, "onConnected: " + locationObject.getmLatitude() + " " + locationObject.getmLongitude());
        if (locationObject.isLocationSet() == false) {
            mapFragment.getMapAsync(this);
        } else {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation2));
            mMap.moveCamera(CameraUpdateFactory.zoomTo(15));
            mMap.setMyLocationEnabled(true);
            Log.d(this.getClass().getSimpleName(), "onMapReady: ");
            geofence = new Geofence.Builder()
                    // Set the request ID of the geofence. This is a string to identify this
                    // geofence.
                    .setRequestId(MY_LOCATION_ID)

                    .setCircularRegion(
                            currentLocation2.latitude,
                            currentLocation2.longitude,
                            Constants.GEOFENCE_RADIUS_IN_METERS
                    )
                    .setExpirationDuration(Constants.GEOFENCE_EXPIRATION_IN_MILLISECONDS)
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                            Geofence.GEOFENCE_TRANSITION_EXIT)
                    .build();
        }
    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//        Log.d("no location", "onConnectionFailed: do something");
//    }
//
//    @Override
//    public void onConnected(@Nullable Bundle bundle) {
////        Log.d(this.getClass().getSimpleName(), "onConnected: ");
////        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
////            // TODO: Consider calling
////            Log.d("not allowed", "onConnected: shyt");
////            return;
////        }
////
////        location = LocationServices.FusedLocationApi;
////        LatLng currentLocation = new LatLng(location.getLastLocation(googleApiClient).getLatitude(), location.getLastLocation(googleApiClient).getLongitude());
////        LatLng currentLocation2 = new LatLng(locationObject.getmLatitude(), locationObject.getmLongitude()); //40.7128° N, -74.0059° W
////        Log.d("", "onConnected: " + location.getLastLocation(googleApiClient).getLatitude() + " " + location.getLastLocation(googleApiClient).getLongitude());
////        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation2));
////        mMap.moveCamera(CameraUpdateFactory.zoomTo(15));
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//        Log.d(this.getClass().getSimpleName(), "onConnectionSuspended: " + String.valueOf(i));
//    }
//}
}
