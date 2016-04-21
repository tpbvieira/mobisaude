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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONObject;

import co.salutary.mobisaude.R;
import co.salutary.mobisaude.config.Settings;
import co.salutary.mobisaude.controller.TokenManager;
import co.salutary.mobisaude.controller.ServiceBroker;
import co.salutary.mobisaude.controller.ClientCache;
import co.salutary.mobisaude.db.CidadeDAO;
import co.salutary.mobisaude.db.LocalDataBase;
import co.salutary.mobisaude.db.UfDAO;
import co.salutary.mobisaude.model.Cidade;
import co.salutary.mobisaude.model.UF;
import co.salutary.mobisaude.util.DeviceInfo;
import co.salutary.mobisaude.util.JsonUtils;


public class LocalitySelectionActivity extends Activity implements Runnable, LocationListener {

    private static final String TAG = new Object() {
    }.getClass().getName();

    private static final int PROGRESS_BAR_VERIFICAR = 1;
    private static final int PROGRESS_BAR_DETERMINAR = 2;

    public static final int RESULTADO_LOCAL_SELECIONADO = 1;

    private static final int ACTIVITY_LIST_SELECT = 1;
    private static final long TIME_TEMP = 1000;

    private EditText edtUF;
    private EditText edtCidade;

    private ClientCache clientCache;
    private LocalDataBase db;
    private Location location;

    // thread loop
    private boolean timerInatividadeBool;
    private int timerInatividadeCount;
    private Handler timerInatividade;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_locality_selection);

        clientCache = ClientCache.getInstance();
        db = LocalDataBase.getInstance();
        location = DeviceInfo.updateLocation(getApplicationContext(),this);

        LinearLayout btnUF = (LinearLayout) findViewById(R.id.locality_selection_btn_uf);
        LinearLayout btnCidade = (LinearLayout) findViewById(R.id.locality_selection_btn_cidade);
        edtUF = (EditText) findViewById(R.id.locality_selection_edt_uf);
        edtCidade = (EditText) findViewById(R.id.locality_selection_edt_cidade);
        Button btnMeuLocal = (Button) findViewById(R.id.locality_selection_btn_meu_local);

        btnUF.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onShowListaSelect(LocalitySelectionListActivity.LISTA_UF);
            }
        });
        edtUF.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onShowListaSelect(LocalitySelectionListActivity.LISTA_UF);
            }
        });

        btnCidade.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onShowListaSelect(LocalitySelectionListActivity.LISTA_CIDADE);
            }
        });

        edtCidade.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onShowListaSelect(LocalitySelectionListActivity.LISTA_CIDADE);
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
        if(value == null || value.equals(getString(R.string.empty))){
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
                pDialog.setMessage(getString(R.string.check_gps));
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                break;
            case PROGRESS_BAR_DETERMINAR:
                pDialog = new ProgressDialog(this);
                pDialog.setMessage(getString(R.string.check_city));
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
        if(tipoLista == LocalitySelectionListActivity.LISTA_CIDADE){
            if(ClientCache.getInstance().getUf() != null){
                Intent it = new Intent(this, LocalitySelectionListActivity.class);
                it.putExtra("tipoLista", tipoLista);
                startActivityForResult(it, ACTIVITY_LIST_SELECT);
            }
        }
        else {
            Intent it = new Intent(this, LocalitySelectionListActivity.class);
            it.putExtra("tipoLista", tipoLista);
            startActivityForResult(it, ACTIVITY_LIST_SELECT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ACTIVITY_LIST_SELECT && resultCode == LocalitySelectionListActivity.RESULTADO_ITEM_SELECIONADO){
            atualizarCampos();
            if(ClientCache.getInstance().getCidade() != null){
                final String cidadeName = ClientCache.getInstance().getCidade().getNome();
                db.open(getApplicationContext());
                Cidade cidade = new CidadeDAO(db).getCidadeByNome(cidadeName);
                UF uf = new UfDAO(db).getUfById(cidade.getIdUF());
                db.close();
                clientCache.setCidade(cidade);
                clientCache.setUf(uf);
                fecharTela();
            }
        }
    }

    private void atualizarCampos(){
        UF uf = ClientCache.getInstance().getUf();
        if(uf != null){
            edtUF.setText(uf.getNome());
            edtCidade.setText(getString(R.string.empty));
            edtCidade.setHint(R.string.cidade);
        }

        Cidade cidade = ClientCache.getInstance().getCidade();
        if(cidade != null){
            edtCidade.setText(cidade.getNome());
            clientCache.setListEstabelecimentosSaude(null);
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
                Log.e(TAG, e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            try {
                dismissDialog(PROGRESS_BAR_VERIFICAR);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
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
                    Log.e(TAG, e.getMessage());
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
                            TokenManager.gerarToken(getApplicationContext());
                            token = localPref.getPreferenceValue(Settings.TOKEN);
                        }
                        JSONObject jDados = new JSONObject();
                        jDados.put("latitude",local.getLatitude());
                        jDados.put("longitude",local.getLongitude());
                        jDados.put("token",token);

                        JSONObject jRequest = new JSONObject();
                        jRequest.put("geocodeRequest", jDados);

                        String reponder = ServiceBroker.getInstance(getApplicationContext()).geocode(jRequest.toString());
                        if(reponder != null && !reponder.startsWith(getString(R.string.erro_starts))){
                            JSONObject jObject = new JSONObject(reponder);
                            JSONObject jReponder = (JSONObject) jObject.get("geocodeResponse");
                            int idErro = JsonUtils.getErrorCode(jReponder);
                            if(idErro == 6){
                                if(!TokenManager.gerarToken(getApplicationContext())){
                                    return false;
                                }
                            }
                            else if(idErro == 0) {
                                int codMunicipioIbge = jReponder.getInt("codMunicipioIbge");
                                db.open(getApplicationContext());
                                Cidade cidade = new CidadeDAO(db).getCidadeById(codMunicipioIbge);
                                if(cidade != null){
                                    UF uf = new UfDAO(db).getUfById(cidade.getIdUF());
                                    clientCache.setUf(uf);
                                    clientCache.setCidade(cidade);

                                    // reset
                                    db.close();
                                    clientCache.setListEstabelecimentosSaude(null);
                                    return true;
                                }
                                else {
                                    db.close();
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
                Log.e(TAG, e.getMessage());
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            try {
                Thread.sleep(TIME_TEMP);
                dismissDialog(PROGRESS_BAR_DETERMINAR);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
            try {
                if(result){
                    fecharTela();
                }
                else {
                    Toast.makeText(getApplicationContext(), getString(R.string.unknown_location), Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
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
                }
                else {
                    timerInatividadeCount++;
                    timerInatividade.postDelayed(this, TIME_TEMP);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }
}