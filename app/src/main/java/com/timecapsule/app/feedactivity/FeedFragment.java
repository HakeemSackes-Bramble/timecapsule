package com.timecapsule.app.feedactivity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.timecapsule.app.R;
import com.timecapsule.app.feedactivity.controller.FeedAdapter;
import com.timecapsule.app.feedactivity.model.ImageModel;
import com.timecapsule.app.profilefragment.model.Capsule;

import java.util.ArrayList;

import static com.facebook.internal.FacebookDialogFragment.TAG;


public class FeedFragment extends Fragment {


    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ImageView iv_feed_photo;
    private ImageView iv_feed_user_photo;
    private TextView tv_feed_username;
    private TextView tv_feed_address;
    private TextView tv_feed_date;
    private ArrayList<Capsule> queryCapsules;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReferenceFromUrl("https://timecapsule-8b809.firebaseio.com/");
        queryCapsules = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed, parent, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setViews(view);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_feed);
        capsuleDBReference(view);

    }

    private ArrayList<ImageModel> setImageData() {

        ArrayList<ImageModel> projectList = new ArrayList<>();

        ImageModel imageModel1 = new ImageModel();
        imageModel1.setImageId(R.drawable.museum_of_ice_cream);
        imageModel1.setLocationId("Museum of Ice Cream");
        imageModel1.setAboutText("A pool full of sprinkles");
        projectList.add(imageModel1);

        ImageModel imageModel2 = new ImageModel();
        imageModel2.setImageId(R.drawable.mumford_and_sons_soho_house);
        imageModel2.setLocationId("Soho House");
        imageModel2.setAboutText("Mumford and Sons :)");
        projectList.add(imageModel2);

        ImageModel imageModel3 = new ImageModel();
        imageModel3.setImageId(R.drawable.the_standard_nyc);
        imageModel3.setLocationId("The Standard Rooftop Bar");
        imageModel3.setAboutText("Hot in the city, hot in the city tonight!");
        projectList.add(imageModel3);

        ImageModel imageModel4 = new ImageModel();
        imageModel4.setImageId(R.drawable.google);
        imageModel4.setLocationId("Google NYC");
        imageModel4.setAboutText("Our first time at Google NYC");
        projectList.add(imageModel4);

        ImageModel imageModel5 = new ImageModel();
        imageModel5.setImageId(R.drawable.chelse_market_couple);
        imageModel5.setLocationId("Chelsea Market");
        imageModel5.setAboutText("Our first kiss");
        projectList.add(imageModel5);

        ImageModel imageModel6 = new ImageModel();
        imageModel6.setImageId(R.drawable.mural_on_highline);
        imageModel6.setLocationId("The High Line");
        imageModel6.setAboutText("Eduardo Kobra mural");
        projectList.add(imageModel6);

        return projectList;
    }

    public void setViews(View view) {
        iv_feed_user_photo = (ImageView) view.findViewById(R.id.feed_card_user_photo);
        iv_feed_photo = (ImageView) view.findViewById(R.id.iv_feed_photo);
        tv_feed_username = (TextView) view.findViewById(R.id.feed_card_username);
        tv_feed_address = (TextView) view.findViewById(R.id.tv_feed_photo_location);
        tv_feed_date = (TextView) view.findViewById(R.id.feed_card_date);
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

    private void capsuleDBReference(final View view) {
        final DatabaseReference timeCapsules = databaseReference.child("capsules");
        timeCapsules.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> timeCapsules = dataSnapshot.getChildren();
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
                                (String) snapShot.child("address").getValue());

                    }
                    queryCapsules.add(moment);
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                recyclerView.setAdapter(new FeedAdapter(queryCapsules, view.getContext()));


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
}

