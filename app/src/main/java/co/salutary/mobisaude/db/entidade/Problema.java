package co.salutary.mobisaude.db.entidade;

import android.util.Log;

import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class Problema implements ClusterItem {

    private static final String TAG = Problema.class.getSimpleName();

    private JSONObject problema;
	private LatLng mPosition;
	
	public Problema(JSONObject problema) {
		this.problema = problema;
		try {
			mPosition = new LatLng(problema.getDouble("latitude"), problema.getDouble("longitude"));			
		} catch (Exception e) {
            Log.d(TAG, "Error getting LatLong position", e);
		}
	}
	
	@Override
    public LatLng getPosition() {
        return mPosition;
    }
	
	public JSONObject getProblema() {
		return problema;
	}
}
