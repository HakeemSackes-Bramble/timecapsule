package com.timecapsule.app.addmediafragment.cat_test;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.timecapsule.app.R;
import com.timecapsule.app.locationpick.PlacePickerFragmentActivity;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by catwong on 3/12/17.
 */

public class AddCapsuleLocationFragmentVideo extends DialogFragment {

    private View mRoot;
    private ImageView iv_gif_location;
    private ImageView iv_close_dialog;
    private TextView tv_add_location;

    public static AddCapsuleLocationFragmentCamera newInstance(String capsule) {
        AddCapsuleLocationFragmentCamera fragment = new AddCapsuleLocationFragmentCamera();
        Bundle args = new Bundle();
        args.putString("capsule", capsule);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_add_location, parent, false);
        setViews();
        setGif();
        setCloseDialog();
        goToAddLocation();
        return mRoot;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    private void setViews() {
        iv_gif_location = (ImageView) mRoot.findViewById(R.id.iv_gif_location);
        iv_close_dialog = (ImageView) mRoot.findViewById(R.id.iv_close_dialog);
        tv_add_location = (TextView) mRoot.findViewById(R.id.tv_add_location);
    }

    private void setGif() {
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(iv_gif_location);
        Glide.with(this)
                .load(R.drawable.giphy2)
                .crossFade()
                .into(imageViewTarget);
    }

    private void setCloseDialog() {
        iv_close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
    }

    private void goToAddLocation(){
        tv_add_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPlacePicker("video");
            }
        });

    }

    private void goToPlacePicker(){
        Intent intent = new Intent(getActivity(), PlacePickerFragmentActivity.class);
        AddCapsuleLocationFragmentVideo.this.startActivity(intent);
    }

    private void openPlacePicker(String mediaType) {
        // Create an explicit content Intent that starts the timePlacePickerActivity.
        Intent placepickerIntent = new Intent(getApplicationContext(), PlacePickerFragmentActivity.class);
        placepickerIntent.putExtra("key", mediaType);
        startActivity(placepickerIntent);
    }


}
