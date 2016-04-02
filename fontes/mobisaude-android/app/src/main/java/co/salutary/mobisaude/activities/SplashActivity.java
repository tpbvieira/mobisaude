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

import co.salutary.mobisaude.R;
import co.salutary.mobisaude.config.Settings;
import co.salutary.mobisaude.controller.TokenManager;
import co.salutary.mobisaude.controller.ServiceBroker;
import co.salutary.mobisaude.controller.UserController;
import co.salutary.mobisaude.db.CidadeDAO;
import co.salutary.mobisaude.db.LocalDataBase;
import co.salutary.mobisaude.db.UfDAO;
import co.salutary.mobisaude.model.Cidade;
import co.salutary.mobisaude.model.UF;
import co.salutary.mobisaude.util.ConnectivityUtils;
import co.salutary.mobisaude.util.DeviceInfo;
import co.salutary.mobisaude.util.JsonUtils;

public class SplashActivity extends Activity implements Runnable, LocationListener {

    private static final String TAG = new Object() {
    }.getClass().getName();

    private static final int ACTIVITY_DENIFIR_LOCALIDADE = 1;
    private static final long SLEEP_TIME = 1000;
    private final int PROGRESS_BAR = 1;

    private TextView txtLabel;

    private boolean timerTelaBool;

    private UserController userController;

    private Location location;

    private boolean isShowDialog = true;

    LocalDataBase localDataBase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(new Object() {
        }.getClass().getName(), new Object() {
        }.getClass().getEnclosingMethod().getName());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        txtLabel = (TextView) findViewById(R.id.frm_splash_label);

        DeviceInfo.setUpGCM(this);

        localDataBase = LocalDataBase.getInstance();
        userController = UserController.getInstance();
        location = DeviceInfo.updateLocation(getApplicationContext(), this);

        Settings.setPreferenceValue(this, Settings.VIEWPAGER_POS_PORTRAIT, 0);
        Settings.setPreferenceValue(this, Settings.VIEWPAGER_POS_LANDSCAPE, 0);
        Handler timerTela = new Handler();
        timerTela.postDelayed(this, SLEEP_TIME);
    }

    @Override
    protected void onStart() {
        Log.d(new Object() {
        }.getClass().getName(), new Object() {
        }.getClass().getEnclosingMethod().getName());
        super.onStart();
        timerTelaBool = true;
    }

    public void run() {
        Log.d(new Object() {
        }.getClass().getName(), new Object() {
        }.getClass().getEnclosingMethod().getName());
        try {
            if (timerTelaBool) {
                onVerifyConectivity();
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(new Object() {
        }.getClass().getName(), new Object() {
        }.getClass().getEnclosingMethod().getName());
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ACTIVITY_DENIFIR_LOCALIDADE) {
            if (resultCode == LocalitySelectionActivity.RESULTADO_LOCAL_SELECIONADO) {
                if (DeviceInfo.hasConnectivity(getApplicationContext())) {
                    isShowDialog = true;
                    new QueryAvailableViewsTask().execute(0);
                }
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.error_obtaining_locality), Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        Log.d(new Object() {
        }.getClass().getName(), new Object() {
        }.getClass().getEnclosingMethod().getName());
        timerTelaBool = false;
        DeviceInfo.removeUpdates(getApplicationContext(), this);
        super.onDestroy();
    }

    private void onVerifyConectivity() {
        Log.d(new Object() {
        }.getClass().getName(), new Object() {
        }.getClass().getEnclosingMethod().getName());
        setTextLabelInfor(getString(R.string.check_connectivity));
        new VerifyConectivity().execute(0);
    }

    private class VerifyConectivity extends AsyncTask<Integer, Integer, Boolean> {

        @Override
        protected void onPreExecute() {
            Log.d(new Object() {
            }.getClass().getName(), new Object() {
            }.getClass().getEnclosingMethod().getName());
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Integer... params) {
            Log.d(new Object() {
            }.getClass().getName(), new Object() {
            }.getClass().getEnclosingMethod().getName());
            boolean isSuccess = false;

            try {
                // Initiate Database
                localDataBase.open(getApplicationContext());

                // verify connection
                ConnectivityUtils.getInstance(getApplicationContext()).requisitConexaoMobile();
                isSuccess = DeviceInfo.hasConnectivity(getApplicationContext());

                // gera token e salva no preferences
                if (isSuccess) {
                    isSuccess = TokenManager.gerarToken(getApplicationContext());
                }

                return isSuccess;
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
            return isSuccess;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            Log.d(new Object() {
            }.getClass().getName(), new Object() {
            }.getClass().getEnclosingMethod().getName());
            try {
                if (result) {
                    onVerifyGPS();
                } else {
                    if (DeviceInfo.hasConnectivity(getApplicationContext()))
                        showServerConnectionError();
                    else
                        showConnectivityError();
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    private void onVerifyGPS() {
        Log.d(new Object() {
        }.getClass().getName(), new Object() {
        }.getClass().getEnclosingMethod().getName());
        setTextLabelInfor(getString(R.string.check_gps));
        new VerifyGPS().execute(0);
    }

    private class VerifyGPS extends AsyncTask<Integer, Integer, Boolean> {

        @Override
        protected void onPreExecute() {
            Log.d(new Object() {
            }.getClass().getName(), new Object() {
            }.getClass().getEnclosingMethod().getName());
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Integer... params) {
            Log.d(new Object() {
            }.getClass().getName(), new Object() {
            }.getClass().getEnclosingMethod().getName());
            try {
                Thread.sleep(SLEEP_TIME);
                return DeviceInfo.hasLocationProvider();
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            Log.d(new Object() {
            }.getClass().getName(), new Object() {
            }.getClass().getEnclosingMethod().getName());
            try {
                if (result) {
                    onDefineLocalFromGPS();
                } else {
                    disabledGPSAlert();
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    private void onLocationFromData() {
        Log.d(new Object() {
        }.getClass().getName(), new Object() {
        }.getClass().getEnclosingMethod().getName());
        setTextLabelInfor(getString(R.string.loading_data));
        new LocationFromData().execute(0);
    }

    private void locateFromList() {
        Log.d(new Object() {
        }.getClass().getName(), new Object() {
        }.getClass().getEnclosingMethod().getName());
        Intent localidadeIntent = new Intent(this, LocalitySelectionActivity.class);
        startActivityForResult(localidadeIntent, ACTIVITY_DENIFIR_LOCALIDADE);
    }

    private class LocationFromData extends AsyncTask<Integer, Integer, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Integer... params) {
            try {
                Thread.sleep(SLEEP_TIME);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            try {
                onDefineLocalFromGPS();
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    private void onDefineLocalFromGPS() {
        Log.d(new Object() {
        }.getClass().getName(), new Object() {
        }.getClass().getEnclosingMethod().getName());
        if (DeviceInfo.hasLocationProvider() && DeviceInfo.hasConnectivity(getApplicationContext())) {
            setTextLabelInfor(getString(R.string.obtaining_location_from_gps));
            new DefineLocalFromGPS(getApplicationContext()).execute(15);
        } else {
            locateFromList();
        }
    }

    private class DefineLocalFromGPS extends AsyncTask<Integer, Integer, Boolean> {

        private Context context;

        public DefineLocalFromGPS(Context ctx) {
            this.context = ctx;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Integer... params) {
            Log.d(new Object() {
            }.getClass().getName(), new Object() {
            }.getClass().getEnclosingMethod().getName());
            try {
                for (int i = 0; i < params[0]; i++) {
                    Thread.sleep(SLEEP_TIME);

                    // Location by Rede
                    if (location == null) {
                        location = DeviceInfo.getLastKnownLocation(context);
                    }

                    // verificar
                    if (location != null) {
                        Settings localPref = new Settings(getApplicationContext());
                        String token = localPref.getPreferenceValue(Settings.TOKEN);
                        if (token == null || token.isEmpty()) {
                            TokenManager.gerarToken(getApplicationContext());//gera token e salva no preferences
                            token = localPref.getPreferenceValue(Settings.TOKEN);
                        }

                        JSONObject jDados = new JSONObject();
                        jDados.put("latitude", location.getLatitude());
                        jDados.put("longitude", location.getLongitude());
                        DeviceInfo.lastLatitude = location.getLatitude();
                        DeviceInfo.lastLongitude = location.getLongitude();
                        jDados.put("token", token);

                        JSONObject jRequest = new JSONObject();
                        jRequest.put("geocodeRequest", jDados);

                        String reponder = ServiceBroker.getInstance(getApplicationContext()).geocode(jRequest.toString());
                        if (reponder != null && !reponder.startsWith(getString(R.string.erro_starts))) {
                            JSONObject jObject = new JSONObject(reponder);
                            JSONObject jReponder = (JSONObject) jObject.get("geocodeResponse");
                            int idErro = JsonUtils.getErrorCode(jReponder);
                            if (idErro == 6) {
                                // gerar novo token
                                if (!TokenManager.gerarToken(getApplicationContext())) {
                                    return false;
                                }
                            } else if (idErro == 0) {
                                int codMunicipioIbge = jReponder.getInt("codMunicipioIbge");

                                Cidade cidade = new CidadeDAO(localDataBase).getCidadeById(codMunicipioIbge);
                                if (cidade != null) {
                                    UF uf = new UfDAO(localDataBase).getUfById(cidade.getIdUF());
                                    userController.setUf(uf);
                                    userController.setCidade(cidade);
                                    DeviceInfo.idCidade = cidade.getIdCidade();
                                    DeviceInfo.idUF = uf.getIdUf();
                                    userController.atualizarCidadeSelecionado();

                                    // para a cidade local
                                    userController.setCidadeLocal(cidade);
                                    return true;
                                } else {
                                    return false;
                                }
                            } else {
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
            Log.d(new Object() {
            }.getClass().getName(), new Object() {
            }.getClass().getEnclosingMethod().getName());
            try {
                if (result) {
                    new QueryAvailableViewsTask().execute(0);
                } else {
                    Settings localPref = new Settings(getApplicationContext());
                    String token = localPref.getPreferenceValue(Settings.TOKEN);
                    if (token == null || token.isEmpty()) {
                        showServerConnectionError();
                    } else {
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
    public void onProviderDisabled(String provider) { }

    @Override
    public void onProviderEnabled(String provider) { }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) { }

    @Override
    protected Dialog onCreateDialog(int id) {
        Log.d(new Object() {
        }.getClass().getName(), new Object() {
        }.getClass().getEnclosingMethod().getName());
        ProgressDialog pDialog;
        switch (id) {
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

        @Override
        protected Boolean doInBackground(Integer... params) {
            Log.d(new Object() {
            }.getClass().getName(), new Object() {
            }.getClass().getEnclosingMethod().getName());

            Settings settings = new Settings(getApplicationContext());

            try {

                if (DeviceInfo.hasConnectivity(getApplicationContext())) {

                    // obtain domain tables
                    JSONObject consultaDominiosRequest = JsonUtils.createRequest(getApplicationContext(), "consultaDominiosRequest");
                    String consultaDominiosResponse = ServiceBroker.getInstance(getApplicationContext()).consultaDominios(consultaDominiosRequest.toString());

                    if (consultaDominiosResponse != null && !consultaDominiosResponse.startsWith(getString(R.string.erro_starts))) {

                        // get domain tables
                        JSONObject domains = (JSONObject) new JSONObject(consultaDominiosResponse).get("consultaDominiosResponse");
                        int idErro = JsonUtils.getErrorCode(domains);
                        if (idErro == 6) {
                            // renew the token and try one more time, according to numAttempts
                            if (!TokenManager.gerarToken(getApplicationContext())) {
                                return false;
                            }
                        } else if (idErro == 0) {

                            // save domains into settings
                            settings.setPreferenceValues(Settings.regiao, domains.getJSONArray("regiao").toString());
                            settings.setPreferenceValues(Settings.tiposSistemaOperacional, domains.getJSONArray("tiposSistemaOperacional").toString());
                            settings.setPreferenceValues(Settings.tiposEstabelecimentoSaude, domains.getJSONArray("tiposEstabelecimentoSaude").toString());
                            settings.setPreferenceValues(Settings.tipoGestao, domains.getJSONArray("tiposGestao").toString());

                            // save filters into a list of elements
                            if (settings.getPreferenceValues(Settings.FILTER_TIPO_ESTABELECIMENTO_SAUDE).length() == 0) {

                                // região
                                String regioesListStr = "";
                                JSONArray regioesJson = new JSONArray(settings.getPreferenceValues(Settings.regiao));
                                for (int j = 0; j < regioesJson.length(); j++) {
                                    if (regioesListStr.length() == 0) {
                                        regioesListStr += regioesJson.getJSONObject(j).getInt("id");
                                    } else {
                                        regioesListStr += "," + regioesJson.getJSONObject(j).getInt("id");
                                    }
                                }
                                settings.setPreferenceValues(Settings.FILTER_REGIAO, regioesListStr);

                                // tipo de gestão
                                String tiposGestaoListStr = "";
                                JSONArray tiposGestao = new JSONArray(settings.getPreferenceValues(Settings.tipoGestao));
                                for (int j = 0; j < tiposGestao.length(); j++) {
                                    if (tiposGestaoListStr.length() == 0) {
                                        tiposGestaoListStr += tiposGestao.getJSONObject(j).getInt("id");
                                    } else {
                                        tiposGestaoListStr += "," + tiposGestao.getJSONObject(j).getInt("id");
                                    }
                                }
                                settings.setPreferenceValues(Settings.FILTER_TIPO_GESTAO, tiposGestaoListStr);

                                // tipo de estabelecimento de saúde
                                String tiposESStr = "";
                                JSONArray tiposEs = new JSONArray(settings.getPreferenceValues(Settings.tiposEstabelecimentoSaude));
                                for (int j = 0; j < tiposEs.length(); j++) {
                                    if (tiposESStr.length() == 0) {
                                        tiposESStr += tiposEs.getJSONObject(j).getInt("id");
                                    } else {
                                        tiposESStr += "," + tiposEs.getJSONObject(j).getInt("id");
                                    }
                                }
                                settings.setPreferenceValues(Settings.FILTER_TIPO_ESTABELECIMENTO_SAUDE, tiposESStr);
                            }

                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }

//                    obtain views
//                    JSONObject consultaTelasRequest = JsonUtils.createRequest(getApplicationContext(), "consultaTelasRequest");
//                    String consultaTelasResponse = ServiceBroker.getInstance(getApplicationContext()).queryAvailableViews(consultaTelasRequest.toString());
//
//                    if (consultaTelasResponse != null && !consultaTelasResponse.startsWith(getString(R.string.erro_starts))) {
//
//                        JSONObject views = (JSONObject) new JSONObject(consultaTelasResponse).get("consultaTelasResponse");
//                        int idErro = JsonUtils.getErrorCode(views);
//
//                        if (idErro == 6) {
//                            // renew the token and try one more time, according to numAttempts
//                            if (!TokenManager.gerarToken(getApplicationContext())) {
//                                return false;
//                            }
//                        } else if (idErro == 0) {
//                            try {
//                                Iterator<String> iterator = views.keys();
//                                HashMap<String, Boolean> availableViews = new HashMap<String, Boolean>();
//                                while (iterator.hasNext()) {
//                                    String key = iterator.next();
//                                    if (!key.equals("erro") && !key.equals("description")) {
//                                        availableViews.put(key, views.getBoolean(key));
//                                    }
//                                }
//                                localDataBase.setAvailableViews(availableViews);
//                                return true;
//                            } catch (Exception e) {
//                                Log.e(TAG, e.getMessage());
//                                return false;
//                            }
//                        } else {
//                            return false;
//                        }
//                    } else {
//                        localDataBase.setAvailableViews(new HashMap<String, Boolean>());
//                        return true;
//                    }
                } else {
                    // no connectivity or server connection, but still works offline
                    return true;
                }

            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            Log.d(new Object() {
            }.getClass().getName(), new Object() {
            }.getClass().getEnclosingMethod().getName());
            try {
                if (isShowDialog) {
                    dismissDialog(PROGRESS_BAR);
                }
                isShowDialog = true;
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
            try {
                if (result) {
                    Intent it = new Intent(SplashActivity.this, MainActivity.class);
                    it.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(it);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.error), Toast.LENGTH_LONG).show();
                    finish();
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    private void showConnectivityError() {
        Log.d(new Object() {
        }.getClass().getName(), new Object() {
        }.getClass().getEnclosingMethod().getName());
        try {
            AlertDialog.Builder alerta = new AlertDialog.Builder(this);
            alerta.setIcon(R.mipmap.ic_launcher);
            alerta.setTitle(R.string.error);
            alerta.setMessage(R.string.error_no_connectivity);
            alerta.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    onVerifyGPS();
                }
            });
            alerta.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_conectivity_required), Toast.LENGTH_LONG).show();
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

    private void showServerConnectionError() {
        Log.d(new Object() {
        }.getClass().getName(), new Object() {
        }.getClass().getEnclosingMethod().getName());
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setIcon(R.mipmap.ic_launcher);
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
        Log.d(new Object() {
        }.getClass().getName(), new Object() {
        }.getClass().getEnclosingMethod().getName());
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setIcon(R.mipmap.ic_launcher);
        alerta.setTitle(getString(R.string.alert));
        alerta.setMessage(getString(R.string.unidentified_location));
        alerta.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                locateFromList();
            }
        });
        alerta.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), getString(R.string.error_location_required), Toast.LENGTH_LONG).show();
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

    private void disabledGPSAlert() {
        Log.d(new Object() {
        }.getClass().getName(), new Object() {
        }.getClass().getEnclosingMethod().getName());
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setIcon(R.mipmap.ic_launcher);
        alert.setTitle(getString(R.string.alert));
        alert.setMessage(getString(R.string.gps_not_available));
        alert.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                onLocationFromData();
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

    private void setTextLabelInfor(String texto) {
        txtLabel.setText(texto);
    }

}