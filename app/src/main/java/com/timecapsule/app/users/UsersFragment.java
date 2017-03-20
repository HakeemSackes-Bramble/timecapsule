package com.timecapsule.app.users;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.timecapsule.app.R;
import com.timecapsule.app.profilefragment.ProfileFragment;
import com.timecapsule.app.profilefragment.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by catwong on 3/18/17.
 */

public class UsersFragment extends ListFragment {


    public static final String TAG = UsersFragment.class.getSimpleName();
    public static final String EXTRA_USERS = "USERS";
    List<User> users;
    ListView userListView;
    private DatabaseReference usersReference;
    private View mRoot;
    private DatabaseReference databaseReference;
    private FirebaseDatabase usersDatabase;
    private User user;
    private TextView username;
    private FirebaseStorage firebaseStorage;
    private ArrayAdapter<User> userAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        users = new ArrayList<>();


        if (getArguments() != null) {
            users.addAll((List<User>) getArguments().getSerializable(EXTRA_USERS));
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userAdapter = new UserListAdapter(getActivity(), users);
        setListAdapter(userAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_user_list, parent, false);
        userListView = (ListView) mRoot.findViewById(android.R.id.list);
        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(android.R.id.list, new ProfileFragment())
                        .addToBackStack("profile")
                        .commit();
            }
        });
        return mRoot;
    }

    @Override
    public ListView getListView() {
        return super.getListView();
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    private void setUsersDatabase() {
        DatabaseReference allUsers = databaseReference.child("users");
        allUsers.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child : children) {
                    Log.d(TAG, "USERS: " + dataSnapshot.getChildren());
                    user = new User((String) child.child("name").getValue(),
                            (String) child.child("username").getValue(),
                            (String) child.child("profilePhoto").getValue());
                    Log.d(TAG, "USERS: " + user);
                    users.add(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void setUsers(List<User> users) {
        this.users.clear();
        this.users.addAll(users);
        userAdapter.clear();
        userAdapter.addAll(users);
    }

    static class ViewHolder {
        public TextView tvName;
        public TextView tvUsername;
        public ImageView ivProfilePhoto;
    }
}