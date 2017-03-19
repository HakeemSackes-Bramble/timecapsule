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


    private final TextView cardDate;
    private final TextView cardUserName;
    private final ImageView cardPhoto;
    private final TextView cardUserNameComment;

    public HubViewHolder(View itemView) {
        super(itemView);
        cardDate = (TextView) itemView.findViewById(R.id.feed_card_date);
        cardUserName = (TextView) itemView.findViewById(R.id.feed_card_username);
        cardPhoto = (ImageView) itemView.findViewById(R.id.iv_feed_photo);
        cardUserNameComment= (TextView) itemView.findViewById(R.id.tv_feed_username_comment);
    }
    public void bind(Capsule capsule, Context context){
        cardDate.setText(capsule.getDate());
        Picasso.with(context).load(capsule.getStorageUrl()).into(cardPhoto);


    }
}
