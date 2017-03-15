package com.timecapsule.app.geofence;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.timecapsule.app.addmediafragment.AddCapsuleLocationFragment;
import com.timecapsule.app.addmediafragment.GoToMedia;

import static android.app.Activity.RESULT_OK;
import static com.facebook.FacebookSdk.getApplicationContext;

public class TimePlacePicker extends Fragment {

    int PLACE_PICKER_REQUEST = 1;
    String mediaType;
    private Button get_place;
    private AddCapsuleLocationFragment addCapsuleLocationFragment;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mediaType = getActivity().getIntent().getExtras().getString("key");

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        Intent intent;
        try {
            intent = builder.build(getActivity());
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
        Toast.makeText(getActivity(), "place selected" + resultCode, Toast.LENGTH_SHORT).show();
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(getActivity(), data);
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
                addCapsuleLocationFragment = new AddCapsuleLocationFragment();
                addCapsuleLocationFragment.setArguments(bundle);
                startActivity(gotoMediaIntent);

            }
        }
    }
}
