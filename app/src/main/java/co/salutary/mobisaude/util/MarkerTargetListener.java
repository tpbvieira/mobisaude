package co.salutary.mobisaude.util;

import java.util.ArrayList;

import com.google.android.gms.maps.model.Marker;

import co.salutary.mobisaude.model.Problema;

public interface MarkerTargetListener {

    ArrayList<Problema> positionableObjectForMarker(Marker marker);

}
