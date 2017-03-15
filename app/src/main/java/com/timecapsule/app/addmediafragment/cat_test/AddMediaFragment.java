package com.timecapsule.app.addmediafragment.cat_test;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.timecapsule.app.R;
import com.timecapsule.app.addmediafragment.AudioFragment;
import com.timecapsule.app.locationpick.PlacePickerFragmentActivity;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import static android.app.Activity.RESULT_OK;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by catwong on 3/4/17.
 */

public class AddMediaFragment extends Fragment {

    private static final int TAKE_PICTURE = 200;
    private static final int CAPTURE_VIDEO = 201;
    private View mRoot;
    private ImageView iv_camera;
    private ImageView iv_audio;
    private ImageView iv_videocam;
    private String mCurrentPhotoPath;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private StorageReference imagesRef;
    private UploadTask uploadTask;
    private File image;
    private ProgressDialog mProgress;
    private String mediaType;
    private double locationLat;
    private double locationLong;
    private String address;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        imagesRef = storageReference.child("images");
        mProgress = new ProgressDialog(getActivity());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_add_media, parent, false);
        setViews();
        clickCamera();
        clickAudio();
        clickVideocam();
        return mRoot;
    }

    private void setViews() {
        iv_camera = (ImageView) mRoot.findViewById(R.id.iv_camera);
        iv_audio = (ImageView) mRoot.findViewById(R.id.iv_audio);
        iv_videocam = (ImageView) mRoot.findViewById(R.id.iv_videocam);
    }

    public void clickCamera() {
        iv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPlacePicker("camera");
                //goToNativeCamera();
            }
        });
    }

    public void clickAudio() {
        iv_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPlacePicker("audio");
//                goToAudio();
            }
        });
    }

    private void goToAudio() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container_main, new AudioFragment())
                .commit();
    }

    public void clickVideocam() {
        iv_videocam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPlacePicker("video");
//                goToNativeVideo();
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == RESULT_OK) {
                    mProgress.setMessage("uploading photo...");
                    mProgress.show();

                    if (data != null) {
                        Bundle extras = data.getExtras();
                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                        byte[] dataBAOS = baos.toByteArray();
                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                        String imageFileName = "PNG_" + timeStamp + "_";
                        String firebaseReference = imageFileName.concat(".png");
                        imagesRef = imagesRef.child(firebaseReference);
                        StorageReference newImageRef = storageReference.child("images/".concat(firebaseReference));
                        newImageRef.getName().equals(newImageRef.getName());
                        newImageRef.getPath().equals(newImageRef.getPath());
                        UploadTask uploadTask = imagesRef.putBytes(dataBAOS);
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle unsuccessful uploads
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                                @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                mProgress.dismiss();
                            }
                        });
                    }
                }

        }
    }


    public File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        String firebaseReference = imageFileName.concat(".jpg");
        imagesRef = imagesRef.child(firebaseReference);
        StorageReference newImageRef = storageReference.child("images/".concat(firebaseReference));
        newImageRef.getName().equals(newImageRef.getName());
        newImageRef.getPath().equals(newImageRef.getPath());

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;

    }


    private void uploadImage() {
        Uri file = Uri.fromFile(image);
        StorageReference imageRef = storageReference.child("images/" + file.getLastPathSegment());
        uploadTask = imageRef.putFile(file);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
            }
        });
    }

    private void openPlacePicker(String mediaType) {
        // Create an explicit content Intent that starts the timePlacePickerActivity.
        Intent placepickerIntent = new Intent(getApplicationContext(), PlacePickerFragmentActivity.class);
        placepickerIntent.putExtra("key", mediaType);
        startActivity(placepickerIntent);
    }

}
