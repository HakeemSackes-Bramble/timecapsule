package com.timecapsule.app.feedactivity.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.timecapsule.app.R;
import com.timecapsule.app.feedactivity.model.ImageModel;
import com.timecapsule.app.profilefragment.model.Capsule;

/**
 * Created by catwong on 3/3/17.
 */

public class FeedViewHolder extends RecyclerView.ViewHolder {

    private ImageView iv_feed_photo;
    private TextView image_location;
    private TextView image_description;
    private ImageView iv_feed_user_photo;
    private TextView tv_feed_username;
    private TextView tv_feed_address;
    private TextView tv_feed_date;


    public FeedViewHolder(View itemView) {
        super(itemView);
        iv_feed_photo = (ImageView) itemView.findViewById(R.id.iv_feed_photo);
        image_location = (TextView) itemView.findViewById(R.id.tv_feed_photo_location);
        image_description = (TextView) itemView.findViewById(R.id.tv_photo_description);
        iv_feed_user_photo = (ImageView) itemView.findViewById(R.id.feed_card_user_photo);
        iv_feed_photo = (ImageView) itemView.findViewById(R.id.iv_feed_photo);
        tv_feed_username = (TextView) itemView.findViewById(R.id.feed_card_username);
        tv_feed_address = (TextView) itemView.findViewById(R.id.tv_feed_photo_location);
        tv_feed_date = (TextView) itemView.findViewById(R.id.feed_card_date);
    }

    public void bind(ImageModel imageModel) {
        iv_feed_photo.setImageResource(imageModel.getImageId());
        image_location.setText(imageModel.getLocationId());
        image_description.setText(imageModel.getAboutText());
    }

    public void bind(Capsule capsule, int position, Context context) {
        Picasso.with(context)
                .load(capsule.getStorageUrl())
                .resize(200, 200)
                .into(iv_feed_photo);
        tv_feed_date.setText(capsule.getDate());
        tv_feed_address.setText(capsule.getAddress());
    }
}
