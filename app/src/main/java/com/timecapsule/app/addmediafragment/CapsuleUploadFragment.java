package com.timecapsule.app.addmediafragment;

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
import com.timecapsule.app.feedactivity.FeedActivity;

/**
 * Created by catwong on 3/14/17.
 */

public class CapsuleUploadFragment extends DialogFragment {

    private ImageView iv_time_capsule_upload;
    private TextView tv_capsule_uploaded;
    private View mRoot;


    public static CapsuleUploadFragment newInstance(String capsuleUpload) {
        CapsuleUploadFragment fragment = new CapsuleUploadFragment();
        Bundle args = new Bundle();
        args.putString("capsule upload", capsuleUpload);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_capsule_upload, parent, false);
        setViews();
        return mRoot;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void setViews (){
        iv_time_capsule_upload = (ImageView) mRoot.findViewById(R.id.iv_time_capsule_upload);
        iv_time_capsule_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoFeedActivity();
            }
        });
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(iv_time_capsule_upload);
        Glide.with(this)
                .load(R.drawable.gif_time_capsule)
                .crossFade()
                .into(imageViewTarget);
        tv_capsule_uploaded = (TextView) mRoot.findViewById(R.id.tv_capsule_uploaded);
        tv_capsule_uploaded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoFeedActivity();
            }
        });
    }

    private void gotoFeedActivity(){
        Intent intent = new Intent(getActivity(), FeedActivity.class);
        CapsuleUploadFragment.this.startActivity(intent);
    }

}
