package com.timecapsule.app.profilefragment;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.timecapsule.app.R;
import com.timecapsule.app.profilefragment.controller.ProfileCapsulesCreatedAdapter;
import com.timecapsule.app.profilefragment.model.Capsule;

import java.util.ArrayList;

import static com.facebook.internal.FacebookDialogFragment.TAG;


public class ProfileFragment extends Fragment {

    private View mRoot;
    private String MY_PREF = "MY_PREF";
    private String NAME_KEY = "nameKey";
    private String EMAIL_KEY = "emailKey";
    private String USERNAME_KEY = "usernameKey";
    private String PROFILE_PHOTO_KEY = "profilePhotoKey";
    private Button bt_edit_profile;
    private ImageView iv_profile_photo;
    private TextView tv_profile_username;
    private TextView tv_profile_name;
    private TextView tv_profile_capsules;
    private SharedPreferences sharedPreferences;
    private ImageView profile;
    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<Capsule> queriedCapsules;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReferenceFromUrl("https://timecapsule-8b809.firebaseio.com/");
        queriedCapsules = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, parent, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);
        setViews(view);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_profile);
        userDBReference(view);
        clickEditProfile();

    }


    public void setViews(View view) {
        bt_edit_profile = (Button) view.findViewById(R.id.bt_edit_profile);
        iv_profile_photo = (ImageView) view.findViewById(R.id.iv_profile_photo);
        tv_profile_username = (TextView) view.findViewById(R.id.tv_profile_username);
        tv_profile_name = (TextView) view.findViewById(R.id.tv_profile_name);
        tv_profile_capsules = (TextView) view.findViewById(R.id.tv_profile_created_num);
    }

    public void setSharedPrefs() {
        String username = sharedPreferences.getString(USERNAME_KEY, "");
        tv_profile_username.setText(username);
        String name = sharedPreferences.getString(NAME_KEY, "");
        tv_profile_name.setText(name);
        String photo = sharedPreferences.getString(PROFILE_PHOTO_KEY, "");
        iv_profile_photo.setImageDrawable(Drawable.createFromPath(photo));
    }

    public void clickEditProfile() {
        bt_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEditProfile();
            }
        });
    }

    public void setEditProfile() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container_main, new EditProfileFragment())
                .addToBackStack("edit profile")
                .commit();
    }

    private void userDBReference(final View view) {

        final DatabaseReference userCapsules = databaseReference.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        Log.d("Taggger", "userDBReference: " + FirebaseAuth.getInstance().getCurrentUser().getUid());
        //FirebaseAuth.getInstance().getCurrentUser().getUid());

        userCapsules.orderByChild("timestamp").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> timeCapsules = dataSnapshot.child("capsules").getChildren();
                tv_profile_username.setText((String) dataSnapshot.child("username").getValue());
                tv_profile_name.setText((String) dataSnapshot.child("name").getValue());
                Picasso.with(getActivity())
                        .load((String) dataSnapshot.child("profilePhoto").getValue())
                        .resize(125, 125)
                        .into(iv_profile_photo);

                Log.d("TAG", "onDataChange1: " + dataSnapshot.child("username").getValue() + " "
                        + dataSnapshot.child("name").getValue());
                for (DataSnapshot snapShot : timeCapsules) {
                    Log.d("TAG", "onDataChange2: " + snapShot.getValue());
                    Capsule moment;
                    if (snapShot.child("positionLat").getValue() == null || snapShot.child("positionLong").getValue() == null) {
                        Log.d(TAG, "onDataChange: skipped cap image");
                        continue;
                    }
                    if (snapShot.getValue().toString().split(",").length == 5) {
                        moment = new Capsule(
                                (String) snapShot.child("userId").getValue(),
                                (String) snapShot.child("storageUrl").getValue(),
                                (double) snapShot.child("positionLat").getValue(),
                                (double) snapShot.child("positionLong").getValue(),
                                (String) snapShot.child("date").getValue());
                    } else {
                        moment = new Capsule(
                                (String) snapShot.child("userId").getValue(),
                                (String) snapShot.child("storageUrl").getValue(),
                                (double) snapShot.child("positionLat").getValue(),
                                (double) snapShot.child("positionLong").getValue(),
                                (String) snapShot.child("date").getValue(),
                                (String) snapShot.child("address").getValue(),
                                (String) snapShot.child("timestamp").getValue());
                    }

                    queriedCapsules.add(moment);
                }
                tv_profile_capsules.setText(String.valueOf(queriedCapsules.size()));
                recyclerView.setAdapter(new ProfileCapsulesCreatedAdapter(queriedCapsules, view.getContext()));
                recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}