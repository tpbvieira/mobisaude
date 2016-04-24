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
import android.widget.Toast;

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
import co.salutary.mobisaude.adapters.GenericListAdapter;
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

    private HashMap<String, String> tipoESMap;
    private HashMap<String,String> markers = new HashMap<>();

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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Location currLocation = DeviceInfo.getLastKnownLocation(getApplicationContext());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currLocation.getLatitude(), currLocation.getLongitude()), 12));

        // plot ES
        try {
            String values = settings.getPreferenceValues(Settings.TIPOS_ESTABELECIMENTO_SAUDE);
            String idTipoESStr = settings.getPreferenceValues(Settings.ID_TIPO_ESTABELECIMENTO_SAUDE);
            tipoESMap = JsonUtils.fromJsonArraytoDomainHashMap(new JSONArray(values));
            List<EstabelecimentoSaude> esList = clientCache.getListEstabelecimentosSaude();
            if (esList != null && esList.size() > 0) {
                for (EstabelecimentoSaude es : esList) {

                    if(idTipoESStr != null){
                        int selected = Integer.parseInt(idTipoESStr);
                        int idTipoES = (int)es.getIdTipoEstabelecimentoSaude();
                        if(idTipoES == selected){
                            LatLng esLatLong = new LatLng(es.getLatitude(), es.getLongitude());
                            String tipoESValue = tipoESMap.get(Short.toString(es.getIdTipoEstabelecimentoSaude()));
                            Marker marker = mMap.addMarker(new MarkerOptions()
                                    .position(esLatLong)
                                    .title(es.getNomeFantasia())
                                    .snippet(tipoESValue)
                                    .icon(BitmapDescriptorFactory.defaultMarker((int) es.getIdTipoEstabelecimentoSaude() * 10)));
                            markers.put(marker.getId(),Integer.toString(es.getIdCnes()));
                        }
                    }else{
                        LatLng esLatLong = new LatLng(es.getLatitude(), es.getLongitude());
                        String tipoESValue = tipoESMap.get(Short.toString(es.getIdTipoEstabelecimentoSaude()));
                        Marker marker = mMap.addMarker(new MarkerOptions()
                                .position(esLatLong)
                                .title(es.getNomeFantasia())
                                .snippet(tipoESValue)
                                .icon(BitmapDescriptorFactory.defaultMarker((int) es.getIdTipoEstabelecimentoSaude() * 10)));
                        markers.put(marker.getId(),Integer.toString(es.getIdCnes()));
                    }

                }

            }

        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                settings.setPreferenceValue(Settings.ID_ESTABELECIMENTO_SAUDE, markers.get(marker.getId()));
                startActivity(HealthPlaceActivity.class);
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }

    private void startActivity(final Class<? extends Activity> activity) {
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