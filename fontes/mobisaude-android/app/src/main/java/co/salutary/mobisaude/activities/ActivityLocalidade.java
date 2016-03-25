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
import android.util.Log;
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
import co.salutary.mobisaude.controller.ServiceBroker;
import co.salutary.mobisaude.controller.UserController;
import co.salutary.mobisaude.db.CidadeDAO;
import co.salutary.mobisaude.db.LocalDataBase;
import co.salutary.mobisaude.db.UfDAO;
import co.salutary.mobisaude.model.Cidade;
import co.salutary.mobisaude.model.UF;
import co.salutary.mobisaude.util.ConnectivityManager;
import co.salutary.mobisaude.util.DeviceInfo;
import co.salutary.mobisaude.util.JsonUtils;

public class ActivityLocalidade extends Activity implements LocationListener {

    private static final String TAG = ActivityLocalidade.class.getSimpleName();

    private static final long TIME_TEMP = 1000;
    private static final int SHOW_TELA_SELECT = 1;

    private EditText edtUF;
    private EditText edtCidade;

    private final int PROGRESS_BAR = 1;
    private boolean isShowDialog = true;

    // gps
    private Location location;

    LocalDataBase LocalDataBase;
    UserController userController;

    int size = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_item_localidade);

        LocalDataBase = LocalDataBase.getInstance();
        userController = UserController.getInstance();
        DeviceInfo.isDadosAtivos = false;

        // requisicao gps
        location = DeviceInfo.updateLocation(getApplicationContext(),this);

        LinearLayout btnUF = (LinearLayout) findViewById(R.id.tela_localidade_btn_uf);
        LinearLayout btnCidade = (LinearLayout) findViewById(R.id.tela_localidade_btn_cidade);
        edtUF = (EditText) findViewById(R.id.tela_localidade_edt_uf);
        edtCidade = (EditText) findViewById(R.id.tela_localidade_edt_cidade);
        Button btnMeuLocal = (Button) findViewById(R.id.tela_localidade_btn_meu_local);

        // acao
        btnUF.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onShowListaSelect(SelectListActivity.LISTA_UF);
            }
        });
        edtUF.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onShowListaSelect(SelectListActivity.LISTA_UF);
            }
        });

        btnCidade.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onShowListaSelect(SelectListActivity.LISTA_CIDADE);
            }
        });
        edtCidade.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onShowListaSelect(SelectListActivity.LISTA_CIDADE);
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
    protected void onDestroy() {
        DeviceInfo.removeUpdates(getApplicationContext(),this);
        super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		Intent it = new Intent( ActivityLocalidade.this, MainActivity.class );
		it.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(it);
		super.onBackPressed();
	}

	public void onMinhaLocalidade() {
		new ShowDialog(this.getApplicationContext()).execute();
	}

	public void onShowListaSelect(int tipoLista) {
		if(tipoLista == SelectListActivity.LISTA_CIDADE){
			if(UserController.getInstance().getUf() != null){
				Intent it = new Intent(this, SelectListActivity.class);
				it.putExtra("tipoLista", tipoLista);
				startActivityForResult(it, SHOW_TELA_SELECT);
			}
		}
		else {
			Intent it = new Intent(this, SelectListActivity.class);
			it.putExtra("tipoLista", tipoLista);
			startActivityForResult(it, SHOW_TELA_SELECT);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == SHOW_TELA_SELECT && resultCode == SelectListActivity.RESULTADO_ITEM_SELECIONADO){
			// Atualizar campos
			atualizarCampos();
			// Se cidade selecionado, finalizar tela
			if(UserController.getInstance().getCidade() != null){				
				final String cidadeName = UserController.getInstance().getCidade().getNome();
				String uf = UserController.getInstance().getUf().getSigla();				
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
                ((TextView)dialog.findViewById(R.id.txt_view_info1)).setText("DeNovo????????");
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
			UserController.getInstance().setMapErbsControle(null);
			UserController.getInstance().setListEstabelecimentoSaudes(null);
		}
	}

	private void fecharTela(){
		if(DeviceInfo.idCidade == UserController.getInstance().getCidade().getIdCidade() && DeviceInfo.idUF == UserController.getInstance().getUf().getIdUf()){
            DeviceInfo.isDeviceLocated = true;
		}
		else{
            DeviceInfo.isDeviceLocated = false;
		}		
		if(!DeviceInfo.isDadosAtivos && ConnectivityManager.getInstance(getApplicationContext()).isConnected()){
			isShowDialog = true;
			size = 0;
		}		
	}

	private class ShowDialog extends AsyncTask<Integer, Integer, Boolean> {

		private ProgressDialog pDialog;
        private Context context;

        public ShowDialog(Context ctx){
            this.context = ctx;
        }

		@Override
		protected void onPreExecute() {
			pDialog = new ProgressDialog(ActivityLocalidade.this);
			pDialog.setMessage("Verificando GPS");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
			super.onPreExecute();
		}

		protected Boolean doInBackground(Integer... params) {
			try {
				Thread.sleep(TIME_TEMP);
			} catch (Exception e) {
				Log.e(TAG, "ShowDialog.dismissDialog: "+e);
			}
			return null;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			try {
				pDialog.dismiss();
			} catch (Exception e) {
				Log.e(TAG, "ShowDialog.dismiss: "+e);
			}
			new DeterminarLocal(context).execute(4);
		}
	}

	private class DeterminarLocal extends AsyncTask<Integer, Integer, Boolean> {

		private ProgressDialog pDialog;
		private UserController userController;
        private Context context;

        public DeterminarLocal(Context ctx){
            this.context = ctx;
        }

		@Override
		protected void onPreExecute() {
			userController = UserController.getInstance();
			// alerta
			try {
				pDialog = new ProgressDialog(ActivityLocalidade.this);
				pDialog.setMessage("Determinando seu município");
				pDialog.setIndeterminate(false);
				pDialog.setCancelable(true);
				pDialog.show();
			} catch (Exception e) {
				Log.e(TAG, "DeterminarLocal.dismissDialog: "+e);
			}
			super.onPreExecute();
		}

		@Override
		protected Boolean doInBackground(Integer... params) {
			try {
				try {
					Thread.sleep(TIME_TEMP*2);
				} catch (Exception e) {
					Log.e(TAG, "DeterminarLocal.dismissDialog: "+e);
				}
				for (int i = 0; i < params[0]; i++) {
					Thread.sleep(TIME_TEMP);
					// Location by GPS
					Location local = location;

					// Location by Rede
					if(local == null){
                        local = DeviceInfo.getLastKnownLocation(context);
					}

					// verificar
					if(local != null){
						Settings localPref = new Settings(ActivityLocalidade.this);
						String token = localPref.getPreferenceValue(Settings.TOKEN);
						if(token == null || token.isEmpty()) {
							ManagerToken.gerarToken(ActivityLocalidade.this);
							token = localPref.getPreferenceValue(Settings.TOKEN);
						}
						JSONObject jDados = new JSONObject();
						jDados.put("latitude",local.getLatitude());
						jDados.put("longitude",local.getLongitude());
						jDados.put("token",token);

						JSONObject jRequest = new JSONObject();
						jRequest.put("geocodeRequest", jDados);

						String reponder = ServiceBroker.getInstance(ActivityLocalidade.this).geocode(jRequest.toString());
						if(reponder != null && !reponder.startsWith(getString(R.string.erro_starts))){
							JSONObject jObject = new JSONObject(reponder);
							JSONObject jReponder = (JSONObject) jObject.get("geocodeResponse");
							int idErro = JsonUtils.getErrorCode(jReponder);
							if(idErro == 6){
								// gerar novo token
								if(!ManagerToken.gerarToken(ActivityLocalidade.this)){
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
									userController.setListEstabelecimentoSaudes(null);
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
				Log.e(TAG, "DeterminarLocal.doInBackground: "+e);
			}
			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			try {
				pDialog.dismiss();
			} catch (Exception e) {
				Log.e(TAG, "DeterminarLocal.dismiss: "+e);
			}
			try {
				if(result){
					fecharTela();
				}
				else {
					Toast.makeText(ActivityLocalidade.this, "Não foi possível determinar a sua localização.", Toast.LENGTH_LONG).show();
				}
			} catch (Exception e) {
				Log.e(TAG, "DeterminarLocal.onPostExecute: "+e);
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
    
	@Override
	protected Dialog onCreateDialog(int id) {
		ProgressDialog pDialog;
		switch(id) {
		case PROGRESS_BAR:
			pDialog = new ProgressDialog(this);
			pDialog.setMessage("Procurando");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			break;
		default:
			pDialog = null;
		}
		return pDialog;
	}

}