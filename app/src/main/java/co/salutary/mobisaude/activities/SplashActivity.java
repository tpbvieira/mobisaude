package co.salutary.mobisaude.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

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
import co.salutary.mobisaude.util.ConnectivityManager;
import co.salutary.mobisaude.util.DeviceInfo;
import co.salutary.mobisaude.util.UtilJson;

public class SplashActivity extends Activity implements Runnable, LocationListener {

    private static final String TAG = SplashActivity.class.getSimpleName();

    private static final int ACTIVITY_DENIFIR_LOCALIDADE = 1;
    private static final long SLEEP_TIME = 1000;
    private final int PROGRESS_BAR = 1;

    private TextView txtLabel;

    // thread loop
    private boolean timerTelaBool;

    private UserController userController;

    private Location location;

    private boolean isSemConexao = false;

    private boolean isShowDialog = true;

    LocalDataBase localDataBase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // gui
        txtLabel = (TextView)findViewById(R.id.frm_splash_label);

        Settings.CONEXAO   = false;
        Settings.CONEXAO2G = false;
        Settings.CONEXAO3G = false;
        Settings.CONEXAO4G = false;
        Settings.DEVICE_LOCATED = false;
        Settings.hashNoERBs = new HashMap<String, Boolean>();

        DeviceInfo.setUpGCM(this);
        DeviceInfo.isDadosAtivos = false;

        localDataBase = LocalDataBase.getInstance();

        Settings.setPreferenceValue(this, Settings.VIEWPAGER_POS_PORTRAIT, 0);
        Settings.setPreferenceValue(this, Settings.VIEWPAGER_POS_LANDSCAPE, 0);

        userController = UserController.getInstance();

        // location
        location = DeviceInfo.updateLocation(getApplicationContext(), this);

        // Carregar timer
        Handler timerTela = new Handler();
        timerTela.postDelayed(this, SLEEP_TIME);
    }

    @Override
    protected void onStart() {
        super.onStart();
        timerTelaBool = true;
    }

    public void run() {
        try {
            if(timerTelaBool){
                onVerificarConectividade();
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ACTIVITY_DENIFIR_LOCALIDADE){
            if(resultCode == LocalityActivity.RESULTADO_LOCAL_SELECIONADO){
                if(!DeviceInfo.isDadosAtivos && ConnectivityManager.getInstance(getApplicationContext()).isConnected()){
                    isShowDialog = true;
                    new QueryAvailableViewsTask().execute(0);
                }
            }
            else {
                Toast.makeText(getApplicationContext(), getString(R.string.error_obtaining_locality), Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        timerTelaBool = false;
        DeviceInfo.removeUpdates(getApplicationContext(), this);
        super.onDestroy();
    }

    private void onVerificarConectividade() {
        setTextLabelInfor(getString(R.string.check_connectivity));
        new VerificarConectividade().execute(0);
    }

    private class VerificarConectividade extends AsyncTask<Integer, Integer, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Integer... params) {
            boolean isSucess = false;

            try {
                // Initiate Database
                localDataBase.open(getApplicationContext());

                // verify connection
                ConnectivityManager.getInstance(getApplicationContext()).requisitConexaoMobile();
                isSucess = ConnectivityManager.getInstance(getApplicationContext()).isConnected();

                // Generate token
                if(isSucess)
                    ManagerToken.gerarToken(getApplicationContext());

                return isSucess;
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
            return isSucess;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            try {
                if(result){
                    onVerificarGPS();
                }
                else {
                    erroSemConexao();
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    private void onVerificarGPS() {
        setTextLabelInfor(getString(R.string.check_gps));
        new VerificarGPS().execute(0);
    }

    private class VerificarGPS extends AsyncTask<Integer, Integer, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Integer... params) {
            try {
//                Thread.sleep(SLEEP_TIME);
                return DeviceInfo.isProviderEnabled();
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            try {
                if(result){
                    onDeterminarLocal();
                }
                else {
                    alertaGpsDesativado();
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    private void onCarregarDados() {
        setTextLabelInfor(getString(R.string.loading_data));
        new CarregarDados().execute(0);
    }

    private void openTelaLocalidade() {
        Intent localidadeIntent= new Intent(this, LocalityActivity.class);
        startActivityForResult(localidadeIntent, ACTIVITY_DENIFIR_LOCALIDADE);
    }

    private class CarregarDados extends AsyncTask<Integer, Integer, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Integer... params) {
            try {
//                Thread.sleep(SLEEP_TIME);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            try {
                onDeterminarLocal();
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    private void onDeterminarLocal(){
        if(DeviceInfo.isProviderEnabled() && !isSemConexao){
            setTextLabelInfor(getString(R.string.obtaining_location));
            new DeterminarLocal(getApplicationContext()).execute(15);
        }
        else {
            openTelaLocalidade();
        }
    }

    private class DeterminarLocal extends AsyncTask<Integer, Integer, Boolean> {

        private Context context;
        
        public DeterminarLocal(Context ctx){
            this.context = ctx;
        }
        
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Integer... params) {
            try {
                for (int i = 0; i < params[0]; i++) {
//                    Thread.sleep(SLEEP_TIME);

                    // Location by Rede
                    if(location == null){
                        location = DeviceInfo.getLastKnownLocation(context);
                    }

                    // verificar
                    if(location != null){
                        Settings localPref = new Settings(getApplicationContext());
                        String token = localPref.getPreferenceValue(Settings.TOKEN);
                        if(token == null || token.isEmpty()) {
                            ManagerToken.gerarToken(getApplicationContext());
                            token = localPref.getPreferenceValue(Settings.TOKEN);
                        }

                        JSONObject jDados = new JSONObject();
                        jDados.put("latitude", location.getLatitude());
                        jDados.put("longitude", location.getLongitude());
                        Settings.lat = location.getLatitude();
                        Settings.lon = location.getLongitude();
                        jDados.put("token", token);

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

                                Cidade cidade = new CidadeDAO(localDataBase).getCidadeById(codMunicipioIbge);
                                if(cidade != null){
                                    UF uf = new UfDAO(localDataBase).getUfById(cidade.getIdUF());
                                    userController.setUf(uf);
                                    userController.setCidade(cidade);
                                    Settings.ID_CIDADE = cidade.getIdCidade();
                                    Settings.ID_UF = uf.getIdUf();
                                    userController.atualizarCidadeSelecionado();

                                    // para a cidade local
                                    userController.setCidadeLocal(cidade);
                                    Settings.DEVICE_LOCATED = true;
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
                Log.e(TAG, e.getMessage());
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            try {
                if(result) {
                    new QueryAvailableViewsTask().execute(0);
                }
                else{
                    Settings localPref = new Settings(getApplicationContext());
                    String token = localPref.getPreferenceValue(Settings.TOKEN);
                    if(token == null || token.isEmpty()) {
                        erroConexaoServidor();
                    }
                    else {
                        erroDeterminarLocal();
                    }
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
    protected Dialog onCreateDialog(int id) {
        ProgressDialog pDialog;
        switch(id) {
            case PROGRESS_BAR:
                pDialog = new ProgressDialog(this);
                pDialog.setMessage(getString(R.string.searching));
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(false);
                break;
            default:
                pDialog = null;
        }
        return pDialog;
    }

    //lbadias: Query for available views
    private class QueryAvailableViewsTask extends AsyncTask<Integer, Integer, Boolean> {

        private short numAttempts = 2;

        @Override
        protected Boolean doInBackground(Integer... params) {

            Settings settings = new Settings(getApplicationContext());

            try {

                if(ConnectivityManager.getInstance(getApplicationContext()).isConnected()){

                    // obtain domain tables
                    for (int i = 0; i < numAttempts; i++) {

                        JSONObject consultaDominiosRequest = UtilJson.createRequest(getApplicationContext(), "consultaDominiosRequest");
                        String consultaDominiosResponse = ServiceRequester.getInstance(getApplicationContext()).consultaDataToReport(consultaDominiosRequest.toString());

//                        Log.d(TAG, consultaDominiosResponse);
                        if(consultaDominiosResponse != null && !consultaDominiosResponse.startsWith(getString(R.string.erro_starts))){

                            // get domain tables
                            JSONObject domains = (JSONObject) new JSONObject(consultaDominiosResponse).get("consultaDominiosResponse");
                            int idErro = UtilJson.getErrorCode(domains);

                            if(idErro == 6){
                                // renew the token and try one more time, according to numAttempts
                                if(!ManagerToken.gerarToken(getApplicationContext())){
                                    return false;
                                }
                                continue;
                            }
                            else if(idErro == 0) {

                                // save domains into settings
                                settings.setPreferenceValues(Settings.operadoras, domains.getJSONArray("operadoras").toString());
                                settings.setPreferenceValues(Settings.tiposAmbiente, domains.getJSONArray("tiposAmbiente").toString());
                                settings.setPreferenceValues(Settings.tiposProblema, domains.getJSONArray("tiposProblema").toString());
                                settings.setPreferenceValues(Settings.tiposServico, domains.getJSONArray("tiposServico").toString());
                                settings.setPreferenceValues(Settings.tiposSistemaOperacional, domains.getJSONArray("tiposSistemaOperacional").toString());

                                // save filters
                                if(settings.getPreferenceValues(Settings.FILTER_OPERADORAS).length() == 0) {

                                    String selectedPrestadoras = "";
                                    JSONArray prestadoras = new JSONArray(settings.getPreferenceValues(Settings.operadoras));
                                    for (int j = 0; j < prestadoras.length(); j++) {
                                        if(selectedPrestadoras.length() == 0) {
                                            selectedPrestadoras += prestadoras.getJSONObject(j).getInt("id");
                                        } else {
                                            selectedPrestadoras += "," + prestadoras.getJSONObject(j).getInt("id");
                                        }
                                    }
                                    settings.setPreferenceValues(Settings.FILTER_OPERADORAS, selectedPrestadoras);

                                    JSONArray ambientes = new JSONArray(settings.getPreferenceValues(Settings.tiposAmbiente));
                                    String selectedAmbientes = "";
                                    for (int j = 0; j < ambientes.length(); j++) {
                                        if(selectedAmbientes.length() == 0) {
                                            selectedAmbientes += ambientes.getJSONObject(j).getInt("id");
                                        } else {
                                            selectedAmbientes += "," + ambientes.getJSONObject(j).getInt("id");
                                        }
                                    }
                                    settings.setPreferenceValues(Settings.FILTER_AMBIENTES, selectedAmbientes);

                                    JSONArray problemas = new JSONArray(settings.getPreferenceValues(Settings.tiposProblema));
                                    String selectedProblemas = "";
                                    for (int j = 0; j < problemas.length(); j++) {
                                        if(selectedProblemas.length() == 0) {
                                            selectedProblemas += problemas.getJSONObject(j).getInt("id");
                                        } else {
                                            selectedProblemas += "," + problemas.getJSONObject(j).getInt("id");
                                        }
                                    }
                                    settings.setPreferenceValues(Settings.FILTER_PROBLEMAS, selectedProblemas);
                                }
                            } else {
                                return false;
                            }
                            break; //to avoid a new attempt, considering the current as successful
                        }
                        else {
                            return false;
                        }
                    }

                    // obtain views
                    for (int i = 0; i < numAttempts; i++) {

                        JSONObject consultaTelasRequest = UtilJson.createRequest(getApplicationContext(), "consultaTelasRequest");
                        String consultaTelasResponse = ServiceRequester.getInstance(getApplicationContext()).queryAvailableViews(consultaTelasRequest.toString());

//                        Log.d(TAG, consultaTelasResponse);
                        if(consultaTelasResponse != null && !consultaTelasResponse.startsWith(getString(R.string.erro_starts))){

                            JSONObject views = (JSONObject) new JSONObject(consultaTelasResponse).get("consultaTelasResponse");
                            int idErro = UtilJson.getErrorCode(views);

                            if(idErro == 6){
                                // renew the token and try one more time, according to numAttempts
                                if(!ManagerToken.gerarToken(getApplicationContext())){
                                    return false;
                                }
                            }else if(idErro == 0) {
                                try {
                                    Iterator<String> iterator = views.keys();
                                    HashMap<String, Boolean> availableViews = new HashMap<String, Boolean>();
                                    while(iterator.hasNext()) {
                                        String key = iterator.next();
                                        if(!key.equals("erro") && !key.equals("description")) {
                                            availableViews.put(key, views.getBoolean(key));
                                        }
                                    }
                                    localDataBase.setAvailableViews(availableViews);
                                    return true;
                                } catch (Exception e) {
                                    Log.e(TAG, e.getMessage());
                                    return false;
                                }
                            }
                            else {
                                return false;
                            }
                            break; //to avoid a new attempt, considering the current as successful
                        }
                        else {
                            localDataBase.setAvailableViews(new HashMap<String, Boolean>());
                            return true;
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
                if(isShowDialog){
                    dismissDialog(PROGRESS_BAR);
                }
                isShowDialog = true;
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
            try {
                if(result) {
                    Intent it = new Intent( SplashActivity.this, MainActivity.class );
                    it.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(it);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), getString(R.string.error), Toast.LENGTH_SHORT).show();
                    finish();
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    private void erroSemConexao() {
        try {
            AlertDialog.Builder alerta = new AlertDialog.Builder(this);
            alerta.setIcon(R.drawable.ic_launcher);
            alerta.setTitle(R.string.error);
            alerta.setMessage(R.string.error_no_connectivity);
            alerta.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    isSemConexao = true;
                    onVerificarGPS();
                }
            });
            alerta.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_conectivity_required), Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
            alerta.setOnCancelListener(new OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    finish();
                }
            });
            alerta.show();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void erroConexaoServidor() {
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setIcon(R.drawable.ic_launcher);
        alerta.setTitle(getString(R.string.error));
        alerta.setMessage(getString(R.string.error_connecting_server));
        alerta.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                erroDeterminarLocal();
            }
        });
        alerta.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                erroDeterminarLocal();
            }
        });
        alerta.show();
    }

    private void erroDeterminarLocal() {
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setIcon(R.drawable.ic_launcher);
        alerta.setTitle(getString(R.string.alert));
        alerta.setMessage(getString(R.string.unidentified_location));
        alerta.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                openTelaLocalidade();
            }
        });
        alerta.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), getString(R.string.error_location_required), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        alerta.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });
        alerta.show();
    }

    private void alertaGpsDesativado() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setIcon(R.drawable.ic_launcher);
        alert.setTitle(getString(R.string.alert));
        alert.setMessage(getString(R.string.gps_not_available));
        alert.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                onCarregarDados();
            }
        });
        alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), getString(R.string.error_location_required), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        alert.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });
        alert.show();
    }

    private void setTextLabelInfor(String texto){
        txtLabel.setText(texto);
    }

}