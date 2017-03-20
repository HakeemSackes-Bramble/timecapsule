package com.timecapsule.app.searchfragment;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.timecapsule.app.R;
import com.timecapsule.app.profilefragment.model.Capsule;
import com.timecapsule.app.searchfragment.hub_recycler_view.HubAdapter;

import java.util.ArrayList;

/**
 * Created by hakeemsackes-bramble on 3/19/17.
 */

public class TimeCapsuleHubFragment extends DialogFragment {

    private View mRoot;
    private RecyclerView rvlist;
    private ArrayList<Capsule> capsules;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_time_capsulehub,container);
        rvlist = (RecyclerView) mRoot.findViewById(R.id.time_capsule_hub_recyclerView);
        rvlist.setAdapter(new HubAdapter(capsules, getActivity()));
        rvlist.setLayoutManager(new LinearLayoutManager(getActivity()));
        return mRoot;
    }

    public void setCapsules(ArrayList<Capsule> capsules) {
        this.capsules = capsules;
    }
}

//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Log.d("GO TO MEDIA", "onActivityResult: ");
//        String imageFileName;
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        imageFileName = "JPEG_" + timeStamp + "_";
//        String firebaseReference = imageFileName.concat(".jpg");
//        switch (requestCode) {
//            case TAKE_PICTURE:
//                String mPath = Environment.getExternalStorageDirectory().toString() + "/Pictures/" + imageFileName + ".jpg";
//                File imageFile = new File(mPath);
//                Log.d(TAG, "onActivityResult: "+ imageFile.toString());
//                Uri outputFileUri = Uri.fromFile(imageFile);
//
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
//                if (resultCode == RESULT_OK) {
//                    mProgress.setMessage("Uploading Photo");
//                    mProgress.setIcon(R.drawable.time_capsule_logo12);
//                    mProgress.show();
//                    if (data != null) {
//                        Bundle extras = data.getExtras();
//                        Bitmap imageBitmap = (Bitmap) extras.get("data");
//                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//                        byte[] dataBAOS = baos.toByteArray();
//
//                        imagesRef = imagesRef.child(firebaseReference);
//                        StorageReference newImageRef = storageReference.child("images/".concat(firebaseReference));
////                        newImageRef.getName().equals(newImageRef.getName());
////                        newImageRef.getPath().equals(newImageRef.getPath());
////                        UploadTask uploadTask = imagesRef.putBytes(dataBAOS);
////                        uploadTask.addOnFailureListener(new OnFailureListener() {
////                            @Override
////                            public void onFailure(@NonNull Exception exception) {
////                                // Handle unsuccessful uploads
////                            }
////                        })
//                        Uri uri = data.getData();
//                        newImageRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                            @Override
//                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
//                                @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
//
//                                addUrlToDatabase(downloadUrl);
//                                mProgress.dismiss();
//                                goToCapsuleUploadFragment("capsule upload");
//                            }
//                        });
//                    }
//                }
//                break;
//            case CAPTURE_VIDEO:
//                if (resultCode == RESULT_OK) {
//                    mProgress.setMessage("uploading video...");
//                    mProgress.show();
//                    if (data != null) {
//                    }
//                }
//                break;
//        }
//    }
