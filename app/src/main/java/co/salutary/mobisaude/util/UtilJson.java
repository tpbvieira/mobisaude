package co.salutary.mobisaude.util;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import co.salutary.mobisaude.config.Settings;
import co.salutary.mobisaude.controller.ManagerToken;

public class UtilJson {

    private static final String TAG = UtilJson.class.getSimpleName();

    public static Double parceJsonToDouble(JSONObject json, String key){
    	try {
			return json.getDouble(key);
		} catch (Exception e) {
            Log.d(TAG, "UtilError converting from json", e);
		}
    	return 0d;
    }

    public static JSONObject createRequest(Context context, String requestString){
        Settings settings = new Settings(context);
        String token = settings.getPreferenceValue(Settings.TOKEN);
        if(token == null || token.isEmpty()) {
            ManagerToken.gerarToken(context);
            token = settings.getPreferenceValue(Settings.TOKEN);
        }

        try{
            JSONObject data = new JSONObject();
            data.put("token", token);

            JSONObject request = new JSONObject();
            request.put(requestString, data);
            return request;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }

    public static int getErrorCode(String json, String jsonObject){
        try{
            JSONObject jObject = new JSONObject(json);
            JSONObject jReponder = (JSONObject) jObject.get(jsonObject);
            return getErrorCode(jReponder);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return -1;
        }
    }

    public static int getErrorCode(JSONObject json){
        try{
            String erro = json.getString("erro");
            String[] splitResult = erro.split("\\|");
            return Integer.parseInt(splitResult[0]);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return -1;
        }
    }

}