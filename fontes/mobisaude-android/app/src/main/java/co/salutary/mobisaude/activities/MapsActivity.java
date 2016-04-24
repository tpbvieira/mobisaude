package co.salutary.mobisaude.activities;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.List;

import co.salutary.mobisaude.R;
import co.salutary.mobisaude.config.Settings;
import co.salutary.mobisaude.controller.ClientCache;
import co.salutary.mobisaude.model.EstabelecimentoSaude;
import co.salutary.mobisaude.util.DeviceInfo;
import co.salutary.mobisaude.util.JsonUtils;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,NavigationView.OnNavigationItemSelectedListener  {

    private static final String TAG = new Object() {
    }.getClass().getName();

    private GoogleMap mMap;

    private ClientCache clientCache;
    private Settings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // contextual information
        clientCache = ClientCache.getInstance();
        settings = new Settings(getApplicationContext());

        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Location currLocation = DeviceInfo.getLastKnownLocation(getApplicationContext());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currLocation.getLatitude(), currLocation.getLongitude()), 12));

        // plot ES
        try {
            String values = settings.getPreferenceValues(Settings.TIPOS_ESTABELECIMENTO_SAUDE);
            HashMap tipoESMap = JsonUtils.fromJsonArraytoDomainHashMap(new JSONArray(values));
            List<EstabelecimentoSaude> esList = clientCache.getListEstabelecimentosSaude();
            if (esList != null && esList.size() > 0) {
                for (EstabelecimentoSaude es : esList) {
                    LatLng esLatLong = new LatLng(es.getLatitude(), es.getLongitude());
                    String tipoESValue = (String) tipoESMap.get(Short.toString(es.getIdTipoEstabelecimentoSaude()));
                    mMap.addMarker(new MarkerOptions()
                            .position(esLatLong)
                            .title(es.getNomeFantasia())
                            .snippet(tipoESValue));
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }

}