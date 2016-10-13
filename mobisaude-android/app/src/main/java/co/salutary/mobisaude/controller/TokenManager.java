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
import co.salutary.mobisaude.config.DeviceInfo;
import co.salutary.mobisaude.util.JsonUtils;
import co.salutary.mobisaude.util.MobiSaudeAppException;

public class TokenManager {

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
                if(!jsonResponse.has("gerarTokenResponse")){
                    throw new MobiSaudeAppException(JsonUtils.getError(jsonResponse));
                }
				JSONObject gerarTokenResponse = (JSONObject) jsonResponse.get("gerarTokenResponse");
                String error = JsonUtils.getError(gerarTokenResponse);
                if(error == null){// null == success
					String token = gerarTokenResponse.getString("token");
                    Settings localPref = new Settings(context);
					localPref.setPreferenceValue(Settings.TOKEN, token);
					return true;
				}
				else {
                    throw new MobiSaudeAppException(JsonUtils.getError(gerarTokenResponse));
				}
			}
			else {
                throw new MobiSaudeAppException(Resources.getSystem().getString(R.string.error));
			}

        } catch (MobiSaudeAppException | JSONException e) {
            Log.e(TAG, e.getMessage());
            DeviceInfo.statusMessage = e.getMessage();
            DeviceInfo.hasToken = false;
            Settings localPref = new Settings(context);
            localPref.setPreferenceValue(Settings.TOKEN, Settings.EMPTY);
            return false;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            DeviceInfo.statusMessage = e.getMessage();
            DeviceInfo.hasToken = false;
            Settings localPref = new Settings(context);
            localPref.setPreferenceValue(Settings.TOKEN, Settings.EMPTY);
            return false;
        }
	}

	private static String gerarChave() {
		int[] arrPermutacao = { 7, 5, 3, 1, 4, 6, 0, 2 };
		SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy", Locale.getDefault());
		String dataStr = dateFormat.format(new Date());
		StringBuilder chave = new StringBuilder("");

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
        List<Integer> factors = new ArrayList<>();
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