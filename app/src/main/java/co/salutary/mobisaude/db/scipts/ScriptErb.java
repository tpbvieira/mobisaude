package co.salutary.mobisaude.db.scipts;
/**************************************
* Projeto Anatel
* 
* @author Digitrack
* 
* @param Email alexlova@gmail.com
* @param Data 10/06/2014
**************************************/
public class ScriptErb {

	public static final String NOME_TABELA = "erb";
	public static final String SCRIPT_DELETAR_TABELA = "DROP TABLE IF EXISTS erb;";
	public static final String SCRIPT_CRIAR_TABELA = "CREATE TABLE erb ( "
														+ "id INTEGER PRIMARY KEY AUTOINCREMENT, "
														+ "idCidade INTEGER, "
														+ "latitudeStel DOUBLE PRECISION, "
														+ "longitudeStel DOUBLE PRECISION, "
														+ "nomeFantasia TEXT, "
														+ "prestadora TEXT, "
														+ "tecnologia2g BOOLEAN, "
														+ "tecnologia3g BOOLEAN, "
														+ "tecnologia4g BOOLEAN "
													+ ");";
	
	public static final String[] colunas = new String[]{ScriptErb._ID,
														ScriptErb._ID_CIDADE,
														ScriptErb._LATITUDE_STEL,
														ScriptErb._LONGITUDE_STEL,
														ScriptErb._NOME_FANTASIA,
														ScriptErb._PRESTADORA,
														ScriptErb._TECNOLOGIA_2G,
														ScriptErb._TECNOLOGIA_3G,
														ScriptErb._TECNOLOGIA_4G,
														};
	
	
	public static final String _ID = "id";
	public static final String _ID_CIDADE = "idCidade";
	public static final String _LATITUDE_STEL = "latitudeStel";
	public static final String _LONGITUDE_STEL = "longitudeStel";
	public static final String _NOME_FANTASIA = "nomeFantasia";
	public static final String _PRESTADORA = "prestadora";
	public static final String _TECNOLOGIA_2G = "tecnologia2g";
	public static final String _TECNOLOGIA_3G = "tecnologia3g";
	public static final String _TECNOLOGIA_4G = "tecnologia4g";
}