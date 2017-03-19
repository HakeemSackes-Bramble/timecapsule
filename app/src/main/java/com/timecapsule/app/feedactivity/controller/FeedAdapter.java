package com.timecapsule.app.feedactivity.controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.timecapsule.app.R;
import com.timecapsule.app.feedactivity.model.ImageModel;
import com.timecapsule.app.feedactivity.view.FeedViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by catwong on 3/3/17.
 */

public class FeedAdapter extends RecyclerView.Adapter<FeedViewHolder> {

    private List<ImageModel> mImageList;


    public FeedAdapter(ArrayList<ImageModel> mImageList) {
        this.mImageList = mImageList;
    }


    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View childview = inflater.inflate(R.layout.holder_feed, parent, false);
        return new FeedViewHolder(childview);
    }

    @Override
    public void onBindViewHolder(FeedViewHolder holder, int position) {
        holder.bind(mImageList.get(position));
    }

    @Override
    public int getItemCount() {
        return mImageList.size();
    }
}
