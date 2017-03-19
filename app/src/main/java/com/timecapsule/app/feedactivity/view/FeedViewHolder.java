package com.timecapsule.app.feedactivity.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.timecapsule.app.R;
import com.timecapsule.app.feedactivity.model.ImageModel;

/**
 * Created by catwong on 3/3/17.
 */

public class FeedViewHolder extends RecyclerView.ViewHolder {

    private ImageView feed_image;
    private TextView image_location;
    private TextView image_description;


    public FeedViewHolder(View itemView) {
        super(itemView);
        feed_image = (ImageView) itemView.findViewById(R.id.iv_feed_photo);
        image_location = (TextView) itemView.findViewById(R.id.tv_feed_photo_location);
        image_description = (TextView) itemView.findViewById(R.id.tv_photo_description);

    }

    public void bind(ImageModel imageModel) {
        feed_image.setImageResource(imageModel.getImageId());
        image_location.setText(imageModel.getLocationId());
        image_description.setText(imageModel.getAboutText());
    }
}
