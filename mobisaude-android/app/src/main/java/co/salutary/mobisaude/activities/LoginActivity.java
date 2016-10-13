package co.salutary.mobisaude.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
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
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
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


public class LoginActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        LoaderCallbacks<Cursor>,
        View.OnClickListener  {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 9001;

    private CallbackManager callbackManager;    //declaring callback instance
    private LoginButton faceSignInButton;       //declaring loginbutton of facebook


    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * Keep track of the emailPwdLogin task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mContentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(new Object() {
        }.getClass().getName(), new Object() {
        }.getClass().getEnclosingMethod().getName());

        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());

//        try {
//            PackageInfo info = getPackageManager().getPackageInfo("co.salutary.mobisaude", PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//            Log.e(TAG, e.getMessage(), e);
//        } catch (NoSuchAlgorithmException e) {
//            Log.e(TAG, e.getMessage(), e);
//        }

        setContentView(R.layout.activity_login);

        callbackManager = CallbackManager.Factory.create();

        // Set up the emailPwdLogin form.
        mContentView = findViewById(R.id.login_form_view);
        mProgressView = findViewById(R.id.login_progress_bar);

        DeviceInfo.mGoogleApiClient = DeviceInfo.getGoogleApiClient(this,this,this);

        // email field
        mEmailView = (AutoCompleteTextView) findViewById(R.id.login_email);
        populateAutoComplete();

        // passwork field
        mPasswordView = (EditText) findViewById(R.id.login_password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    userLogin();
                    return true;
                }
                return false;
            }
        });

        // User_PWD Login
        findViewById(R.id.login_sign_in_button).setOnClickListener(this);

        // Google
        SignInButton googleSignInButton = (SignInButton) findViewById(R.id.google_sign_in_button);
        googleSignInButton.setSize(SignInButton.SIZE_STANDARD);
        googleSignInButton.setOnClickListener(this);

        // Facebook
        faceSignInButton = (LoginButton) findViewById(R.id.facebook_sign_in_button);
        faceSignInButton.setReadPermissions(Arrays.asList("public_profile, email, user_birthday"));
        faceSignInButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(new Object() {
                }.getClass().getName(), new Object() {
                }.getClass().getEnclosingMethod().getName());
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    String name = object.getString("name");
                                    String email = object.getString("email");

                                    // Save information and sign up new user, if dont exists
                                    DeviceInfo.facebookLogin(getApplicationContext(), email, name);
                                    // Try add signed in user into MobiSaúde DB
                                    SignUpTask mSignUpTask = new SignUpTask(email, email, name, "");
                                    mSignUpTask.execute((Void) null);

                                    // Success
                                    Log.d(TAG, getString(R.string.signedin) + ":" + DeviceInfo.getLoginType().name());
                                    Toast.makeText(getApplicationContext(), getString(R.string.signedin), Toast.LENGTH_SHORT).show();
                                    finish();

                                } catch(JSONException e) {
                                    Log.e(TAG, e.getMessage(), e);
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields","id,name,email,gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException e) {
            }

        });

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.google_sign_in_button:
                googleLogin();
                break;
            case R.id.facebook_sign_in_button:
                // Look for onCompleted(JSONObject object, GraphResponse response)
                break;
            case R.id.login_sign_in_button:
                userLogin();
                break;
            default:
                Log.e(TAG, getString(R.string.error));
                Toast.makeText(getApplicationContext(), getString(R.string.error), Toast.LENGTH_SHORT).show();
        }
    }

    private void googleLogin() {
        Log.d(new Object() {
        }.getClass().getName(), new Object() {
        }.getClass().getEnclosingMethod().getName());

        // Pending Google Login
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(DeviceInfo.mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleGoogleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            //showProgress(true);
            //opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
            //    @Override
            //    public void onResult(GoogleSignInResult googleSignInResult) {
            //        showProgress(false);
            //        handleGoogleSignInResult(googleSignInResult);
            //    }
            //});

            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(DeviceInfo.mGoogleApiClient);
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(new Object() {
        }.getClass().getName(), new Object() {
        }.getClass().getEnclosingMethod().getName());
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult:" + requestCode);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult signInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleGoogleSignInResult(signInResult);
        }else {// Facebook
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }

    }

    private void handleGoogleSignInResult(GoogleSignInResult signInResult) {
        Log.d(new Object() {
        }.getClass().getName(), new Object() {
        }.getClass().getEnclosingMethod().getName());

        if (signInResult.isSuccess()) {
            // Signed in successfully
            GoogleSignInAccount acct = signInResult.getSignInAccount();

            // Save information and sign up new user, if dont exists
            DeviceInfo.googleLogin(getApplicationContext(), acct.getEmail(), acct.getDisplayName());
            SignUpTask mAuthTask = new SignUpTask(acct.getEmail(), acct.getEmail(), acct.getDisplayName(), "");
            mAuthTask.execute((Void) null);

            // Success
            Log.d(TAG, getString(R.string.signedin) + ":" + DeviceInfo.getLoginType().name());
            Toast.makeText(getApplicationContext(), getString(R.string.signedin), Toast.LENGTH_SHORT).show();
            finish();
        } else {
            // Signed out, show unauthenticated UI.
            DeviceInfo.logout(getApplicationContext());
            Toast.makeText(getApplicationContext(), getString(R.string.error_login), Toast.LENGTH_SHORT).show();
        }

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
                    .setAction(android.R.string.ok, new View.OnClickListener() {
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }

    /**
     * Attempts to sign in or register the account specified by the emailPwdLogin form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual emailPwdLogin attempt is made.
     */
    private void userLogin() {
        Log.d(new Object() {
        }.getClass().getName(), new Object() {
        }.getClass().getEnclosingMethod().getName());

        if (mAuthTask != null) {
            return;
        }

        // Reset variables
        mEmailView.setError(null);
        mPasswordView.setError(null);
        boolean isValid = true;
        View focusView = null;

        // Update emailPwdLogin and password
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        // Check for a valid email address
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            isValid = false;
        }
        if (isValid && !Validator.isValidEmail(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            isValid = false;
        }

        // Check for a valid password
        if (isValid && (TextUtils.isEmpty(password) || !Validator.isValidPassword(password))) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            isValid = false;
        }

        // If OK, start Login Async Task
        if (isValid) {
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        } else {
            focusView.requestFocus();
        }

    }

    /**
     * Shows the progress UI and hides the emailPwdLogin form.
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
                new ArrayAdapter<>(LoginActivity.this,
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

    /**
     * Represents an asynchronous emailPwdLogin/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private String errMsg = null;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Log.d(new Object() {
            }.getClass().getName(), new Object() {
            }.getClass().getEnclosingMethod().getName());

            boolean hasAuth = false;
            Settings settings = new Settings(getApplicationContext());

            String token = settings.getPreferenceValue(Settings.TOKEN);
            if (token == null || token.isEmpty()) {
                TokenManager.gerarToken(getApplicationContext());//renew token and saves into preferences
                token = settings.getPreferenceValue(Settings.TOKEN);
            }

            try {
                JSONObject data = new JSONObject();
                data.put("token", token);
                data.put("email", mEmail);
                data.put("password", mPassword);

                JSONObject request = new JSONObject();
                request.put("userRequest", data);

                String responseStr = ServiceBroker.getInstance(getApplicationContext()).signin(request.toString());
                if (responseStr != null) {
                    JSONObject json = new JSONObject(responseStr);
                    JSONObject signinResponse = (JSONObject) json.get("userResponse");
                    String error = JsonUtils.getError(signinResponse);
                    if (error == null) {
                        responseStr = ServiceBroker.getInstance(getApplicationContext()).getUser(request.toString());
                        json = new JSONObject(responseStr);
                        JSONObject userResponse = (JSONObject) json.get("userResponse");
                        DeviceInfo.emailPwdLogin(getApplicationContext(), mEmail, DeviceInfo.LoginType.EMAIL_PWD, userResponse.get("name").toString());
                        hasAuth = true;
                    } else {
                        throw new MobiSaudeAppException(JsonUtils.getError(signinResponse));
                    }
                }
            } catch (Exception e) {
                DeviceInfo.logout(getApplicationContext());
                errMsg = e.getMessage();
                Log.e(TAG, e.getMessage(), e);
            }

            return hasAuth;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                Log.d(TAG, getString(R.string.signedin) + ":" + DeviceInfo.getLoginType().name());
                Toast.makeText(getApplicationContext(), getString(R.string.signedin), Toast.LENGTH_SHORT).show();
                finish();
            } else {
                if(errMsg == null){
                    mPasswordView.setError(getString(R.string.error_incorrect_password));
                    mPasswordView.requestFocus();
                }else{
                    Toast.makeText(getApplicationContext(), errMsg, Toast.LENGTH_SHORT).show();
                }
            }

        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.e(TAG, "onConnectionFailed:" + connectionResult);
    }

    public class SignUpTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private final String mName;
        private final String mPhone;

        SignUpTask(String email, String password, String name, String phone) {
            mEmail = email;
            mPassword = password;
            mName = name;
            mPhone = phone;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Log.d(new Object() {
            }.getClass().getName(), new Object() {
            }.getClass().getEnclosingMethod().getName());

            boolean signedUp = false;

            Settings settings = new Settings(getApplicationContext());

            String token = settings.getPreferenceValue(Settings.TOKEN);
            if (token == null || token.isEmpty()) {
                TokenManager.gerarToken(getApplicationContext());//renew token and saves into preferences
                token = settings.getPreferenceValue(Settings.TOKEN);
            }

            try {
                JSONObject data = new JSONObject();
                data.put("token", token);
                data.put("email", mEmail);
                data.put("password", mPassword);
                data.put("name", mName);
                data.put("phone", mPhone);

                JSONObject request = new JSONObject();
                request.put("userRequest", data);

                String responseStr = ServiceBroker.getInstance(getApplicationContext()).signup(request.toString());
                if (responseStr != null) {
                    JSONObject json = new JSONObject(responseStr);
                    JSONObject signinResponse = (JSONObject) json.get("userResponse");
                    String error = JsonUtils.getError(signinResponse);
                    if (error == null) {
                        signedUp = true;
                    } else {
                        throw new MobiSaudeAppException(JsonUtils.getError(signinResponse));
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
                signedUp = false;
            }

            return signedUp;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                Toast.makeText(getApplicationContext(), getString(R.string.signedup), Toast.LENGTH_SHORT).show();
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

}