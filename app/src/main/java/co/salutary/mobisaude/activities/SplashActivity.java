package co.salutary.mobisaude.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

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

// substituir por fabric.io

public class SplashActivity extends Activity implements Runnable, LocationListener {

    private static final String TAG = SplashActivity.class.getSimpleName();

    private static final int ACTIVITY_DENIFIR_LOCALIDADE = 1;
    private static final long TIME_TEMP = 1000;

    private TextView txtLabel;
    private WebView telaGifLoader;

    // thread loop
    private boolean timerTelaBool;
    private Handler timerTela;

    private UserController userController;

    // gps
    private Location location;

    private boolean isSemConexao = false;

    int size = 0;

    private final int PROGRESS_BAR = 1;
    private boolean isShowDialog = true;

    LocalDataBase LocalDataBase;

    private SimpleDateFormat df = new SimpleDateFormat(getString(R.string.date_format), Locale.getDefault());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_splash);

        // gui
        txtLabel = (TextView)findViewById(R.id.frm_splash_label);
        telaGifLoader = (WebView)findViewById(R.id.frm_splash_view_gif);

        // Carregar gif
        try {
            telaGifLoader.loadUrl(getString(R.string.gif_loader));
            BitmapDrawable gif = (BitmapDrawable) this.getResources().getDrawable(R.drawable.anim_loader);
            ViewGroup.LayoutParams params = telaGifLoader.getLayoutParams();
            params.height = gif.getBitmap().getHeight();
            params.width = gif.getBitmap().getWidth();
            telaGifLoader.setBackgroundResource(R.color.splash);
            telaGifLoader.setLayoutParams(params);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        Settings.CONEXAO   = false;
        Settings.CONEXAO2G = false;
        Settings.CONEXAO3G = false;
        Settings.CONEXAO4G = false;
        Settings.DEVICE_LOCATED = false;
        Settings.hashNoERBs = new HashMap<String, Boolean>();

        DeviceInfo.setUpGCM(this);
        DeviceInfo.isDadosAtivos = false;

        LocalDataBase = LocalDataBase.getInstance();

        Settings.setPreferenceValue(this, Settings.VIEWPAGER_POS_PORTRAIT, 0);
        Settings.setPreferenceValue(this, Settings.VIEWPAGER_POS_LANDSCAPE, 0);

        userController = UserController.getInstance();

        // location
        location = DeviceInfo.updateLocation(getApplicationContext(), this);

        // Carregar timer
        timerTela = new Handler();
        timerTela.postDelayed(this, TIME_TEMP);
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
            if(resultCode == LocalidadeActivity.RESULTADO_LOCAL_SELECIONADO){
                openMenuGeral();
            }
            else {
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
        showGifLoader();
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
                LocalDataBase.getInstance().open(getApplicationContext());

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
                Thread.sleep(TIME_TEMP);
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

    private void onCarredarDados() {
        setTextLabelInfor("Carregando Dados...");
        new CarredarDados().execute(0);
    }

    private void onDeterminarLocal(){
        if(DeviceInfo.isProviderEnabled() && !isSemConexao){
            setTextLabelInfor("Determinando seu município.");
            new DeterminarLocal(getApplicationContext()).execute(15);
        }
        else {
            openTelaLocalidade();
        }
    }

    private void openMenuGeral() {
        if(!DeviceInfo.isDadosAtivos && ConnectivityManager.getInstance(getApplicationContext()).isConnected()){
            isShowDialog = true;
        }
    }

    private void openTelaLocalidade() {
        Intent localidadeIntent= new Intent(this, LocalidadeActivity.class);
        startActivityForResult(localidadeIntent, ACTIVITY_DENIFIR_LOCALIDADE);
    }

    private class CarredarDados extends AsyncTask<Integer, Integer, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
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
                onDeterminarLocal();
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
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
                    Thread.sleep(TIME_TEMP);

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
                        //jDados.put("latitude", -15.7217621);
                        //jDados.put("longitude", -47.9373578);
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
                                if(ManagerToken.gerarToken(getApplicationContext())){
                                    continue;
                                }
                                else {
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
                if(result){
                    openMenuGeral();
                }
                else {
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
                pDialog.setMessage("Procurando");
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

        private String msgErro = "Verifique sua conexão ou selecione outra rede disponível para acessar os dados.";

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

                        JSONObject jDados = new JSONObject();
                        jDados.put("token", token);

                        JSONObject jRequest = new JSONObject();
                        jRequest.put("consultaDominiosRequest", jDados);

                        String reponder = ServiceRequester.getInstance(getApplicationContext()).consultaDataToReport(jRequest.toString());
                        Log.d(TAG, reponder);
                        if(reponder != null && !reponder.startsWith(getString(R.string.erro_starts))){

                            // salvar registros
                            JSONObject jObject = new JSONObject(reponder);
                            JSONObject jReponder = (JSONObject) jObject.get("consultaDominiosResponse");
                            String erro = jReponder.getString("erro");
                            String[] splitResult = erro.split("\\|");
                            int idErro = Integer.parseInt(splitResult[0]);
                            if(idErro == 6){
                                // gerar novo token
                                if(ManagerToken.gerarToken(getApplicationContext())){
                                    continue;
                                }
                                else {
                                    return false;
                                }
                            }
                            else if(idErro == 0) {
                                JSONArray prestadoras = jReponder.getJSONArray("operadoras");
                                localPref.setPreferenceValueR(Settings.operadoras, prestadoras.toString());

                                JSONArray ambientes = jReponder.getJSONArray("tiposAmbiente");
                                localPref.setPreferenceValueR(Settings.tiposAmbiente, ambientes.toString());

                                JSONArray problemas = jReponder.getJSONArray("tiposProblema");
                                localPref.setPreferenceValueR(Settings.tiposProblema, problemas.toString());

                                localPref.setPreferenceValueR(Settings.tiposServico, jReponder.getJSONArray("tiposServico").toString());
                                localPref.setPreferenceValueR(Settings.tiposSistemaOperacional, jReponder.getJSONArray("tiposSistemaOperacional").toString());

                                //Filtros por default, mostrar todo

                                if(localPref.getPreferenceValueR(Settings.FILTER_OPERADORAS).length() == 0) {
                                    String selectedPrestadoras = "";
                                    for (int j = 0; j < prestadoras.length(); j++) {
                                        if(selectedPrestadoras.length() == 0) {
                                            selectedPrestadoras += prestadoras.getJSONObject(j).getInt("id");
                                        } else {
                                            selectedPrestadoras += "," + prestadoras.getJSONObject(j).getInt("id");
                                        }
                                    }
                                    localPref.setPreferenceValueR(Settings.FILTER_OPERADORAS, selectedPrestadoras.toString());

                                    String selectedAmbientes = "";
                                    for (int j = 0; j < ambientes.length(); j++) {
                                        if(selectedAmbientes.length() == 0) {
                                            selectedAmbientes += ambientes.getJSONObject(j).getInt("id");
                                        } else {
                                            selectedAmbientes += "," + ambientes.getJSONObject(j).getInt("id");
                                        }
                                    }
                                    localPref.setPreferenceValueR(Settings.FILTER_AMBIENTES, selectedAmbientes.toString());

                                    String selectedProblemas = "";
                                    for (int j = 0; j < problemas.length(); j++) {
                                        if(selectedProblemas.length() == 0) {
                                            selectedProblemas += problemas.getJSONObject(j).getInt("id");
                                        } else {
                                            selectedProblemas += "," + problemas.getJSONObject(j).getInt("id");
                                        }
                                        //selectedProblemas.put(problemas.getJSONObject(i).getInt("id"));
                                    }
                                    localPref.setPreferenceValueR(Settings.FILTER_PROBLEMAS, selectedProblemas.toString());
                                }
                            }
                            else {
                                //msgErro = splitResult[1];
                                return false;
                            }
                        }
                        else {
                            //msgErro = "Verifique sua conexão ou selecione outra rede disponível para acessar os dados.";
                            return false;
                        }
                    }

                    // laco para repeti caso precise gera novo token
                    for (int i = 0; i <= 1; i++) {
                        Settings localPref = new Settings(getApplicationContext());
                        String token = localPref.getPreferenceValue(Settings.TOKEN);
                        if(token == null || token.isEmpty()) {
                            ManagerToken.gerarToken(getApplicationContext());
                            token = localPref.getPreferenceValue(Settings.TOKEN);
                        }
                        JSONObject jDados = new JSONObject();
                        jDados.put("token",token);

                        JSONObject jRequest = new JSONObject();
                        jRequest.put("consultaTelasRequest", jDados);

                        String reponder = ServiceRequester.getInstance(getApplicationContext()).queryAvailableViews(jRequest.toString());
                        if(reponder != null && !reponder.startsWith(getString(R.string.erro_starts))){
                            // deletar registros antigos
                            //LocalDataBase.deletarHistoricoByMunicipio(userController.getCidade().getIdCidade(), Historico.TIPO_VOZ);

                            // salvar registros
                            JSONObject jObject = new JSONObject(reponder);
                            JSONObject jReponder = (JSONObject) jObject.get("consultaTelasResponse");
                            String erro = jReponder.getString("erro");
                            String[] splitResult = erro.split("\\|");
                            int idErro = Integer.parseInt(splitResult[0]);
                            if(idErro == 6){
                                // gerar novo token
                                if(ManagerToken.gerarToken(getApplicationContext())){
                                    continue;
                                }
                                else {
                                    return false;
                                }
                            }
                            else if(idErro == 0) {
                                try {
                                    Iterator<String> iterator = jReponder.keys();
                                    HashMap<String, Boolean> availableViews = new HashMap<String, Boolean>();
                                    while(iterator.hasNext()) {
                                        String key = iterator.next();
                                        if(!key.equals("erro") && !key.equals("description")) {
                                            availableViews.put(key, jReponder.getBoolean(key));
                                        }
                                    }
                                    LocalDataBase.setAvailableViews(availableViews);
                                    return true;
                                } catch (Exception e) {
                                    Log.e(TAG, e.getMessage());
                                    return false;
                                }
                            }
                            else {
                                msgErro = splitResult[1];
                                return false;
                            }
                        }
                        else {
                            LocalDataBase.setAvailableViews(new HashMap<String, Boolean>());
                            return true;
                            //msgErro = "Verifique sua conexão ou selecione outra rede disponível para acessar os dados.";
                            //return false;
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
                    Toast.makeText(getApplicationContext(), "ConsultaHistoricoVoz " + msgErro, Toast.LENGTH_SHORT).show();
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
            alerta.setMessage(R.string.error_no_connection);
            alerta.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    isSemConexao = true;
                    onVerificarGPS();
                }
            });
            alerta.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
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
        alerta.setTitle("Alerta");
        alerta.setMessage("Infelizmente não foi possível acessar o servidor da Anatel.");
        alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
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
        alerta.setTitle("Alerta");
        alerta.setMessage("Não foi possível determinar a sua localização, deseja escolher o município manualmente?");
        alerta.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                openTelaLocalidade();
            }
        });
        alerta.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
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
        alert.setTitle("Alerta");
        alert.setMessage("Seu GPS esta desativado, deseja escolher o município manualmente?");
        alert.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                onCarredarDados();
            }
        });
        alert.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
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

    private void showGifLoader(){
        telaGifLoader.setVisibility(View.VISIBLE);
    }

}