package com.timecapsule.app.searchfragment.hub_recycler_view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.timecapsule.app.R;
import com.timecapsule.app.profilefragment.model.Capsule;

/**
 * Created by hakeemsackes-bramble on 3/19/17.
 */

public class HubViewHolder extends RecyclerView.ViewHolder {

    private final TextView cardUserName;
    private final ImageView cardPhoto;

    public HubViewHolder(View itemView) {
        super(itemView);
        cardUserName = (TextView) itemView.findViewById(R.id.search_card_username);
        cardPhoto = (ImageView) itemView.findViewById(R.id.iv_search_card_photo);
    }
    public void bind(Capsule capsule, Context context){
        Picasso.with(context)
                .load(capsule.getStorageUrl())
                .resize(800,800)
                .centerCrop()
                .rotate(90f)
                .into(cardPhoto);
    }
}
