package co.salutary.mobisaude.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import co.salutary.mobisaude.R;
import co.salutary.mobisaude.config.Settings;
import co.salutary.mobisaude.util.DeviceInfo;
import co.salutary.mobisaude.util.JsonUtils;

public class ManagerToken  {

    private static final String TAG = new Object(){}.getClass().getName();

	public static boolean gerarToken(Context context) {

		try {
			JSONObject values = new JSONObject();
			values.put("chave", gerarChave());
		    
			JSONObject gerarTokenRequest = new JSONObject();
		    gerarTokenRequest.put("gerarTokenRequest", values);
			
		    String response = ServiceBroker.getInstance(context).gerarToken(gerarTokenRequest.toString());

		    if(response != null) {
		    	JSONObject jsonResponse = new JSONObject(response);
				JSONObject gerarTokenResponse = (JSONObject) jsonResponse.get("gerarTokenResponse");
                int idErro = JsonUtils.getErrorCode(gerarTokenResponse);
                if(idErro == 0){// 0 = success
					String token = gerarTokenResponse.getString("token");
                    Settings localPref = new Settings(context);
					localPref.setPreferenceValue(Settings.TOKEN, token);
                    DeviceInfo.isConnected = true;
					return true;
				}
				else {
                    DeviceInfo.statusMesage = JsonUtils.getErrorMessage(gerarTokenResponse);
                    DeviceInfo.isConnected = false;
                    DeviceInfo.hasToken = false;
                    Settings localPref = new Settings(context);
                    localPref.setPreferenceValue(Settings.TOKEN, Settings.EMPTY);
					return false;
				}
			}
			else {
                DeviceInfo.statusMesage = Resources.getSystem().getString(R.string.error);
                DeviceInfo.isConnected = false;
                DeviceInfo.hasToken = false;
                Settings localPref = new Settings(context);
                localPref.setPreferenceValue(Settings.TOKEN, Settings.EMPTY);
                return false;
			}

		} catch (JSONException e) {
			Log.e(TAG, e.getMessage());
			return false;
		} catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return false;
        }
	}

	private static String gerarChave() {
		int[] arrPermutacao = { 7, 5, 3, 1, 4, 6, 0, 2 };
		SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy", Locale.getDefault());
		String dataStr = dateFormat.format(new Date());
		StringBuffer chave = new StringBuffer("");

		// Permutar os digitos da data atual DDMMAAAA
		for (int i = 0; i < dataStr.length(); i++) {
			chave.append(dataStr.charAt(arrPermutacao[i]));
		}

		// Fatorar o numero resultado da permutacao
		Integer chaveInt = Integer.parseInt(chave.toString());
		List<Integer> fatoresChaveInt = primeFactors(chaveInt);

        // clear
		chave.setLength(0);

        // Concatenar os fatores em ordem crescente numa string
		for (Integer fator : fatoresChaveInt) {
			chave.append(fator.toString());
		}

		return chave.toString();
	}

    private static List<Integer> primeFactors(int numbers) {
        int n = numbers;
        List<Integer> factors = new ArrayList<Integer>();
        for (int i = 2; i <= n / i; i++) {
            while (n % i == 0) {
                factors.add(i);
                n /= i;
            }
        }
        if (n > 1) {
            factors.add(n);
        }
        return factors;
    }

}