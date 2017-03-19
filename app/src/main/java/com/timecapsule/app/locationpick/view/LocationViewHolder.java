package com.timecapsule.app.locationpick.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.timecapsule.app.R;
import com.timecapsule.app.locationpick.controller.MediaListener;
import com.timecapsule.app.locationpick.model.NearbyLocation;

/**
 * Created by catwong on 3/15/17.
 */

public class LocationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView tv_name;
    private TextView tv_address;
    private String mediaType;
    private double locationLat;
    private double locationLong;
    private String address;
    private Context context;
    private MediaListener listener;


    public LocationViewHolder(View itemView, String mediaType, MediaListener listener) {
        super(itemView);
        Log.d("tag", "LocationViewHolder: " + mediaType);
        itemView.setClickable(true);
        itemView.setOnClickListener(this);
        this.mediaType = mediaType;
        this.listener = listener;
        tv_name = (TextView) itemView.findViewById(R.id.tv_place_location_name);
        tv_address = (TextView) itemView.findViewById(R.id.tv_place_location_address);
    }


    public void bind(NearbyLocation location) {
        tv_name.setText(location.getName());
        tv_name.setOnClickListener(this);
        tv_address.setText(location.getAddress());
        tv_address.setOnClickListener(this);
        this.locationLat = Double.valueOf(location.getLatlong().split(",")[0].split("\\(")[1]);
        this.locationLong = Double.valueOf(location.getLatlong().split(",")[1].replace(")", ""));
    }


    @Override
    public void onClick(View v) {
        Toast.makeText(v.getContext(), tv_name.getText(), Toast.LENGTH_SHORT).show();
        context = v.getContext();
        Intent intent = new Intent();
//      set Fragmentclass Arguments
        intent.putExtra("keyMediaType", mediaType);
        intent.putExtra("keyLocationLat", locationLat);
        intent.putExtra("keyLocationLong", locationLong);
        intent.putExtra("keyAddress", address);
        openMedia(mediaType,intent);
        listener.setLatLongValues(locationLat,locationLong);
    }

    private void openMedia(String mediaType,Intent intent) {
        switch (mediaType) {
            case "camera":
                intent.setAction(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                listener.goToCamera(intent);
                break;
            case "video":
                intent.setAction(android.provider.MediaStore.ACTION_VIDEO_CAPTURE);
                listener.goToVideo(intent);
                break;
            case "audio":
                listener.goToAudio();
                break;
        }
    }
}



