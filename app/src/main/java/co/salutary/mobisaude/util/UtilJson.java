package co.salutary.mobisaude.util;

import android.util.Log;

import org.json.JSONObject;

import co.salutary.mobisaude.db.entidade.Erb;

public class UtilJson {

    private static final String TAG = UtilJson.class.getSimpleName();

    private static Double parceJsonToDouble(JSONObject json, String key){
    	try {
			return json.getDouble(key);
		} catch (Exception e) {
            Log.d(TAG, "UtilError converting from json", e);
		}
    	return 0d;
    }

}