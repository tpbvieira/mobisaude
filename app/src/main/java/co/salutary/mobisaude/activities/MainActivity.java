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
import co.salutary.mobisaude.db.ErbDAO;
import co.salutary.mobisaude.db.LocalDataBase;
import co.salutary.mobisaude.db.UfDAO;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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

        TextView stateView = (TextView)findViewById(R.id.state);
        UfDAO statesDao = new UfDAO(db);
        stateView.setText("CurrentState=" + statesDao.getUfById(Settings.ID_UF).getNome());

        TextView citieView = (TextView)findViewById(R.id.city);
        CidadeDAO citiesDao = new CidadeDAO(db);
        citieView.setText("CurrentCity=" + citiesDao.getCidadeById(Settings.ID_CIDADE).getNome());

        TextView operadorasView = (TextView)findViewById(R.id.operadoras);
        Settings settings = new Settings(getApplicationContext());
        String operadorasString = settings.getPreferenceValues(Settings.FILTER_OPERADORAS);
        List<String> operadorasList = Arrays.asList(operadorasString.split("\\s*,\\s*"));
        operadorasView.setText("Operators=" + operadorasList.size());

        TextView erbsView = (TextView)findViewById(R.id.erbs);
        ErbDAO erbDao = new ErbDAO(db);
        erbsView.setText("ERBs=" + erbDao.listarErb(Settings.ID_CIDADE,true,true,true,operadorasList).size());

        TextView viewsView = (TextView)findViewById(R.id.views);
        viewsView.setText("Views=" + db.getAvailableViews().size());

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

        MenuItem menuLogin = (MenuItem) menuNav.findItem(R.id.menu_login);
        if (menuLogin != null) {
            menuLogin.setVisible(!Settings.IS_LOGGEDIN);
            this.invalidateOptionsMenu();
        }
        MenuItem menuLogout = (MenuItem) menuNav.findItem(R.id.menu_logout);
        if (menuLogout != null) {
            menuLogout.setVisible(Settings.IS_LOGGEDIN);
            this.invalidateOptionsMenu();
        }
        MenuItem menuProfile = (MenuItem) menuNav.findItem(R.id.menu_profile);
        if (menuProfile != null) {
            menuProfile.setVisible(Settings.IS_LOGGEDIN);
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

        if (Settings.IS_LOGGEDIN) {
            TextView textUserName = (TextView) findViewById(R.id.text_userName);
            if (textUserName != null) {
                textUserName.setText("Thiago Vieira");
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

        if (id == R.id.menu_home) {
            startActivity(MainActivity.class);
        } else if (id == R.id.menu_bookmarks) {
            startActivity(MainActivity.class);
        } else if (id == R.id.menu_dashboard) {
            startActivity(MainActivity.class);
        } else if (id == R.id.menu_search) {
            startActivity(MainActivity.class);
        } else if (id == R.id.menu_maps) {
            startActivity(MapsActivity.class);
        } else if (id == R.id.menu_manage) {
            startActivity(SettingsActivity.class);
        } else if (id == R.id.menu_profile) {
            startActivity(SettingsActivity.class);
        } else if (id == R.id.menu_login) {
            startActivity(LoginActivity.class);
        } else if (id == R.id.menu_logout) {
            Settings.IS_LOGGEDIN = false;
            startActivity(MainActivity.class);
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
