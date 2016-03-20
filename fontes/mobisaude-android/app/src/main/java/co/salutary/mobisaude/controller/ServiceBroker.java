package co.salutary.mobisaude.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.util.Log;
import co.salutary.mobisaude.config.JSONParams;
import co.salutary.mobisaude.config.Settings;
import co.salutary.mobisaude.util.ConnectivityManager;

public class ServiceBroker {

    private static final String TAG = ServiceBroker.class.getSimpleName();

	private static ServiceBroker instance = null;

	private ConnectivityManager connectivityManager;


	public static ServiceBroker getInstance(Context context) {
		if (instance == null) {
			instance = new ServiceBroker(context);
		}
		return instance;
	}

	private ServiceBroker(Context ctx) {
		connectivityManager = ConnectivityManager.getInstance(ctx);
	}

	private String requestJson(String service, String json) {
		String dados = "Error";
		try {
			if(connectivityManager.isConnected()) {
				service = Settings.servicesUrl + service;

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
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}

		return dados;
	}
	
	private String response(InputStream in) throws IOException {
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			String line;
			StringBuilder sb =  new StringBuilder();
			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}
			rd.close();
			return sb.toString();
		} catch (Exception e) {
			Log.e(TAG,e.getMessage());
		}
        return "Error";
	}
	
	public String gerarToken(String json) {
		return requestJson("/gerarToken", json);
	}
	
	public String geocode(String json) {
		return requestJson("/geocode", json);
	}
	
	public String consultaDataToReport(String json) {
		return requestJson("/consultaDominios", json);
	}

	public String getESByMunicipio(String json) {
		return requestJson("/getESByMunicipio", json);
	}

    public String getESByMunicipioTipoEstabelecimento(String json) {
        return requestJson("/getESByMunicipioTipoEstabelecimento", json);
    }





	public String queryAvailableViews(String json) {
        return requestJson("/consultaTelas", json);
	}

	public String consultaHelpText(String json) {
		return requestJson("/textoAjudaServicoMovel", json);
	}

	public String consultaDisclaimerText(String json) {
		return requestJson("/textoAvisoRelatarProblema", json);
	}

	public String report(String json) {
		return requestJson("/relatarProblema", json);
	}

    public String queryReportedProblems(String json) {
        return requestJson("/listarProblema", json);
    }

}