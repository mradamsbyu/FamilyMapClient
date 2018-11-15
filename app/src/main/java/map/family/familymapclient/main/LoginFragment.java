package map.family.familymapclient.main;

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

import map.family.familymapclient.R;

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
        mRegisterButton = (Button) v.findViewById(R.id.register_button);
        mRegisterButton.setEnabled(false);

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
}
