package com.timecapsule.app.feedactivity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.timecapsule.app.R;
import com.timecapsule.app.feedactivity.controller.FeedAdapter;
import com.timecapsule.app.feedactivity.model.ImageModel;

import java.util.ArrayList;


public class FeedFragment extends Fragment {

    private View mRoot;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_feed, parent, false);
        return mRoot;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) mRoot.findViewById(R.id.rv_feed);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new FeedAdapter(setImageData()));
    }

    private ArrayList<ImageModel> setImageData(){

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
}

