package co.salutary.mobisaude.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.salutary.mobisaude.R;
import co.salutary.mobisaude.config.DeviceInfo;
import co.salutary.mobisaude.config.Settings;
import co.salutary.mobisaude.controller.ClientCache;
import co.salutary.mobisaude.controller.ServiceBroker;
import co.salutary.mobisaude.controller.TokenManager;
import co.salutary.mobisaude.model.EstabelecimentoSaude;
import co.salutary.mobisaude.util.JsonUtils;
import co.salutary.mobisaude.util.MobiSaudeAppException;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = new Object() {
    }.getClass().getName();

    //context
    private Settings settings;
    private ClientCache clientCache;

    //ui
    private View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.main_menu);

        // Main top bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_tool_bar);
        setSupportActionBar(toolbar);

        // Navigation Bar
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.menu_nav_drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        // contextual information
        settings = new Settings(getApplicationContext());
        clientCache = ClientCache.getInstance();

        //ui
        mProgressView = findViewById(R.id.main_content_progress_bar);

        new UpdateFieldsTask(String.valueOf(clientCache.getCidade().getIdCidade())).execute();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Navigation Menu Items
        NavigationView navigationView = (NavigationView) findViewById(R.id.menu_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Menu menuNav = navigationView.getMenu();

        MenuItem menuLogin = (MenuItem) menuNav.findItem(R.id.menu_signin);
        if (menuLogin != null) {
            menuLogin.setVisible(!DeviceInfo.isLoggedin());
            this.invalidateOptionsMenu();
        }
        MenuItem menuLogout = (MenuItem) menuNav.findItem(R.id.menu_logout);
        if (menuLogout != null) {
            menuLogout.setVisible(DeviceInfo.isLoggedin());
            this.invalidateOptionsMenu();
        }
        MenuItem menuProfile = (MenuItem) menuNav.findItem(R.id.menu_profile);
        if (menuProfile != null) {
            menuProfile.setVisible(DeviceInfo.isLoggedin());
            this.invalidateOptionsMenu();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.menu_nav_drawer);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (DeviceInfo.isLoggedin()) {
            TextView textUserName = (TextView) findViewById(R.id.text_userName);
            if (textUserName != null) {
                String userName = settings.getPreferenceValue(Settings.USER_NAME);
                textUserName.setText(userName);
            }
        }

        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.menu_bookmarks) {
            startActivity(LoginGoogleActivity.class);
        } else if (id == R.id.menu_search) {
            startActivity(HealthPlaceSelectionActivity.class);
        } else if (id == R.id.menu_maps) {
            startActivity(MapsActivity.class);
        } else if (id == R.id.menu_dashboard) {
            startActivity(DashboardActivity.class);
//        } else if (id == R.id.menu_settings) {
//            startActivity(LoginGoogleActivity.class);
        } else if (id == R.id.menu_profile) {
            startActivity(ProfileActivity.class);
        } else if (id == R.id.menu_signup) {
            startActivity(SignupActivity.class);
        } else if (id == R.id.menu_signin) {
            startActivity(LoginActivity.class);
        } else if (id == R.id.menu_logout) {
            DeviceInfo.logout(getApplicationContext());
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.menu_nav_drawer);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void startActivity(final Class<? extends Activity> activity) {
        if (activity != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(MainActivity.this, activity));
                }
            }, 300);
        }
    }

    public class UpdateFieldsTask extends AsyncTask<Void, Void, Boolean> {

        String mNumES, mCity, mErrorMsg;

        UpdateFieldsTask(String city) {
            mCity = city;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress(true);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            boolean ok = true;

            List<EstabelecimentoSaude> esList = clientCache.getListEstabelecimentosSaudeTipoES();
            if(esList == null || esList.size() == 0){
                esList = clientCache.getListEstabelecimentosSaudeCidade();
            }

            if(esList == null || esList.size() == 0){

                clientCache.setListEstabelecimentosSaudeCidade(new ArrayList<EstabelecimentoSaude>());

                String token = settings.getPreferenceValue(Settings.TOKEN);
                if (token == null || token.isEmpty()) {
                    TokenManager.gerarToken(getApplicationContext());//renew token and saves into preferences
                    token = settings.getPreferenceValue(Settings.TOKEN);
                }

                try {
                    JSONObject data = new JSONObject();
                    data.put("token", token);
                    data.put("idMunicipio", mCity.substring(0, mCity.length()-1));

                    JSONObject request = new JSONObject();
                    request.put("esRequest", data);

                    String responseStr = ServiceBroker.getInstance(getApplicationContext()).getESByIdMunicipio(request.toString());
                    if (responseStr != null) {
                        JSONObject json = new JSONObject(responseStr);
                        JSONObject esResponse = (JSONObject) json.get("esResponse");
                        String error = JsonUtils.getError(esResponse);
                        if (error == null) {
                            JSONArray ess = esResponse.getJSONArray("estabelecimentosSaude");
                            mNumES = String.valueOf(ess.length());
                            for (int i = 0; i < ess.length(); ++i) {
                                JSONObject rec = ess.getJSONObject(i);
                                EstabelecimentoSaude es = JsonUtils.jsonObjectToES(rec);
                                if(es != null){
                                    clientCache.getListEstabelecimentosSaudeCidade().add(es);
                                }
                            }
                        } else {
                            throw new MobiSaudeAppException(JsonUtils.getError(esResponse));
                        }
                    }
                } catch (Exception e) {
                    mErrorMsg = e.getMessage();
                    Log.e(TAG, e.getMessage(), e);
                    ok = false;
                }

            }

            return ok;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

            if (success) {
                showProgress(false);
            } else {
                Toast.makeText(getApplicationContext(), mErrorMsg, Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onCancelled() { }

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        }

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.e(TAG, "onConnectionFailed:" + connectionResult);
    }

}