package co.salutary.mobisaude.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import co.salutary.mobisaude.R;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,NavigationView.OnNavigationItemSelectedListener  {

    private static final String TAG = new Object() {
    }.getClass().getName();

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.menu_bookmarks) {
//            startActivity(MainActivity.class);
//        } else if (id == R.id.menu_dashboard) {
//            startActivity(MainActivity.class);
//        } else if (id == R.id.menu_search) {
//            startActivity(MainActivity.class);
//        } else if (id == R.id.menu_maps) {
//            startActivity(MapsActivity.class);
//        } else if (id == R.id.menu_manage) {
//            startActivity(SettingsActivity.class);
//        } else if (id == R.id.menu_profile) {
//            startActivity(SettingsActivity.class);
//        } else if (id == R.id.menu_signup) {
//            startActivity(SignupActivity.class);
//        } else if (id == R.id.menu_signin) {
//            startActivity(LoginActivity.class);
//        } else if (id == R.id.menu_logout) {
//            startActivity(MainActivity.class);
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.menu_nav_drawer);
//        drawer.closeDrawer(GravityCompat.START);
//        return true
        Log.d(TAG,"????????????");
        return false;

    }

    public void startActivity(final Class<? extends Activity> activity) {
        if (activity != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(MapsActivity.this, activity));
                }
            }, 300);
        }
    }

}