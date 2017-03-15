package com.timecapsule.app.feedactivity;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.facebook.FacebookSdk;
import com.facebook.share.model.AppInviteContent;
import com.facebook.share.widget.AppInviteDialog;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.timecapsule.app.NotificationsFragment;
import com.timecapsule.app.R;
import com.timecapsule.app.SearchFragment;
import com.timecapsule.app.addmediafragment.AddCapsuleLocationFragment;
import com.timecapsule.app.addmediafragment.cat_test.AddCapsuleLocationFragmentCamera;
import com.timecapsule.app.profilefragment.ProfileFragment;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class FeedActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static final int REQUEST_LOCATION = 201;
    private static final int REQUEST_CAMERA_PERMISSION = 203;
    private BottomNavigationView bottomNavigationView;
    private ImageView iv_add_friend;
    private FloatingActionsMenu fab_add_media;
    private FloatingActionButton fab_photo;
    private FloatingActionButton fab_audio;
    private FloatingActionButton fab_videocam;
    private String mCurrentPhotoPath;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private StorageReference imagesRef;
    private GoogleApiClient googleApiClient;
    private AddCapsuleLocationFragment addCapsuleLocationFragment;
    private Fragment timePlacePickerFragment;
    private String mediaType;
    private AddCapsuleLocationFragmentCamera addCapsuleLocationFragmentCamera;


    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        imagesRef = storageReference.child("images");
        FacebookSdk.sdkInitialize(FacebookSdk.getApplicationContext());
        requestLocationPermission();
        requestCameraPemission();
        requestAudioPermission();
        setViews();
        setBottomNavButtons();
        clickCamera();
        clickAudio();
        clickVideocam();

        timePlacePickerFragment = new Fragment();
        timePlacePickerFragment.setArguments(getIntent().getExtras());

        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.container_main, new FeedFragment())
                    .commit();
        }

        googleApiClient = new GoogleApiClient
                .Builder(getApplicationContext())
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addApi(LocationServices.API)
                .build();
    }


    private void setViews() {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        iv_add_friend = (ImageView) findViewById(R.id.iv_add_friend);
        fab_photo = (FloatingActionButton) findViewById(R.id.fab_photo);
        fab_audio = (FloatingActionButton) findViewById(R.id.fab_audio);
        fab_videocam = (FloatingActionButton) findViewById(R.id.fab_videocam);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_add_friend:
                setAddFriend();
        }
    }


    private void goToAddLocation(String mediaType) {
        android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        addCapsuleLocationFragment = AddCapsuleLocationFragment.newInstance(mediaType);
        addCapsuleLocationFragment.show(ft, "Location");
    }


    private void clickCamera() {
        fab_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaType = "camera";
                goToAddLocation("camera");
            }
        });
    }

    private void clickAudio() {
        fab_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaType = "audio";
                goToAddLocation("audio");

            }
        });

    }

    private void clickVideocam() {
        fab_videocam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaType = "video";
                goToAddLocation("video");

            }
        });
    }

    public void setAddFriend() {
        String appLinkUrl;
        String previewImageUrl;

        appLinkUrl = "https://fb.me/1777539359241152";
        previewImageUrl = "https://sarahasousa.files.wordpress.com/2014/11/time-capsule.gif";

        AppInviteContent content = new AppInviteContent.Builder()
                .setApplinkUrl(appLinkUrl)
                .setPreviewImageUrl(previewImageUrl)
                .build();
        AppInviteDialog.show(this, content);
    }


    private void setBottomNavButtons() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_feed:
                        setFeedFragment();
                        break;
                    case R.id.action_search:
                        setSearchFragment();
                        break;
                    case R.id.action_notifications:
                        setNotificationsFragment();
                        break;
                    case R.id.action_profile:
                        setProfileFragment();
                        break;
                }
                return true;
            }
        });
    }

    private void setFeedFragment() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container_main, new FeedFragment())
                .addToBackStack("feed")
                .commit();

        getFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
            }
        });
    }


    private void setSearchFragment() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container_main, new SearchFragment())
                .addToBackStack("search")
                .commit();
    }


    private void setNotificationsFragment() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container_main, new NotificationsFragment())
                .addToBackStack("notifications")
                .commit();
    }


    private void setProfileFragment() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container_main, new ProfileFragment())
                .addToBackStack("profile")
                .commit();
    }

    private void requestCameraPemission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
            Log.d("damn", "openCamera: ");
            return;
        }
    }

    private void requestAudioPermission() {
        ActivityCompat.requestPermissions(FeedActivity.this, new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, REQUEST_RECORD_AUDIO_PERMISSION);
    }

    private void requestLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_RECORD_AUDIO_PERMISSION:
                if (grantResults.length > 0) {
                    boolean StoragePermission = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission = grantResults[1] ==
                            PackageManager.PERMISSION_GRANTED;
                }
                break;

            case REQUEST_LOCATION:
                if (grantResults.length == 1
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // We can now safely use the API we requested access to
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                } else {
                    // Permission was denied or request was cancelled
                }
                break;
        }

    }


}
