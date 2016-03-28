package co.salutary.mobisaude.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import co.salutary.mobisaude.R;
import co.salutary.mobisaude.config.Settings;
import co.salutary.mobisaude.db.CidadeDAO;
import co.salutary.mobisaude.db.LocalDataBase;
import co.salutary.mobisaude.db.UfDAO;
import co.salutary.mobisaude.util.DeviceInfo;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String RECEIVER_MAIN_ACTIVITY = "ReceiverMainActivity";
    public static final int RECEIVER_ABRIR_ATUALIZAR_PRESTADORES = 1;
    public static final int RECEIVER_ATUALIZAR_PRESTADORES = 2;

    private static final String TAG = MainActivity.class.getSimpleName();
    private boolean isInFront;

    private LocalDataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.menu_nav_drawer);

        // Main top bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_tool_bar);
        setSupportActionBar(toolbar);

        // Navigation Bar
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.menu_nav_drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        db = LocalDataBase.getInstance();
        Settings settings = new Settings(getApplicationContext());

        UfDAO statesDao = new UfDAO(db);
        TextView stateView = (TextView) findViewById(R.id.state);
        stateView.setText(getString(R.string.estado) + "=" + statesDao.getUfById(DeviceInfo.idUF).getNome());

        CidadeDAO citiesDao = new CidadeDAO(db);
        TextView citieView = (TextView) findViewById(R.id.city);
        citieView.setText(getString(R.string.cidade) + "=" + citiesDao.getCidadeById(DeviceInfo.idCidade).getNome());

        String tipoESString = settings.getPreferenceValues(Settings.FILTER_TIPO_ESTABELECIMENTO_SAUDE);
        List<String> tipoESList = Arrays.asList(tipoESString.split("\\s*,\\s*"));
        TextView tipoESView = (TextView) findViewById(R.id.tipo_es);
        tipoESView.setText(getString(R.string.tipo_estabelecimento_saude) + "=" + tipoESList.size());

        String tipoGestaoString = settings.getPreferenceValues(Settings.FILTER_TIPO_GESTAO);
        List<String> tipoGestaoList = Arrays.asList(tipoGestaoString.split("\\s*,\\s*"));
        TextView tipoGestaoView = (TextView) findViewById(R.id.tipo_gestao);
        tipoGestaoView.setText(getString(R.string.tipo_gestao) + "=" + tipoGestaoList.size());

        String regiaoString = settings.getPreferenceValues(Settings.FILTER_REGIAO);
        List<String> regiaoList = Arrays.asList(regiaoString.split("\\s*,\\s*"));
        TextView regiaoView = (TextView) findViewById(R.id.regiao);
        regiaoView.setText(getString(R.string.regiao) + "=" + regiaoList.size());

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        isInFront = true;

        // Navigation Menu Items
        NavigationView navigationView = (NavigationView) findViewById(R.id.menu_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Menu menuNav = navigationView.getMenu();

        MenuItem menuLogin = (MenuItem) menuNav.findItem(R.id.menu_signin);
        if (menuLogin != null) {
            menuLogin.setVisible(!DeviceInfo.isLoggedin);
            this.invalidateOptionsMenu();
        }
        MenuItem menuLogout = (MenuItem) menuNav.findItem(R.id.menu_logout);
        if (menuLogout != null) {
            menuLogout.setVisible(DeviceInfo.isLoggedin);
            this.invalidateOptionsMenu();
        }
        MenuItem menuProfile = (MenuItem) menuNav.findItem(R.id.menu_profile);
        if (menuProfile != null) {
            menuProfile.setVisible(DeviceInfo.isLoggedin);
            this.invalidateOptionsMenu();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        isInFront = false;
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
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_top_right, menu);

        if (DeviceInfo.isLoggedin) {
            TextView textUserName = (TextView) findViewById(R.id.text_userName);
            if (textUserName != null) {
                Settings settings = new Settings(getApplicationContext());
                String userName = settings.getPreferenceValue(Settings.USER_NAME);
                textUserName.setText(userName);
            }
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.menu_bookmarks) {

        } else if (id == R.id.menu_search) {

        } else if (id == R.id.menu_maps) {

        } else if (id == R.id.menu_dashboard) {

        } else if (id == R.id.menu_settings) {

        } else if (id == R.id.menu_profile) {
            startActivity(ProfileActivity.class);
        } else if (id == R.id.menu_signup) {
            startActivity(SignupActivity.class);
        } else if (id == R.id.menu_signin) {
            startActivity(LoginActivity.class);
        } else if (id == R.id.menu_logout) {
            DeviceInfo.isLoggedin = false;
            Settings settings = new Settings(getApplicationContext());
            settings.setPreferenceValue(Settings.USER_EMAIL, null);
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

}