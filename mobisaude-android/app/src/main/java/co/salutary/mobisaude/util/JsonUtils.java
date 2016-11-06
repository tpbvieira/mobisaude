package co.salutary.mobisaude.util;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.salutary.mobisaude.config.Settings;
import co.salutary.mobisaude.controller.TokenManager;
import co.salutary.mobisaude.model.Avaliacao;
import co.salutary.mobisaude.model.AvaliacaoMedia;
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
            Log.e(TAG, e.getMessage(),e);
            return null;
        }
    }

    public static String getError(String json, String subObject){
        String error = null;
        try{
            JSONObject object = new JSONObject(json);
            JSONObject target = (JSONObject) object.get(subObject);
            error = getError(target);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(),e);
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
            Log.e(TAG, e.getMessage(),e);
        }
        return error;
    }

    public static String createErrorMessage(String errorMessage){
        JSONObject jsonErrorMessage = new JSONObject();
        try{
            jsonErrorMessage.put("erro", errorMessage);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(),e);
        }
        return jsonErrorMessage.toString();
    }

    public static HashMap<String, String> fromJsonArraytoDomainHashMap(JSONArray jsonArray){
        HashMap<String, String> pairs = new HashMap<>();

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                pairs.put(jsonObject.getString("id"), jsonObject.getString("nome"));
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(),e);
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
            Log.e(TAG, e.getMessage(),e);
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
            Log.e(TAG, e.getMessage(),e);
            return null;
        }
    }

    public static List<Avaliacao> jsonObjectToListAvaliacao(JSONObject obj){

        List<Avaliacao> avaliacoes = new ArrayList<>();

        try {

            if (obj.has("avaliacoesMediaMes")){
                JSONArray jsonAvaliacoes = obj.getJSONArray("avaliacoesMediaMes");
                for (int i = 0; i < jsonAvaliacoes.length(); i++) {
                    JSONObject jsonAvaliacao = jsonAvaliacoes.optJSONObject(i);
                    Avaliacao av = new Avaliacao();
                    if (jsonAvaliacao.has("idES")){
                        av.setIdES(Integer.parseInt(jsonAvaliacao.getString("idES")));
                    }
                    if (jsonAvaliacao.has("rating")){
                        av.setRating(Float.parseFloat(jsonAvaliacao.getString("rating")));
                    }
                    if (jsonAvaliacao.has("date")){
                        av.setDate(sdfDMY.parse(jsonAvaliacao.getString("date")));
                    }
                    avaliacoes.add(av);
                }

            }

        } catch (Exception e) {
            Log.e(TAG, e.getMessage(),e);
        }

        return avaliacoes;
    }

    public static List<AvaliacaoMedia> jsonObjectToListAvaliacaoMedia(JSONObject obj){

        List<AvaliacaoMedia> avaliacoes = new ArrayList<>();

        try {

            if (obj.has("avaliacoesMediaMes")){
                JSONArray jsonAvaliacoes = obj.getJSONArray("avaliacoesMediaMes");
                for (int i = 0; i < jsonAvaliacoes.length(); i++) {
                    JSONObject jsonAvaliacao = jsonAvaliacoes.optJSONObject(i);
                    AvaliacaoMedia avaliacaoTmp = new AvaliacaoMedia();
                    if (jsonAvaliacao.has("idES")){
                        avaliacaoTmp.setIdES(Integer.parseInt(jsonAvaliacao.getString("idES")));
                    }
                    if (jsonAvaliacao.has("rating")){
                        avaliacaoTmp.setRating(Float.parseFloat(jsonAvaliacao.getString("rating")));
                    }
                    if (jsonAvaliacao.has("date")){
                        avaliacaoTmp.setDate(sdfDMY.parse(jsonAvaliacao.getString("date")));
                    }
                    if (jsonAvaliacao.has("count")){
                        avaliacaoTmp.setCount(Integer.parseInt(jsonAvaliacao.getString("count")));
                    }
                    avaliacoes.add(avaliacaoTmp);
                }

            }

        } catch (Exception e) {
            Log.e(TAG, e.getMessage(),e);
        }

        return avaliacoes;
    }

    public static Map<String, String> fromStrToMap(String str){
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        Gson gson = new Gson();
        return gson.fromJson(str, type);
    }

    public static String fromMapToString(Map<String, String> map){
        Gson gson = new Gson();
        return gson.toJson(map);
    }

}