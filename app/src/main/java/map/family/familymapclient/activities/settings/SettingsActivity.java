package map.family.familymapclient.activities.settings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.Toast;

import map.family.familymapclient.R;
import map.family.familymapclient.activities.main.MainActivity;
import map.family.familymapclient.client.HttpClient;
import map.family.familymapclient.fragments.login.LoginFragment;
import map.family.familymapclient.fragments.map.TopLevelMapFragment;
import map.family.familymapclient.model.Model;
import map.family.familymapclient.proxy.EventProxy;
import map.family.familymapclient.proxy.LoginProxy;
import map.family.familymapclient.proxy.PersonProxy;
import map.family.familymapclient.request.LoginRequest;
import map.family.familymapclient.response.EventResponse;
import map.family.familymapclient.response.LoginResponse;
import map.family.familymapclient.response.PersonResponse;

/**
 * Created by mradams on 11/19/18.
 */

public class SettingsActivity extends AppCompatActivity {
    private View logoutButton;
    private View resyncButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        logoutButton = findViewById(R.id.logout_button);
        resyncButton = findViewById(R.id.re_sync_button);
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
                Model.getInstance().setEvents(response.eventResponse.getEvents());
                Model.getInstance().setPersons(response.personResponse.getPersons());
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
