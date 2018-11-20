package map.family.familymapclient.main;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import map.family.familymapclient.R;
import map.family.familymapclient.client.HttpClient;
import map.family.familymapclient.memberobjects.Person;
import map.family.familymapclient.proxy.EventProxy;
import map.family.familymapclient.proxy.LoginProxy;
import map.family.familymapclient.proxy.PersonProxy;
import map.family.familymapclient.proxy.RegisterProxy;
import map.family.familymapclient.request.LoginRequest;
import map.family.familymapclient.request.RegisterRequest;
import map.family.familymapclient.response.EventResponse;
import map.family.familymapclient.response.LoginResponse;
import map.family.familymapclient.response.PersonResponse;
import map.family.familymapclient.response.RegisterResponse;

public class LoginFragment extends Fragment {

    private Button mSignInButton;
    private Button mRegisterButton;
    private EditText mServerHostField;
    private EditText mServerPortField;
    private EditText mUserNameField;
    private EditText mPasswordField;
    private EditText mFirstNameField;
    private EditText mLastNameField;
    private EditText mEmailField;
    private RadioButton mFemaleButton;
    private RadioButton mMaleButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        mSignInButton = (Button) v.findViewById(R.id.sign_in_button);
        mSignInButton.setEnabled(false);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpClient.getInstance().setServer(mServerHostField.getText().toString(), mServerPortField.getText().toString());
                LoginRequest request = new LoginRequest();
                request.setPassword(mPasswordField.getText().toString());
                request.setUserName(mUserNameField.getText().toString());
                LoginTask task = new LoginTask();
                task.execute(request);
            }
        });

        mRegisterButton = (Button) v.findViewById(R.id.register_button);
        mRegisterButton.setEnabled(false);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpClient.getInstance().setServer(mServerHostField.getText().toString(), mServerPortField.getText().toString());
                RegisterRequest request = new RegisterRequest();
                request.setPassword(mPasswordField.getText().toString());
                request.setUserName(mUserNameField.getText().toString());
                request.setEmail(mEmailField.getText().toString());
                request.setFirstName(mFirstNameField.getText().toString());
                request.setLastName(mLastNameField.getText().toString());
                if (mFemaleButton.isChecked()) {
                    request.setGender("f");
                }
                else {
                    request.setGender("m");
                }
                RegisterTask task = new RegisterTask();
                task.execute(request);
            }
        });

        TextWatcher editTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                checkForValidFields();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        mServerHostField = (EditText) v.findViewById(R.id.serverHostText);
        mServerHostField.addTextChangedListener(editTextWatcher);
        mServerPortField = (EditText) v.findViewById(R.id.serverPortText);
        mServerPortField.addTextChangedListener(editTextWatcher);
        mUserNameField = (EditText) v.findViewById(R.id.userNameText);
        mUserNameField.addTextChangedListener(editTextWatcher);
        mPasswordField = (EditText) v.findViewById(R.id.passwordText);
        mPasswordField.addTextChangedListener(editTextWatcher);
        mFirstNameField = (EditText) v.findViewById(R.id.firstNameText);
        mFirstNameField.addTextChangedListener(editTextWatcher);
        mLastNameField = (EditText) v.findViewById(R.id.lastNameText);
        mLastNameField.addTextChangedListener(editTextWatcher);
        mEmailField = (EditText) v.findViewById(R.id.emailText);
        mEmailField.addTextChangedListener(editTextWatcher);

        mServerHostField.setText("192.168.255.231");
        mServerPortField.setText("8080");
        mUserNameField.setText("usernm");
        mPasswordField.setText("pass");

        mFemaleButton = (RadioButton) v.findViewById(R.id.female_button);
        mMaleButton = (RadioButton) v.findViewById(R.id.male_button);

        return v;
    }

    private void checkForValidFields() {
        final String EMPTY_STRING = "";
        if (mServerHostField.getText().toString().equals(EMPTY_STRING) ||
                mServerPortField.getText().toString().equals(EMPTY_STRING) ||
                mUserNameField.getText().toString().equals(EMPTY_STRING) ||
                mPasswordField.getText().toString().equals(EMPTY_STRING)) {
            mSignInButton.setEnabled(false);
            mRegisterButton.setEnabled(false);
        }
        else {
            mSignInButton.setEnabled(true);
            if (mFirstNameField.getText().toString().equals(EMPTY_STRING) ||
                    mLastNameField.getText().toString().equals(EMPTY_STRING) ||
                    mEmailField.getText().toString().equals(EMPTY_STRING)) {
                mRegisterButton.setEnabled(false);
            }
            else {
                mRegisterButton.setEnabled(true);
            }
        }
    }

    public class LoginTask extends AsyncTask<LoginRequest, Integer, LoginResponse> {
        @Override
        protected LoginResponse doInBackground(LoginRequest... request) {
            LoginResponse response = LoginProxy.getInstance().login(request[0]);
            return response;
        }

        @Override
        protected void onPostExecute(LoginResponse response) {
            if (response != null) {
                if (response.getMessage() != null) {
                    Toast.makeText(getActivity(), response.getMessage(),Toast.LENGTH_LONG).show();
                }
                else {
                    RetrieveFamilyDataTask task = new RetrieveFamilyDataTask();
                    task.execute();
                }
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
        }
    }

    public class RegisterTask extends AsyncTask<RegisterRequest, Integer, RegisterResponse> {
        @Override
        protected RegisterResponse doInBackground(RegisterRequest... request) {
            RegisterResponse response = RegisterProxy.getInstance().register(request[0]);
            return response;
        }

        @Override
        protected void onPostExecute(RegisterResponse response) {
            if (response != null) {
                if (response.getErrorMessage() != null) {
                    Toast.makeText(getActivity(), response.getErrorMessage(), Toast.LENGTH_LONG).show();
                } else {
                    RetrieveFamilyDataTask task = new RetrieveFamilyDataTask();
                    task.execute();
                }
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
        }
    }

    public class RetrieveFamilyDataTask extends AsyncTask<Void, Void, FamilyDataResponse> {
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
            // Display first and last name (iterate through the response people until you find
            for (Person person : response.personResponse.getPersons()) {
                if (person.getSpouse() == null) {
                    Toast.makeText(getActivity(), person.getFirstName() + " " + person.getLastName(), Toast.LENGTH_LONG).show();
                    break;
                }
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
