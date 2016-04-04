package co.salutary.mobisaude.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import co.salutary.mobisaude.R;
import co.salutary.mobisaude.config.Settings;
import co.salutary.mobisaude.controller.TokenManager;

public class JsonUtils {

    private static final String TAG = new Object(){}.getClass().getName();

    public static Double parceJsonToDouble(JSONObject json, String key){
    	try {
			return json.getDouble(key);
		} catch (Exception e) {
            Log.d(TAG, Resources.getSystem().getString(R.string.error), e);
		}
    	return 0d;
    }

    public static JSONObject createRequest(Context context, String requestString){
        Settings settings = new Settings(context);
        String token = settings.getPreferenceValue(Settings.TOKEN);

        if(token == null || token.isEmpty()) {
            TokenManager.gerarToken(context);
            token = settings.getPreferenceValue(Settings.TOKEN);
        }

        try{
            JSONObject tokenJson = new JSONObject();
            tokenJson.put("token", token);

            JSONObject request = new JSONObject();
            request.put(requestString, tokenJson);
            return request;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }

    public static String toJsonString(Object jsonObject) {
        return new Gson().toJson(jsonObject);
    }

    public static Object fromJsonString(String jsonString, Type type) {
        return new Gson().fromJson(jsonString, type);
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

    public static String getErrorMessage(JSONObject json){
        try{
            String erro = json.getString("erro");
            String[] splitResult = erro.split("\\|");
            return splitResult[1];
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return Resources.getSystem().getString(R.string.error);
        }
    }

    public static JSONObject createErrorMessageJson(String errorMessage){
        JSONObject jsonErrorMessage = new JSONObject();
        try{
            jsonErrorMessage.put("erro", errorMessage);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
        return jsonErrorMessage;
    }

    public static String createErrorMessage(String errorMessage){
        JSONObject jsonErrorMessage = new JSONObject();
        try{
            jsonErrorMessage.put("erro", errorMessage);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
        return jsonErrorMessage.toString();
    }

}