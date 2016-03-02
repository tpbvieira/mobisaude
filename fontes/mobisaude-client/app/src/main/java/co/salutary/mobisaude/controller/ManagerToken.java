package co.salutary.mobisaude.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import co.salutary.mobisaude.config.Settings;

public class ManagerToken  {

	public static boolean gerarToken(Context context) {
		try {
			JSONObject jDados = new JSONObject();
			jDados.put("chave",gerarChave());
		    
			JSONObject jRequest = new JSONObject();
		    jRequest.put("gerarTokenRequest", jDados);
			
		    String reponder = ServiceBroker.getInstance(context).gerarToken(jRequest.toString());

		    if(reponder != null && !reponder.startsWith("Error")) {
		    	JSONObject jObject = new JSONObject(reponder);
				JSONObject jReponder = (JSONObject) jObject.get("gerarTokenResponse");
				String erro = jReponder.getString("erro");
				String[] splitResult = erro.split("\\|");
				int idErro = Integer.parseInt(splitResult[0]);
				if(idErro == 0){
					String token = jReponder.getString("token");
                    Settings localPref = new Settings(context);
					localPref.setPreferenceValue(Settings.TOKEN, token);
					return true;
				}
				else {
					return false;
				}
			}
			else {
				return false;
			}
		} catch (Exception e) {
			Log.e("Anatel", "ManagerToken.gerarToken: "+e);
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