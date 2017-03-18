package com.timecapsule.app.users;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.timecapsule.app.profilefragment.model.User;

/**
 * Created by catwong on 3/18/17.
 */

public class UserListViewHolder extends RecyclerView.ViewHolder {

    private TextView username;
    private View mRoot;
    Context context;



    public UserListViewHolder(View itemView) {
        super(itemView);
//        username = (TextView) mRoot.findViewById(R.id.tv_userlist_username);
    }

    public void bind(User user){
        username.setText(user.getUsername());
    }




}
