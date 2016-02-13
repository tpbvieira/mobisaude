package co.salutary.mobisaude.activities;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import co.salutary.mobisaude.R;
import co.salutary.mobisaude.config.Settings;
import co.salutary.mobisaude.controller.ManagerToken;
import co.salutary.mobisaude.controller.ServiceRequester;
import co.salutary.mobisaude.controller.UserController;
import co.salutary.mobisaude.db.CidadeDAO;
import co.salutary.mobisaude.db.LocalDataBase;
import co.salutary.mobisaude.db.UfDAO;
import co.salutary.mobisaude.model.Cidade;
import co.salutary.mobisaude.model.UF;
import co.salutary.mobisaude.util.DeviceInfo;


public class LocalidadeActivity extends Activity implements Runnable, LocationListener {

	private static final int PROGRESS_BAR_VERIFICAR = 1;
	private static final int PROGRESS_BAR_DETERMINAR = 2;

	public static final int RESULTADO_LOCAL_SELECIONADO = 1;

	private static final int ACTIVITY_LIST_SELECT = 1;
	private static final long TIME_TEMP = 1000;

	private LinearLayout btnUF;
	private LinearLayout btnCidade;
	private EditText edtUF;
	private EditText edtCidade;
	private Button btnMeuLocal;

	private UserController userController;

	// thread loop
	private boolean timerInatividadeBool;
	private int timerInatividadeCount;
	private Handler timerInatividade;

	// gps
	private Location location;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tela_localidade);

		// user
		userController = UserController.getInstance();

        // location
		location = DeviceInfo.updateLocation(getApplicationContext(),this);

		btnUF = (LinearLayout) findViewById(R.id.tela_localidade_btn_uf);
		btnCidade = (LinearLayout) findViewById(R.id.tela_localidade_btn_cidade);
		edtUF = (EditText) findViewById(R.id.tela_localidade_edt_uf);
		edtCidade = (EditText) findViewById(R.id.tela_localidade_edt_cidade);
		btnMeuLocal = (Button) findViewById(R.id.tela_localidade_btn_meu_local);

		// acao
		btnUF.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onShowListaSelect(LocalidadeSelecionarActivity.LISTA_UF);
			}
		});
		edtUF.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onShowListaSelect(LocalidadeSelecionarActivity.LISTA_UF);
			}
		});

		btnCidade.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onShowListaSelect(LocalidadeSelecionarActivity.LISTA_CIDADE);
			}
		});
		edtCidade.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onShowListaSelect(LocalidadeSelecionarActivity.LISTA_CIDADE);
			}
		});

		btnMeuLocal.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onMinhaLocalidade();
			}
		});
	}

	@Override
	protected void onResume() {
		// verificar tela de help
        Settings localPref = new Settings(this);
		String value = localPref.getPreferenceValue(Settings.SHOW_SCREEN_TELA_2);
		if(value == null || value.equals("")){
			// Carregar timer
			timerInatividade = new Handler();
			timerInatividade.postDelayed(this, TIME_TEMP);
			timerInatividadeCount = 1;
			timerInatividadeBool = true;
		}
		super.onResume();
	}

	@Override
	protected void onPause() {
		// desativar thread de contagem de inatividade
		timerInatividadeBool = false;
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		timerInatividadeBool = false;
		super.onDestroy();
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		ProgressDialog pDialog;
		switch(id) {
		case PROGRESS_BAR_VERIFICAR:
			pDialog = new ProgressDialog(this);
			pDialog.setMessage("Verificando GPS");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			break;
		case PROGRESS_BAR_DETERMINAR:
			pDialog = new ProgressDialog(this);
			pDialog.setMessage("Determinando seu município");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			break;
		default:
			pDialog = null;
		}
		return pDialog;
	}

	public void onMinhaLocalidade() {
		new ShowDialog().execute();
	}

	public void onShowListaSelect(int tipoLista) {
		if(tipoLista == LocalidadeSelecionarActivity.LISTA_CIDADE){
			if(UserController.getInstance().getUf() != null){
				Intent it = new Intent(this, LocalidadeSelecionarActivity.class);
				it.putExtra("tipoLista", tipoLista);
				startActivityForResult(it, ACTIVITY_LIST_SELECT);
			}
		}
		else {
			Intent it = new Intent(this, LocalidadeSelecionarActivity.class);
			it.putExtra("tipoLista", tipoLista);
			startActivityForResult(it, ACTIVITY_LIST_SELECT);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == ACTIVITY_LIST_SELECT && resultCode == LocalidadeSelecionarActivity.RESULTADO_ITEM_SELECIONADO){
			// Atualizar campos
			atualizarCampos();
			// Se cidade selecionado, finalizar tela
			if(UserController.getInstance().getCidade() != null){				
				final String cidadeName = UserController.getInstance().getCidade().getNome();
                final Dialog dialog = new Dialog(this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_info);
                Button closeBtn = (Button) dialog.findViewById(R.id.btn_close);
                closeBtn.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cidade cidade = new CidadeDAO(LocalDataBase.getInstance()).getCidadeByNome(cidadeName);
                        userController.setCidade(cidade);
                        UF uf = new UfDAO(LocalDataBase.getInstance()).getUfById(cidade.getIdUF());
                        userController.setUf(uf);
                        fecharTela();
                        dialog.dismiss();
                    }
                });
                ((TextView)dialog.findViewById(R.id.txt_view_info1)).setText("????????????");
                dialog.show();
			}
		}
	}

	private void atualizarCampos(){
		UF uf = UserController.getInstance().getUf();
		if(uf != null){
			edtUF.setText(uf.getNome());
			edtCidade.setText("");
			edtCidade.setHint(R.string.cidade);
		}

		Cidade cidade = UserController.getInstance().getCidade();
		if(cidade != null){
			edtCidade.setText(cidade.getNome());
			userController.setMapErbsControle(null);
			userController.setListErbs(null);
		}
	}

	private void fecharTela() {
		setResult(RESULTADO_LOCAL_SELECIONADO);
		finish();
	}

	private class ShowDialog extends AsyncTask<Integer, Integer, Boolean> {

		@Override
		protected void onPreExecute() {
			showDialog(PROGRESS_BAR_VERIFICAR);
			super.onPreExecute();
		}

		protected Boolean doInBackground(Integer... params) {
			try {
				Thread.sleep(TIME_TEMP);
			} catch (Exception e) {
				Log.e("Anatel", "ShowDialog.doInBackground: "+e);
			}
			return null;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			try {
				dismissDialog(PROGRESS_BAR_VERIFICAR);
			} catch (Exception e) {
				Log.e("Anatel", "ShowDialog.dismissDialog: "+e);
			}
			new DeterminarLocal(getApplicationContext()).execute(4);
		}
	}

	private class DeterminarLocal extends AsyncTask<Integer, Integer, Boolean> {

        private Context context;

        public  DeterminarLocal(Context ctx){
            this.context = ctx;
        }

		@Override
		protected void onPreExecute() {
			showDialog(PROGRESS_BAR_DETERMINAR);
			super.onPreExecute();
		}

		@Override
		protected Boolean doInBackground(Integer... params) {
			try {
				try {
					Thread.sleep(TIME_TEMP);
				} catch (Exception e) {
					Log.e("Anatel", "DeterminarLocal.dismissDialog: "+e);
				}
				for (int i = 0; i < params[0]; i++) {
					Thread.sleep(TIME_TEMP);
					Location local = location;
					if(local == null){
                        local = DeviceInfo.getLastKnownLocation(context);
					}

					// verificar
					if(local != null){
                        Settings localPref = new Settings(getApplicationContext());
						String token = localPref.getPreferenceValue(Settings.TOKEN);
						if(token == null || token.isEmpty()) {
							ManagerToken.gerarToken(getApplicationContext());
							token = localPref.getPreferenceValue(Settings.TOKEN);
						}
						JSONObject jDados = new JSONObject();
						jDados.put("latitude",local.getLatitude());
						jDados.put("longitude",local.getLongitude());
						jDados.put("token",token);

						JSONObject jRequest = new JSONObject();
						jRequest.put("geocodeRequest", jDados);

						String reponder = ServiceRequester.getInstance(getApplicationContext()).geocode(jRequest.toString());
						if(reponder != null && !reponder.startsWith(getString(R.string.erro_starts))){
							JSONObject jObject = new JSONObject(reponder);
							JSONObject jReponder = (JSONObject) jObject.get("geocodeResponse");
							String erro = jReponder.getString("erro");
							String[] splitResult = erro.split("\\|");
							int idErro = Integer.parseInt(splitResult[0]);
							if(idErro == 6){
								// gerar novo token
								if(!ManagerToken.gerarToken(getApplicationContext())){
									return false;
								}
							}
							else if(idErro == 0) {
								int codMunicipioIbge = jReponder.getInt("codMunicipioIbge");

								Cidade cidade = new CidadeDAO(LocalDataBase.getInstance()).getCidadeById(codMunicipioIbge);
								if(cidade != null){
									UF uf = new UfDAO(LocalDataBase.getInstance()).getUfById(cidade.getIdUF());
									userController.setUf(uf);
									userController.setCidade(cidade);
									userController.atualizarCidadeSelecionado();

									// para a cidade local
									userController.setCidadeLocal(cidade);

									// resetar memoria
									userController.setMapErbsControle(null);
									userController.setListErbs(null);
									return true;
								}
								else {
									return false;
								}
							}
							else {
								return false;
							}
						}
					}
				}
			} catch (Exception e) {
				Log.e("Anatel", "DeterminarLocal.doInBackground: "+e);
			}
			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			try {
				Thread.sleep(TIME_TEMP);
				dismissDialog(PROGRESS_BAR_DETERMINAR);
			} catch (Exception e) {
				Log.e("Anatel", "DeterminarLocal.dismissDialog: "+e);
			}
			try {
				if(result){
					fecharTela();
				}
				else {
					Toast.makeText(getApplicationContext(), "Não foi possível determinar a sua localização.", Toast.LENGTH_LONG).show();
				}
			} catch (Exception e) {
				Log.e("Anatel", "DeterminarLocal.onPostExecute: "+e);
			}
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		this.location = location;
	}

	@Override
	public void onProviderDisabled(String provider) {

	}

	@Override
	public void onProviderEnabled(String provider) {

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}

	private void showTelaHelp() {
		// salvar registro de tela exibida
        Settings localPref = new Settings(this);
		localPref.setPreferenceValue(Settings.SHOW_SCREEN_TELA_2, "true");
		// mostrar tela
		final Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
		dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
		dialog.setContentView(R.layout.help_2);
		LinearLayout layout = (LinearLayout) dialog.findViewById(R.id.help2_over_layout);
		layout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});
		dialog.show();
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		timerInatividadeCount = 0;
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public void run() {
		try {
			if(timerInatividadeBool){
				if(timerInatividadeCount >= 15){
					timerInatividadeBool = false;
					showTelaHelp();
				}
				else {
					timerInatividadeCount++;
					timerInatividade.postDelayed(this, TIME_TEMP);
				}
			}
		} catch (Exception e) {
			Log.e("Anatel", getTitle()+".run: "+e);
		}
	}
}