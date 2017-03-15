package com.timecapsule.app.locationpick.controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.timecapsule.app.R;
import com.timecapsule.app.locationpick.model.NearbyLocation;
import com.timecapsule.app.locationpick.view.LocationViewHolder;

import java.util.List;

/**
 * Created by catwong on 3/15/17.
 */

public class LocationAdapter extends RecyclerView.Adapter<LocationViewHolder> {

    private Context context;
    private List<NearbyLocation> nearbyLocationList;


    public LocationAdapter(){};

    public LocationAdapter(Context context, List<NearbyLocation> nearbyLocationList) {
        this.context = context;
        this.nearbyLocationList = nearbyLocationList;
    }

    @Override
    public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View childview = inflater.inflate(R.layout.holder_location, parent, false);
        return new LocationViewHolder(childview);
    }

    @Override
    public void onBindViewHolder(LocationViewHolder holder, int position) {
        holder.bind(nearbyLocationList.get(position));
    }

    @Override
    public int getItemCount() {
        return nearbyLocationList.size();
    }

    public void addPlaces(List<NearbyLocation> nearbyPlaces) {
        nearbyLocationList.clear();
        nearbyLocationList.addAll(nearbyPlaces);
        notifyDataSetChanged();
    }

    public void removeNearByPlaces() {
        nearbyLocationList.clear();
        notifyDataSetChanged();
    }


    public void setData(List<NearbyLocation> data) {
        this.nearbyLocationList = data;
    }
}
