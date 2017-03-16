package com.timecapsule.app.locationpick;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.timecapsule.app.R;
import com.timecapsule.app.locationpick.controller.LocationAdapter;
import com.timecapsule.app.locationpick.model.NearbyLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by catwong on 3/14/17.
 */

public class PlaceDetectionFragment extends DialogFragment implements LoaderManager.LoaderCallbacks<List<NearbyLocation>> {

    private static final String TAG = PlaceDetectionFragment.class.getSimpleName();
    private static final int PLACES_DETECTION_LOADER = 0;
    private View mRoot;
    private String mediaType;
    private TextView tv_place_name;
    private TextView tv_place_address;
    private RecyclerView recyclerView;
    private List<NearbyLocation> nearbyLocationList;
    private LocationAdapter mLocationAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public static class Builder {
        private String mediaType;


        public Builder(String type) {
            this.mediaType = type;
        }

        public PlaceDetectionFragment build() {
            Log.d(TAG, "build: "+ mediaType);
            PlaceDetectionFragment fragment = new PlaceDetectionFragment();
            Bundle bundle = new Bundle();
            bundle.putString( "mediaType", mediaType);
            fragment.setArguments(bundle);
            return fragment;
        }

        public String getMediaType() {
            return mediaType;
        }

        public void setMediaType(String mediaType) {
            this.mediaType = mediaType;
        }
    }

    public static PlaceDetectionFragment newInstance(String mediaType) {
        PlaceDetectionFragment fragment = new PlaceDetectionFragment();
        Bundle args = new Bundle();
        args.putString("keyMediaType", mediaType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(PLACES_DETECTION_LOADER, null, this);
        mediaType = getArguments().getString("mediaType");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle args = getArguments();
        mediaType = args.getString("keyMediaType");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, Bundle savedInstanceState) {
        mediaType = getArguments().getString("keyMediaType");
        mRoot = inflater.inflate(R.layout.fragment_place_picker, parent, false);
        return mRoot;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) mRoot.findViewById(R.id.rv_nearbyLocation);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mLocationAdapter = new LocationAdapter(getActivity(), new ArrayList<NearbyLocation>());
        recyclerView.setAdapter(mLocationAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public Loader<List<NearbyLocation>> onCreateLoader(int id, Bundle args) {
        Log.d(TAG, "onCreateLoader() called");
        return new LocationLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<NearbyLocation>> loader, List<NearbyLocation> nearbyLocations) {
        Log.d(TAG, "onLoadFinished() called");
        mLocationAdapter.addPlaces(nearbyLocations);

    }

    @Override
    public void onLoaderReset(Loader<List<NearbyLocation>> loader) {
        Log.d(TAG, "onLoaderReset() called");
        mLocationAdapter.removeNearByPlaces();
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        Window window = getDialog().getWindow();
//        if(window == null) return;
//        WindowManager.LayoutParams params = window.getAttributes();
//        params.width = 1000;
//        params.height = 800;
//        window.setAttributes(params);
//    }
}
