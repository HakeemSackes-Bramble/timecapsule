package com.timecapsule.app.addmediafragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

public class PlacePickerFragmentActivity extends FragmentActivity {

    private static final String TAG = "TimePlacePickerFrag";
    int PLACE_PICKER_REQUEST = 1;
    //    private AddCapsuleLocationFragment addCapsuleLocationFragment;
    String mediaType;
    private View mRoot;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setContentView();
        mediaType = getIntent().getExtras().getString("key");


        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        Intent intent;
        try {
            intent = builder.build(this);
            startActivityForResult(intent, PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            Log.d(this.getClass().getSimpleName(), "onClick: ");
            e.printStackTrace();
        }

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d("TIMEPLACE", "onActivityResult: ");
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                LatLng locationLatLng = place.getLatLng();
                String address = (String) place.getAddress();

                double locationLat = locationLatLng.latitude;
                double locationLong = locationLatLng.longitude;

                Intent gotoMediaIntent = new Intent(getApplicationContext(), GoToMedia.class);
                Bundle bundle = new Bundle();
                bundle.putString("key", "value");
//      set Fragmentclass Arguments
                gotoMediaIntent.putExtra("keyMediaType", mediaType);
                gotoMediaIntent.putExtra("keyLocationLat", locationLat);
                gotoMediaIntent.putExtra("keyLocationLong", locationLong);
                gotoMediaIntent.putExtra("keyAddress", address);

                startActivity(gotoMediaIntent);

            }
        }
    }

}