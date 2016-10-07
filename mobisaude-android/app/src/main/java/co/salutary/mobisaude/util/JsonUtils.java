package co.salutary.mobisaude.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import co.salutary.mobisaude.R;
import co.salutary.mobisaude.config.Settings;
import co.salutary.mobisaude.controller.TokenManager;
import co.salutary.mobisaude.model.Avaliacao;
import co.salutary.mobisaude.model.EstabelecimentoSaude;

public class JsonUtils {

    private static final String TAG = new Object(){}.getClass().getName();
    public static final SimpleDateFormat sdfDMY = new SimpleDateFormat("dd/MM/yyyy");

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
            e.printStackTrace();
            return null;
        }
    }

    public static String getError(String json, String subObject){
        String error = null;
        try{
            JSONObject object = new JSONObject(json);
            JSONObject target = (JSONObject) object.get(subObject);
            return getError(target);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
        return error;
    }

    public static boolean hasError(JSONObject json){
        return json.has("erro");
    }

    public static String getError(JSONObject json){
        String error = null;
        try{
            if(json.has("erro")){
                error = json.getString("erro");
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
        return error;
    }

    public static String createErrorMessage(String errorMessage){
        JSONObject jsonErrorMessage = new JSONObject();
        try{
            jsonErrorMessage.put("erro", errorMessage);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
        return jsonErrorMessage.toString();
    }

    public static HashMap<String, String> fromJsonArraytoDomainHashMap(JSONArray jsonArray){
        HashMap<String, String> pairs = new HashMap<String, String>();

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                pairs.put(jsonObject.getString("id"), jsonObject.getString("nome"));
            }
        } catch (JSONException e) {
            Log.d(TAG, Resources.getSystem().getString(R.string.error), e);
            e.printStackTrace();
        }

        return pairs;
    }

    public static EstabelecimentoSaude jsonObjectToES(JSONObject rec){
        try {
            EstabelecimentoSaude es = new EstabelecimentoSaude();
            es.setIdCnes(rec.getInt("idES"));
            if (rec.has("idTipoES")){
                es.setIdTipoEstabelecimentoSaude(Short.parseShort(rec.getString("idTipoES")));
            }
            if (rec.has("idTipoGestao")){
                es.setIdTipoGestao(Short.parseShort(rec.getString("idTipoGestao")));
            }
            if (rec.has("endereco")){
                es.setEndereco(rec.getString("endereco"));
            }
            if (rec.has("latitude")){
                es.setLatitude(Double.parseDouble(rec.getString("latitude")));
            }
            if (rec.has("longitude")){
                es.setLongitude(Double.parseDouble(rec.getString("longitude")));
            }
            if (rec.has("nomeFantasia")){
                es.setNomeFantasia(rec.getString("nomeFantasia"));
            }

            return es;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static Avaliacao jsonObjectToAvaliacao(JSONObject obj){
        try {
            Avaliacao av = new Avaliacao();
            av.setIdES(obj.getInt("idES"));

            if (obj.has("email")){
                av.setEmail(obj.getString("email"));
            }
            if (obj.has("titulo")){
                av.setTitulo(obj.getString("titulo"));
            }
            if (obj.has("avaliacao")){
                av.setAvaliacao(obj.getString("avaliacao"));
            }
            if (obj.has("rating")){
                av.setRating(Float.parseFloat(obj.getString("rating")));
            }
            if (obj.has("date")){
                av.setDate(sdfDMY.parse(obj.getString("date")));
            }

            return av;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

}