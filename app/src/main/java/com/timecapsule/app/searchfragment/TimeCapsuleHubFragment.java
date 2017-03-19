package com.timecapsule.app.searchfragment;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.timecapsule.app.R;
import com.timecapsule.app.profilefragment.model.Capsule;
import com.timecapsule.app.searchfragment.hub_recycler_view.HubAdapter;

import java.util.ArrayList;

/**
 * Created by hakeemsackes-bramble on 3/19/17.
 */

public class TimeCapsuleHubFragment extends DialogFragment {

    private View mRoot;
    private RecyclerView rvlist;
    private ArrayList<Capsule> capsules;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_time_capsulehub,container);
        rvlist = (RecyclerView) mRoot.findViewById(R.id.time_capsule_hub_recyclerView);
        rvlist.setAdapter(new HubAdapter(capsules, getActivity()));
        rvlist.setLayoutManager(new LinearLayoutManager(getActivity()));
        return mRoot;

    }

    public ArrayList<Capsule> getCapsules() {
        return capsules;
    }

    public void setCapsules(ArrayList<Capsule> capsules) {
        this.capsules = capsules;
    }
}
