package co.salutary.mobisaude.controller;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

import co.salutary.mobisaude.R;
import co.salutary.mobisaude.config.Settings;
import co.salutary.mobisaude.model.EstabelecimentoSaude;
import co.salutary.mobisaude.util.ConnectivityUtils;
import co.salutary.mobisaude.util.JsonUtils;
import co.salutary.mobisaude.util.MobiSaudeAppException;

public class ServiceBroker {

    private static final String TAG = new Object(){}.getClass().getName();

    private static ServiceBroker instance = null;

    private ConnectivityUtils connectivityUtils;

    private Context context;

    private ServiceBroker(Context ctx) {
        connectivityUtils = ConnectivityUtils.getInstance(ctx);
        context = ctx;
    }

    private String requestJson(String service, String json) {
        String dados = null;
        try {
            if(connectivityUtils.hasConnectivity()) {
                service = Settings.serverUrl + service;

                URL url = new URL(service);
                HttpURLConnection servConn = (HttpURLConnection) url.openConnection();
                servConn.setRequestMethod("POST");
                servConn.setReadTimeout(15000);
                servConn.setConnectTimeout(15000);
                servConn.setRequestProperty("Content-Type", "application/json");
                servConn.setUseCaches(false);
                servConn.setAllowUserInteraction(false);
                servConn.connect();

                OutputStream os = new BufferedOutputStream(servConn.getOutputStream());
                os.write(json.getBytes("UTF-8"));
                os.flush();

                int status = servConn.getResponseCode();
                if (status == HttpURLConnection.HTTP_OK || status == HttpURLConnection.HTTP_CREATED) {
                    dados = response(servConn.getInputStream());
                }
            }
        } catch (ConnectException e) {
            Log.e(TAG, e.getMessage());
            dados = JsonUtils.createErrorMessage(context.getString(R.string.error_connecting_server));
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            dados = JsonUtils.createErrorMessage(context.getString(R.string.error));
        }

        return dados;
    }

    private String response(InputStream in) throws IOException {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            StringBuilder stringBuilder =  new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();
            return stringBuilder.toString();
        } catch (Exception e) {
            Log.e(TAG,e.getMessage());
            JsonUtils.createErrorMessage(context.getString(R.string.error));
        }
        return JsonUtils.createErrorMessage(context.getString(R.string.error));
    }

    public static ServiceBroker getInstance(Context context) {
        if (instance == null) {
            instance = new ServiceBroker(context);
        }
        return instance;
    }

    public String gerarToken(String json) {
        return requestJson("/gerarToken", json);
    }

    public String geocode(String json) {
        return requestJson("/geocode", json);
    }

    public String signup(String json) {
        return requestJson("/signup", json);
    }

    public String signin(String json) {
        return requestJson("/signin", json);
    }

    public String getUser(String json) {
        return requestJson("/getUser", json);
    }

    public String updateUser(String json) {
        return requestJson("/updateUser", json);
    }

    public String consultaDominios(String json) {
        return requestJson("/consultaDominios", json);
    }

    public String getESByIdES(String json) {
        return requestJson("/getESByIdES", json);
    }

    public String getESByIdMunicipio(String json) {
        return requestJson("/getESByIdMunicipio", json);
    }

    public EstabelecimentoSaude getES(String token, String idES) throws MobiSaudeAppException {
        EstabelecimentoSaude es = null;
        try {
            JSONObject params = new JSONObject();
            params.put("token", token);
            params.put("idES", idES);
            JSONObject request = new JSONObject();
            request.put("esRequest", params);
            String responseStr = ServiceBroker.getInstance(context).getESByIdES(request.toString());
            if (responseStr != null) {
                JSONObject json = new JSONObject(responseStr);
                JSONObject response = (JSONObject) json.get("esResponse");
                String error = JsonUtils.getError(response);
                if (error == null) {
                    JSONObject obj = (JSONObject) response.get("estabelecimentosSaude");
                    es = JsonUtils.jsonObjectToES(obj);
                } else {
                    throw new MobiSaudeAppException(JsonUtils.getError(response));
                }
            } else {
                throw new MobiSaudeAppException(context.getString(R.string.error_getting_es));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return es;

    }

    public String sugerir(String json) {
        return requestJson("/sugerir", json);
    }

    public String avaliar(String json) {
        return requestJson("/avaliar", json);
    }

    public String getAvaliacaoByIdESEmail(String json) {
        return requestJson("/getAvaliacaoByIdESEmail", json);
    }

    public String getAvaliacaoMediaByIdES(String json) {
        return requestJson("/getAvaliacaoMediaByIdES", json);
    }

    public String listAvaliacaoByIdES(String json) {
        return requestJson("/listAvaliacaoByIdES", json);
    }

    public String listAvaliacaoMediaMesByIdES(String json) {
        return requestJson("/listAvaliacaoMediaMesByIdES", json);
    }

    public String listAvaliacaoBySiglaUF(String json) {
        return requestJson("/listAvaliacaoBySiglaUF", json);
    }

    public String listAvaliacaoByIdMunicipio(String json) {
        return requestJson("/listAvaliacaoByIdMunicipio", json);
    }

    public String listAvaliacaoByIdTipoES(String json) {
        return requestJson("/listAvaliacaoByIdTipoES", json);
    }

}