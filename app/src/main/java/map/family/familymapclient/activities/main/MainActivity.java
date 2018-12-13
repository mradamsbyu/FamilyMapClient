package map.family.familymapclient.activities.main;

import android.support.v4.app.FragmentActivity;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import android.os.Bundle;
import android.support.v4.app.Person;
import android.support.v7.app.AppCompatActivity;

import map.family.familymapclient.R;
import map.family.familymapclient.fragments.login.LoginFragment;
import map.family.familymapclient.fragments.map.TopLevelMapFragment;
import map.family.familymapclient.model.Model;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            Iconify.with(new FontAwesomeModule());
            if (!Model.getInstance().authTokenExists()) {
                LoginFragment loginFragment = new LoginFragment();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, loginFragment).commit();
            }
            else {
                Model.getInstance().setCurrentPerson(null);
                Model.getInstance().setCurrentEvent(null);
                TopLevelMapFragment topLevelMapFragment = new TopLevelMapFragment();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, topLevelMapFragment).commit();
            }
        }
    }

    @Override
    public void onBackPressed() {
    }
}
