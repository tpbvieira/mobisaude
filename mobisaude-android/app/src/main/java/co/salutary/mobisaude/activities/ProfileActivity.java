package co.salutary.mobisaude.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.salutary.mobisaude.R;
import co.salutary.mobisaude.config.DeviceInfo;
import co.salutary.mobisaude.config.Settings;
import co.salutary.mobisaude.controller.ServiceBroker;
import co.salutary.mobisaude.controller.TokenManager;
import co.salutary.mobisaude.util.JsonUtils;
import co.salutary.mobisaude.util.MobiSaudeAppException;
import co.salutary.mobisaude.util.Validator;

import static android.Manifest.permission.READ_CONTACTS;


public class ProfileActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    private static final String TAG = ProfileActivity.class.getSimpleName();

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserUpdateTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText mNameView;
    private CheckBox mContactable;
    private View mProgressView;
    private View mContentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!DeviceInfo.isLoggedin()) {
            finish();
        }

        setContentView(R.layout.activity_profile);

        mProgressView = findViewById(R.id.profile_progress_bar);

        // Set up the login form.
        mContentView = findViewById(R.id.profile_form_view);

        // email field
        mEmailView = (AutoCompleteTextView) findViewById(R.id.profile_email);
        populateAutoComplete();

        // passwork field
        mPasswordView = (EditText) findViewById(R.id.profile_password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.profile_email || id == EditorInfo.IME_NULL) {
                    attemptUpdate();
                    return true;
                }
                return false;
            }
        });

        mNameView = (EditText) findViewById(R.id.profile_name);

        mContactable = (CheckBox) findViewById(R.id.profile_contactable);

        Button mUpdateButton = (Button) findViewById(R.id.profile_button);
        mUpdateButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptUpdate();
            }
        });

        new UpdateFieldsTask().execute();

    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }

    private void attemptUpdate() {

        if (mAuthTask != null) {
            return;
        }

        // Reset variables
        mNameView.setError(null);
        mEmailView.setError(null);
        mPasswordView.setError(null);
        mContactable.setError(null);
        boolean isValid = true;
        View focusView = null;

        // Update login and password
        String name = mNameView.getText().toString();
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        boolean contactable = mContactable.isChecked();

        // Check for a valid name
        if (TextUtils.isEmpty(name)) {
            mNameView.setError(getString(R.string.error_field_required));
            focusView = mNameView;
            isValid = isValid && false;
        }
        if (!Validator.isValidName(name)) {
            mNameView.setError(getString(R.string.error_invalid_name));
            focusView = mNameView;
            isValid = isValid && false;
        }

        // Check for a valid email address
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            isValid = isValid && false;
        }
        if (!Validator.isValidEmail(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            isValid = isValid && false;
        }

        // Check for a valid password
        if (TextUtils.isEmpty(password) || !Validator.isValidPassword(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            isValid = isValid && false;
        }

        if (isValid) {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserUpdateTask(email, password, name, contactable);
            mAuthTask.execute((Void) null);
        } else {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }

    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mContentView.setVisibility(show ? View.GONE : View.VISIBLE);
            mContentView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mContentView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mContentView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(ProfileActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
    }

    public class UserUpdateTask extends AsyncTask<Void, Void, Boolean> {

        private final String email;
        private final String password;
        private final String name;
        private final boolean contactable;

        UserUpdateTask(String email, String password, String name, Boolean contactable) {
            this.email = email;
            this.password = password;
            this.name = name;
            this.contactable = contactable;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            boolean updated = false;

            Settings settings = new Settings(getApplicationContext());

            String token = settings.getPreferenceValue(Settings.TOKEN);
            if (token == null || token.isEmpty()) {
                TokenManager.gerarToken(getApplicationContext());//renew token and saves into preferences
                token = settings.getPreferenceValue(Settings.TOKEN);
            }

            try {
                JSONObject data = new JSONObject();
                data.put("token", token);
                data.put("email", email);
                data.put("password", password);
                data.put("name", name);
                data.put("contactable", contactable);

                JSONObject request = new JSONObject();
                request.put("userRequest", data);

                String responseStr = ServiceBroker.getInstance(getApplicationContext()).updateUser(request.toString());
                if (responseStr != null) {
                    JSONObject json = new JSONObject(responseStr);
                    JSONObject updateResponse = (JSONObject) json.get("userResponse");
                    String error = JsonUtils.getError(updateResponse);
                    if (error == null) {
                        settings.setPreferenceValue(Settings.USER_NAME,name);
                        updated = true;
                    } else {
                        throw new MobiSaudeAppException(JsonUtils.getError(updateResponse));
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
                updated = false;
            }

            return updated;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                Toast.makeText(getApplicationContext(), getString(R.string.updated), Toast.LENGTH_SHORT).show();
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_duplicated_email));
                mPasswordView.requestFocus();
            }

        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }

    }

    public class UpdateFieldsTask extends AsyncTask<Void, Void, Boolean> {

        String email, password, name, contactable;

        UpdateFieldsTask() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            boolean ok = true;

            Settings settings = new Settings(getApplicationContext());

            String token = settings.getPreferenceValue(Settings.TOKEN);
            if (token == null || token.isEmpty()) {
                TokenManager.gerarToken(getApplicationContext());//renew token and saves into preferences
                token = settings.getPreferenceValue(Settings.TOKEN);
            }
            String userEmail = settings.getPreferenceValue(Settings.USER_EMAIL);

            try {
                JSONObject data = new JSONObject();
                data.put("token", token);
                data.put("email", userEmail);

                JSONObject request = new JSONObject();
                request.put("userRequest", data);

                String responseStr = ServiceBroker.getInstance(getApplicationContext()).getUser(request.toString());
                if (responseStr != null) {
                    JSONObject json = new JSONObject(responseStr);
                    JSONObject updateResponse = (JSONObject) json.get("userResponse");
                    String error = JsonUtils.getError(updateResponse);
                    if (error == null) {
                        email = updateResponse.get("email").toString();
                        password = updateResponse.get("password").toString();
                        name = updateResponse.get("name").toString();
                        contactable = updateResponse.get("contactable").toString();
                    } else {
                        throw new MobiSaudeAppException(JsonUtils.getError(updateResponse));
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
                ok = false;
            }

            return ok;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                mEmailView.setText(email, TextView.BufferType.EDITABLE);
                mEmailView.setEnabled(false);
                mPasswordView.setText(password, TextView.BufferType.EDITABLE);
                mNameView.setText(name, TextView.BufferType.EDITABLE);
                mContactable.setChecked(Boolean.valueOf(contactable));
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.error_connecting_server), Toast.LENGTH_SHORT).show();
                finish();
            }

        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }

    }

}