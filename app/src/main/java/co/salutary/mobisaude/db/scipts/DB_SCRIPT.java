package co.salutary.mobisaude.db.scipts;

public class DB_SCRIPT {

    private static final String TAG = DB_SCRIPT.class.getSimpleName();

	// Dados do Banco
	public static final String NOME_BANCO = "DB_ANATEL.db";
	public static final int VERSAO_BANCO = 140714;

	public static final String[] SCRIPT_DATABASE_CREATE = new String[] {
		ScriptUF.SCRIPT_CRIAR_TABELA,
		ScriptCidade.SCRIPT_CRIAR_TABELA,
		ScriptErb.SCRIPT_CRIAR_TABELA,
	};

	public static final String[] SCRIPT_DATABASE_DELETE = new String[] {
		ScriptUF.SCRIPT_DELETAR_TABELA,
		ScriptCidade.SCRIPT_DELETAR_TABELA,
		ScriptErb.SCRIPT_DELETAR_TABELA,
	};
}