package com.timecapsule.app.feedactivity.controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.timecapsule.app.R;
import com.timecapsule.app.feedactivity.model.ImageModel;
import com.timecapsule.app.feedactivity.view.FeedViewHolder;
import com.timecapsule.app.profilefragment.model.Capsule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by catwong on 3/3/17.
 */

public class FeedAdapter extends RecyclerView.Adapter<FeedViewHolder> {

    private List<ImageModel> mImageList;
    ArrayList<Capsule> capsules = new ArrayList<>();
    private Context context;

    public FeedAdapter(ArrayList<Capsule> queriedCapsules, Context context) {
        this.capsules = capsules;
        this.context = context;
    }

//    public FeedAdapter(ArrayList<ImageModel> mImageList) {
//        this.mImageList = mImageList;
//    }


    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View childview = inflater.inflate(R.layout.holder_feed, parent, false);
        return new FeedViewHolder(childview);
    }

    @Override
    public void onBindViewHolder(FeedViewHolder holder, int position) {
//        holder.bind(mImageList.get(position));
        holder.bind(capsules.get(position), position, context);
    }

    @Override
    public int getItemCount() {
//        return mImageList.size();
        return capsules.size();
    }
}
