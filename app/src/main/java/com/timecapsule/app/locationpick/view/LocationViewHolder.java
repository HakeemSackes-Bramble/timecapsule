package com.timecapsule.app.locationpick.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.timecapsule.app.R;
import com.timecapsule.app.locationpick.model.NearbyLocation;

/**
 * Created by catwong on 3/15/17.
 */

public class LocationViewHolder extends RecyclerView.ViewHolder {

    TextView tv_name;
    TextView tv_address;

    public LocationViewHolder(View itemView) {
        super(itemView);
        tv_name = (TextView) itemView.findViewById(R.id.tv_place_location_name);
        tv_address = (TextView) itemView.findViewById(R.id.tv_place_location_address);
    }


    public void bind(NearbyLocation location) {
        tv_name.setText(location.getName());
        tv_address.setText(location.getAddress());
    }
}
