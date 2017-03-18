package com.timecapsule.app.users;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.timecapsule.app.R;
import com.timecapsule.app.profilefragment.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by catwong on 3/16/17.
 */

public class UserListFragment extends ListFragment {

    private static final String TAG = UserListFragment.class.getSimpleName();
    private DatabaseReference usersReference;
    private View mRoot;
    private FirebaseDatabase usersDatabase;
    private User user;
    private ListView userListView;
    private TextView username;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final List<User> users = new ArrayList<User>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference();
        databaseReference.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child : children) {
                    user = child.getValue(User.class);
                    users.add(user);
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ArrayAdapter<User> userAdapter = new ArrayAdapter<User>(getActivity(), R.layout.fragment_user_list, users);
        setListAdapter(userAdapter);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_user_list, parent, false);
        userListView = (ListView) mRoot.findViewById(android.R.id.list);
        username = (TextView) mRoot.findViewById(R.id.empty);
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


}
