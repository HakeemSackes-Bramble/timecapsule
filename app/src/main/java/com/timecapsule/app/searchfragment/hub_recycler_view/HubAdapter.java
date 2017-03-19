package com.timecapsule.app.searchfragment.hub_recycler_view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.timecapsule.app.R;
import com.timecapsule.app.profilefragment.model.Capsule;

import java.util.ArrayList;

/**
 * Created by hakeemsackes-bramble on 3/19/17.
 */

public class HubAdapter extends RecyclerView.Adapter<HubViewHolder> {


    ArrayList<Capsule> capsules;
    private Context context;

    public HubAdapter(ArrayList<Capsule> capsules, Context context) {
        this.capsules = capsules;
        this.context = context;
    }

    @Override
    public HubViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View childview = inflater.inflate(R.layout.hub_capsule_rv_layout, parent, false);

        return new HubViewHolder(childview);
    }

    @Override
    public void onBindViewHolder(HubViewHolder holder, int position) {
        holder.bind(capsules.get(position),context);
    }

    @Override
    public int getItemCount() {
        return capsules.size();
    }
}
