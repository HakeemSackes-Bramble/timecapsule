package com.timecapsule.app.locationpick.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.timecapsule.app.R;
import com.timecapsule.app.addmediafragment.GoToMedia;
import com.timecapsule.app.locationpick.model.NearbyLocation;

/**
 * Created by catwong on 3/15/17.
 */

public class LocationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView tv_name;
    private TextView tv_address;
    public RadioButton radioButton;
    private String mediaType;
    private String locationLat;
    private String locationLong;
    private String address;


    public LocationViewHolder(View itemView) {
        super(itemView);
        itemView.setClickable(true);
        itemView.setOnClickListener(this);
        tv_name = (TextView) itemView.findViewById(R.id.tv_place_location_name);
        tv_address = (TextView) itemView.findViewById(R.id.tv_place_location_address);
    }


    public void bind(NearbyLocation location) {
        tv_name.setText(location.getName());
        tv_name.setOnClickListener(this);
        tv_address.setText(location.getAddress());
        tv_address.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Toast.makeText(v.getContext(), tv_name.getText(), Toast.LENGTH_SHORT).show();
       /// GoToMedia.Builder builder = new GoToMedia.Builder(mediaType);
        Intent intent = new Intent(v.getContext(), GoToMedia.class);
        Bundle bundle = new Bundle();
        bundle.putString("mediaType", mediaType);
       // bundle.putString("key", "value");
//      set Fragmentclass Arguments
        intent.putExtras(bundle);
        intent.putExtra("keyMediaType", mediaType);
        intent.putExtra("keyLocationLat", locationLat);
        intent.putExtra("keyLocationLong", locationLong);
        intent.putExtra("keyAddress", address);
        v.getContext().startActivity(intent);

    }



}



