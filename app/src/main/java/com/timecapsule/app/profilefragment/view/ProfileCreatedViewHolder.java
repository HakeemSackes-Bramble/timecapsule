package com.timecapsule.app.profilefragment.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;
import com.timecapsule.app.R;
import com.timecapsule.app.profilefragment.model.Capsule;
import com.timecapsule.app.profilefragment.model.User;

/**
 * Created by catwong on 3/10/17.
 */

public class ProfileCreatedViewHolder extends RecyclerView.ViewHolder {

    private final TextView buildingName;
    private TextView tv_location_name;
    private TextView tv_date;
    private ImageView iv_profile_photo;
    private Bitmap bmImg;
    private ImageView imageView;
    private User user;
    private DatabaseReference databaseReference;


    public ProfileCreatedViewHolder(View itemView) {
        super(itemView);
        tv_location_name = (TextView) itemView.findViewById(R.id.profile_card_tc_created_location_address);
        tv_date = (TextView) itemView.findViewById(R.id.profile_card_date);
        imageView = (ImageView) itemView.findViewById(R.id.iv_profile_card_tc_logo);
        buildingName = (TextView) itemView.findViewById(R.id.profile_card_tc_created_location_name);
        iv_profile_photo = (ImageView) itemView.findViewById(R.id.profile_card_photo);
    }


    public void bind(Capsule capsule, int numbers, Context context) {
        //tv_location_name.setText(capsule.getAddress());
        Picasso.with(context)
                .load(capsule.getStorageUrl())
                .resize(200, 200)
                .rotate(90f)
                .into(imageView);
        tv_date.setText(capsule.getDate());
        buildingName.setText(capsule.getAddress());



//        URL url = null;
//        try {
//            url = new URL(capsule.getStorageUrl().toString());
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        HttpURLConnection conn = null;
//        try {
//            conn = (HttpURLConnection) url.openConnection();
//            conn.connect();
//            InputStream is = conn.getInputStream();
//            bmImg = BitmapFactory.decodeStream(is);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        conn.setDoInput(true);
    }
}
