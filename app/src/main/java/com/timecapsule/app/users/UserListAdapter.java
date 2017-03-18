package com.timecapsule.app.users;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import com.timecapsule.app.profilefragment.model.User;

/**
 * Created by catwong on 3/18/17.
 */

public class UserListAdapter extends ArrayAdapter<User> {


    public UserListAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }
}
