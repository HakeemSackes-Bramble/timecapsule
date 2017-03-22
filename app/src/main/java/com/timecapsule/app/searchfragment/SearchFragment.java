package com.timecapsule.app.searchfragment;

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
import android.widget.Toast;

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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.timecapsule.app.R;
import com.timecapsule.app.geofence.Constants;
import com.timecapsule.app.googleplaces.LocationObject;
import com.timecapsule.app.profilefragment.model.Capsule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by catwong on 3/4/17.
 */

public class SearchFragment extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.OnConnectionFailedListener, ResultCallback<Status> {

    private static final String TAG = SearchFragment.class.getSimpleName();
    protected Set<Geofence> mGeofenceSet;
    private View mRoot;
    private GoogleMap mMap;
    private FusedLocationProviderApi location;
    private GoogleApiClient googleApiClient;
    private LocationObject locationObject;
    private MapFragment mapFragment;
    private String MY_LOCATION_ID = "MY_LOCATION";
    private FirebaseDatabase fireBsaseDB;
    private DatabaseReference databasereff;
    private HashMap<LatLng, List<Capsule>> timeCapsuleHubs;
    private TimeCapsuleHubFragment hubFragment;
    private Set<Geofence> mNewGeofenceSet;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mGeofenceSet = new HashSet<>();
        mNewGeofenceSet = new HashSet<>();
        locationObject = new LocationObject(getApplicationContext());
        fireBsaseDB = FirebaseDatabase.getInstance();
        databasereff = fireBsaseDB.getReferenceFromUrl("https://timecapsule-8b809.firebaseio.com/");
        timeCapsuleHubs = new HashMap<>();
        capsuleDBReference();
        if (!locationObject.getmGoogleApiClient().isConnecting() || !locationObject.getmGoogleApiClient().isConnected()) {
            locationObject.getmGoogleApiClient().connect();
        }


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
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
    public void onStop() {
        super.onStop();
        if (locationObject.getmGoogleApiClient().isConnecting() || locationObject.getmGoogleApiClient().isConnected()) {
            locationObject.getmGoogleApiClient().disconnect();
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
        if (!locationObject.isLocationSet()) {
            mapFragment.getMapAsync(this);
        } else {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation2));
            mMap.moveCamera(CameraUpdateFactory.zoomTo(15));
            mMap.setMyLocationEnabled(true);
            addMapMarker(timeCapsuleHubs, mMap);
            addGeofences();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onResult(@NonNull Status status) {
    }

    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(new ArrayList<>(mNewGeofenceSet));
        mNewGeofenceSet.clear();
        return builder.build();
    }

    private PendingIntent getGeofencePendingIntent() {
        Intent intent = new Intent(getApplicationContext(), TransitionIntentService.class);
        return PendingIntent.getService(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    void addGeofences() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            Log.d(TAG, "addGeofencesONE: this is a geofence");
            return;
        }
        LocationServices.GeofencingApi.addGeofences(googleApiClient, getGeofencingRequest(), getGeofencePendingIntent()).setResultCallback(this);
        Log.d(TAG, "addGeofences: this is a geofence");
    }

    private void populateGeofenceList() {

        for (LatLng entry : timeCapsuleHubs.keySet()) {
            if (mGeofenceSet.contains(entry)) {
                continue;
            } else {
                mNewGeofenceSet.add(new Geofence.Builder()
                        .setRequestId("" + entry.latitude + entry.longitude)
                        .setCircularRegion(
                                entry.latitude,
                                entry.longitude,
                                50
                        )
                        .setExpirationDuration(Constants.GEOFENCE_EXPIRATION_IN_MILLISECONDS)
                        .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                                Geofence.GEOFENCE_TRANSITION_EXIT).build());

                mGeofenceSet.add(new Geofence.Builder()
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
        }
        Log.d(TAG, "populateGeofenceList: " + mGeofenceSet.toString());
    }

    private void addMapMarker(final HashMap<LatLng, List<Capsule>> timeCapsuleHub, final GoogleMap map) {
        for (LatLng capsule : timeCapsuleHub.keySet()) {
            map.addMarker(new MarkerOptions().
                    position(capsule).title(timeCapsuleHub.get(capsule).get(0).getAddress().split(",")[0])
                    .snippet(timeCapsuleHub.get(capsule).size() + " Time Capsules here"));

        }
        Log.d(TAG, "addMapMarker: ");
        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

                LatLng myloc = new LatLng(locationObject.getmLatitude(), locationObject.getmLongitude());
                Log.d(TAG, "onInfoWindowClick: " + myloc.toString() + marker.getPosition());
                Double dist = distanceFromPoint(marker.getPosition(), myloc);
                if (dist <= (1 / 69d)) {
                    android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                    hubFragment = new TimeCapsuleHubFragment();
                    hubFragment.setCapsules((ArrayList<Capsule>) timeCapsuleHub.get(marker.getPosition()));
                    hubFragment.show(ft, "nearbyCapsules");
                } else {
                    Toast.makeText(getActivity(), "capsule is too far to view", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private double distanceFromPoint(LatLng a, LatLng b) {
        Log.d(TAG, "distanceFromPoint: "+ Math.sqrt(Math.pow(a.longitude - b.longitude, 2) + Math.pow(a.latitude - b.latitude, 2)));
        return Math.sqrt(Math.pow(a.longitude - b.longitude, 2) + Math.pow(a.latitude - b.latitude, 2));

    }

    private void capsuleDBReference() {

        final DatabaseReference allCapsules = databasereff.child("capsules"); //FirebaseAuth.getInstance().getCurrentUser().getUid());

        allCapsules.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> timeCapsules = dataSnapshot.getChildren();
                for (DataSnapshot snapShot : timeCapsules) {
                    //Log.d(TAG, "onDataChange: " + snapShot.getValue());
                    LatLng spot = new LatLng((double) snapShot.child("positionLat").getValue(), (double) snapShot.child("positionLong").getValue());
                    Capsule moment;
                    if (snapShot.getValue().toString().split(",").length == 5) {
                        moment = new Capsule(
                                (String) snapShot.child("userId").getValue(),
                                (String) snapShot.child("storageUrl").getValue(),
                                (double) snapShot.child("positionLat").getValue(),
                                (double) snapShot.child("positionLong").getValue(),
                                (String) snapShot.child("date").getValue());
                    } else {
                        moment = new Capsule(
                                (String) snapShot.child("userId").getValue(),
                                (String) snapShot.child("storageUrl").getValue(),
                                (double) snapShot.child("positionLat").getValue(),
                                (double) snapShot.child("positionLong").getValue(),
                                (String) snapShot.child("date").getValue(),
                                (String) snapShot.child("address").getValue(),
                                (String) snapShot.child("userName").getValue());
                    }
                    // if (snapShot.getValue().toString().split(",").length == 5) {
                    if (timeCapsuleHubs.containsKey(spot)) {
                        timeCapsuleHubs.get(spot).add(moment);
                    } else {
                        timeCapsuleHubs.put(spot, new ArrayList<Capsule>());
                        timeCapsuleHubs.get(spot).add(moment);
                    }

                }
                populateGeofenceList();
                Log.d(TAG, "onDataChange: ");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
}
