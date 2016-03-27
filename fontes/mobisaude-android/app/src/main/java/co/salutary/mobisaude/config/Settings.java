package co.salutary.mobisaude.config;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class Settings {

    private static final String TAG = new Object(){}.getClass().getName();

//    public static final String serverUrl = "http://gatewaysiec.anatel.gov.br/gatewayarcher/mobile";
    public static final String serverUrl = "http://192.168.1.38:8080/mobisaude-services/mobile";

	public static final String VIEWPAGER_POS_PORTRAIT  = "VIEWPAGER_POS_PORTRAIT";
	public static final String VIEWPAGER_POS_LANDSCAPE = "VIEWPAGER_POS_LANDSCAPE";
	public static final String SHOW_SCREEN_TELA_2 = "SHOW_SCREEN_TELA_2";

    // domain tables
    public static final String regiao = "regiao";
    public static final String tipoGestao = "tipoGestao";
    public static final String tiposSistemaOperacional = "tiposSistemaOperacional";
    public static final String tiposEstabelecimentoSaude = "tiposEstabelecimentoSaude";

    // filters
    public static final String FILTER_REGIAO = "filter_regiao";
    public static final String FILTER_TIPO_GESTAO = "filter_tipo_gestao";
    public static final String FILTER_TIPO_ESTABELECIMENTO_SAUDE = "filter_tipo_estabelecimento_saude";

    public static final String DATE_FORMAT = "dd/MM/yyyy HH:mm";
    public static final String EMPTY = "";

    // session
    public static final String TOKEN = "token";
    public static final String USER_EMAIL = "userEmail";
    public static final String USER_NAME = "userName";

    // ??
    public static final String REPORT_PROBLEMS = "REPORT_PROBLEMS";

    private Context context;
    
    public Settings(Context context) {
		super();
		this.context = context;
	}
    
    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
    }
    
    private static SharedPreferences getSharedPreferencesReport(Context context) {
        return context.getSharedPreferences("ReportPreferences", Context.MODE_PRIVATE);
    }
    
    public void setPreferenceValue(String key, String value) {
        final SharedPreferences prefs = getSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }
    
	public String getPreferenceValue(String key) {
        final SharedPreferences prefs = getSharedPreferences(context);
        String registrationId = prefs.getString(key, "");
        return registrationId;
    }
	
	public void setPreferenceValues(String key, String value) {
        final SharedPreferences prefs = getSharedPreferencesReport(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }
    
	public String getPreferenceValues(String key) {
        final SharedPreferences prefs = getSharedPreferencesReport(context);
        String registrationId = prefs.getString(key, "");
        return registrationId;
    }

	
	public static void setPreferenceValue(Context ctx, String key, int value) {
        SharedPreferences prefs = getSharedPreferences(ctx);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, value);
        editor.commit();
    }
    
	public static int getPreferenceValueInt(Context ctx, String key) {
        final SharedPreferences prefs = getSharedPreferences(ctx);
        int value = prefs.getInt(key, 0);
        return value;
    }
}