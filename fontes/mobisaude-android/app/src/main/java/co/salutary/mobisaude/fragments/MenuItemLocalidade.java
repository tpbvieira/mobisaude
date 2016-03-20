package co.salutary.mobisaude.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONObject;

import co.salutary.mobisaude.R;
import co.salutary.mobisaude.activities.SelectListActivity;
import co.salutary.mobisaude.activities.MainActivity;
import co.salutary.mobisaude.config.Settings;
import co.salutary.mobisaude.controller.ManagerToken;
import co.salutary.mobisaude.controller.ServiceBroker;
import co.salutary.mobisaude.controller.UserController;
import co.salutary.mobisaude.db.CidadeDAO;
import co.salutary.mobisaude.db.LocalDataBase;
import co.salutary.mobisaude.db.UfDAO;
import co.salutary.mobisaude.model.Cidade;
import co.salutary.mobisaude.model.UF;
import co.salutary.mobisaude.util.DeviceInfo;

public class MenuItemLocalidade extends Fragment implements LocationListener {

    private static final long TIME_TEMP = 1000;
    private static final int SHOW_TELA_SELECT = 1;

    private EditText edtUF;
    private EditText edtCidade;

    // gps
    private Location location;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.menu_item_localidade, container, false);

        // requisicao gps
        location = DeviceInfo.updateLocation(this.getContext(),this);

        LinearLayout btnUF = (LinearLayout) rootView.findViewById(R.id.tela_localidade_btn_uf);
        LinearLayout btnCidade = (LinearLayout) rootView.findViewById(R.id.tela_localidade_btn_cidade);
        edtUF = (EditText) rootView.findViewById(R.id.tela_localidade_edt_uf);
        edtCidade = (EditText) rootView.findViewById(R.id.tela_localidade_edt_cidade);
        Button btnMeuLocal = (Button) rootView.findViewById(R.id.tela_localidade_btn_meu_local);

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
        return rootView;
    }

    @Override
    public void onDestroy() {
        DeviceInfo.removeUpdates(this.getContext(),this);
        super.onDestroy();
    }

    public void onMinhaLocalidade() {
        new ShowDialog(this.getContext()).execute();
    }

    public void onShowListaSelect(int tipoLista) {
        if (tipoLista == SelectListActivity.LISTA_CIDADE) {
            if (UserController.getInstance().getUf() != null) {
                Intent it = new Intent(getActivity(), SelectListActivity.class);
                it.putExtra("tipoLista", tipoLista);
                startActivityForResult(it, SHOW_TELA_SELECT);
            }
        } else {
            Intent it = new Intent(getActivity(), SelectListActivity.class);
            it.putExtra("tipoLista", tipoLista);
            startActivityForResult(it, SHOW_TELA_SELECT);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SHOW_TELA_SELECT && resultCode == SelectListActivity.RESULTADO_ITEM_SELECIONADO) {

            // Atualizar campos
            atualizarCampos();

            // Se cidade selecionado, finalizar tela
            if (UserController.getInstance().getCidade() != null) {
                fecharTela();
            }
        }
    }

    private void atualizarCampos() {
        UF uf = UserController.getInstance().getUf();
        if (uf != null) {
            edtUF.setText(uf.getNome());
            edtCidade.setText("");
            edtCidade.setHint(R.string.cidade);
        }

        Cidade cidade = UserController.getInstance().getCidade();
        if (cidade != null) {
            edtCidade.setText(cidade.getNome());
            UserController.getInstance().setMapErbsControle(null);
            UserController.getInstance().setListErbs(null);
        }
    }

    private void fecharTela() {
        // fechar tela para prestador
        Intent intent = new Intent(MainActivity.RECEIVER_MAIN_ACTIVITY);
        intent.putExtra("opc", MainActivity.RECEIVER_ABRIR_ATUALIZAR_PRESTADORES);
        getActivity().sendBroadcast(intent);
    }

    private class ShowDialog extends AsyncTask<Integer, Integer, Boolean> {

        private ProgressDialog pDialog;
        private Context context;

        public ShowDialog(Context ctx){
            this.context = ctx;
        }

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(getActivity());
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
                Log.e("Anatel", "ShowDialog.dismissDialog: " + e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            try {
                pDialog.dismiss();
            } catch (Exception e) {
                Log.e("Anatel", "ShowDialog.dismiss: " + e);
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
                pDialog = new ProgressDialog(getActivity());
                pDialog.setMessage("Determinando seu município");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
            } catch (Exception e) {
                Log.e("Anatel", "DeterminarLocal.dismissDialog: " + e);
            }
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Integer... params) {
            try {
                try {
                    Thread.sleep(TIME_TEMP * 2);
                } catch (Exception e) {
                    Log.e("Anatel", "DeterminarLocal.dismissDialog: " + e);
                }
                for (int i = 0; i < params[0]; i++) {
                    Thread.sleep(TIME_TEMP);
                    // Location by GPS
                    Location local = location;

                    // Location by Rede
                    if (local == null) {
                        local = DeviceInfo.getLastKnownLocation(context);
                    }
					
					// verificar
					if(local != null){
						Settings localPref = new Settings(getActivity());
						String token = localPref.getPreferenceValue(Settings.TOKEN);
				    	if(token == null || token.isEmpty()) {
				    		ManagerToken.gerarToken(getActivity());
				    		token = localPref.getPreferenceValue(Settings.TOKEN);
					    }
						JSONObject jDados = new JSONObject();
						jDados.put("latitude",local.getLatitude());
						jDados.put("longitude",local.getLongitude());
						jDados.put("token",token);
						
					    JSONObject jRequest = new JSONObject();
					    jRequest.put("geocodeRequest", jDados);
						
					    String reponder = ServiceBroker.getInstance(getActivity()).geocode(jRequest.toString());
					    if(reponder != null && !reponder.startsWith(getString(R.string.erro_starts))){
					    	JSONObject jObject = new JSONObject(reponder);
							JSONObject jReponder = (JSONObject) jObject.get("geocodeResponse");
							String erro = jReponder.getString("erro");
							String[] splitResult = erro.split("\\|");
							int idErro = Integer.parseInt(splitResult[0]);
							if(idErro == 6){
								// gerar novo token
								if(!ManagerToken.gerarToken(getActivity())){
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
				pDialog.dismiss();
			} catch (Exception e) {
				Log.e("Anatel", "DeterminarLocal.dismiss: "+e);
			}
			try {
				if(result){
					fecharTela();
				}
				else {
					Toast.makeText(getActivity(), "Não foi possível determinar a sua localização.", Toast.LENGTH_LONG).show();
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
}