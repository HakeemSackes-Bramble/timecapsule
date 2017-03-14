package com.timecapsule.app.feedactivity;

import android.Manifest;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.share.model.AppInviteContent;
import com.facebook.share.widget.AppInviteDialog;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.timecapsule.app.NotificationsFragment;
import com.timecapsule.app.R;
import com.timecapsule.app.SearchFragment;
import com.timecapsule.app.addmediafragment.AddCapsuleLocationFragment;
import com.timecapsule.app.addmediafragment.AudioFragment;
import com.timecapsule.app.addmediafragment.GoToMedia;
import com.timecapsule.app.addmediafragment.cat_test.AddCapsuleLocationFragmentCamera;
import com.timecapsule.app.profilefragment.ProfileFragment;

import java.io.File;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class FeedActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static final int REQUEST_LOCATION = 201;
    private static final int REQUEST_CAMERA_PERMISSION = 203;
    private static final int TAKE_PICTURE = 204;
    private static final int CAPTURE_VIDEO = 205;
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
    private UploadTask uploadTask;
    private File image;
    private AudioFragment audioFragment;
    private Fragment timePlacePickerFragment;
    private AddCapsuleLocationFragmentCamera addCapsuleLocationFragmentCamera;
    private ProgressDialog mProgress;
    private String mediaType;

    int PLACE_PICKER_REQUEST = 1;


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
        mProgress = new ProgressDialog(this);

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
        timePlacePickerFragment.setArguments( getIntent().getExtras() );

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

    private void goToAddLocationCamera() {
        android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        addCapsuleLocationFragmentCamera = AddCapsuleLocationFragmentCamera.newInstance("Add Capsule Location");
        addCapsuleLocationFragmentCamera.show(ft, "Location");
    }

    private void goToAddLocationAudio() {
        android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        addCapsuleLocationFragmentCamera = AddCapsuleLocationFragmentCamera.newInstance("Add Capsule Location");
        addCapsuleLocationFragmentCamera.show(ft, "Location");
    }

    private void goToAddLocationVideo() {
        android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        addCapsuleLocationFragmentCamera = AddCapsuleLocationFragmentCamera.newInstance("Add Capsule Location");
        addCapsuleLocationFragmentCamera.show(ft, "Location");
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

    private void goToNativeCamera() {
        Intent capture = new Intent(
                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(capture, TAKE_PICTURE);
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

    private void goToAudio() {
        android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        audioFragment = AudioFragment.newInstance("Audio");
        audioFragment.show(ft, "audio");
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

    public void goToNativeVideo() {
        Intent record = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(record, CAPTURE_VIDEO);
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        switch (requestCode) {
//            case TAKE_PICTURE:
//                if (resultCode == RESULT_OK) {
//                    mProgress.setMessage("uploading photo...");
//                    mProgress.show();
//                    if (data != null) {
//                        Bundle extras = data.getExtras();
//                        Bitmap imageBitmap = (Bitmap) extras.get("data");
//                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//                        byte[] dataBAOS = baos.toByteArray();
//                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//                        String imageFileName = "JPEG_" + timeStamp + "_";
//                        String firebaseReference = imageFileName.concat(".jpg");
//                        imagesRef = imagesRef.child(firebaseReference);
//                        StorageReference newImageRef = storageReference.child("images/".concat(firebaseReference));
//                        newImageRef.getName().equals(newImageRef.getName());
//                        newImageRef.getPath().equals(newImageRef.getPath());
//                        UploadTask uploadTask = imagesRef.putBytes(dataBAOS);
//                        uploadTask.addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception exception) {
//                                // Handle unsuccessful uploads
//                            }
//                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                            @Override
//                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
//                                @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
//                                addUrlToDatabase(downloadUrl);
//                                mProgress.dismiss();
//
//                            }
//                        });
//                    }
//                }
//        }
//    }


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
                .commit();
    }

    private void setSearchFragment() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container_main, new SearchFragment())
                .commit();
    }


    private void setNotificationsFragment() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container_main, new NotificationsFragment())
                .commit();
    }


    private void setProfileFragment() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container_main, new ProfileFragment())
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
    private void addUrlToDatabase(Uri uri){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("capsules");
        myRef.setValue(uri.toString());
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

    private void TimePlacePickerMethod (){



            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            Intent intent;
            try {
                intent = builder.build(this);
                startActivityForResult(intent, PLACE_PICKER_REQUEST);
            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();
            } catch (GooglePlayServicesNotAvailableException e) {
                Log.d(this.getClass().getSimpleName(), "onClick: ");
                e.printStackTrace();
            }


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d("TIMEPLACE", "onActivityResult: ");
        Toast.makeText(this, "place selected" + resultCode, Toast.LENGTH_SHORT).show();
        if (requestCode == PLACE_PICKER_REQUEST) {
            Toast.makeText(this, "if statement" + resultCode, Toast.LENGTH_SHORT).show();
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "second if statement" + resultCode, Toast.LENGTH_SHORT).show();
                Place place = PlacePicker.getPlace(this, data);
                LatLng locationLatLng = place.getLatLng();
                String address = (String) place.getAddress();

                double locationLat = locationLatLng.latitude;
                double locationLong = locationLatLng.longitude;

                Intent gotoMediaIntent = new Intent(getApplicationContext(), GoToMedia.class);
                Bundle bundle = new Bundle();
                bundle.putString("key", "value");
//      set Fragmentclass Arguments
                gotoMediaIntent.putExtra("keyMediaType", mediaType);
                gotoMediaIntent.putExtra("keyLocationLat", locationLat);
                gotoMediaIntent.putExtra("keyLocationLong", locationLong);
                gotoMediaIntent.putExtra("keyAddress", address);
//                addCapsuleLocationFragment = new AddCapsuleLocationFragment();
//                addCapsuleLocationFragment.setArguments(bundle);
                startActivity(gotoMediaIntent);

            }
        }
    }


}
