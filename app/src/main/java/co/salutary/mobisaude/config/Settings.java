package co.salutary.mobisaude.config;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class Settings {

    public static final String servicesUrl = "http://gatewaysiec.anatel.gov.br/gatewayarcher/mobile";

    public static boolean IS_LOGGEDIN = false;

    public static boolean CONEXAO2G;
    public static boolean CONEXAO3G;
    public static boolean CONEXAO4G;
    public static boolean CONEXAO;
    public static boolean DEVICE_LOCATED;
    public static double lat;
    public static double lon;
    public static int ID_CIDADE = 0;
    public static int ID_UF = 0;
    public static HashMap<String, Boolean> hashNoERBs;
    public static boolean disableSensor = false;
    public static boolean GO_HOME;
    public static boolean REPORT_PROBLEM;

    public static final String DATE_FORMAT = "dd/MM/yyyy HH:mm";
    public static final String EMPTY = "";
	
	public static final String VIEWPAGER_POS_PORTRAIT  = "VIEWPAGER_POS_PORTRAIT";
	public static final String VIEWPAGER_POS_LANDSCAPE = "VIEWPAGER_POS_LANDSCAPE";
	public static final String HELP_TEXT = "HELP_TEXT";
	public static final String TOKEN = "TOKEN";
	public static final String DATA_DADOS = "DATA_DADOS";
	public static final String SHOW_SCREEN_TELA_1 = "SHOW_SCREEN_TELA_1";
    public static final String SHOW_SCREEN_TELA_2 = "SHOW_SCREEN_TELA_2";
    public static final String SHOW_SCREEN_TELA_3 = "SHOW_SCREEN_TELA_3";
    public static final String operadoras = "operadoras";
    public static final String tiposAmbiente = "tiposAmbiente";
    public static final String tiposProblema = "tiposProblema";
    public static final String tiposServico = "tiposServico";
    public static final String tiposSistemaOperacional = "tiposSistemaOperacional";
    
    public static final String FILTER_OPERADORAS = "filter_operadoras";
    public static final String FILTER_AMBIENTES = "filter_ambientes";
    public static final String FILTER_PROBLEMAS = "filter_problemas";
    
    public static final String onlyWifi = "onlyWifi";
    public static final String showDisclaimer = "showDisclaimer";
    
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