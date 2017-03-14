package com.timecapsule.app;

import android.Manifest;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.timecapsule.app.geofence.Constants;
import com.timecapsule.app.geofence.GeofenceTransitionsIntentService;
import com.timecapsule.app.googleplaces.LocationObject;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by catwong on 3/4/17.
 */

public class SearchFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, ResultCallback<Status> {

    private static final String TAG = SearchFragment.class.getSimpleName();
    protected ArrayList<Geofence> mGeofenceList;
    private View mRoot;
    private GoogleMap mMap;
    private FusedLocationProviderApi location;
    private GoogleApiClient googleApiClient;
    private LocationObject locationObject;
    private MapFragment mapFragment;
    private String MY_LOCATION_ID = "MY_LOCATION";


    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mGeofenceList = new ArrayList<>();
        populateGeofenceList();
        googleApiClient = locationObject.getmGoogleApiClient();


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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_search, parent, false);
        mapFragment = new MapFragment();
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.container_map, mapFragment)
                .commit();
        mapFragment.getMapAsync(this);
        return mRoot;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }



    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    void addGeofences() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.GeofencingApi.addGeofences(googleApiClient, getGeofencingRequest(), getGeofencePendingIntent()).setResultCallback(this);
    }

    private void populateGeofenceList() {
        for (LatLng entry : new LatLng[]{new LatLng(40.742571, -73.935421)}) {
            mGeofenceList.add(new Geofence.Builder()
                    .setRequestId("" + entry.latitude + entry.longitude)
                    .setCircularRegion(
                            entry.latitude,
                            entry.longitude,
                            50
                    )
                    .setExpirationDuration(Constants.GEOFENCE_EXPIRATION_IN_MILLISECONDS)
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                            Geofence.GEOFENCE_TRANSITION_EXIT)
                    .build());
        }
        Log.d(TAG, "populateGeofenceList: " + mGeofenceList.toString());

    }

    @Override
    public void onStop() {
        super.onStop();
//        googleApiClient.disconnect();
        if (locationObject.getmGoogleApiClient().isConnecting() || locationObject.getmGoogleApiClient().isConnected()) {
            locationObject.getmGoogleApiClient().disconnect();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        locationObject = new LocationObject(getApplicationContext());
        if (!locationObject.getmGoogleApiClient().isConnecting() || !locationObject.getmGoogleApiClient().isConnected()) {
            locationObject.getmGoogleApiClient().connect();
        }

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

        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        addGeofences();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(mGeofenceList);
        return builder.build();
    }

    private PendingIntent getGeofencePendingIntent() {
        Intent intent = new Intent(getApplicationContext(), GeofenceTransitionsIntentService.class);
        return PendingIntent.getService(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    public void onResult(@NonNull Status status) {
        Log.d(TAG, "onResult: YEEEAAAAA");
    }
}
