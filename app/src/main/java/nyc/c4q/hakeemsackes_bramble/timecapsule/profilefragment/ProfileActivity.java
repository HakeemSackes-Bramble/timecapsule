package nyc.c4q.hakeemsackes_bramble.timecapsule.profilefragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import nyc.c4q.hakeemsackes_bramble.timecapsule.R;


public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.container_main, new EditProfileFragment())
                    .commit();
        }
    }
}
