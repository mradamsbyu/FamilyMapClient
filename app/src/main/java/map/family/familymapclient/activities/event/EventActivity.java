package map.family.familymapclient.activities.event;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import map.family.familymapclient.R;
import map.family.familymapclient.fragments.map.EventMapFragment;
import map.family.familymapclient.fragments.map.TopLevelMapFragment;

/**
 * Created by mradams on 11/19/18.
 */

public class EventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            EventMapFragment mapFragment = new EventMapFragment();
            Iconify.with(new FontAwesomeModule());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, mapFragment).commit();
        }
    }
}
