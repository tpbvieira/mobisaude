package co.salutary.mobisaude.util;

import java.util.ArrayList;

import org.json.JSONObject;

import com.google.android.gms.maps.model.Marker;

import co.salutary.mobisaude.db.entidade.Problema;

public interface MarkerTargetListener {

    ArrayList<Problema> positionableObjectForMarker(Marker marker);

}
