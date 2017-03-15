package com.timecapsule.app.addmediafragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by tarynking on 3/11/17.
 */

public class GoToMedia extends AppCompatActivity {

    private static final int TAKE_PICTURE = 200;
    private static final int CAPTURE_VIDEO = 201;
    private View mRoot;
    private AudioFragment audioFragment;
    private String mCurrentPhotoPath;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private StorageReference imagesRef;
    private UploadTask uploadTask;
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
        mediaType = getIntent().getExtras().getString("keyMediaType");
        locationLat = getIntent().getExtras().getDouble("keyLocationLat");
        locationLong = getIntent().getExtras().getDouble("keyLocationLong");
        address = getIntent().getExtras().getString("keyAddress");
        openMedia(mediaType);

    }

    private void openMedia(String mediaType) {
        switch (mediaType) {
            case "camera":
                goToNativeCamera();
                break;
            case "video":
                goToNativeVideo();
                break;
            case "audio":
                goToAudio();
                break;
        }
    }

    private void goToNativeCamera() {
        Intent capture = new Intent(
                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(capture, TAKE_PICTURE);
    }


    private void goToAudio() {
        android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        audioFragment = AudioFragment.newInstance("Audio");
        audioFragment.show(ft, "audio");
    }


    public void goToNativeVideo() {
        Intent record = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(record, CAPTURE_VIDEO);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        Bundle extras = data.getExtras();
                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] dataBAOS = baos.toByteArray();
                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                        String imageFileName = "JPEG_" + timeStamp + "_";
                        String firebaseReference = imageFileName.concat(".jpg");
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
                            }
                        });
                    }
                }
        }
    }
}