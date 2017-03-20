package com.timecapsule.app.users;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.timecapsule.app.R;
import com.timecapsule.app.profilefragment.model.User;

import java.util.List;

/**
 * Created by catwong on 3/18/17.
 */

public class UserListAdapter extends ArrayAdapter<User> {


    public UserListAdapter(Context context, List<User> users) {
        super(context, R.layout.user_list_single, users);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        UsersFragment.ViewHolder holder;

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.user_list_single, null);
            holder = new UsersFragment.ViewHolder();
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tvUsername = (TextView) convertView.findViewById(R.id.tv_username);
            holder.ivProfilePhoto = (ImageView)convertView.findViewById(R.id.iv_users_profile_photo);
            convertView.setTag(holder);

        } else {
            holder = (UsersFragment.ViewHolder) convertView.getTag();
        }

        User user = getItem(position);
        holder.tvName.setText(user.getName());
        holder.tvUsername.setText(user.getUsername());
                Picasso.with(getContext())
                .load(user.getProfilePhoto()) //extract as User instance method
                .resize(125,125)
                .into(holder.ivProfilePhoto);
        return convertView;
    }


}
