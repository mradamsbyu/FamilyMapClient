package map.family.familymapclient.activities.settings;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.HashSet;

import map.family.familymapclient.R;
import map.family.familymapclient.activities.main.MainActivity;
import map.family.familymapclient.memberobjects.Event;
import map.family.familymapclient.memberobjects.Person;
import map.family.familymapclient.model.Model;
import map.family.familymapclient.proxy.EventProxy;
import map.family.familymapclient.proxy.PersonProxy;
import map.family.familymapclient.response.EventResponse;
import map.family.familymapclient.response.PersonResponse;

/**
 * Created by mradams on 11/19/18.
 */

public class SettingsActivity extends AppCompatActivity {
    private View logoutButton;
    private View resyncButton;
    private Switch lifeStoryLineSwitch;
    private Switch familyTreeLineSwitch;
    private Switch spouseLineSwitch;
    private Spinner lifeStoryLineSpinner;
    private Spinner familyTreeLineSpinner;
    private Spinner spouseLineSpinner;
    private Spinner mapTypeSpinner;
//    final String[] colors = getResources().getStringArray(R.array.colors);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        logoutButton = findViewById(R.id.logout_button);
        resyncButton = findViewById(R.id.re_sync_button);
        lifeStoryLineSwitch = findViewById(R.id.lifeStorySwitch);
        lifeStoryLineSwitch.setChecked(Model.getInstance().isLifeStoryLinesEnabled());
        familyTreeLineSwitch = findViewById(R.id.familyTreeSwitch);
        familyTreeLineSwitch.setChecked(Model.getInstance().isFamilyTreeLinesEnabled());
        spouseLineSwitch = findViewById(R.id.spouseSwitch);
        spouseLineSwitch.setChecked(Model.getInstance().isSpouseLinesEnabled());
        lifeStoryLineSpinner = findViewById(R.id.life_story_spinner);
        lifeStoryLineSpinner.setSelection(Model.getInstance().getSpinnerPositionFromColor(Model.getInstance().getLifeStoryLineColor()));
        familyTreeLineSpinner = findViewById(R.id.family_tree_spinner);
        familyTreeLineSpinner.setSelection(Model.getInstance().getSpinnerPositionFromColor(Model.getInstance().getFamilyTreeLineColor()));
        spouseLineSpinner = findViewById(R.id.spouse_spinner);
        spouseLineSpinner.setSelection(Model.getInstance().getSpinnerPositionFromColor(Model.getInstance().getSpouseLineColor()));
        mapTypeSpinner = findViewById(R.id.map_type_spinner);
        mapTypeSpinner.setSelection(Model.getInstance().getSpinnerPositionFromMapType(Model.getInstance().getMapType()));
        lifeStoryLineSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model.getInstance().setLifeStoryLinesEnabled(lifeStoryLineSwitch.isChecked());
            }
        });
        familyTreeLineSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model.getInstance().setFamilyTreeLinesEnabled(familyTreeLineSwitch.isChecked());
            }
        });
        spouseLineSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model.getInstance().setSpouseLinesEnabled(spouseLineSwitch.isChecked());
            }
        });
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        resyncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resync();
            }
        });
        lifeStoryLineSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Model.getInstance().setLifeStoryLineColor((String) lifeStoryLineSpinner.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        familyTreeLineSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Model.getInstance().setFamilyTreeLineColor((String) familyTreeLineSpinner.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spouseLineSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Model.getInstance().setSpouseLineColor((String) spouseLineSpinner.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mapTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Model.getInstance().setMapType((String) mapTypeSpinner.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void logout() {
        Model.cleanModel();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void resync() {
        ResyncTask task = new ResyncTask();
        task.execute();
    }

    public class ResyncTask extends AsyncTask<Void, Void, FamilyDataResponse> {
        @Override
        protected FamilyDataResponse doInBackground(Void... params) {
            PersonResponse personResponse = PersonProxy.getInstance().getPersons();
            EventResponse eventResponse = EventProxy.getInstance().getEvents();
            FamilyDataResponse response = new FamilyDataResponse();
            response.eventResponse = eventResponse;
            response.personResponse = personResponse;
            return response;
        }

        @Override
        protected void onPostExecute(FamilyDataResponse response) {
            if (response.eventResponse.getMessage() == null && response.personResponse.getError() == null) {
                Model.getInstance().setEvents(new HashSet<Event>(response.eventResponse.getEvents()));
                Model.getInstance().setPersons(new HashSet<Person>(response.personResponse.getPersons()));
                Model.getInstance().updateEventTypes();
                Model.getInstance().updateEventPersonMap();
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(SettingsActivity.this, "Re-sync failed",Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... params) {
        }
    }

    public class FamilyDataResponse {
        public PersonResponse personResponse;
        public EventResponse eventResponse;
    }
}
