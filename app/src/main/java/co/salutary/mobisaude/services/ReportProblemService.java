package co.salutary.mobisaude.services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import co.salutary.mobisaude.R;
import co.salutary.mobisaude.config.Settings;
import co.salutary.mobisaude.controller.ManagerToken;
import co.salutary.mobisaude.controller.ServiceRequester;
import co.salutary.mobisaude.util.ConnectivityManager;

public class ReportProblemService extends Service{

	final String tag = "ReportProblemService";	
	final IBinder mBinder = new MyBinder();

	LocalBroadcastManager broadcaster;	
	static final public String DATA_SYNC = "co.salutary.mobisaude.services.DATA_SYNC";
	Settings pref ;

	@Override
	public void onCreate() {
		super.onCreate();
		pref = new Settings(this);
		broadcaster = LocalBroadcastManager.getInstance(this);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(tag, "onStartCommand");
		uploadProblem();
		return Service.START_NOT_STICKY;
	}


	public void uploadProblem(){		
		try {
			String jsonArray = pref.getPreferenceValues(Settings.REPORT_PROBLEMS);
			JSONArray array;
			if(!TextUtils.isEmpty(jsonArray)){
				array = new JSONArray(jsonArray);
			}
			else{
				array = new JSONArray();
			}	
			Log.d(tag, "pending to upload problems " + array.length());
			if(array.length() > 0){
				Log.d(tag, array.getJSONObject(0).toString());
				new SaveReport(array.getJSONObject(0)).execute();
			}
			else{
				stopSelf();			
				Intent intent = new Intent(DATA_SYNC);
				broadcaster.sendBroadcast(intent);				
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}


	private class SaveReport extends AsyncTask<Integer, Integer, Boolean> {		

		JSONObject jDados;

		public SaveReport(JSONObject jDados){
			this.jDados = jDados;			
		}

		@Override
		protected Boolean doInBackground(Integer... params) {
			try {
				// carregar
				if(ConnectivityManager.getInstance(getApplicationContext()).isConnected()){
					// laco para repeti caso precise gera novo token
					for (int i = 0; i <= 1; i++) {
						Settings localPref = new Settings(getApplicationContext());
						String token = localPref.getPreferenceValue(Settings.TOKEN);
						if(token == null || token.isEmpty()) {
							ManagerToken.gerarToken(getApplicationContext());
							token = localPref.getPreferenceValue(Settings.TOKEN);
						}
						jDados.put("token", token);
						JSONObject jRequest = new JSONObject();
						jRequest.put("relatarProblemaRequest", jDados);

						String reponder = ServiceRequester.getInstance(getApplicationContext()).report(jRequest.toString());
						if(reponder != null && !reponder.startsWith(getString(R.string.erro_starts))){
							// salvar registros
							JSONObject jObject = new JSONObject(reponder);
							JSONObject jReponder = (JSONObject) jObject.get("relatarProblemaResponse");
							//Log.d("ActivityReportar", jReponder.toString());
							String erro = jReponder.getString("erro");
							String[] splitResult = erro.split("\\|");
							int idErro = Integer.parseInt(splitResult[0]);
							if(idErro == 6){
								// gerar novo token
								if(!ManagerToken.gerarToken(getApplicationContext())){
									return false;
								}
							}
							else {
								return (idErro == 0);
							}
						}
						else {							
							return false;
						}
					}
				}
			} catch (Exception e) {
				Log.e("Anatel", "ConsultaRanking.doInBackground: "+e);
			}
			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {			
			if(result){
				Log.d(tag, "saved succesfully ");
				updateArrayAndGo();
				uploadProblem();
			}			
		}
	}

	public void updateArrayAndGo(){
		try {
			JSONArray array = new JSONArray(pref.getPreferenceValues(Settings.REPORT_PROBLEMS));
			JSONArray aux = new JSONArray();
			for (int i = 1; i < array.length(); i++) {
				aux.put(array.getJSONObject(i));				
			}			
			pref.setPreferenceValues(Settings.REPORT_PROBLEMS, aux.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void onDestroy() {		
		super.onDestroy();
	}

	public class MyBinder extends Binder {
		ReportProblemService getService() {
			return ReportProblemService.this;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}	
} 
