package com.timecapsule.app.profilefragment.controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.timecapsule.app.R;
import com.timecapsule.app.profilefragment.model.Capsule;
import com.timecapsule.app.profilefragment.view.ProfileCreatedViewHolder;

import java.util.ArrayList;

/**
 * Created by catwong on 3/10/17.
 */

public class ProfileCapsulesCreatedAdapter extends RecyclerView.Adapter<ProfileCreatedViewHolder> {

    ArrayList<Capsule> capsules = new ArrayList<>();
    private Context context;

    public ProfileCapsulesCreatedAdapter(ArrayList<Capsule> capsules, Context context) {
        this.capsules = capsules;
        this.context = context;
    }

    @Override
    public ProfileCreatedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View childview = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_profile_created, parent, false);
        return new ProfileCreatedViewHolder(childview);
    }

    @Override
    public void onBindViewHolder(ProfileCreatedViewHolder holder, int position) {
        holder.bind(capsules.get(position),position, context);
    }

    @Override
    public int getItemCount() {

        return capsules.size();
    }
}
